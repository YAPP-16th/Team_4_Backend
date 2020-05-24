package com.teamplay.api.com.teamplay.api.external.service

import com.teamplay.api.com.teamplay.api.external.request.SignInByEmailRequest
import com.teamplay.api.com.teamplay.api.external.request.SignUpByEmailRequest
import com.teamplay.api.com.teamplay.api.external.response.PossibleEmailResponse
import com.teamplay.api.com.teamplay.api.external.response.PossibleNicknameResponse
import com.teamplay.api.com.teamplay.api.external.response.SignInResponse
import com.teamplay.domain.business.token.dto.AccessToken

import com.teamplay.domain.business.token.function.*
import com.teamplay.domain.business.user.dto.UserInfo
import com.teamplay.domain.business.user.dto.InputPasswordAndRealPassword
import com.teamplay.domain.business.user.function.*
import com.teamplay.domain.business.user.validator.CheckDuplicateUserEmail
import com.teamplay.domain.business.user.validator.CheckDuplicateUserNickname
import com.teamplay.domain.business.user.validator.CheckExistUserById
import com.teamplay.domain.business.user.validator.CheckExistUserEmail
import com.teamplay.domain.business.user.validator.ConfirmPasswordMatching
import com.teamplay.domain.database.jpa.user.repository.UserRepository
import com.teamplay.domain.database.user.entity.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.*

@Service
class AuthService @Autowired constructor(
    @Value("\${jwt.secret}")
    private val jwtSecretKey : String,
    @Value("\${jwt.expiration}")
    private val jwtExpirationInMs: Int,
    userRepository: UserRepository
) {
    private val signUpUser = SignUpUser(userRepository)
    private val generateAccessTokenByUser = GenerateAccessTokenByUser(jwtExpirationInMs)
    private val generateRefreshTokenByUser = GenerateRefreshTokenByUser()
    private val encodeToken = EncodeToken(jwtSecretKey)
    private val findUserById = FindUserById(userRepository)
    private val findUserIdByAccessToken = FindUserIdByAccessToken(jwtSecretKey)
    private val findUserIdByRefreshToken = FindUserIdByRefreshToken(jwtSecretKey)
    private val findByUserEmail = FindByUserEmail(userRepository)
    private val checkPossibleEmail = CheckPossibleEmail(userRepository)
    private val checkPossibleNickname = CheckPossibleNickname(userRepository)

    private val checkExistUserEmail = CheckExistUserEmail(userRepository)
    private val confirmPasswordMatching = ConfirmPasswordMatching()
    private val checkDuplicateUserEmail = CheckDuplicateUserEmail(userRepository)
    private val checkDuplicateUserNickname = CheckDuplicateUserNickname(userRepository)
    private val checkExistUserById = CheckExistUserById(userRepository)

    fun signUpByEmail(signUpByEmailRequest: SignUpByEmailRequest): SignInResponse{
        checkDuplicateUserEmail.verify(signUpByEmailRequest.email)
        checkDuplicateUserNickname.verify(signUpByEmailRequest.nickname)

        val user = signUpUser(
            User(
            id = null,
            email = signUpByEmailRequest.email,
            nickname = signUpByEmailRequest.nickname,
            hashedPassword = signUpByEmailRequest.hashedPassword,
            signUpDate = Date()
            )
        )

       return userToSignInResponse(user)
    }

    fun signInByEmail(signInByEmailRequest: SignInByEmailRequest): SignInResponse{
        checkExistUserEmail.verify(signInByEmailRequest.email)
        val user = findByUserEmail(signInByEmailRequest.email)
        confirmPasswordMatching.verify(
            InputPasswordAndRealPassword(signInByEmailRequest.hashedPassword, user.hashedPassword)
        )

        return userToSignInResponse(user)
    }

    fun getUserByAccessToken(accessToken: String): User{
        val userId = getUserIdByAccessToken(accessToken)

        return findUserById(userId)
    }

    fun getUserIdByAccessToken(accessToken: String): Long{
        val userId = findUserIdByAccessToken(accessToken)
        checkExistUserById.verify(userId)

        return userId
    }

    fun checkPossibleUserNickname(Nickname: String): PossibleNicknameResponse {
        val possible = checkPossibleNickname(Nickname)
        var message = if(possible) "존재하는 닉네임입니다." else "존재하지 않는 닉네임입니다."

        return PossibleNicknameResponse(possible, message)
    }

    fun checkPossibleUserEmail(email: String): PossibleEmailResponse{
        val possible = checkPossibleEmail(email)
        var message = if(possible) "존재하는 이메일입니다." else "존재하지 않는 이메일입니다."

        return PossibleEmailResponse(possible, message)
    }

    private fun userToSignInResponse(user: User): SignInResponse{
        return SignInResponse(
            generateAccessToken(user),
            generateRefreshToken(user),
            UserInfo(user.id, user.nickname, user.email)
        )
    }

    fun refreshAccessToken(refreshToken: String): AccessToken{
        val userId = findUserIdByRefreshToken(refreshToken)
        checkExistUserById.verify(userId)
        val user = findUserById(userId)
        return generateAccessToken(user)
    }

    private fun generateAccessToken(user: User): AccessToken{
        val token = generateAccessTokenByUser(user)

        return AccessToken(encodeToken(token), token.expiration!!.time)
    }

    private fun generateRefreshToken(user: User): String{
        return encodeToken(generateRefreshTokenByUser(user))
    }


}
