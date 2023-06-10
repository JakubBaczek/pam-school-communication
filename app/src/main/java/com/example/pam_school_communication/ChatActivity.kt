package com.example.pam_school_communication

import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.database.FirebaseListAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase





class ChatActivity : AppCompatActivity() {
    private lateinit var firebaseAuth : FirebaseAuth

    private lateinit var adapter: FirebaseListAdapter<ChatMessage>

    private fun displayChatMessages() {
        val listOfMessages = findViewById<ListView>(R.id.list_of_messages)

        adapter = object : FirebaseListAdapter<ChatMessage>(
            this, ChatMessage::class.java,
            R.layout.message, FirebaseDatabase.getInstance().reference
        ) {
            override fun populateView(v: View, model: ChatMessage, position: Int) {

                // Get references to the views of message.xml
                val messageText = v.findViewById<View>(R.id.message_text) as TextView
                val messageUser = v.findViewById<View>(R.id.message_user) as TextView
                val messageTime = v.findViewById<View>(R.id.message_time) as TextView

                // Set their text
                messageText.text = model.messageText
                messageUser.text = model.messageUser

                // Format the date before showing it
                messageTime.text = DateFormat.format(
                    "dd-MM-yyyy (HH:mm:ss)",
                    model.messageTime
                )
            }
        }

        listOfMessages.adapter = adapter

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        firebaseAuth = FirebaseAuth.getInstance()

        val fab: FloatingActionButton = findViewById(R.id.fab)

        fab.setOnClickListener(){
                val input: TextInputEditText = findViewById(R.id.input)

            // Read the input field and push a new instance of ChatMessage to the Firebase database

                FirebaseDatabase.getInstance()
                    .reference
                    .push()
                    .setValue(
                        ChatMessage(
                            input.text.toString(),
                            FirebaseAuth.getInstance()
                                .currentUser
                                ?.email
                        )
                    )

                // Clear the input
                //input.setText(" ")
        }

        displayChatMessages();
    }
}