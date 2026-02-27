# Naver Map Compose for Kotlin Multiplatform

[![Maven Central](https://img.shields.io/badge/Maven_Central-v0.0.1-blue)](https://search.maven.org/artifact/io.github.hiwhwnsgh/naver-map-compose)
[![License](https://img.shields.io/badge/License-Apache_2.0-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0)

## Introduction

**Naver Map Compose** is a wrapper library that brings the power of the [Naver Map SDK](https://navermaps.github.io/android-map-sdk/guide-en/) to [Compose Multiplatform](https://www.jetbrains.com/compose-multiplatform/). It allows you to implement interactive maps for both Android and iOS from a single codebase.

## Progress

This section outlines the current implementation status of the project. While core map functionalities and basic overlays are mostly complete, advanced features like clustering are on the roadmap.

### Overall Status

| Platform | Progress | Status |
| :--- | :--- | :--- |
| **Android** | ![85%](https://progress-bar.xyz/85/?progress_color=3DDC84&width=200) | Stable (Core Features) |
| **iOS** | ![80%](https://progress-bar.xyz/80/?progress_color=147efb&width=200) | Beta (Core Features) |

### Feature Coverage

| Category | Feature | Android | iOS |
| :--- | :--- | :---: | :---: |
| **Map & Camera** | Camera Control (Animate/Fit) | ✅ | ✅ |
| | Camera Events & State Sync | ✅ | ✅ |
| | Map Types & Night Mode | ✅ | ✅ |
| **UI Settings** | Gestures & UI Controls | ✅ | ✅ |
| | Layer Groups (Transit, Traffic, etc.) | ✅ | ✅ |
| **Basic Overlays** | Marker (Caption, Icon, Sizing) | ✅ | ✅ |
| | Polyline / Polygon / Circle | ✅ | ✅ |
| | Path / ArrowheadPath | ✅ | ✅ |
| **Advanced Overlays** | InfoWindow (Marker/Map attached) | ✅ | ✅ |
| | Location Overlay | ✅ | ✅ |
| | GroundOverlay | ❌ | ❌ |
| **Data Visualization** | Clustering | ❌ | ❌ |
| | HeatMap | ❌ | ❌ |
| **Utility** | Snapshot API | ❌ | ❌ |
| | Projection (Screen-to-Map Coords) | ❌ | ❌ |

✅: Supported | ❌: Not Supported

## Installation

### 1. Add Dependency
Add the dependency to your `commonMain` source set in `build.gradle.kts`:

```kotlin
kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation("io.github.hiwhwnsgh:naver-map-compose:0.0.1")
        }
    }
}
```

### 2. iOS Setup (CocoaPods)
To use Naver Map on iOS, you need the native SDK (`NMapsMap`). For detailed instructions, refer to the [official Kotlin Multiplatform documentation for CocoaPods](https://kotlinlang.org/docs/native-cocoapods.html).

#### `build.gradle.kts` Configuration

```kotlin
plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
}

kotlin {
    iosTarget() 

    cocoapods {
        summary = "Naver Map SDK wrapper for Kotlin Multiplatform Compose"
        homepage = "https://github.com/hiwhwnsgh/ComposeNaverMap"
        ios.deploymentTarget = "13.0"

        pod("NMapsMap") {
            version = "3.23.1"
        }
        
        podfile = project.file("../iosApp/Podfile")
    }
}
```

#### API Key (Client ID) Setup
- **Android**: `AndroidManifest.xml`
  ```xml
  <meta-data
      android:name="com.naver.maps.map.CLIENT_ID"
      android:value="YOUR_CLIENT_ID_HERE" />
  ```
- **iOS**: `Info.plist`
  ```xml
  <key>NMFClientId</key>
  <string>YOUR_CLIENT_ID_HERE</string>
  ```

#### Location Permission (Optional)
- **Android**: `AndroidManifest.xml`
  ```xml
  <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
  ```
- **iOS**: `Info.plist`
  ```xml
  <key>NSLocationWhenInUseUsageDescription</key>
  <string>Location permission is required to show your current location on the map.</string>
  ```

## Basic Usage

```kotlin
import io.github.hiwhwnsgh.maps.naver.compose.ui.NaverMap
import io.github.hiwhwnsgh.maps.naver.compose.model.LatLng
import io.github.hiwhwnsgh.maps.naver.compose.state.rememberNaverMapState
import io.github.hiwhwnsgh.maps.naver.compose.model.CameraPosition

@Composable
fun MapScreen() {
    val seoul = LatLng(37.5666102, 126.9783881)
    val mapState = rememberNaverMapState(
        initialPosition = CameraPosition(target = seoul, zoom = 11.0)
    )

    NaverMap(
        modifier = Modifier.fillMaxSize(),
        state = mapState
    ) {
        Marker(
            position = seoul,
            caption = "Seoul City Hall"
        )
    }
}
```

## Advanced Usage

### Camera Control with MapEffect
Use `MapEffect` to safely access the `NaverMapState` and perform declarative map operations, such as moving the camera, after the map is ready.

```kotlin
NaverMap(...) {
    // Move the camera whenever a selected place changes
    MapEffect(selectedPlace) {
        animateCamera(
            position = CameraPosition(target = selectedPlace.latLng, zoom = 14.0),
            durationMs = 1000
        )
    }
}
```

### Map UI Settings & Layers
Fine-tune the map's appearance by configuring UI controls (compass, zoom buttons) and layer groups (traffic, buildings).

```kotlin
val uiSettings = MapUiSettings(
    isLocationButtonEnabled = true,
    isCompassEnabled = false,
    isTrafficLayerGroupEnabled = true,
    isBuildingLayerGroupEnabled = true
)

NaverMap(
    uiSettings = uiSettings,
    isNightModeEnabled = true
)
```

## Key Components

- **`NaverMap`**: The main Composable for displaying the map.
- **`NaverMapState`**: Controls the map's state, such as camera position, animations, and dynamic overlays. Created via `rememberNaverMapState()`.
- **`NaverMapScope`**: The scope provided within the `NaverMap` content block, allowing you to declaratively add `Marker`, `Polyline`, `MapEffect`, etc.
- **`MapUiSettings`**: An immutable object for configuring the map's UI elements (zoom buttons, compass, logo) and layer visibility.

## Contributing

This project is under active development. Bug reports and feature requests (PRs) are always welcome! Contributions are especially appreciated for improving the iOS implementation and adding unsupported features like clustering.

## License

```text
Copyright 2024 Jun Cho

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
...
```
