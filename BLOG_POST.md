# [KMP] Naver Map Compose: Kotlin Multiplatform을 위한 네이버 지도 라이브러리

안녕하세요! 오늘은 Android와 iOS 모두에서 사용할 수 있는 Kotlin Multiplatform(KMP)용 네이버 지도 Compose 라이브러리인 **Naver Map Compose**를 소개합니다.

이 프로젝트는 네이버 지도 SDK를 기반으로 하며, Jetpack Compose의 선언적 UI 프로그래밍 모델을 사용하여 지도를 제어할 수 있도록 설계되었습니다.

## 🚀 주요 특징

- **Kotlin Multiplatform 지원**: 하나의 코드로 Android와 iOS에서 네이버 지도를 구현합니다.
- **Compose 전용 API**: `NaverMap`, `Marker`, `Polyline` 등 친숙한 Compose 컴포넌트를 제공합니다.
- **다양한 오버레이 지원**: 
    - 마커 (Marker)
    - 경로선 (Path Overlay)
    - 화살표 경로선 (Arrowhead Path Overlay)
    - 정보 창 (Info Window)
    - 원형 오버레이 (Circle Overlay)
- **고급 제어**: 카메라 위치 제어, 위치 추적 모드, 지도 컨트롤 커스터마이징 등을 지원합니다.

## 📦 설치 방법

`build.gradle.kts` (또는 라이브러리 관리 파일)에 다음과 같이 추가하여 사용할 수 있습니다.

```kotlin
// gradle.properties 기준 정보
implementation("io.github.hiwhwnsgh:naver-map-compose:0.0.3")
```

## 🛠 사용 예시

가장 기본적인 지도를 띄우고 마커를 추가하는 방법입니다.

```kotlin
@Composable
fun MapSample() {
    // 서울시청 좌표
    val seoulCityHall = LatLng(37.5666102, 126.9783881)
    
    // 지도의 상태 관리 (카메라 위치 등)
    val mapState = rememberNaverMapState(
        initialPosition = CameraPosition(target = seoulCityHall, zoom = 12.0)
    )

    NaverMap(
        modifier = Modifier.fillMaxSize(),
        state = mapState
    ) {
        // 선언적으로 마커 추가
        Marker(
            position = LatLng(37.5666, 126.9783),
            caption = "서울시청",
            iconTintColor = Color.Red.toArgb()
        )
    }
}
```

## 🌟 고급 마커 기능

이 라이브러리는 네이버 지도 SDK의 강력한 기능들을 Compose 스타일로 매끄럽게 제공합니다.

- **줌 제약**: 특정 줌 레벨에서만 마커가 노출되도록 설정할 수 있습니다. (`minZoom`, `maxZoom`)
- **원근감**: 지도를 기울였을 때 마커와 캡션이 입체적으로 누워 보이게 할 수 있습니다. (`isPerspectiveEnabled`)
- **Z-Index**: 지도 위 심볼(건물 이름 등) 아래로 마커를 배치하는 등의 계층 제어가 가능합니다. (`globalZIndex`)

## 📂 프로젝트 데모 구성

현재 프로젝트에는 다음과 같은 기능별 데모 화면이 포함되어 있습니다.

- **Marker Demo**: 기본 마커부터 원근감, 줌 제약이 걸린 고급 마커 테스트
- **Path Demo**: 경로선 및 화살표 경로선 표현
- **InfoWindow Demo**: 마커 클릭 시 나타나는 정보 창 제어
- **Location Demo**: 사용자 실시간 위치 추적 및 권한 처리
- **Map Controls**: 지도 UI 컨트롤(줌 버튼, 내 위치 버튼 등) 커스터마이징

## 🔗 링크 및 참조

- **GitHub Repository**: [ComposeNaverMap](https://github.com/hiwhwnsgh/ComposeNaverMap)
- **Maintainer**: Jun Cho (hiwhwnsgh@gmail.com)

---

KMP 환경에서 네이버 지도를 도입하려는 분들에게 이 라이브러리가 큰 도움이 되기를 바랍니다. 질문이나 제안은 GitHub Issue를 통해 남겨주세요!
