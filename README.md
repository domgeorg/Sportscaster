# Sportscaster

Sportscaster is an Android app that keeps sports enthusiasts informed about upcoming events. The app loads sports events from the network, categorizing them by the sport, such as football, basketball, tennis, and more.

## Features

- **Explore Events:** Immerse yourself in the world of sports with our intuitive event browser. Effortlessly navigate and discover a diverse array of sports events, neatly categorized by sport. Explore the excitement of upcoming games at your fingertips.
- **Detailed Insights:** Dive deep into the world of sports with comprehensive event details. Uncover a wealth of information about each event, including in-depth insights into the participating teams or individuals, ensuring you stay well-informed about every aspect of the upcoming competition.
- **Countdown Timer:** Never miss a moment! Stay informed about upcoming games with a precise countdown timer. It dynamically calculates and displays the time remaining until the start of each event, breaking it down into days, hours, minutes, and seconds for a comprehensive overview.
- **Favorites:** Personalize your experience by marking events as favorites for quick access and streamlined tracking. Tailor your sports journey by curating a list of events that matter most to you.
- **Filter Favorites:** Navigate effortlessly through your personalized sports universe. Utilize the filter option to view and focus exclusively on your favorite events, ensuring a customized and immersive experience tailored to your preferences.

## Screenshots
![Home Screen Screenshot1](https://i.imgur.com/0WapWdD.png)
___
![Home Screen Screenshot1](https://i.imgur.com/IoxFrdP.png)

## Architecture

The Sportscaster app employs the Clean Architecture, which is structured into three main layers:

### Data Layer

The data layer encapsulates the details of data sources and persistence, shielding the internal layers. It is responsible for retrieving and storing data through a defined repository.

#### Domain Layer

The domain layer defines the core elements of the software, independent of specific application layers. It includes business models, rules, entities, and use cases. This layer is crucial as it houses the business logic of our application, managing specific operational rules without knowledge from outer layers. In the domain layer, specific abstract use cases are defined, each with a single responsibility. These use cases encapsulate the business logic of the application, providing a clear separation from the outer layers. They are designed to be versatile and can be utilized across various sections of the application.

#### Presentation Layer

The presentation layer acts as the application's user interface design, involving user interaction and data presentation. Sportscaster follows the Unidirectional Data Flow (UDF) pattern, also known as State Hoisting. This architectural pattern ensures a uni-directional stream of information, where the state descends and the events ascend.

### Architecture Diagram

![Architecture](https://i.imgur.com/5XIgXMM.jpg)


## Core-Modules

### 1. `core-resources`

Encapsulates Android resources (fonts, animations, string values) for clean separation, enhancing maintainability and reusability.

### 2. `core-designsystem`

Centralizes Jetpack Compose UI components and an app-specific design system for consistent UI across the app, simplifying component creation.

### 3. `core-navigation`

Manages navigation events using `NavController`, providing a clean structure and decoupling navigation concerns from other app parts.

### 4. `core-network`

Handles network data fetching logic, ensuring clean separation for easy modification or extension without affecting other app parts.

### 5. `core-database`

Manages data storage logic, including `SportEventDao`, achieving separation of concerns and facilitating database implementation upgrades.

### 6. `core-data`

Bridges `core-network` and `core-database`, abstracting data retrieval intricacies. Ensures the caller remains agnostic to data origin for a clean, modular architecture.

### 7. `core-domain`

Shapes high-level app functionality, exposing well-defined, abstract use cases with single responsibilities. Enables seamless integration across various app sections, fostering a modular architecture.

## Libraries Used

1. **KSP (Kotlin Symbol Processing):** KSP is a Kotlin compiler plugin that facilitates the generation of code during compilation. It's instrumental in reducing boilerplate code and improving overall code generation.

2. **Coroutines-Test:** This library provides testing utilities for Kotlin coroutines, allowing for the efficient testing of asynchronous code in a controlled environment.

3. **MockK:** MockK is a mocking library for Kotlin that simplifies the process of creating mocks for testing. It enhances the ease of writing test cases and improves overall testability.

4. **Hilt:** Hilt is a dependency injection library that is built on top of Dagger. It simplifies the implementation of Dagger for Android apps, making dependency injection more straightforward.

5. **Retrofit:** Retrofit simplifies the process of making network requests. It seamlessly converts HTTP API calls to Kotlin functions.

6. **Moshi:** Moshi is a modern JSON library for Kotlin and Java. It facilitates the parsing of JSON data into Kotlin objects and vice versa, enhancing data serialization and deserialization.

7. **Room:** Room is a persistence library that provides an abstraction layer over SQLite. It simplifies database operations and allows for efficient data storage.

8. **Jetpack Compose:** Jetpack Compose is a modern UI toolkit for building native Android UIs. It simplifies UI development by using a declarative syntax and offers a more intuitive approach to UI design.

9. **Lottie-Compose:** Lottie-Compose is a library for playing Lottie animations in Jetpack Compose. Lottie animations are vector animations that can be easily integrated into Android applications.

10. **Android-Navigation-Compose:** This library simplifies navigation in Jetpack Compose applications, providing a declarative way to navigate between different screens.

11. **AndroidX-Arch-Core-Test:** This library provides testing utilities for AndroidX architecture components, allowing for the efficient testing of components like ViewModels and LiveData.

12. **AndroidX-Compose-Material3:** This library extends Jetpack Compose by providing Material Design 3 components, enhancing the visual and interactive aspects of the app's UI with the latest design principles.

## Icons Attribution

The icons used in the Sportscaster app are sourced from [icons8.com](https://icons8.com/), and all rights are reserved to Icons8.
