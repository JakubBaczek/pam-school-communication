package com.example.pam_school_communication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class RegistrationActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        val registrationButton: Button = findViewById(R.id.registerButton)
        val emailText: EditText = findViewById(R.id.emailField)
        val passwordText: EditText = findViewById(R.id.passwordField)
        val retypePasswordText: EditText = findViewById(R.id.retypePassword)
        val alreadyRegisteredText: TextView = findViewById(R.id.RegisteredTextView)

        firebaseAuth = FirebaseAuth.getInstance()

        registrationButton.setOnClickListener() {
            val email = emailText.text.toString()
            val password = passwordText.text.toString()
            val retypedPassword = retypePasswordText.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty() && retypedPassword.isNotEmpty()) {

                if (password == retypedPassword) {
                    firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                val intent = Intent(this, MainActivity::class.java)
                                startActivity(intent)
                            } else {
                                Toast.makeText(this, "${it.exception}", Toast.LENGTH_LONG).show()
                            }
                        }
                } else {
                    Toast.makeText(this, "Hasła nie są identyczne", Toast.LENGTH_LONG).show()
                }

            } else {
                Toast.makeText(this, "Wypełnij wszystkie pola", Toast.LENGTH_LONG).show()
            }
        }

        alreadyRegisteredText.setOnClickListener() {
            Intent(this, MainActivity::class.java).also {
                startActivity(it)
            }
        }
    }
}