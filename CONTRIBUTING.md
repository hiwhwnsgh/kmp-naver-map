# Contributing to Naver Map Compose

First off, thank you for considering contributing to Naver Map Compose! It's people like you that make this library a great tool for the Kotlin Multiplatform community.

## How Can I Contribute?

### Reporting Bugs
- Check the [Issues](https://github.com/hiwhwnsgh/kmp-naver-map/issues) tab to see if the bug has already been reported.
- If not, create a new issue. Include a clear title, a description of the problem, steps to reproduce, and the expected behavior.

### Suggesting Enhancements
- Open a new issue with the tag `enhancement`.
- Describe the feature you'd like to see and why it would be useful.

### Pull Requests
1. Fork the repository.
2. Create a new branch for your feature or bug fix: `git checkout -b feature/your-feature-name`.
3. Make your changes.
4. Ensure the project builds locally (see [Local Development](#local-development)).
5. Commit your changes with a clear message.
6. Push to your fork and submit a pull request to the `main` branch.

## Local Development

### Prerequisites
- Android Studio Koala or newer.
- JDK 17.
- A Naver Cloud Platform Client ID (to run the sample app).

### Setup
1. Clone your fork.
2. Create a `local.properties` file in the root directory.
3. Add your Naver Client ID to `local.properties`:
   ```properties
   NAVER_CLIENT_ID=your_actual_client_id_here
   ```
4. Sync the project with Gradle.

### Running the Sample App
- **Android**: Select the `composeApp` run configuration and run on an emulator or device.
- **iOS**: 
  - Run `pod install` in the `iosApp` directory.
  - Open `iosApp/iosApp.xcworkspace` in Xcode.
  - Or use the `iosApp` run configuration in Android Studio (with the KMP plugin).

## Code Style
- Follow the [Kotlin Coding Conventions](https://kotlinlang.org/docs/coding-conventions.html).
- Use meaningful variable and function names.
- Add KDoc comments for new public APIs.

## License
By contributing, you agree that your contributions will be licensed under its [Apache License 2.0](LICENSE).
