package com.example.justdo.model.data.server.deserializer

import com.example.justdo.domain.entities.tasks.TodoTask
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class TodoTasksListDeserializer : JsonDeserializer<Array<TodoTask>> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Array<TodoTask> =
        GsonBuilder()
            .create()
            .fromJson(
                json.asJsonObject["result"]
                    .asJsonObject["list"]
                    .asJsonArray, Array<TodoTask>::class.java
            )

    /*
    {
        "result": true,
        "list": [
            {},
            {}
        ]
    }
    */
}