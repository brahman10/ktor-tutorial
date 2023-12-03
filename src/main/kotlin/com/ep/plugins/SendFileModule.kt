package com.ep.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File

fun Application.fileModule() {
    routing {
        get("/download-mom-image") {
            val file = File("/Users/admin/Downloads/ep-blog/files/mom.png")
            call.response.header(
                HttpHeaders.ContentDisposition,
                ContentDisposition.Attachment.withParameter(
                    ContentDisposition.Parameters.FileName, "mom.png"
                ).toString()
            )
            call.respondFile(file)
        }

        get("/view-mom-image") {
            val file = File("/Users/admin/Downloads/ep-blog/files/mom.png")
            call.response.header(
                HttpHeaders.ContentDisposition,
                ContentDisposition.Inline.withParameter(
                    ContentDisposition.Parameters.FileName, "mom.png"
                ).toString()
            )
            call.respondFile(file)
        }
    }
}