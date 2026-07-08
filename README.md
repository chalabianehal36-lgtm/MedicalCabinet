# 🏥 Medical Cabinet — Application de Gestion de Cabinet Médical
 
**A professional Android application for medical clinic management**  
*Mini-Projet — Licence 3 Informatique | Université Ferhat Abbas Sétif 1*
 
[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](https://opensource.org/licenses/MIT)
![API Level](https://img.shields.io/badge/API-24%2B-brightgreen)
![Language](https://img.shields.io/badge/Language-Java-orange)
![Database](https://img.shields.io/badge/Database-SQLite-lightblue)
 
---
 
## 📋 Table of Contents
 
- [About](#-about)
- [Features](#-features)
- [Tech Stack](#-tech-stack)
- [Architecture](#-architecture)
- [Getting Started](#-getting-started)
- [Project Structure](#-project-structure)
- [Database Schema](#-database-schema)
- [What I Learned](#-what-i-learned)
- [Author](#-author)
---
 
## 🎯 About
 
**Medical Cabinet** is a fully functional Android application designed to help doctors
manage their clinic efficiently — without any internet connection.
It was built as a university mini-project for the *Mobile Application Development* course
at Université Ferhat Abbas Sétif 1 (2025–2026).
 
> The app demonstrates proficiency in Android fundamentals: SQLite, RecyclerView,
> Intents, SharedPreferences, multi-language support, and Material Design UI.
 
---
 
## ✨ Features
 
### 🔐 Authentication System
- Account registration with **full name**, **email or phone**, and **password**
- Password validation (minimum 6 characters + confirmation)
- Persistent session management with **SharedPreferences**
- Password reset functionality
- Secure logout
### 📊 Dashboard
- Real-time statistics: **total patients**, **appointments**, **today's appointments**
- Personalized greeting with doctor's name and current date
- Quick navigation cards to all sections
### 👥 Patient Management
- Add / Edit / Delete patients with confirmation dialogs
- Fields: name, age, phone, medical history
- **Real-time search** by patient name (`TextWatcher`)
- RecyclerView with **circular avatar** (initials)
### 📅 Appointment Management
- Schedule appointments with date, time, and reason
- **Status tracking**: `⏳ Waiting` / `✅ Present` / `❌ Absent`
- One-tap status change with **color-coded badges**
- Auto-sorted by date
### 📈 Statistics
- Total registered patients
- Total appointments
- Breakdown by status (Present / Absent / Waiting)
- Today's appointment count
### 🌍 Multi-Language Support
- **3 languages**: Arabic 🇩🇿 / French 🇫🇷 / English 🇬🇧
- Instant language switching with full app restart
- RTL support for Arabic
- All strings externalized in `strings.xml` per locale
### 🔔 Notifications
- Daily reminder notification with today's appointments list
---
 
## 🛠 Tech Stack
 
| Category | Technology |
|----------|-----------|
| Language | Java |
| Platform | Android (API 24+) |
| IDE | Android Studio |
| UI Framework | Material Design 3 |
| Database | SQLite (via `SQLiteOpenHelper`) |
| Local Storage | SharedPreferences |
| Lists | RecyclerView + Custom Adapters |
| Layout | ConstraintLayout + CardView |
| Notifications | NotificationCompat |
 
---
 
## 🏗 Architecture
 
The app follows an **MVC-like architecture** adapted for Android:
 
```
┌─────────────────────────────────────────────────────┐
│                      VIEW                           │
│           XML Layouts (10 files)                    │
├─────────────────────────────────────────────────────┤
│                   CONTROLLER                        │
│           Activities (8 classes)                    │
├──────────────────────┬──────────────────────────────┤
│       MODEL          │         HELPERS              │
│  DatabaseHelper.java │  LanguageHelper.java         │
│  (SQLite CRUD)       │  NotificationHelper.java     │
│                      │  BaseActivity.java           │
└──────────────────────┴──────────────────────────────┘
```
 
### Navigation Flow
 
```
SplashActivity
     │
     ▼
LoginActivity ◄─────────────────────────────┐
     │                                       │ Logout
     ▼                                       │
MainActivity (Dashboard)────────────────────┘
     │
     ├──► PatientListActivity ──► AddPatientActivity
     │
     ├──► AppointmentActivity
     │
     ├──► StatsActivity
     │
     └──► SettingsActivity
```
 
---
 
## 🚀 Getting Started
 
### Prerequisites
 
- Android Studio **Flamingo** or later
- Android SDK **API 24+** (Android 7.0)
- Java **11**
### Installation
 
```bash
# 1. Clone the repository
git clone https://github.com/chalabianehal36/MedicalCabinet.git
 
# 2. Open in Android Studio
# File → Open → select the project folder
 
# 3. Sync Gradle
# Click "Sync Now" when prompted
 
# 4. Run on device or emulator
# Click ▶ Run or press Shift+F10
```
 
### Build
 
```bash
# Debug APK
./gradlew assembleDebug
 
# Release APK
./gradlew assembleRelease
```
 
---
 
## 📁 Project Structure
 
```
app/
├── src/main/
│   ├── java/com/example/medicalcabinet/
│   │   ├── BaseActivity.java           # Locale injection for all activities
│   │   ├── SplashActivity.java         # Entry point — 2.5s splash
│   │   ├── LoginActivity.java          # Register + Login
│   │   ├── MainActivity.java           # Dashboard
│   │   ├── PatientListActivity.java    # Patient list + search
│   │   ├── AddPatientActivity.java     # Add / Edit patient form
│   │   ├── AppointmentActivity.java    # Appointment management
│   │   ├── StatsActivity.java          # Statistics dashboard
│   │   ├── SettingsActivity.java       # Language selection
│   │   ├── DatabaseHelper.java         # SQLite — full CRUD
│   │   ├── PatientAdapter.java         # RecyclerView adapter for patients
│   │   ├── AppointmentAdapter.java     # RecyclerView adapter for appointments
│   │   ├── LanguageHelper.java         # Locale management
│   │   └── NotificationHelper.java     # Daily appointment notifications
│   │
│   └── res/
│       ├── layout/                     # 10 XML layout files
│       ├── drawable/                   # Vector icons + gradient backgrounds
│       ├── values/                     # Arabic strings + colors + themes
│       ├── values-fr/                  # French strings
│       └── values-en/                  # English strings
│
└── AndroidManifest.xml
```
 
---
 
## 🗃 Database Schema
 
### Table: `patients`
 
| Column | Type | Constraint |
|--------|------|-----------|
| `id` | INTEGER | PRIMARY KEY AUTOINCREMENT |
| `name` | TEXT | NOT NULL |
| `age` | INTEGER | — |
| `phone` | TEXT | — |
| `medical_history` | TEXT | — |
 
### Table: `appointments`
 
| Column | Type | Constraint |
|--------|------|-----------|
| `id` | INTEGER | PRIMARY KEY AUTOINCREMENT |
| `patient_name` | TEXT | NOT NULL |
| `date` | TEXT | — |
| `time` | TEXT | — |
| `reason` | TEXT | — |
| `status` | TEXT | DEFAULT `'waiting'` |
 
> **Database version: 2** — migrated from v1 by adding the `status` column
> via `ALTER TABLE` (no data loss).
 
---
 
## 📚 What I Learned
 
This project helped me gain hands-on experience with:
 
- **Android Activity Lifecycle** — managing state across screen transitions
- **SQLite & SQLiteOpenHelper** — designing tables, writing CRUD queries, handling migrations
- **RecyclerView + ViewHolder pattern** — efficient list rendering with custom adapters
- **Intent-based navigation** — passing data between activities with `putExtra` / `getExtra`
- **SharedPreferences** — lightweight persistent key-value storage
- **Material Design 3** — building a professional UI with CardView, FloatingActionButton, and ConstraintLayout
- **Android Localization (i18n)** — supporting multiple languages with resource qualifiers
- **Notification API** — creating notification channels compatible with Android 8.0+
- **MVC Architecture** — separating concerns in a maintainable codebase
---
 
## 👩‍💻 Author
 
**Nehal Chalabia**  
L3 Informatique — Systèmes Informatiques  
Université Ferhat Abbas Sétif 1
 
[![GitHub](https://img.shields.io/badge/GitHub-100000?style=flat&logo=github&logoColor=white)](https://github.com/chalabianehal36-Igtm)
[![Email](https://img.shields.io/badge/Email-D14836?style=flat&logo=gmail&logoColor=white)](mailto:chalabianehal36@gmail.com)
 
---
 
## 📄 License
 
This project is licensed under the **MIT License** — see the [LICENSE](LICENSE) file for details.
