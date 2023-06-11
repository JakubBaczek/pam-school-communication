package com.example.pam_school_communication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseStore: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val loginButton: Button = findViewById(R.id.loginButton)
        val emailText: EditText = findViewById(R.id.emailField)
        val passwordText: EditText = findViewById(R.id.passwordField)
        val retypePasswordText: EditText = findViewById(R.id.retypePassword)
        val notRegisteredText: TextView = findViewById(R.id.notRegisteredText)

        firebaseAuth = FirebaseAuth.getInstance()
        firebaseStore = FirebaseFirestore.getInstance()


        notRegisteredText.setOnClickListener() {
            Intent(this, RegistrationActivity::class.java).also {
                startActivity(it)
            }
        }

        loginButton.setOnClickListener() {
            val email = emailText.text.toString()
            val password = passwordText.text.toString()
            val retypedPassword = retypePasswordText.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty() && retypedPassword.isNotEmpty()) {

                if (password == retypedPassword) {
                    //autentykacja konta
                    firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener {
                            //co po udanej autentykacji
                            val user = firebaseAuth.currentUser
                            if (it.isSuccessful) {
                                if (user != null) {
                                    checkIfParent(user.uid)
                                    checkIfTeacher(user.uid)
                                    checkIfStudent(user.uid)
                                }
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

    }

    fun checkIfStudent(uid: String) {
        val df = firebaseStore.collection("Users").document(uid)
        df.get().addOnSuccessListener() {
            if (it.data?.get("isStudent") != null) {
                val intent = Intent(this, MenuActivity::class.java)
                startActivity(intent)
            }
        }
    }

    fun checkIfParent(uid: String) {
        val df = firebaseStore.collection("Users").document(uid)
        df.get().addOnSuccessListener() {
            if (it.data?.get("isParent") != null) {
                val intent = Intent(this, MenuActivity::class.java)
                startActivity(intent)
            }
        }
    }

    fun checkIfTeacher(uid: String) {
        val df = firebaseStore.collection("Users").document(uid)
        df.get().addOnSuccessListener() {
            if (it.data?.get("isTeacher").toString() == "1") {
                Log.d("Wartosc nauczyciela", "${it.data!!.get("isTeacher")}" )
                val intent = Intent(this, TeacherMenuActivity::class.java)
                startActivity(intent)
            }else if(it.data?.get("isTeacher").toString() == "0"){
                Toast.makeText(this, "jesteś nieautoryzowanym nauczycielem", Toast.LENGTH_LONG).show()
            }
        }
    }


}