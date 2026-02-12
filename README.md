Yummy Planner Android Application

Project Description

A comprehensive Android application designed to help users organize their weekly nutrition by planning meals and discovering new recipes. The app leverages TheMealDB API to provide a vast database of recipes, allowing users to search, filter, and save their favorite dishes for offline access.

Features
Discovery and Search
Meal of the Day: Display of a random meal to inspire users daily.

Advanced Search: Search functionality based on main ingredient, category, or country of origin.

Browsing: View complete lists of meal categories and countries to explore global cuisines.

Meal Details
Comprehensive View: Displays meal name, high-quality image, and origin.

Ingredients: Detailed list of ingredients with corresponding images.

Instructions: Step-by-step preparation guide.

Video Integration: Embedded YouTube video player within the app for visual guidance.

Planning and Storage

Favorites Management: Users can add or remove meals from a personal favorites list.

Weekly Planner: Ability to assign meals to specific days of the current week.

Offline Mode: Users can access their saved favorite meals and their weekly plan without an internet connection.

User Accounts and Sync
Authentication: Support for Email/Password sign-up and social login (Google) via Firebase.

Guest Access: Allows users to browse categories and search for meals without creating an account.

Data Synchronization: Authenticated users can backup their favorites and plans to the cloud (Firebase) and retrieve them upon re-login.

Session Management: Persistent login using SharedPreferences.


Animations: Interactive splash screen using Lottie animations.

Technical Stack
Programming Language: Java.

Architecture: MVP .

Reactive Programming: RxJava - Used for handling all asynchronous operations, API calls, and database transactions.

Local Database: Room - Used for local storage of favorites and meal plans (Offline support).

Networking: Retrofit - For consuming TheMealDB API.

Backend/Cloud: Firebase - Used for Authentication and data backup/sync.

Image Loading: Glide .

UI Components:

Lottie for animations.

Embedded YouTube Player for video playback.

RecyclerViews with custom Adapters.

Implementation Details
Local Storage: Room is the primary source for Favorites and Planning. Firebase is strictly used for synchronization and backup, not as the primary local data handler.

Concurrency: RxJava is implemented throughout the application to ensure smooth UI performance and avoid blocking the main thread.

Connectivity: BroadcastReceivers or Network Callbacks are used to detect connection status and toggle between local (Room) and remote (API) data.

Setup
Clone the repository.

Add your google-services.json file to the app folder for Firebase functionality.

Sync the project with Gradle files.

Build and run the application on an emulator or physical device.
