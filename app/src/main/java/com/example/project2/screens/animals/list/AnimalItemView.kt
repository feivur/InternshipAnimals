package com.example.project2.screens.animals.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.example.project2.structure.animals.Animal
import com.example.project2.structure.animals.Mammal
import com.example.project2.structure.animals.Reptile
import com.example.project2.ui.theme.values.S

@Composable
fun AnimalItemView(
    animal: Animal,
    onClick: () -> Unit,
    onCheckedChange: (Boolean) -> Unit,
    isSelected: Boolean,
    deleteMode: Boolean
) {
    val backgroundColor = when (animal) {
        is Mammal -> Color(0xFFFF9800)
        is Reptile -> Color(0xFF03A9F4)
        else -> Color.LightGray
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .padding(S)
            .clickable { onClick() }
    ) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (deleteMode) {
                    Checkbox(checked = isSelected, onCheckedChange = onCheckedChange)
                }
                Text(
                    text = animal.name,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = MaterialTheme.typography.bodyLarge.fontSize
                    )
                )
            }
            Text(
                text = "Color - ${animal.color}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}