package com.example.pam_school_communication

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class GradeActivity : AppCompatActivity() {

    private lateinit var studentSpinner: Spinner
    private lateinit var subjectSpinner: Spinner
    private lateinit var gradeSpinner: Spinner
    private lateinit var addButton: Button
    private lateinit var firebaseStore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grade)

        studentSpinner = findViewById(R.id.studentSpinner)
        subjectSpinner = findViewById(R.id.subjectSpinner)
        gradeSpinner = findViewById(R.id.gradeSpinner)
        addButton = findViewById(R.id.addButton)

        firebaseStore = FirebaseFirestore.getInstance()

        val students = arrayOf("Uczeń 1", "Uczeń 2", "Uczeń 3") // Przykładowe dane uczniów
        val subjects = arrayOf("Matematyka", "Polski", "Angielski", "Historia") // Dane przedmiotów

        val studentAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, students)
        val subjectAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, subjects)
        val gradeAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, arrayOf("1", "2", "3", "4", "5", "6"))

        studentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        subjectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        gradeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        studentSpinner.adapter = studentAdapter
        subjectSpinner.adapter = subjectAdapter
        gradeSpinner.adapter = gradeAdapter

        addButton.setOnClickListener {
            val selectedStudent = studentSpinner.selectedItem.toString()
            val selectedSubject = subjectSpinner.selectedItem.toString()
            val selectedGrade = gradeSpinner.selectedItem.toString()

            val docRef = firebaseStore.collection("Users").document(selectedStudent)
                .collection("Subjects and Grades").document(selectedSubject)

            val gradeData = hashMapOf(
                "Ocena" to selectedGrade
            )

            docRef.set(gradeData)
                .addOnSuccessListener {
                    Toast.makeText(this, "Ocena dodana do Firestore", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Błąd podczas dodawania oceny: ${it.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }
}
