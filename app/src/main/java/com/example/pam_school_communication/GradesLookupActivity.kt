package com.example.pam_school_communication

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class GradesLookupActivity : AppCompatActivity() {

    private lateinit var studentNameEditText: EditText
    private lateinit var subjectEditText: EditText
    private lateinit var searchButton: Button
    private lateinit var gradesListView: ListView
    private lateinit var firebaseStore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grades_lookup)

        studentNameEditText = findViewById(R.id.studentNameEditText)
        subjectEditText = findViewById(R.id.subjectEditText)
        searchButton = findViewById(R.id.searchButton)
        gradesListView = findViewById(R.id.gradesListView)

        firebaseStore = FirebaseFirestore.getInstance()

        searchButton.setOnClickListener {
            val studentName = studentNameEditText.text.toString()
            val subject = subjectEditText.text.toString()

            if (studentName.isNotEmpty() && subject.isNotEmpty()) {
                searchGrades(studentName, subject)
            }
        }
    }

    private fun searchGrades(studentName: String, subject: String) {
        val studentRef = firebaseStore.collection("Students")
            .whereEqualTo("Name", studentName)
            .get()
            .addOnSuccessListener { studentQuerySnapshot ->
                if (!studentQuerySnapshot.isEmpty) {
                    val studentDocument = studentQuerySnapshot.documents[0]
                    val studentId = studentDocument.id

                    val gradesRef = firebaseStore.collection("Students")
                        .document(studentId)
                        .collection("Grades")
                        .whereEqualTo("Subject", subject)
                        .get()
                        .addOnSuccessListener { gradesQuerySnapshot ->
                            if (!gradesQuerySnapshot.isEmpty) {
                                val gradesList = mutableListOf<String>()
                                for (gradeDocument in gradesQuerySnapshot.documents) {
                                    val grade = gradeDocument.getString("Grade")
                                    gradesList.add(grade!!)
                                }
                                displayGrades(gradesList)
                            } else {
                                displayGrades(emptyList())
                            }
                        }
                        .addOnFailureListener {
                            displayGrades(emptyList())
                        }
                } else {
                    displayGrades(emptyList())
                }
            }
            .addOnFailureListener {
                displayGrades(emptyList())
            }
    }

    private fun displayGrades(gradesList: List<String>) {
        val gradesAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, gradesList)
        gradesListView.adapter = gradesAdapter
    }
}
