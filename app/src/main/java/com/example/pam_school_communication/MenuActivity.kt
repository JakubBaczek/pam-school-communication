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
import com.firebase.ui.database.FirebaseListAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class MenuActivity : AppCompatActivity() {
    private lateinit var firebaseAuth : FirebaseAuth
    private lateinit var firebaseStore: FirebaseFirestore

    var curUserEmail: String = "NOCURUSEREMAIL!"
    var selectedEmail: String = "NOSELECTEDEMAIL!"

    private fun compareEmailsAndStartChat() {
        // check which email is alphabetically first, this is important for the db
        var listOfTwoEmails: List<String> = mutableListOf(curUserEmail, selectedEmail)
        var alphabeticallyFirstEmailNoDots: String = listOfTwoEmails.minOf { it }.toString().replace(".", "")
        var alphabeticallySecondEmailNoDots: String = listOfTwoEmails.maxOf { it }.toString().replace(".", "")


        val intent = Intent(this, ChatActivity::class.java)

        intent.putExtra("Current User Email", curUserEmail)
        Log.d("beforePutExtra", "selectedEmail before intent.putExtra => $selectedEmail")
        intent.putExtra("Selected Email", selectedEmail)
        intent.putExtra("Alphabetically First Email No Dots", alphabeticallyFirstEmailNoDots)
        intent.putExtra("Alphabetically Second Email No Dots", alphabeticallySecondEmailNoDots)

        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        firebaseAuth = FirebaseAuth.getInstance()
        firebaseStore = FirebaseFirestore.getInstance()

        val startChatButton: Button = findViewById(R.id.startChatButton)
        val emailEditText: EditText = findViewById(R.id.chatEmailSelectionField)
        curUserEmail= firebaseAuth.currentUser?.email.toString()

        startChatButton.setOnClickListener(){
            val emailText = emailEditText.text.toString()

            if (emailText.isNotEmpty())
            {
                if (emailText != curUserEmail) {
                    firebaseStore.collection("Users")
                        .whereEqualTo("email", emailText)
                        .get()
                        .addOnSuccessListener { documents ->
                            if(documents.isEmpty) {
                                Toast.makeText(
                                    this,
                                    "Nie znaleziono podanego emailu",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                            for (document in documents) {
                                selectedEmail = document.data["email"].toString()
                                Log.d("successEmail", "${document.id} => $selectedEmail")

                                compareEmailsAndStartChat()
                            }
                        }
                        .addOnFailureListener { exception ->
                            Log.w("failureEmail", "Error getting documents: ", exception)
                        }
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