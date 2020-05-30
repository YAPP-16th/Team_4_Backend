package com.teamplay.api.external.controller

import io.swagger.annotations.ApiOperation
import mu.KLogging
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/tests")
class TestController {

    @ApiOperation(value = "테스트 API")
    @PostMapping("/test/{testId}")
    @ResponseStatus(HttpStatus.OK)
    fun test(@Valid @PathVariable testId: Int) : String{

        return "테스트 : $testId"
    }

    @ApiOperation(value = "logger 예제 API, release 시 삭제 예정")
    @GetMapping("/logging/{level}")
    @ResponseStatus(HttpStatus.OK)
    fun logging(@Valid @PathVariable level: Int) {
        when(level) {
            1 -> logger().trace("trace logging, arg val : {}", CONSTANT_VAL)
            // 현재 logback 설정에 최소 level을 debug 로 설정하여서 trace 로그는 출력되지 않습니다.
            2 -> logger().debug("debug logging, arg val : {}", CONSTANT_VAL)
            3 -> logger().info("info logging, arg val : {}", CONSTANT_VAL)
            4 -> logger().warn("warn logging, arg val : {}", CONSTANT_VAL)
            5 -> logger().error("error logging, arg val : {}", CONSTANT_VAL)
        }
    }

    companion object: KLogging() {
        const val CONSTANT_VAL = "상수값"
    }
}
