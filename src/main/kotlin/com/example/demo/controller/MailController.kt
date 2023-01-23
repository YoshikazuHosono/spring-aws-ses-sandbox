package com.example.demo.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.sesv2.SesV2Client
import software.amazon.awssdk.services.sesv2.model.*
import java.net.URI

@RestController
class MailController {

    @GetMapping("/mail")
    fun sendMail(): String {
        val dummyCredentials = AwsBasicCredentials.create("dummy-access-key-id", "dummy-secret-access-key")

        val client = SesV2Client.builder().apply {
            region(Region.AP_NORTHEAST_1)
            credentialsProvider(StaticCredentialsProvider.create(dummyCredentials))
            endpointOverride(URI("http://localhost:8005"))
        }.build()

        val to = Destination.builder().apply {
            toAddresses("to-hosono@hosono.co.jp")
        }.build()

        val subject = Content.builder().apply {
            data("subject : my name is hosono. ok?")
        }.build()

        val body = Body.builder().apply {
            html(Content.builder().apply { data("hello neighbor") }.build())
        }.build()

        val message = Message.builder().apply {
            subject(subject)
            body(body)
        }.build()

        val emailContent = EmailContent.builder().apply {
            simple(message)
        }.build()

        val sendEmailRequest = SendEmailRequest.builder().apply {
            destination(to)
            content(emailContent)
            fromEmailAddress("from-hosono@hosono.co.jp")
        }.build()

        return try {
            client.sendEmail(sendEmailRequest)
            "success"
        } catch (e: Exception) {
            e.printStackTrace()
            "error"
        }
    }

}
