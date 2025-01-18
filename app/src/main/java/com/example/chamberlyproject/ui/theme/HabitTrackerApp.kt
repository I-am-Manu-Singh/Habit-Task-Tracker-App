package com.example.chamberlyproject.ui.theme

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.itemsIndexed
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.AlertDialog
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Card
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Icon
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.IconButton
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.MaterialTheme
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.OutlinedButton
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.chamberlyproject.model.Habit
import com.example.chamberlyproject.repository.FirebaseRepository
@Composable
fun HabitTrackerApp(firebaseRepository: FirebaseRepository) {
    val userId = "user123" // Replace with actual user ID
    val habits = remember { mutableStateListOf<Habit>() }
    var newHabitName by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) } // State to show dialog
    // Fetch habits when composable is loaded
    LaunchedEffect(Unit) {
        firebaseRepository.getHabits(
            userId,
            onComplete = { fetchedHabits ->
                habits.clear()
                habits.addAll(fetchedHabits)
            },
            onError = { error ->
                Log.e("FirebaseError", "Error fetching habits: ${error.message}")
            }
        )
    }

    Column(modifier = Modifier.padding(16.dp)) {
        // Title text
        Text(
            text = "Habit/Task Tracker by Manpreet Singh",
            fontSize = 20.sp, // Increase the font size as needed
            modifier = Modifier.fillMaxWidth(),
        )

        // IconButton to show the AlertDialog when clicked
        IconButton(
            onClick = {
                showDialog = true // Show the dialog
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Default.AddCircle,
                    contentDescription = "Add Habit/Task",
                    tint = Color(0xFF8A2BE2) // Violet color hex code
                )
                Text("Add Habit/Task")
            }
        }
    }

    // Show AlertDialog when showDialog is true
    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
                showDialog = false // Close the dialog when clicking outside
            },
            title = { Text("Add New Habit/Task",color = Color(0xFF8A2BE2) // Violet color hex code
            ) },

            text = {
                OutlinedTextField(
                    value = newHabitName,
                    onValueChange = { newHabitName = it },
                    label = { Text("Habit/Task Name") },
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                OutlinedButton(
                    onClick = {
                        if (newHabitName.isNotBlank()) {
                            val newHabit = Habit(name = newHabitName)
                            firebaseRepository.addHabit(
                                userId,
                                newHabit,
                                onSuccess = {
                                    habits.add(newHabit)
                                    newHabitName = ""
                                    showDialog = false // Close the dialog on success
                                },
                                onError = { error ->
                                    Log.e("FirebaseError", "Error adding habit: ${error.message}")
                                    showDialog = false // Close the dialog on error
                                }
                            )
                        }
                    }
                ) {
                    Text("Add Habit")
                }
            },
            dismissButton = {
                OutlinedButton(onClick = { showDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    // Display habits in a list
    Box(
        modifier = Modifier
            .fillMaxSize() // Make the Box fill the entire screen
            .padding(top = 100.dp, start = 16.dp, end = 16.dp),  // Apply padding to top, left, and right
        contentAlignment = Alignment.TopStart  // Align content at the top start
    )


    {
        LazyColumn(
            modifier = Modifier.fillMaxWidth(), // LazyColumn fills the width of the Box
            verticalArrangement = Arrangement.Center  // Center items vertically within LazyColumn
        ) {
            itemsIndexed(habits) { index, habit ->
                HabitItem(
                    habit = habit,
                    onMarkCompleted = {
                        firebaseRepository.markHabitCompleted(
                            userId = userId,
                            habitId = habit.id,
                            onSuccess = {
                                val updatedHabit = habit.copy(isCompleted = true)
                                habits[index] = updatedHabit // Update the habit in the list
                            },
                            onError = { error ->
                                Log.e(
                                    "FirebaseError",
                                    "Error marking habit completed: ${error.message}"
                                )
                            }
                        )
                    },
                    onDelete = {
                        // Delete habit from Firebase and remove from local list
                        firebaseRepository.deleteHabit(
                            userId = userId,
                            habitId = habit.id,
                            onSuccess = {
                                habits.removeAt(index) // Remove habit from list
                            },
                            onError = { error ->
                                Log.e("FirebaseError", "Error deleting habit: ${error.message}")
                            }
                        )
                    },
                    onEdit = { updatedHabitName ->
                        habits[index] = habit.copy(name = updatedHabitName)
                    }
                )
            }
        }
    }
}

@Composable
fun HabitItem(
    habit: Habit,
    onMarkCompleted: () -> Unit,
    onDelete: () -> Unit,
    onEdit: (String) -> Unit // Callback for editing the habit
) {
    var showDialog by remember { mutableStateOf(false) } // State for details dialog
    var showEditDialog by remember { mutableStateOf(false) } // State for edit dialog
    var editedHabitName by remember { mutableStateOf(habit.name) } // Temporary edited name

    // Details Dialog
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            text = {
                Column {
                    Text(
                        "Habit/Task Name: ",
                        style = TextStyle(fontWeight = FontWeight.Bold)
                    )
                    Text(habit.name)
                    Text(
                        "Completed: ${if (habit.isCompleted) "Yes" else "No"}",
                        style = TextStyle(fontWeight = FontWeight.Bold)
                    )
                }
            },
            confirmButton = {
                OutlinedButton(onClick = { showDialog = false }) {
                    Text("Close")
                }
            }
        )
    }

    // Edit Dialog
    if (showEditDialog) {
        AlertDialog(
            onDismissRequest = { showEditDialog = false },
            text = {
                Column {
                    Text("Edit Habit Name", style = TextStyle(fontWeight = FontWeight.Bold))
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = editedHabitName,
                        onValueChange = { editedHabitName = it },
                        label = { Text("Habit Name") }
                    )
                }
            },
            confirmButton = {
                OutlinedButton(onClick = {
                    if (editedHabitName.isNotBlank()) {
                        onEdit(editedHabitName) // Trigger the callback
                        showEditDialog = false // Close dialog
                    }
                }) {
                    Text("Save")
                }
            },
            dismissButton = {
                OutlinedButton(onClick = { showEditDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    // Habit Card
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clickable { showDialog = true }, // Open details dialog on click
        elevation = 4.dp,
        shape = MaterialTheme.shapes.small
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = habit.name,
                modifier = Modifier.weight(1f),
                style = if (habit.isCompleted) {
                    TextStyle(textDecoration = TextDecoration.LineThrough)
                } else {
                    TextStyle()
                }
            )
            // Mark Completed Button
            if (!habit.isCompleted) {
                IconButton(onClick = onMarkCompleted) {
                    Icon(
                        imageVector = Icons.Default.Done,
                        contentDescription = "Mark as Done",
                        tint = Color(0xFF17EA17)
                    )
                }
            }
            // Edit Button
            IconButton(onClick = { showEditDialog = true }) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Habit",
                    tint = Color(0xFF2196F3)
                )
            }
            // Delete Button
            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete Habit",
                    tint = Color(0xFFFF0000)
                )
            }
        }
    }
}