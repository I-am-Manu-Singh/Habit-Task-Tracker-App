package com.example.chamberlyproject.model

import java.util.UUID

data class Habit(
    val id: String = UUID.randomUUID().toString(), //to generate unique id for each habit
    val name: String = "",
    val isCompleted: Boolean = false
) {
    // No-argument constructor required by Firebase
    constructor() : this("", "")
}