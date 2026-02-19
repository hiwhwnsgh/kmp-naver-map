package io.github.jun.maps.naver

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform