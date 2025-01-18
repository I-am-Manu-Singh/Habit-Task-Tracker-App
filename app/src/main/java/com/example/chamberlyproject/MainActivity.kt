package com.example.chamberlyproject

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.chamberlyproject.repository.FirebaseRepository
import com.example.chamberlyproject.ui.theme.HabitTrackerApp
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {
    private lateinit var firebaseRepository: FirebaseRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        // Initialize Firebase
        FirebaseApp.initializeApp(this)
        Toast.makeText(this, "Firebase Initialized", Toast.LENGTH_SHORT).show()

        firebaseRepository = FirebaseRepository() //

        setContent {
            HabitTrackerApp(firebaseRepository = firebaseRepository)
        }
    }
}


