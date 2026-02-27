@file:OptIn(ExperimentalForeignApi::class)

package io.github.jun.maps.naver.compose.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.UIKitInteropProperties
import androidx.compose.ui.viewinterop.UIKitView
import cocoapods.NMapsMap.NMFNaverMapView
import io.github.jun.maps.naver.compose.controller.INaverMapController
import io.github.jun.maps.naver.compose.controller.IosNaverMapController
import io.github.jun.maps.naver.compose.state.NaverMapState
import kotlinx.cinterop.ExperimentalForeignApi

@Composable
actual fun NaverMapView(
    modifier: Modifier,
    state: NaverMapState,
    onMapReady: (INaverMapController) -> Unit
) {
    UIKitView(
        factory = {
            val container = NMFNaverMapView()
            // NaverMapState에 NMFNaverMapView 인스턴스 전달
            state.naverMapView = container
            
            val controller = IosNaverMapController(container.mapView)
            onMapReady(controller)
            container
        },
        modifier = modifier,
        update = { _ ->
            // Update logic if needed
        },
        onRelease = {
            state.naverMapView = null
        },
        properties = UIKitInteropProperties(
            isInteractive = true,
            isNativeAccessibilityEnabled = true
        )
    )
}
