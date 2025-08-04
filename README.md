# Yoga Admin - Android Studio Management Application

A comprehensive Android application for managing yoga studio operations, including course management, class scheduling, teacher administration, and data synchronization with Firebase.

## ✨ Features

### 🧘‍♀️ Course Management
- **Add/Edit Courses**: Create and modify yoga courses with detailed information
- **Course Details**: Track course name, description, duration, capacity, price, difficulty, and type
- **Teacher Assignment**: Assign instructors to specific courses
- **Schedule Management**: Set recurring schedules with day and time specifications
- **Course Types**: Support for various yoga styles (Hatha, Vinyasa, Ashtanga, etc.)
- **Difficulty Levels**: Beginner, Intermediate, Advanced, and All Levels

### 📅 Class Instance Management
- **Schedule Classes**: Create specific class instances for courses
- **Date Validation**: Ensures classes are scheduled on correct days
- **Teacher Assignment**: Assign teachers to individual class instances
- **Instance Editing**: Modify existing class instances
- **Instance Deletion**: Remove class instances with confirmation

### 👨‍🏫 Teacher Management
- **Teacher Profiles**: Store teacher information (name, email, phone)
- **Teacher Assignment**: Assign teachers to courses and class instances
- **Teacher List**: View and manage all teachers in the system
- **Data Validation**: Email and phone number format validation

### 🔍 Search and Filter
- **Advanced Search**: Search courses by teacher name
- **Date Filtering**: Filter classes by specific dates
- **Day Filtering**: Filter by day of the week
- **Real-time Results**: Instant search results with progress indicators
- **Clear Filters**: Easy filter reset functionality

### 📊 Data Management
- **SQLite Database**: Local data storage with efficient queries
- **Firebase Integration**: Cloud synchronization for data backup
- **Data Reset**: Complete database reset functionality
- **Manual Sync**: User-controlled Firebase synchronization
- **Offline Support**: Full functionality without internet connection

### 🎨 User Interface
- **Material Design**: Modern, intuitive interface
- **Responsive Layout**: Optimized for various screen sizes
- **Card-based Design**: Clean, organized information display
- **Floating Action Buttons**: Quick access to common actions
- **Confirmation Dialogs**: Safe deletion and reset operations

## 🛠️ Technical Stack

### Core Technologies
- **Language**: Java 11
- **Platform**: Android (API 30+)
- **Architecture**: MVC Pattern with DAO
- **Database**: SQLite with Room-like structure
- **Cloud**: Firebase Realtime Database

### Key Dependencies
```kotlin
// Core Android
implementation("androidx.appcompat:appcompat:1.6.1")
implementation("com.google.android.material:material:1.11.0")
implementation("androidx.constraintlayout:constraintlayout:2.1.4")

// Firebase
implementation(platform("com.google.firebase:firebase-bom:33.1.1"))
implementation("com.google.firebase:firebase-database-ktx")

// Charts for Statistics
implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")
```

### Project Structure
```
app/src/main/java/com/example/yogaadmin/
├── activities/          # UI Activities
├── adapters/           # RecyclerView and ListView Adapters
├── database/           # Data Access Objects and Database
├── models/             # Data Models
└── utils/              # Utility Classes
```

## 🚀 Installation

### Prerequisites
- Android Studio Arctic Fox or later
- Android SDK API 30+
- Java 11 or later
- Firebase project setup

### Setup Instructions

1. **Clone the Repository**
   ```bash
   git clone https://github.com/yourusername/yoga-admin.git
   cd yoga-admin
   ```

2. **Firebase Configuration**
   - Create a Firebase project at [Firebase Console](https://console.firebase.google.com/)
   - Download `google-services.json` and place it in the `app/` directory
   - Enable Realtime Database in your Firebase project

3. **Build and Run**
   ```bash
   # Open in Android Studio
   # Sync project with Gradle files
   # Build and run on device/emulator
   ```

### Firebase Setup
1. Go to [Firebase Console](https://console.firebase.google.com/)
2. Create a new project
3. Add Android app with package name: `com.example.yogaadmin`
4. Download `google-services.json` and place in `app/` directory
5. Enable Realtime Database with default security rules

## 📖 Usage Guide

### Getting Started
1. **Launch the App**: Open Yoga Admin on your Android device
2. **Main Dashboard**: Access all features from the main screen
3. **Add Teachers**: Start by adding yoga instructors
4. **Create Courses**: Set up yoga courses with detailed information
5. **Schedule Classes**: Create class instances for your courses

### Course Management
1. **Add Course**: Tap "Add Course" from main screen
2. **Fill Details**: Enter course name, description, duration, capacity, price
3. **Select Options**: Choose difficulty level, type, day, time, and teacher
4. **Save Course**: Confirm and save the course

### Class Scheduling
1. **View Courses**: Navigate to "View Courses"
2. **Manage Instances**: Tap "Manage Instances" for a course
3. **Add Instance**: Select date and teacher for the class
4. **Validation**: System ensures date matches course schedule

### Teacher Management
1. **Add Teacher**: Navigate to "Manage Teachers"
2. **Enter Details**: Provide name, email, and phone number
3. **Validation**: System validates email and phone formats
4. **Assign to Courses**: Teachers can be assigned to courses

### Search and Filter
1. **Search Courses**: Use the search functionality
2. **Filter Options**: Filter by teacher, day, or date
3. **Clear Filters**: Reset search criteria as needed
4. **View Results**: Browse filtered course results

### Data Synchronization
1. **Manual Sync**: Tap "Sync with Firebase" from main screen
2. **Network Check**: App verifies internet connectivity
3. **Upload Data**: Local data is uploaded to Firebase
4. **Reset Database**: Clear all data with confirmation

## 🏗️ Architecture

### Database Schema
```sql
-- Teachers Table
CREATE TABLE teachers (
    _id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    email TEXT,
    phone TEXT
);

-- Courses Table
CREATE TABLE courses (
    _id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    description TEXT,
    teacher_id INTEGER NOT NULL,
    day_of_week TEXT NOT NULL,
    time TEXT NOT NULL,
    duration INTEGER NOT NULL,
    max_capacity INTEGER NOT NULL,
    price REAL NOT NULL,
    difficulty TEXT,
    type TEXT,
    FOREIGN KEY(teacher_id) REFERENCES teachers(_id)
);

-- Class Instances Table
CREATE TABLE class_instances (
    _id INTEGER PRIMARY KEY AUTOINCREMENT,
    course_id INTEGER NOT NULL,
    teacher_id INTEGER NOT NULL,
    date TEXT NOT NULL,
    FOREIGN KEY(course_id) REFERENCES courses(_id),
    FOREIGN KEY(teacher_id) REFERENCES teachers(_id)
);
```

### Key Components

#### Activities
- **MainActivity**: Dashboard with navigation to all features
- **AddCourseActivity**: Course creation and editing
- **ViewCoursesActivity**: Course listing and management
- **ManageTeachersActivity**: Teacher administration
- **SearchActivity**: Advanced search and filtering
- **AddInstanceActivity**: Class instance scheduling
- **ViewInstancesActivity**: Instance management
- **EditInstanceActivity**: Instance editing
- **CourseDetailActivity**: Detailed course information

#### Adapters
- **CourseViewAdapter**: Course display with action buttons
- **CourseRecyclerAdapter**: Simple course listing
- **InstanceAdapter**: Class instance display
- **TeacherAdapter**: Teacher list display
- **CourseSearchAdapter**: Search result display
- **TeacherSpinnerAdapter**: Teacher selection in spinners

#### Database Layer
- **CourseDAO**: Course data access operations
- **TeacherDAO**: Teacher data access operations
- **InstanceDAO**: Class instance data access operations
- **DatabaseHelper**: SQLite database management
- **FirebaseSyncManager**: Cloud synchronization
