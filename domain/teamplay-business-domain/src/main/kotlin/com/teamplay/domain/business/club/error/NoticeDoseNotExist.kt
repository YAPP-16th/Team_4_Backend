package com.teamplay.domain.business.club.error

import com.teamplay.core.function.error.NotFoundError

class NoticeDoseNotExist(message: String = "notice does not exist") : NotFoundError(message)