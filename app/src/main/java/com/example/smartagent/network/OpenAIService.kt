package com.example.smartagent.network

import com.example.smartagent.model.OpenAIRequest
import com.example.smartagent.model.OpenAIResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface OpenAIService {

    @Headers("Content-Type: application/json")
    @POST("v1/responses")
    suspend fun getResponse(
        @Header("Authorization") authorization: String,
        @Body request: OpenAIRequest
    ): Response<OpenAIResponse>
}