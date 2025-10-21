package com.jvrcoding.lazypizza.product.presentation.cart

import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.jvrcoding.lazypizza.R
import com.jvrcoding.lazypizza.core.presentation.designsystem.components.button.PrimaryButton
import com.jvrcoding.lazypizza.core.presentation.designsystem.components.toolbar.SecondaryToolbar
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.LazyPizzaTheme
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.textPrimary
import com.jvrcoding.lazypizza.core.presentation.util.DeviceConfiguration
import com.jvrcoding.lazypizza.product.presentation.cart.presentation.components.RecommendedAddOnsSection
import com.jvrcoding.lazypizza.product.presentation.cart.presentation.components.cartListContent

@Composable
fun CartScreenRoot() {
    CartScreen()
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun CartScreen(
    deviceConfiguration: DeviceConfiguration? = null
) {
    Scaffold(
        topBar = {
            SecondaryToolbar(
                title = stringResource(R.string.cart),
                showBackButton = false
            )
        }
    ) { innerPadding ->
        val currentDeviceConfiguration = deviceConfiguration ?: run {
            val activity = LocalActivity.current as ComponentActivity
            val windowSizeClass = calculateWindowSizeClass(activity = activity)
            DeviceConfiguration.fromWindowSizeClass(windowSizeClass)
        }

        when(currentDeviceConfiguration) {
            DeviceConfiguration.MOBILE_PORTRAIT,
            DeviceConfiguration.MOBILE_LANDSCAPE,
            DeviceConfiguration.TABLET_PORTRAIT -> {
                Box(
                    modifier = Modifier
                        .padding(top = innerPadding.calculateTopPadding())
                        .fillMaxSize()
                ) {
                    LazyColumn(
                        modifier = Modifier,
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(bottom = 64.dp)
                    ) {
                        cartListContent()
                        item {
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                        item {
                            RecommendedAddOnsSection()
                        }
                    }
                    PrimaryButton(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter),
                        text = "Proceed to checkout ${1}",
                        onClick = {}
                    )
                }

            }
            DeviceConfiguration.TABLET_LANDSCAPE,
            DeviceConfiguration.DESKTOP -> {
                Row(
                    modifier = Modifier
                        .padding(top = innerPadding.calculateTopPadding())
                        .fillMaxSize(),
                ) {
                    LazyColumn(
                        modifier = Modifier.width(380.dp),
                        contentPadding = PaddingValues(bottom = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        cartListContent()
                    }
                    Column(
                        modifier = Modifier
                            .clip(RoundedCornerShape(topStart = 16.dp, bottomStart = 16.dp))
                            .dropShadow(
                                shape = RectangleShape,
                                shadow = Shadow(
                                    radius = 16.dp,
                                    spread = 0.dp,
                                    color = MaterialTheme.colorScheme.textPrimary.copy(alpha = 0.04f),
                                    offset = DpOffset(x = 0.dp, (-4).dp)
                                )
                            )
                            .background(MaterialTheme.colorScheme.surfaceContainerHigh)
                            .padding(vertical = 16.dp),
                    ) {
                        RecommendedAddOnsSection()
                        PrimaryButton(
                            modifier = Modifier
                                .padding(horizontal = 16.dp )
                                .fillMaxWidth(),
                            text = "Proceed to checkout ${1}",
                            onClick = {}
                        )
                    }
                }
            }
        }
    }
}


@Preview(widthDp = 840, heightDp = 1600)
@Composable
private fun CartScreenPreview() {
    LazyPizzaTheme {
        CartScreen(deviceConfiguration = DeviceConfiguration.TABLET_LANDSCAPE)
    }
}
