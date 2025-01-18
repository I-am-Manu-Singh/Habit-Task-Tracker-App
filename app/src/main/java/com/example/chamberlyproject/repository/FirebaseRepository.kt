package com.example.chamberlyproject.repository

import android.annotation.SuppressLint
import android.util.Log
import com.example.chamberlyproject.model.Habit
import com.example.chamberlyproject.model.User
import com.google.firebase.Firebase
import com.google.firebase.database.database
import com.google.firebase.firestore.firestore


class FirebaseRepository {

    private val realtimeDb = Firebase.database.reference
    private val firestore = Firebase.firestore

    fun addUser(@SuppressLint("RestrictedApi") user: User, onSuccess: () -> Unit, onError: (Exception) -> Unit) {
        realtimeDb.child("users").child(user.id).setValue(user)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { exception ->
                Log.e("FirebaseRepository", "Error adding user: ${exception.message}", exception)
                onError(exception)
            }
    }

    fun addHabit(userId: String, habit: Habit, onSuccess: () -> Unit, onError: (Exception) -> Unit) {
        firestore.collection("habits").document(userId).collection("userHabits")
            .add(habit)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { exception ->
                Log.e("FirebaseRepository", "Error adding habit: ${exception.message}", exception)
                onError(exception)
            }
    }

    fun getHabits(userId: String, onComplete: (List<Habit>) -> Unit, onError: (Exception) -> Unit) {
        firestore.collection("habits").document(userId).collection("userHabits")
            .get()
            .addOnSuccessListener { result ->
                val habits = result.map { documentSnapshot ->
                    documentSnapshot.toObject(Habit::class.java).copy(id = documentSnapshot.id)
                }
                onComplete(habits)
            }
            .addOnFailureListener { exception ->
                Log.e("FirebaseRepository", "Error fetching habits: ${exception.message}", exception)
                onError(exception)
            }
    }

    fun markHabitCompleted(userId: String, habitId: String, onSuccess: () -> Unit, onError: (Exception) -> Unit) {
        firestore.collection("habits").document(userId).collection("userHabits").document(habitId)
            .update("isCompleted", true)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { exception ->
                Log.e("FirebaseRepository", "Error marking habit completed: ${exception.message}", exception)
                onError(exception)
            }
    }

    fun deleteHabit(userId: String, habitId: String, onSuccess: () -> Unit, onError: (Exception) -> Unit) {
        val habitRef = Firebase.firestore.collection("users").document(userId).collection("habits").document(habitId)

        habitRef.delete()
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { exception ->
                onError(exception)
            }
    }

}
