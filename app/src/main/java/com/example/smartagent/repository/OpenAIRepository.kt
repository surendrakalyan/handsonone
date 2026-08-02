package com.example.smartagent.repository

import com.example.smartagent.BuildConfig
import com.example.smartagent.model.OpenAIRequest
import com.example.smartagent.network.ApiClient

class OpenAIRepository {

    suspend fun getResponse(userMessage: String): String {

        return try {

            val response = ApiClient.openAIService.getResponse(
                "Bearer ${BuildConfig.OPENAI_API_KEY}",
                OpenAIRequest(
                    model = "gpt-4.1-mini",
                    input = userMessage
                )
            )

            if (response.isSuccessful) {

                val body = response.body()

                body?.output
                    ?.firstOrNull()
                    ?.content
                    ?.firstOrNull()
                    ?.text
                    ?: "No response"

            } else {

                "HTTP ${response.code()}\n${response.errorBody()?.string()}"

            }

        } catch (e: Exception) {

            "Exception:\n${e.message}"

        }
    }
}