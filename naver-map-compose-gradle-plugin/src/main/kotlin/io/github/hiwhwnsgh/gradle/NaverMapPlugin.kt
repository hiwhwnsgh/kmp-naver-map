package io.github.hiwhwnsgh.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import java.net.URI

class NaverMapPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        // 1. 네이버 지도 저장소를 프로젝트에 추가
        project.repositories.maven {
            url = URI("https://repository.map.naver.com/archive/maven")
        }

        // 2. 라이브러리 의존성을 자동으로 추가 (사용자가 implementation을 쓰지 않아도 되게 함)
        // 프로젝트가 평가된 후(afterEvaluate) 실행하거나, 직접 configurations에 추가
        project.configurations.getByName("commonMainApi").dependencies.add(
            project.dependencies.create("io.github.hiwhwnsgh:naver-map-compose:${project.version}")
        )
    }
}
