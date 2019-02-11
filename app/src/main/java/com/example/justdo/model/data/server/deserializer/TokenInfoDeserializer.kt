package com.example.justdo.model.data.server.deserializer

import com.example.justdo.domain.entities.server.TokenInfo
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class TokenInfoDeserializer : JsonDeserializer<TokenInfo> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): TokenInfo =
        GsonBuilder()
            .create()
            .fromJson(
                json.asJsonObject["result"]
                    .asJsonObject, TokenInfo::class.java
            )
}