package com.example.project2.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.project2.ui.theme.values.M
import com.example.project2.ui.theme.values.S
import com.example.project2.ui.theme.values.text_L
import com.example.project2.viewmodel.AnimalsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimalDetailScreen(
    navController: NavController,
    animalId: Long?,
    animalsViewModel: AnimalsViewModel
) {
    val animal = animalId?.let { animalsViewModel.getAnimalById(it) }



    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Animal Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(M),
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(
                S
            ) // todo +
        ) {
            if (animal != null) {
                Text(
                    text = "Animal Type: ${animal.form}",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = text_L.value.sp //
                    )
                )
            }
            if (animal != null) {
                Text(
                    text = "Animal: ${animal.type}",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = text_L.value.sp //
                    )
                )
            }
            if (animal != null) {
                Text(
                    text = "Name: ${animal.name}",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontStyle = FontStyle.Italic,
                        fontSize = text_L.value.sp //
                    )
                )
            }
            if (animal != null) {
                Text(
                    text = "Color: ${animal.color}",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontStyle = FontStyle.Italic,
                        fontSize = text_L.value.sp //
                    )
                )
            }
        }
    }
}
