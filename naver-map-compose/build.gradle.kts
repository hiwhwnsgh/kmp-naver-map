import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import com.vanniktech.maven.publish.SonatypeHost
import java.util.Base64

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinCocoapods)
    alias(libs.plugins.mavenPublish)
    signing // 서명 플러그인 적용
}

group = project.property("GROUP") as String
version = project.property("VERSION_NAME") as String

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        version = project.version.toString()
        summary = "Naver Map SDK wrapper for Kotlin Multiplatform Compose"
        homepage = "https://github.com/jun-cho/ComposeNaverMap"
        ios.deploymentTarget = "13.0"
        
        podfile = project.file("../iosApp/Podfile")

        framework {
            baseName = "naver_map_compose"
            isStatic = false
        }
        
        pod("NMapsMap") {
            version = "3.23.1"
        }
    }
    
    sourceSets {
        commonMain.dependencies {
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation(libs.compose.ui)
        }
        androidMain.dependencies {
            implementation(libs.naver.map.sdk)
            implementation(libs.google.play.services.location)
        }
    }
}

android {
    namespace = "io.github.jun.maps.naver.compose"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

// 서명 정보 처리 및 명시적 주입
val keyId = project.findProperty("signing.keyId") as? String
val password = project.findProperty("signing.password") as? String
val secretKeyBase64 = project.findProperty("signing.secretKeyBase64") as? String

if (!secretKeyBase64.isNullOrBlank() && !keyId.isNullOrBlank()) {
    val decodedKey = String(Base64.getDecoder().decode(secretKeyBase64.trim()))
        .replace("\\n", "\n")
    
    // Gradle Signing 확장을 통해 서명자(Signatory)를 명시적으로 설정
    signing {
        @Suppress("UnstableApiUsage")
        useInMemoryPgpKeys(keyId, decodedKey, password ?: "")
        // 말씀하신 sign(publishing.publications)와 유사한 역할을 vanniktech 플러그인이 수행하지만, 
        // 여기서 명시적으로 sign을 호출하여 안전하게 설정합니다.
        sign(publishing.publications)
    }
}

// Vanniktech Maven Publish 설정
mavenPublishing {
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)
    
    // 이미 위에서 signing 블록을 통해 설정했으므로, 
    // 플러그인에게 서명 작업이 필요함을 알립니다.
    signAllPublications()

    pom {
        name.set(project.property("POM_NAME") as String)
        description.set(project.property("POM_DESCRIPTION") as String)
        url.set(project.property("POM_URL") as String)
        licenses {
            license {
                name.set(project.property("POM_LICENCE_NAME") as String)
                url.set(project.property("POM_LICENCE_URL") as String)
            }
        }
        scm {
            url.set(project.property("POM_URL") as String)
            connection.set(project.property("POM_SCM_CONNECTION") as String)
            developerConnection.set(project.property("POM_SCM_DEV_CONNECTION") as String)
        }
        developers {
            developer {
                id.set(project.property("POM_DEVELOPER_ID") as String)
                name.set(project.property("POM_DEVELOPER_NAME") as String)
                email.set(project.property("POM_DEVELOPER_EMAIL") as String)
            }
        }
    }
}
