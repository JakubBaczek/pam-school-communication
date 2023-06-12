package com.example.pam_school_communication

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RecyclerViewActivity: AppCompatActivity()  {
    private lateinit var recyclerView: RecyclerView
    private lateinit var notificationList: ArrayList<Notification>
    private var db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)

        recyclerView = findViewById(R.id.recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this)

        notificationList = arrayListOf()

        db = FirebaseFirestore.getInstance()

        db.collection("Notifications").get().addOnSuccessListener {
            if (!it.isEmpty) {
                for (data in it.documents) {
                    val notification: Notification? = data.toObject(Notification::class.java)
                }
            }
        }
            .addOnFailureListener {
                Toast.makeText(this, it.toString(), Toast.LENGTH_LONG).show()
            }
    }
}