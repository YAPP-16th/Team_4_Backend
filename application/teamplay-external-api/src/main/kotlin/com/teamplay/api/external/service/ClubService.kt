package com.teamplay.api.com.teamplay.api.external.service

import com.teamplay.api.com.teamplay.api.external.request.CreateClubRequest
import com.teamplay.api.com.teamplay.api.external.request.GetClubsRequest
import com.teamplay.api.com.teamplay.api.external.request.JoinClubRequest
import com.teamplay.api.com.teamplay.api.external.response.ClubResponse
import com.teamplay.api.com.teamplay.api.external.response.ClubsResponse
import com.teamplay.api.com.teamplay.api.external.response.ClubJoinInfoResponse
import com.teamplay.api.com.teamplay.api.external.response.CreateClubResponse
import com.teamplay.domain.business.club.dto.*
import com.teamplay.domain.business.club.error.ClubIsNotExistError
import com.teamplay.domain.business.club.function.*
import com.teamplay.domain.business.club.validator.CheckAlreadyRegisteredClub
import com.teamplay.domain.business.club.validator.CheckDuplicateClubName
import com.teamplay.domain.business.club.validator.CheckExistClub
import com.teamplay.domain.business.user.dto.UserInfo
import com.teamplay.domain.business.user.function.FindUserById
import com.teamplay.domain.database.club.entity.Club
import com.teamplay.domain.database.club.entity.ClubAdmin
import com.teamplay.domain.database.club.entity.ClubCharacter
import com.teamplay.domain.database.club.entity.ClubMember
import com.teamplay.domain.database.jpa.club.repository.ClubAdminRepository
import com.teamplay.domain.database.jpa.club.repository.ClubMemberRepository
import com.teamplay.domain.database.jpa.club.repository.ClubRepository
import com.teamplay.domain.database.jpa.user.repository.UserRepository
import com.teamplay.domain.database.user.entity.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class ClubService @Autowired constructor(
    clubRepository: ClubRepository,
    clubAdminRepository: ClubAdminRepository,
    clubMemberRepository: ClubMemberRepository,
    userRepository: UserRepository
){
    private val findClubById = FindClubById(clubRepository)
    private val createClub = CreateClub(clubRepository)
    private val registerAdmin = RegisterAdmin(clubAdminRepository)
    private val registerMember = RegisterMember(clubMemberRepository)
    private val findClubsByName = FindClubsByName(clubRepository)
    private val findClubMembersByClubId = FindClubMembersByClubId(clubMemberRepository)
    private val findClubAdminsByClubId = FindClubAdminsByClubId(clubAdminRepository)
    private val findClubsByAddress = FindClubsByAddress(clubRepository)
    private val findClubsByCharacters = FindClubsByCharacters(clubRepository)
    private val findUserById = FindUserById(userRepository)

    private val checkDuplicateClubName = CheckDuplicateClubName(clubRepository)
    private val checkExistClub = CheckExistClub(clubRepository)
    private val checkAlreadyRegisterMember = CheckAlreadyRegisteredClub(clubMemberRepository)

    fun joinClub(joinClubRequest: JoinClubRequest): ClubResponse{

        val user = findUserById(joinClubRequest.userId)
        val club = findClubById(joinClubRequest.clubId)
        val clubMember = registerMemberClub(user, club)

        return findClubAndFeed(clubMember.club.id!!);
    }

    fun registerClub(createClubRequest: CreateClubRequest, user: User): CreateClubResponse{
        checkDuplicateClubName.verify(createClubRequest.name)
        var club = createClub(requestToClub(createClubRequest))
        club.admin.add(registerAdminInClub(user, club))
        club.members.add(registerMemberClub(user,club))
        return CreateClubResponse(
            ClubInfo(club),
            clubAdminToUserInfo(club.admin),
            clubMemberToUserInfo(club.members)
        )
    }

    fun registerAdminInClub(user:User, club: Club): ClubAdmin{
        return registerAdmin(ClubAdmin(null, club, user))
    }

    fun registerMemberClub(user: User, club: Club): ClubMember {
        checkAlreadyRegisterMember.verify(ClubIdAndUserId(club.id!!, user.id!!));
        return registerMember(ClubMember(null, club, user))
    }

    // ToDo : 현재 임시 데이터 반환중임, 피드정보 모두 추후 수정 해야 함
    fun findClubAndFeed(clubId: Long): ClubResponse{
        checkExistClub.verify(clubId)
        val members = clubMemberToUserInfo(findClubMembers(clubId))
        val admins = clubAdminToUserInfo(findClubAdmins(clubId))
        val club = findClubById(clubId)

        // 가데이터 생성
        val noticeItem1 = SimpleNoticeInfo(club.name+" 모집 공고", club.createTeamDate, "저희는 소중한 팀플레이어를 구합니다.")
        val resultItem = SimpleResultInfo("테스트팀",club.createTeamDate, true)
        val resultItem2 = SimpleResultInfo("테스트팀",club.createTeamDate, false)
        val simpleFeeds = mutableListOf<SimpleFeeds>()
        simpleFeeds.add(SimpleFeeds(0,null,noticeItem1))
        simpleFeeds.add(SimpleFeeds(1,resultItem,null))
        simpleFeeds.add(SimpleFeeds(1,resultItem2,null))

        return ClubResponse(
            SimpleClubInfo(
                characters = club.characters,
                name = club.name,
                location = club.location,
                createDate = club.createTeamDate,
                memberCount = members.size
            ),
            feedCount = 3,
            simpleFeeds = simpleFeeds
        )
    }

    fun findClubJoinInfo(clubId: Long): ClubJoinInfoResponse{
        checkExistClub.verify(clubId)
        val members = clubMemberToUserInfo(findClubMembers(clubId))
        val club = findClubById(clubId)

        return ClubJoinInfoResponse(
            SimpleClubInfo(
                characters = club.characters,
                name = club.name,
                location = club.location,
                createDate = club.createTeamDate,
                memberCount = members.size
            ),
            club.questions
        )
    }

    fun findClubMembers(clubId: Long): MutableList<ClubMember> {
        return findClubMembersByClubId(clubId)
    }

    fun findClubAdmins(clubId: Long): MutableList<ClubAdmin> {
        return findClubAdminsByClubId(clubId)
    }

    fun findClubInfosByName(name: String, getClubsRequest: GetClubsRequest): ClubsResponse{
        val clubsPage = findClubsByName(NameAndPage(name, getClubsRequest.currentPage))
        val clubs = clubsPage.content

        return ClubsResponse(
            clubsToClubInfos(clubs),
            clubsPage.totalPages,
            clubsPage.number
        )
    }

    fun findClubInfosByAddress(address: String, getClubsRequest: GetClubsRequest): ClubsResponse{
        val clubsPage = findClubsByAddress(NameAndPage(address, getClubsRequest.currentPage))
        val clubs = clubsPage.content

        return ClubsResponse(
            clubsToClubInfos(clubs),
            clubsPage.totalPages,
            clubsPage.number
        )
    }

    fun findClubInfosByCharacters(characters: List<ClubCharacter>, getClubsRequest: GetClubsRequest): ClubsResponse{
        val clubsPage = findClubsByCharacters(CharactersAndPage(characters, getClubsRequest.currentPage))
        val clubs = clubsPage.content

        return ClubsResponse(
            clubsToClubInfos(clubs),
            clubsPage.totalPages,
            clubsPage.number
        )
    }

    private fun clubAdminToUserInfo(admins: MutableList<ClubAdmin>): MutableList<UserInfo>{
        var userInfos = mutableListOf<UserInfo>()
        for (admin in admins){
            userInfos.add(UserInfo(admin.user))
        }

        return userInfos
    }

    private fun clubMemberToUserInfo(members: MutableList<ClubMember>): MutableList<UserInfo>{
        var userInfos = mutableListOf<UserInfo>()
        for (member in members){
            userInfos.add(UserInfo(member.user))
        }

        return userInfos
    }

    private fun requestToClub(createClubRequest: CreateClubRequest): Club{
        return Club(
            id = null,
            name = createClubRequest.name,
            category =  createClubRequest.category,
            location = createClubRequest.location,
            emblem =  createClubRequest.emblem,
            ability = createClubRequest.ability,
            thumbnail = createClubRequest.thumbnail,
            introduce = createClubRequest.introduce,
            contact = createClubRequest.contact,
            createdDate = Date(),
            createTeamDate = createClubRequest.createTeamDate,
            characters = createClubRequest.characters,
            questions = createClubRequest.questions
        )
    }

    private fun clubsToClubInfos(clubs: MutableList<Club>): MutableList<ClubInfo>{
        var clubInfos = mutableListOf<ClubInfo>()
        for(club in clubs){
            clubInfos.add(ClubInfo(club))
        }

        return clubInfos
    }
}