package com.example.smartagent.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.smartagent.R
import com.example.smartagent.model.ChatMessage

class ChatAdapter(
    private val messages: MutableList<ChatMessage>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val USER = 1
        private const val AI = 2
    }

    override fun getItemViewType(position: Int): Int {
        return if (messages[position].isUser) USER else AI
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return if (viewType == USER) {

            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_user, parent, false)

            UserHolder(view)

        } else {

            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_ai, parent, false)

            AIHolder(view)
        }
    }

    override fun getItemCount() = messages.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val msg = messages[position]

        if (holder is UserHolder) {

            holder.txt.text = msg.message

        } else if (holder is AIHolder) {

            holder.txt.text = msg.message
        }
    }

    class UserHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txt: TextView = view.findViewById(R.id.txtUser)
    }

    class AIHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txt: TextView = view.findViewById(R.id.txtAI)
    }
}