# 🏥 Medical Cabinet — Application de Gestion de Cabinet Médical
 
**Une application Android professionnelle pour la gestion de cabinets médicaux**  
*Mini-Projet — Licence 3 Informatique | Université Ferhat Abbas Sétif 1*
 
[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](https://opensource.org/licenses/MIT)
![API Level](https://img.shields.io/badge/API-24%2B-brightgreen)
![Language](https://img.shields.io/badge/Language-Java-orange)
![Database](https://img.shields.io/badge/Database-SQLite-lightblue)

  
### 🌐 [**Essayez l'application en direct dans votre navigateur →**](https://appetize.io/app/b_5ycqfnxc7nhzie5cziiqautd24)
*Aucune installation nécessaire — s'exécute dans un véritable émulateur Android directement dans votre navigateur.*

---

## 📋 Table des matières
 
- [À propos](#-à-propos)
- [Fonctionnalités](#-fonctionnalités)
- [Technologies utilisées](#-technologies-utilisées)
- [Architecture](#-architecture)
- [Démarrage](#-démarrage)
- [Structure du projet](#-structure-du-projet)
- [Schéma de la base de données](#-schéma-de-la-base-de-données)
- [Ce que j'ai appris](#-ce-que-jai-appris)
- [Auteur](#-auteur)
---
 
## 🎯 À propos
 
**Medical Cabinet** est une application Android entièrement fonctionnelle conçue pour aider les médecins
à gérer efficacement leur cabinet — sans aucune connexion internet.
Elle a été développée dans le cadre d'un mini-projet universitaire pour le cours de *Développement d'Applications Mobiles*
à l'Université Ferhat Abbas Sétif 1 (2025–2026).
 
> L'application démontre une maîtrise des fondamentaux Android : SQLite, RecyclerView,
> Intents, SharedPreferences, support multilingue et interface Material Design.
 
---
 
## ✨ Fonctionnalités
 
### 🔐 Système d'authentification
- Inscription avec **nom complet**, **email ou téléphone**, et **mot de passe**
- Validation du mot de passe (minimum 6 caractères + confirmation)
- Gestion de session persistante avec **SharedPreferences**
- Fonction de réinitialisation du mot de passe
- Déconnexion sécurisée
### 📊 Tableau de bord
- Statistiques en temps réel : **total des patients**, **rendez-vous**, **rendez-vous du jour**
- Message de bienvenue personnalisé avec le nom du médecin et la date actuelle
- Cartes de navigation rapide vers toutes les sections
### 👥 Gestion des patients
- Ajouter / Modifier / Supprimer des patients avec boîtes de dialogue de confirmation
- Champs : nom, âge, téléphone, antécédents médicaux
- **Recherche en temps réel** par nom de patient (`TextWatcher`)
- RecyclerView avec **avatar circulaire** (initiales)
### 📅 Gestion des rendez-vous
- Planification des rendez-vous avec date, heure et motif
- **Suivi du statut** : `⏳ En attente` / `✅ Présent` / `❌ Absent`
- Changement de statut en un clic avec **badges colorés**
- Tri automatique par date
### 📈 Statistiques
- Nombre total de patients enregistrés
- Nombre total de rendez-vous
- Répartition par statut (Présent / Absent / En attente)
- Nombre de rendez-vous du jour
### 🌍 Support multilingue
- **3 langues** : Arabe 🇩🇿 / Français 🇫🇷 / Anglais 🇬🇧
- Changement de langue instantané avec redémarrage complet de l'application
- Support RTL pour l'arabe
- Toutes les chaînes de caractères externalisées dans `strings.xml` par langue
### 🔔 Notifications
- Notification de rappel quotidien avec la liste des rendez-vous du jour
---
 
## 🛠 Technologies utilisées
 
| Catégorie | Technologie |
|-----------|-------------|
| Langage | Java |
| Plateforme | Android (API 24+) |
| IDE | Android Studio |
| Framework UI | Material Design 3 |
| Base de données | SQLite (via `SQLiteOpenHelper`) |
| Stockage local | SharedPreferences |
| Listes | RecyclerView + Adaptateurs personnalisés |
| Mise en page | ConstraintLayout + CardView |
| Notifications | NotificationCompat |
 
---
 
## 🏗 Architecture
 
L'application suit une **architecture de type MVC** adaptée à Android :
 
```
┌─────────────────────────────────────────────────────┐
│                       VUE                            │
│           Fichiers de mise en page XML (10)          │
├─────────────────────────────────────────────────────┤
│                   CONTRÔLEUR                         │
│              Activités (8 classes)                   │
├──────────────────────┬──────────────────────────────┤
│       MODÈLE         │         AUXILIAIRES           │
│  DatabaseHelper.java │  LanguageHelper.java          │
│  (CRUD SQLite)       │  NotificationHelper.java      │
│                      │  BaseActivity.java            │
└──────────────────────┴──────────────────────────────┘
```
 
### Flux de navigation
 
```
SplashActivity
     │
     ▼
LoginActivity ◄─────────────────────────────┐
     │                                       │ Déconnexion
     ▼                                       │
MainActivity (Tableau de bord)──────────────┘
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
 
## 🚀 Démarrage
 
### Prérequis
 
- Android Studio **Flamingo** ou version ultérieure
- Android SDK **API 24+** (Android 7.0)
- Java **11**
### Installation
 
```bash
# 1. Cloner le dépôt
git clone https://github.com/chalabianehal36/MedicalCabinet.git
 
# 2. Ouvrir dans Android Studio
# Fichier → Ouvrir → sélectionner le dossier du projet
 
# 3. Synchroniser Gradle
# Cliquer sur "Sync Now" lorsque demandé
 
# 4. Exécuter sur un appareil ou un émulateur
# Cliquer sur ▶ Run ou appuyer sur Shift+F10
```
 
### Compilation
 
```bash
# APK de débogage
./gradlew assembleDebug
 
# APK de release
./gradlew assembleRelease
```
 
---
 
## 📁 Structure du projet
 
```
app/
├── src/main/
│   ├── java/com/example/medicalcabinet/
│   │   ├── BaseActivity.java           # Injection de la locale pour toutes les activités
│   │   ├── SplashActivity.java         # Point d'entrée — 2,5 s de splash
│   │   ├── LoginActivity.java          # Inscription + Connexion
│   │   ├── MainActivity.java           # Tableau de bord
│   │   ├── PatientListActivity.java    # Liste des patients + recherche
│   │   ├── AddPatientActivity.java     # Formulaire d'ajout / modification de patient
│   │   ├── AppointmentActivity.java    # Gestion des rendez-vous
│   │   ├── StatsActivity.java          # Tableau de bord des statistiques
│   │   ├── SettingsActivity.java       # Choix de la langue
│   │   ├── DatabaseHelper.java         # SQLite — CRUD complet
│   │   ├── PatientAdapter.java         # Adaptateur RecyclerView pour les patients
│   │   ├── AppointmentAdapter.java     # Adaptateur RecyclerView pour les rendez-vous
│   │   ├── LanguageHelper.java         # Gestion des langues
│   │   └── NotificationHelper.java     # Notifications quotidiennes des rendez-vous
│   │
│   └── res/
│       ├── layout/                     # 10 fichiers de mise en page XML
│       ├── drawable/                   # Icônes vectorielles + fonds dégradés
│       ├── values/                     # Chaînes arabes + couleurs + thèmes
│       ├── values-fr/                  # Chaînes françaises
│       └── values-en/                  # Chaînes anglaises
│
└── AndroidManifest.xml
```
 
---
 
## 🗃 Schéma de la base de données
 
### Table : `patients`
 
| Colonne | Type | Contrainte |
|---------|------|------------|
| `id` | INTEGER | PRIMARY KEY AUTOINCREMENT |
| `name` | TEXT | NOT NULL |
| `age` | INTEGER | — |
| `phone` | TEXT | — |
| `medical_history` | TEXT | — |
 
### Table : `appointments`
 
| Colonne | Type | Contrainte |
|---------|------|------------|
| `id` | INTEGER | PRIMARY KEY AUTOINCREMENT |
| `patient_name` | TEXT | NOT NULL |
| `date` | TEXT | — |
| `time` | TEXT | — |
| `reason` | TEXT | — |
| `status` | TEXT | DEFAULT `'waiting'` |
 
> **Version de la base de données : 2** — migrée depuis la v1 par l'ajout de la colonne `status`
> via `ALTER TABLE` (sans perte de données).
 
---
 
## 📚 Ce que j'ai appris
 
Ce projet m'a permis d'acquérir une expérience pratique dans :
 
- **Le cycle de vie des activités Android** — gestion de l'état lors des transitions d'écran
- **SQLite & SQLiteOpenHelper** — conception de tables, écriture de requêtes CRUD, gestion des migrations
- **RecyclerView + le pattern ViewHolder** — affichage efficace de listes avec des adaptateurs personnalisés
- **La navigation par Intents** — transmission de données entre activités avec `putExtra` / `getExtra`
- **SharedPreferences** — stockage clé-valeur léger et persistant
- **Material Design 3** — construction d'une interface professionnelle avec CardView, FloatingActionButton et ConstraintLayout
- **La localisation Android (i18n)** — support de plusieurs langues avec des qualificatifs de ressources
- **L'API de notifications** — création de canaux de notification compatibles avec Android 8.0+
- **L'architecture MVC** — séparation des responsabilités dans une base de code maintenable
---
 
## 👩‍💻 Auteur
 
**Nehal Chalabia**  
L3 Informatique — Systèmes Informatiques  
Université Ferhat Abbas Sétif 1
 
[![GitHub](https://img.shields.io/badge/GitHub-100000?style=flat&logo=github&logoColor=white)](https://github.com/chalabianehal36-lgtm)
[![Email](https://img.shields.io/badge/Email-D14836?style=flat&logo=gmail&logoColor=white)](mailto:chalabianehal36@gmail.com)
 
---
 
## 📄 Licence
 
Ce projet est distribué sous la **licence MIT** — voir le fichier [LICENSE](LICENSE) pour plus de détails.
