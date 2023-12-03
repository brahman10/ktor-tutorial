package com.ep.db

import org.litote.kmongo.coroutine.CoroutineClient
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

class DatabaseHelper {
    companion object {
        private var database: CoroutineDatabase? = null
        fun getDatabase(): CoroutineDatabase? {
            synchronized(DatabaseHelper::class.java) {
                if (database == null) {
                    database =
                        KMongo.createClient("mongodb+srv://yashchaturvedi:Yashmongo1@cluster0.5ugj7pm.mongodb.net/").coroutine.getDatabase(
                            DatabaseConstants.DB_NAME
                        )
                }
            }
            return database
        }
    }
}