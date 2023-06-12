package com.example.pam_school_communication

import android.content.Intent
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pam_school_communication.databinding.ActivityTeacherMenuBinding
import com.firebase.ui.database.FirebaseListAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class MenuActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseStore: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val button1: Button = findViewById(R.id.button1)
        button1.setOnClickListener {
            Intent(this, NotificationActivity::class.java).also {
                startActivity(it)
            }
        }

        val button2: Button = findViewById(R.id.button2)
        button2.setOnClickListener {
            Intent(this, GradesLookupActivity::class.java).also {
                startActivity(it)
            }
        }

        val button3: Button = findViewById(R.id.button3)
        button3.setOnClickListener {
            Intent(this, ChatStartActivity::class.java).also {
                startActivity(it)
            }
        }

        firebaseAuth = FirebaseAuth.getInstance()
        val button = findViewById<Button>(R.id.button4)

        fun logout(){
            firebaseAuth.signOut()
            Intent(this, MainActivity::class.java).also{
                startActivity(it)
            }
        }

        button.setOnClickListener(){
            logout()
        }
    }


}

