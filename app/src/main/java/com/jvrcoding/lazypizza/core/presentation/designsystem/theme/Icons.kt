package com.jvrcoding.lazypizza.core.presentation.designsystem.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.jvrcoding.lazypizza.R

val PlusIcon: ImageVector
    @Composable
    get() = ImageVector.vectorResource(id = R.drawable.ic_add)

val MinusIcon: ImageVector
    @Composable
    get() = ImageVector.vectorResource(id = R.drawable.ic_minus)

val TrashIcon: ImageVector
    @Composable
    get() = ImageVector.vectorResource(id = R.drawable.ic_trash)

val PhoneIcon: ImageVector
    @Composable
    get() = ImageVector.vectorResource(id = R.drawable.ic_phone)

val PizzaIcon: ImageVector
    @Composable
    get() = ImageVector.vectorResource(id = R.drawable.ic_pizza)

val SearchIcon: ImageVector
    @Composable
    get() = ImageVector.vectorResource(id = R.drawable.ic_search)

val ArrowLeftIcon: ImageVector
    @Composable
    get() = ImageVector.vectorResource(id = R.drawable.ic_arrow_left)

val ProfileIcon: ImageVector
    @Composable
    get() = ImageVector.vectorResource(id = R.drawable.ic_profile)

val LogoutIcon: ImageVector
    @Composable
    get() = ImageVector.vectorResource(id = R.drawable.ic_logout)