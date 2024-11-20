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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.project2.db.AnimalsEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimalDetailScreen(navController: NavController, animal: AnimalsEntity) {
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
                .padding(dimensionResource(id = com.example.project2.R.dimen.size_m)),
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(
                dimensionResource(id = com.example.project2.R.dimen.size_s)
            ) // todo +
        ) {
            Text(
                text = "Animal Type: ${animal.form}",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = dimensionResource(id = com.example.project2.R.dimen.text_size_l).value.sp //
                )
            )
            Text(
                text = "Animal: ${animal.type}",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = dimensionResource(id = com.example.project2.R.dimen.text_size_l).value.sp //
                )
            )
            Text(
                text = "Name: ${animal.name}",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontStyle = FontStyle.Italic,
                    fontSize = dimensionResource(id = com.example.project2.R.dimen.text_size_l).value.sp //
                )
            )
            Text(
                text = "Color: ${animal.color}",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontStyle = FontStyle.Italic,
                    fontSize = dimensionResource(id = com.example.project2.R.dimen.text_size_l).value.sp //
                )
            )
        }
    }
}
