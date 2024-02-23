package me.whitewin.couponapi.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController {

    @GetMapping("/test")
    fun hello(): String {
        Thread.sleep(500);
        return "test!"
    }
}
