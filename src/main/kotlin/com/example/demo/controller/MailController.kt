package com.example.demo.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class MailController {

    @GetMapping("/mail")
    fun sendMail(): String {
        return "Hello hosono ultimate world 2 !!"
    }

}
