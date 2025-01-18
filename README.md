
# Habit/Task Tracker

A feature-rich Habit/Task Tracker Android app designed to help users build and maintain productive habits or manage daily tasks efficiently. Built using modern Android development practices like Jetpack Compose and integrated with Firebase for seamless data management.


## Table of Contents

1. Introduction
2. Features
3. Technologies Used
4. Setup and Installation
5. File Structure
6. Contributing
7. License

### Introduction
The Habit/Task Tracker app empowers users to:
1. Create, manage, and track habits or daily tasks.
2. Set progress goals for each habit.
3. Monitor performance and stay motivated with insightful tracking.
4. The app is designed with a clean, intuitive user interface for a seamless experience.

### Features
1. Habit Creation: Add and customize habits/tasks with names and goals with fast retrieval of habits/tasks from Firebase
2. Progress Tracking: View habit/task status as mark as completed or not
3. Easy Customization: Simply edit the task if you want to change the habit/task with the given edit, delete & mark as completed buttons.
4. Firebase Integration: Cloud data synchronization for habit storage and retrieval.
5. Modern UI: Built using Jetpack Compose for a responsive and visually appealing user experience.
6. Offline Usage: Easy usage in offline mode(mobile data off) with usage of efficient caching.

### Technologies Used
1. Kotlin: Primary programming language.
2. Jetpack Compose: Modern UI toolkit for Android development.
3. Firebase: Backend support for data synchronization and storage.
4. Firebase Realtime Database/Firestore.
5. Gradle: Dependency management and project build system.

### Setup & Installation
1. Clone the repository:
```git clone https://github.com/yourusername/habit-task-tracker.git```
```cd habit-task-tracker```
2. Open the project in Android Studio.
3. Add the google-services.json file (Firebase configuration) to the project root directory.
4. Sync Gradle and build the project:
5. Ensure all dependencies are downloaded.
6. Run the app on an emulator or a physical device.

### File Structure
Habit/Task Tracker
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/com/chamberlyproject/
│   │   │   │   ├── MainActivity.kt               // Main activity of the app
│   │   │   │   ├── model/
│   │   │   │   │   ├── Habit.kt                  // Data model for Habit (e.g., Habit attributes like name, progress)
│   │   │   │   │   ├── User.kt                   // Data model for User (e.g., user information, settings)
│   │   │   │   ├── repository/
│   │   │   │   │   ├── FirebaseRepository.kt     // Repository for handling Firebase data interactions
│   │   │   │   ├── ui/
│   │   │   │   │   ├── HabitTrackerApp.kt        // Main UI container
│   │   │   │   │   ├── components/
│   │   │   │   │   │   ├── HabitItem.kt          // UI for displaying individual Habit items
│   │   │   ├── res/
│   │   │   │   ├── layout/                       // XML layout files (if any traditional Views are used)
│   │   │   │   ├── drawable/                     // App's drawable resources (icons, shapes, etc.)
│   │   │   │   ├── values/
│   │   │   │   │   ├── colors.xml                // Color resources
│   │   │   │   │   ├── themes.xml                // Theme definitions (light and dark modes)
│   │   │   │   │   ├── strings.xml               // String resources for localization
│   │   │   │   │   ├── dimens.xml                // Dimension resources for consistent spacing/sizing
│   │   │   │   ├── xml/
│   │   ├── androidTest/                          // Android Instrumentation tests
│   │   ├── test/                                 // Unit tests for business logic
│   ├── build.gradle                              // Module-level build configuration (dependencies, versioning)
├── google-services.json                          // Firebase configuration file
├── build.gradle                                  // Project-level build configuration (repositories, plugin versions)
├── settings.gradle                               // Settings for Gradle project (configure project structure)

### Contributing
Contributions are welcome! To contribute:
1. Fork the repository.
2. Create a feature branch:
```git checkout -b feature-name```
3.Commit your changes:
```git commit -m "Add your message here"```
4. Push the branch and submit a Pull Request.

### License
This project is licensed under the MIT License. You’re free to use, modify, and distribute it.
