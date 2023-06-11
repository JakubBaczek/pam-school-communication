package com.example.pam_school_communication


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.pam_school_communication.databinding.ActivityTeacherMenuBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class TeacherMenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTeacherMenuBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseStore: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher_menu)

        /*val button1: Button = findViewById(R.id.button1)
        button1.setOnClickListener(this)*/

        val button2: Button = findViewById(R.id.button2)
        button2.setOnClickListener {
            Intent(this, GradeActivity::class.java).also {
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