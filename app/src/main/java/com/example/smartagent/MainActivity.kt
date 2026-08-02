package com.example.smartagent

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smartagent.adapter.ChatAdapter
import com.example.smartagent.model.ChatMessage
import com.example.smartagent.repository.OpenAIRepository
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var rvChat: RecyclerView
    private lateinit var etMessage: EditText
    private lateinit var btnSend: ImageButton

    private lateinit var adapter: ChatAdapter
    private val messageList = mutableListOf<ChatMessage>()

    private val openAIRepository = OpenAIRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvChat = findViewById(R.id.rvChat)
        etMessage = findViewById(R.id.etMessage)
        btnSend = findViewById(R.id.btnSend)

        adapter = ChatAdapter(messageList)

        rvChat.layoutManager = LinearLayoutManager(this)
        rvChat.adapter = adapter

        // Welcome message
        addMessage(
            "👋 Hello! I am SmartAgent.\n\nAsk me anything.",
            false
        )

        btnSend.setOnClickListener {

            val text = etMessage.text.toString().trim()

            if (text.isNotEmpty()) {

                addMessage(text, true)

                etMessage.text.clear()

                askOpenAI(text)
            }
        }
    }

    private fun askOpenAI(message: String) {

        lifecycleScope.launch {

            addMessage("🤖 Thinking...", false)

            val response = openAIRepository.getResponse(message)

            if (messageList.isNotEmpty()) {
                messageList.removeAt(messageList.size - 1)
                adapter.notifyItemRemoved(messageList.size)
            }

            addMessage(response, false)
        }
    }

    private fun addMessage(message: String, isUser: Boolean) {

        messageList.add(ChatMessage(message, isUser))
        adapter.notifyItemInserted(messageList.size - 1)
        rvChat.scrollToPosition(messageList.size - 1)
    }
}