package com.example.pam_school_communication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Objects

class RegistrationActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseStore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        val registrationButton: Button = findViewById(R.id.registerButton)
        val emailText: EditText = findViewById(R.id.emailField)
        val passwordText: EditText = findViewById(R.id.passwordField)
        val retypePasswordText: EditText = findViewById(R.id.retypePassword)
        val alreadyRegisteredText: TextView = findViewById(R.id.RegisteredTextView)
        val nameText :EditText = findViewById(R.id.nameField)
        val secondNameText :EditText = findViewById(R.id.secondNameField)


        firebaseAuth = FirebaseAuth.getInstance()
        firebaseStore = FirebaseFirestore.getInstance()


        registrationButton.setOnClickListener() {
            val email = emailText.text.toString()
            val password = passwordText.text.toString()
            val retypedPassword = retypePasswordText.text.toString()

            //sprawdzenie czy pola nie są puste
            if (email.isNotEmpty() && password.isNotEmpty() && retypedPassword.isNotEmpty()) {
            //sprawdzenie czy hasła się zgadzają
                if (password == retypedPassword) {
                    firebaseAuth.createUserWithEmailAndPassword(email, password) //tworzenie użytkownika
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                val user: FirebaseUser? = firebaseAuth.currentUser
                                val df = user?.let { it1 ->
                                    firebaseStore.collection("Users").document(
                                        it1.uid)
                                }
                               val userInfo :HashMap<String, Any> = HashMap()
                                userInfo["Name"] = nameText.text.toString()         //zapis danych użytkownika do fireStore
                                userInfo["SecondName"] = secondNameText.text.toString()
                                userInfo["email"] = email
                                userInfo["IsTeacher"] = "0"

                                if (df != null) {
                                    df.set(userInfo)
                                }

                                val intent = Intent(this, MainActivity::class.java) //przejscie do logowania
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