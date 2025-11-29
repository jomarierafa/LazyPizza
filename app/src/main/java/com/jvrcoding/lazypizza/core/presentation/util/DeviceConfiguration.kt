package com.jvrcoding.lazypizza.core.presentation.util

import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass

enum class DeviceConfiguration {
    MOBILE_PORTRAIT,
    MOBILE_LANDSCAPE,
    TABLET_PORTRAIT,
    TABLET_LANDSCAPE,
    DESKTOP;

    companion object {

        fun isExpandedLayout(windowSizeClass: WindowSizeClass): Boolean {
            val windowSizeClass = fromWindowSizeClass(windowSizeClass)
            return when (windowSizeClass) {
                TABLET_LANDSCAPE -> true
                DESKTOP -> true
                else -> false
            }
        }

        fun fromWindowSizeClass(windowSizeClass: WindowSizeClass): DeviceConfiguration {
            val widthClass = windowSizeClass.widthSizeClass
            val heightClass = windowSizeClass.heightSizeClass

            return when {
                widthClass == WindowWidthSizeClass.Compact &&
                        heightClass == WindowHeightSizeClass.Medium -> MOBILE_PORTRAIT
                widthClass == WindowWidthSizeClass.Compact &&
                        heightClass == WindowHeightSizeClass.Expanded -> MOBILE_PORTRAIT
                widthClass == WindowWidthSizeClass.Expanded &&
                        heightClass == WindowHeightSizeClass.Compact -> MOBILE_LANDSCAPE
                widthClass == WindowWidthSizeClass.Medium &&
                        heightClass == WindowHeightSizeClass.Expanded -> TABLET_PORTRAIT
                widthClass == WindowWidthSizeClass.Expanded &&
                        heightClass == WindowHeightSizeClass.Medium -> TABLET_LANDSCAPE
                else -> DESKTOP
            }
        }
    }
}