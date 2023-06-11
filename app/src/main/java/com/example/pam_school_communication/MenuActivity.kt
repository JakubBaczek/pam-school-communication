package com.example.pam_school_communication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import org.checkerframework.checker.units.qual.C
import java.lang.Exception
import kotlin.text.StringBuilder

class MenuActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseStore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseStore = FirebaseFirestore.getInstance()

        val usersRef = firebaseStore.collection("Users")

        val button: Button = findViewById<Button>(R.id.retrieveButton)
        val menuText: TextView = findViewById(R.id.menuText)

        lateinit var imie: String
        lateinit var nazwisko: String
        lateinit var email: String
        lateinit var rola: Role
        lateinit var id: String

        fun retrievePerson() = CoroutineScope(Dispatchers.IO).launch {
            try {
                val querySnapshot = usersRef.get().await()
                val sb = StringBuilder()
                for (document in querySnapshot.documents) {
                    imie = document.get("Name").toString()
                    nazwisko = document.get("SecondName").toString()
                    email = document.get("email").toString()
                    id = document.id
                    if (document.get("isStudent") != null) {
                        rola = Role.Uczeń
                    } else if (document.get("isParent") != null) {
                        rola = Role.Rodzic
                    } else if (document.get("isTeacher") == "1") {
                        rola = Role.Rodzic
                    } else {
                        rola = Role.nieautoryzowanyNauczyciel
                    }
                    val person = User(imie, nazwisko, email, rola, id)
                    sb.append("$person\n")
                }
                withContext(Dispatchers.Main) {
                    menuText.text = sb.toString()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@MenuActivity, e.message, Toast.LENGTH_LONG).show()
                }
            }

        }

        button.setOnClickListener(){
            retrievePerson()
        }


    }


}