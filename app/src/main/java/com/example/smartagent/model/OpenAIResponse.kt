package com.example.smartagent.model

data class OpenAIResponse(
    val output: List<Output>
)

data class Output(
    val content: List<Content>
)

data class Content(
    val text: String
)