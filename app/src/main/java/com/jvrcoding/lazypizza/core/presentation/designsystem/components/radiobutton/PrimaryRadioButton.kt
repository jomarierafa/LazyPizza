package com.jvrcoding.lazypizza.core.presentation.designsystem.components.radiobutton

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.LazyPizzaTheme
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.body3Medium
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.textPrimary
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.textSecondary

@Composable
fun PrimaryRadioButton(
    text: String,
    selected: Boolean,
    onSelect: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier
            .clip(CircleShape)
            .selectable(
                selected = selected,
                onClick = onSelect,
                role = Role.RadioButton
            )
            .height(48.dp)
            .border(
                border = BorderStroke(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outline,
                ),
                shape = CircleShape
            )
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        RadioButton(
            selected = selected,
            onClick = null,
            modifier = Modifier.scale(0.7f)
        )
        Text(
            text = text,
            style = MaterialTheme.typography.body3Medium,
            color = if(selected) {
                MaterialTheme.colorScheme.textPrimary
            } else {
                MaterialTheme.colorScheme.textSecondary
            }
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PrimaryRadioButtonPreview() {
    LazyPizzaTheme {
        PrimaryRadioButton(
            text = "Earliest available time",
            selected = true,
            onSelect = {},
            modifier = Modifier.padding(top = 60.dp)
        )
    }
}