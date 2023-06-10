package com.example.pam_school_communication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.pam_school_communication.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MenuActivity : AppCompatActivity() {
    private lateinit var firebaseAuth : FirebaseAuth
    private lateinit var firebaseStore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val startChatButton: Button = findViewById(R.id.startChatButton)
        val emailEditText: EditText = findViewById(R.id.chatEmailSelectionField)

        firebaseAuth = FirebaseAuth.getInstance()
        firebaseStore = FirebaseFirestore.getInstance()

        startChatButton.setOnClickListener(){
            val emailText = emailEditText.text.toString()

            if (emailText.isNotEmpty())
            {
                if (emailText != firebaseAuth.currentUser?.email) {
                    // sprawdzic, czy uzytkownik o podanym emailu istnieje
                    //if (firebaseAuth...)
                    val intent = Intent(this, ChatActivity::class.java)
                    startActivity(intent)
                }
                else {
                    Toast.makeText(this, "Podales wlasny email. Podaj email adresata", Toast.LENGTH_LONG).show()
                }
            }
            else
            {
                Toast.makeText(this, "Podaj email adresata", Toast.LENGTH_LONG).show()
            }
        }

    }
}