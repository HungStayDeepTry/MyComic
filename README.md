
# 📚 MangaReader - MangaDex Inspired Android App

MangaReader is a modern, modular, and scalable Android application designed to deliver a smooth manga reading experience. Built with **Jetpack Compose**, **Hilt**, and **Clean Architecture**, this app supports **offline-first** functionality for key features like home and reading chapters.

---

## 🚀 Features (Phase 1 - MVP)

### 🔍 Manga Discovery
- Home: Latest updated manga (with offline caching)
- Search: Basic & Advanced Search
- Manga Detail View
- Chapter Listing per Manga
- Read Chapter (with offline support)

### 📚 Library & Tracking
- Bookmark your favorite manga
- Recently Read / History
- Language Filter & Genre browsing

### ⚙️ Settings
- Dark mode / Light mode
- Image quality and reading preferences

---

## 🧱 Tech Stack

| Layer           | Tech / Library                                  |
|----------------|--------------------------------------------------|
| UI             | Jetpack Compose                                  |
| DI             | Hilt                                             |
| Network        | Retrofit + OkHttp                                |
| Persistence    | Room (SQLite), DataStore                         |
| Architecture   | Clean Architecture + MVVM                        |
| Offline-first  | NetworkBoundResource pattern (manual or library) |
| Modularization | Gradle modules (multi-module setup)              |
| Pagination     | Paging 3 (optional for later)                    |

---

## 🧩 Modularization Structure

```
:app
:core:network
:core:database
:core:model
:core:common-ui
:feature:home
:feature:search
:feature:reader
:feature:detail
:feature:bookmark
:feature:settings
```

Each feature is isolated for scalability and maintainability.

---

## 🧠 Architecture

### Clean Architecture Layers:
```
📦 domain
 ┣ 📂 model
 ┣ 📂 repository
 ┗ 📂 usecase

📦 data
 ┣ 📂 remote (Retrofit + DTOs)
 ┣ 📂 local (Room DAOs, Entities)
 ┣ 📂 mapper
 ┗ 📂 repositoryImpl

📦 presentation
 ┣ 📂 ui (Screens & Composables)
 ┗ 📂 viewmodel
```

---

## 🔌 API

This app uses the **[MangaDex API v5](https://api.mangadex.org/docs/)**  
All data (manga, chapters, authors, etc.) are fetched from MangaDex, with selective offline caching.

---

## 🗂️ Project Goals

- ✅ Build a maintainable and scalable codebase
- ✅ Support offline-first experience where applicable
- ✅ Follow modern Android development best practices
- ⏳ Add full MangaDex features incrementally
- 🚧 Future: User login, Notifications, Downloads Manager...

---

## 🛠️ Setup Instructions

```bash
# Clone the repo
git clone https://github.com/your-username/mangareader.git
cd mangareader

# Open in Android Studio
# Sync Gradle & Run the app on device or emulator
```

---

## 💡 Contribution

Feel free to fork and open a PR!  
Or open an issue for bugs / feature requests.  
(Multi-language support, performance, etc.)

---

## 📜 License

MIT License. This project is for educational and personal-use purposes.

---

## 👨‍💻 Author

Made with ❤️ by HungStayDeepTry
Contact: hchu557@gmail.com
