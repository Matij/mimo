# Mimo Mobile Coding Challenge (Android)

This project is a simplified Android implementation of the Mimo lesson experience.  
It fetches lessons from a remote API, renders them according to their content definition, handles user input validation, and persists lesson completion events locally.

The goal of this exercise was to focus on **clarity, correctness, and UX**, while keeping the implementation intentionally minimal.

---

## Overview

At app startup, the first lesson is displayed.  
Each lesson consists of:

- A list of text segments, each with its own color
- An optional input range that the user must complete correctly
- A “Next” button that advances only when the lesson is solved

When a lesson is completed, a **lesson completion event** is stored locally.  
After the final lesson, the app displays a simple **“Done”** screen.

---

## Architecture

The app follows a clean, layered architecture:

- **UI layer**
    - Jetpack Compose for declarative UI
    - ViewModels manage UI state and user interactions

- **Domain layer**
    - Core business models (Lesson, LessonContent, LessonCompletionEvent)
    - Pure logic for lesson validation and rendering rules

- **Data layer**
    - Network source for fetching lessons
    - Local database for persisting lesson completion events
    - Repository coordinating data access

This separation keeps responsibilities clear and makes the codebase easy to reason about and extend.

---

## State Management

UI state is driven by the ViewModel and exposed using Kotlin `Flow`.

State includes:
- Current lesson
- User input
- Validation result
- Navigation state (next lesson / done)

Derived values (such as leading, input, and trailing text segments) are computed in a predictable way to avoid UI logic duplication.

---

## Lesson Rendering

Lessons are rendered based on their JSON definition:

- Text segments are displayed with individual colors
- The input range is extracted using the provided `startIndex` and `endIndex`
- Leading, input, and trailing segments are derived from the lesson content
- The “Next” button:
    - Is disabled until the user enters input (if required)
    - Validates the input before allowing progression

This logic mirrors the mental model of how lessons are defined and keeps rendering deterministic.

---

## Local Persistence

When a lesson is solved, a **Lesson Completion Event** is stored locally, containing:

- Lesson ID
- Timestamp when the lesson was started
- Timestamp when the lesson was completed

For this exercise, a local database was chosen to model completion as an **event**, rather than a boolean flag.  
The implementation is intentionally minimal: a single entity, DAO, and repository.

In a production app, this could later be extended to support analytics, syncing, or retries.

---

## Tech Stack

- Kotlin
- Jetpack Compose
- Coroutines & Flow
- Hilt for dependency injection
- Local persistence via a lightweight database abstraction
- Retrofit (or equivalent) for networking

---

## Trade-offs & Notes

- Persistence is kept simple on purpose; advanced concerns like migrations or syncing are out of scope for this exercise.
- Lesson start and completion timestamps are recorded together for simplicity.
- The focus was on readable, maintainable code rather than premature optimization.

---

## Possible Next Improvements

If this were to be extended further:
- Persist lesson start events separately
- Add basic unit tests for lesson validation logic
- Improve state restoration across process death
- Support partial progress or retries

---

## Final Notes

This project intentionally avoids overengineering and focuses on delivering a clear, correct, and user-friendly solution that reflects real-world Android development practices.
