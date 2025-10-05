package com.jvrcoding.lazypizza.core.presentation.designsystem.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.jvrcoding.lazypizza.R

val INSTRUMENT_SANS = FontFamily(
    Font(
        resId = R.font.instrument_sans_regular,
        weight = FontWeight.Normal
    ),
    Font(
        resId = R.font.instrument_sans_medium,
        weight = FontWeight.Medium
    ),
    Font(
        resId = R.font.instrument_sans_semibold,
        weight = FontWeight.SemiBold
    ),
    Font(
        resId = R.font.instrument_sans_bold,
        weight = FontWeight.Bold
    )
)

val Title1SemiBold =  TextStyle(
    fontFamily = INSTRUMENT_SANS,
    fontWeight = FontWeight.SemiBold,
    fontSize = 24.sp,
    lineHeight = 28.sp
)

val Title2 =  TextStyle(
    fontFamily = INSTRUMENT_SANS,
    fontWeight = FontWeight.SemiBold,
    fontSize = 20.sp,
    lineHeight = 24.sp
)

val Title3 =  TextStyle(
    fontFamily = INSTRUMENT_SANS,
    fontWeight = FontWeight.SemiBold,
    fontSize = 15.sp,
    lineHeight = 22.sp
)

val Label2SemiBold =  TextStyle(
    fontFamily = INSTRUMENT_SANS,
    fontWeight = FontWeight.SemiBold,
    fontSize = 12.sp,
    lineHeight = 16.sp
)

val Body1Regular =  TextStyle(
    fontFamily = INSTRUMENT_SANS,
    fontWeight = FontWeight.Normal,
    fontSize = 16.sp,
    lineHeight = 22.sp
)

val Body1Medium =  TextStyle(
    fontFamily = INSTRUMENT_SANS,
    fontWeight = FontWeight.Medium,
    fontSize = 16.sp,
    lineHeight = 22.sp
)

val Body3Regular =  TextStyle(
    fontFamily = INSTRUMENT_SANS,
    fontWeight = FontWeight.Normal,
    fontSize = 14.sp,
    lineHeight = 18.sp
)

val Body3Medium =  TextStyle(
    fontFamily = INSTRUMENT_SANS,
    fontWeight = FontWeight.Medium,
    fontSize = 14.sp,
    lineHeight = 18.sp
)

val Body3Bold =  TextStyle(
    fontFamily = INSTRUMENT_SANS,
    fontWeight = FontWeight.Bold,
    fontSize = 14.sp,
    lineHeight = 18.sp
)

val Body4Regular =  TextStyle(
    fontFamily = INSTRUMENT_SANS,
    fontWeight = FontWeight.Normal,
    fontSize = 12.sp,
    lineHeight = 16.sp
)

val Typography.title1SemiBold: TextStyle
    get() = Title1SemiBold

val Typography.title2: TextStyle
    get() = Title2

val Typography.title3: TextStyle
    get() = Title3

val Typography.label2SemiBold: TextStyle
    get() = Label2SemiBold

val Typography.body1Regular: TextStyle
    get() = Body1Regular

val Typography.body1Medium: TextStyle
    get() = Body1Medium

val Typography.body3Regular: TextStyle
    get() = Body3Regular

val Typography.body3Medium: TextStyle
    get() = Body3Medium

val Typography.body3Bold: TextStyle
    get() = Body3Bold

val Typography.body4Regular: TextStyle
    get() = Body4Regular
