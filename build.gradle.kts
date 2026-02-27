import java.util.Properties

plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.androidKotlinMultiplatformLibrary) apply false
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.kotlinCocoapods) apply false
    alias(libs.plugins.mavenPublish) apply false
}

// 1. local.properties 로드 (로컬 개발용)
val localProperties = Properties()
val localPropertiesFile = rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
    localPropertiesFile.inputStream().use { localProperties.load(it) }
}

// 2. 통합 시크릿 로더 정의: CI 환경변수 > gradle.properties > local.properties 순서로 확인
fun getPublishSecret(key: String): String? {
    return System.getenv(key) 
        ?: project.findProperty(key)?.toString() 
        ?: localProperties.getProperty(key)
}

// 3. 배포용 자격 증명 추출
val mavenUser = getPublishSecret("MAVEN_CENTRAL_USERNAME") ?: ""
val mavenPassword = getPublishSecret("MAVEN_CENTRAL_PASSWORD") ?: ""

// 4. Vanniktech 플러그인의 내부 서비스(build-service)를 위해 시스템 프로퍼티로 주입
if (mavenUser.isNotEmpty()) {
    System.setProperty("org.gradle.project.mavenCentralUsername", mavenUser)
    System.setProperty("org.gradle.project.mavenCentralPassword", mavenPassword)
    System.setProperty("org.gradle.project.sonatypeUsername", mavenUser)
    System.setProperty("org.gradle.project.sonatypePassword", mavenPassword)
}

// 5. 서브 프로젝트에서 사용할 수 있도록 extra 속성에 함수 저장
allprojects {
    extra.set("getPublishSecret", ::getPublishSecret)
}
