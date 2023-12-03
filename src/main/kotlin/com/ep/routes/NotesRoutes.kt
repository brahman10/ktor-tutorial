package com.ep.routes

import com.ep.db.dto.NoteDTO
import com.ep.db.entity.BaseResponse
import com.ep.db.entity.Notes
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.notesRoute(
    noteDTO: NoteDTO
) {

    route("/notes") {

        post {
            val requestBody = call.receive<Notes>()

            try {
                val user = noteDTO.addNote(requestBody)
                call.respond(
                    HttpStatusCode.OK, BaseResponse(
                        user,
                        true
                    )
                )
            } catch (e: Exception) {
                application.run { log.error("Failed to register user", e.message) }
                call.respond(
                    HttpStatusCode.InternalServerError, BaseResponse(
                        null,
                        false,
                        "Something went wrong"
                    )
                )
            }
        }

        get {
            try {

                val user = noteDTO.getAllNotes()
                call.respond(user)

            } catch (e: Exception) {
                application.run { log.error("Failed to register user", e.message) }
                call.respond(
                    HttpStatusCode.InternalServerError, BaseResponse(
                        null,
                        false,
                        "Something went wrong"
                    )
                )
            }
        }

        get("/{id}") {
            val id = call.parameters["id"]

            try {
                val user = id?.let { it1 -> noteDTO.getNoteById(it1) }
                call.respond(user!!)

            } catch (e: Exception) {
                application.run { log.error("Failed to register user", e.message) }
                call.respond(
                    HttpStatusCode.InternalServerError, BaseResponse(
                        null,
                        false,
                        "Something went wrong"
                    )
                )
            }
        }

        delete("/{id}") {
            val id = call.parameters["id"]

            try {
                val delete = noteDTO.deleteNote(id!!)
                if (delete) {
                    call.respond("user delete")
                } else {
                    call.respond("user not found")
                }

            } catch (e: Exception) {
                application.run { log.error("Failed to register user", e.message) }
                call.respond(
                    HttpStatusCode.InternalServerError, BaseResponse(
                        null,
                        false,
                        "Something went wrong"
                    )
                )
            }
        }

        put("/{id}") {
            val requestBody = call.receive<Notes>()

            try {
                val update = noteDTO.updateNote(requestBody)
                if (update) {
                    call.respond("Note updated successfully.")
                } else {
                    call.respond("Note not found")
                }

            } catch (e: Exception) {
                application.run { log.error("Failed to register user", e.message) }
                call.respond(
                    HttpStatusCode.InternalServerError, BaseResponse(
                        null,
                        false,
                        "Something went wrong"
                    )
                )
            }
        }


    }

}