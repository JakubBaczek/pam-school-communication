package com.example.pam_school_communication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pam_school_communication.databinding.ActivityNotificationBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.lang.Exception
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class NotificationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotificationBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseStore: FirebaseFirestore
    private val notificationCollectionRef = Firebase.firestore.collection("Notifications")




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)

         val writeName : EditText = findViewById(R.id.writeName)
         val writeSurname : EditText = findViewById(R.id.writeSurname)
         val writeEmail : EditText = findViewById(R.id.writeEmail)
         val writeNotification : EditText = findViewById(R.id.writeName)

        val uploadNote : Button = findViewById(R.id.uploadNote)

        uploadNote.setOnClickListener {
            val name = writeName.text.toString()
            val surname = writeSurname.text.toString()
            val email = writeEmail.text.toString()
            val note = writeNotification.text.toString()
            val notification = Notification(name, surname, email, note)
            saveNotification(notification)
        }

        val checkNotes: Button = findViewById(R.id.checkNotes)
        checkNotes.setOnClickListener {
            Intent(this, RecyclerViewActivity::class.java).also {
                startActivity(it)
            }
        }
    }

    private fun saveNotification(notification: Notification) =
        CoroutineScope(Dispatchers.IO).launch {
            try {
                notificationCollectionRef.add(notification).await()
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@NotificationActivity,
                        "Ogłoszenie zapisano",
                        Toast.LENGTH_LONG
                    ).show()
                }

            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@NotificationActivity, e.message, Toast.LENGTH_LONG).show()
                }
            }
        }
}