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

        val to = "to-hosono@hosono.co.jp"
        val from = "from-hosono@hosono.co.jp"
//        val from = null
        val subject = "subject : my name is hosono. ok?"
        val content = "hello neighbor"

        val request = mailRequestFactory(to = to, from = from, subject = subject, content = content)

        return try {
            client.sendEmail(request)
            "success"
        } catch (e: Exception) {
            e.printStackTrace()
            "error"
        }
    }

    private fun mailRequestFactory(to: String, from: String?, subject: String, content: String): SendEmailRequest? {
        return SendEmailRequest.builder().apply {
            // to
            val destinationModel = Destination.builder().apply { toAddresses(to) }.build()
            destination(destinationModel)

            // from
            fromEmailAddress(from)

            // subject
            val subjectModel = Content.builder().apply { data(subject) }.build()

            // body
            val bodyContent = Content.builder().data(content).build()
            val bodyModel = Body.builder().html(bodyContent).build()

            // message ( subject + body )
            val messageModel = Message.builder().subject(subjectModel).body(bodyModel).build()

            // email ( message )
            val emailContentModel = EmailContent.builder().simple(messageModel).build()
            content(emailContentModel)
        }.build()
    }

}
