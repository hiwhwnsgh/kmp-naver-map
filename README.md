# Naver Map Compose for Kotlin Multiplatform

[![Maven Central](https://img.shields.io/badge/Maven_Central-v0.0.3-blue)](https://search.maven.org/artifact/io.github.hiwhwnsgh/naver-map-compose)
[![License](https://img.shields.io/badge/License-Apache_2.0-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0)

## Introduction

**Naver Map Compose** is a wrapper library that brings the power of the [Naver Map SDK](https://navermaps.github.io/android-map-sdk/guide-en/) to [Compose Multiplatform](https://www.jetbrains.com/compose-multiplatform/). It allows you to implement interactive maps for both Android and iOS from a single codebase.

## Installation

### 1. Add Gradle Plugin
The easiest way to set up Naver Map is using our Gradle helper plugin. It automatically configures the required Maven repositories and adds the library dependency.

Add this to your **root** `build.gradle.kts` (or sub-project):

```kotlin
plugins {
    id("io.github.hiwhwnsgh.naver-map") version "0.0.3"
}
```

## Basic Usage

Initialize the SDK at the top level of your `App()` using `NaverMapSdkProvider`. This ensures the SDK is ready before any map is rendered.

```kotlin
import io.github.hiwhwnsgh.maps.naver.compose.ui.NaverMap
import io.github.hiwhwnsgh.maps.naver.compose.NaverMapSdkProvider

@Composable
fun App() {
    // Initialize with your Naver Cloud Platform Client ID
    NaverMapSdkProvider(clientId = "YOUR_NAVER_CLIENT_ID_HERE") {
        MaterialTheme {
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
    }
}
```

## Advanced Usage

### Camera Control with MapEffect
Use `MapEffect` to safely access the `NaverMapState` and perform declarative map operations, such as moving the camera, after the map is ready.

```kotlin
NaverMap(...) {
    MapEffect(selectedPlace) {
        animateCamera(
            position = CameraPosition(target = selectedPlace.latLng, zoom = 14.0),
            durationMs = 1000
        )
    }
}
```

## Key Components

- **`NaverMapSdkProvider`**: The root provider to initialize the SDK with your Client ID.
- **`NaverMap`**: The main Composable for displaying the map.
- **`NaverMapState`**: Controls the map's state, such as camera position and animations.
- **`MapUiSettings`**: Configure map UI elements (zoom buttons, compass, logo).

## Contributing

This project is under active development. Bug reports and feature requests (PRs) are always welcome!

## License

Copyright 2024 Jun Cho
Licensed under the Apache License, Version 2.0 (the "License");
