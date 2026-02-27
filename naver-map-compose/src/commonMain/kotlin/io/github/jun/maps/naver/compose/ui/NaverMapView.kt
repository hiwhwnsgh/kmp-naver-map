package io.github.jun.maps.naver.compose.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.github.jun.maps.naver.compose.controller.INaverMapController
import io.github.jun.maps.naver.compose.state.NaverMapState

@Composable
expect fun NaverMapView(
    modifier: Modifier = Modifier,
    state: NaverMapState,
    onMapReady: (INaverMapController) -> Unit = {}
)
