package com.example.project2

//import androidx.benchmark.perfetto.Row --
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

enum class AnimalType {
    Mammal, Reptile, Cat, Dog, Frog, Triton
}

@Composable
fun AnimalSelectionScreen(
    onDismissRequest: () -> Unit,
    onSubmit: (Animal?) -> Unit
) {

    var selectedType by remember { mutableStateOf(AnimalType.Mammal) }
    var selectedAnimal by remember { mutableStateOf(AnimalType.Cat) }
    var name by remember { mutableStateOf("") }
    var color by remember { mutableStateOf("") }
    val isFormValid = name.isNotEmpty() && color.isNotEmpty()

    Dialog(
        onDismissRequest = onDismissRequest
    ) {
        //cписок животных в зависимости от выбраного типа
        val animalList = remember(selectedType) {
            if (selectedType == AnimalType.Mammal) listOf(
                AnimalType.Cat,
                AnimalType.Dog
            ) else listOf(AnimalType.Frog, AnimalType.Triton)
        }

        LaunchedEffect(selectedType) {
            selectedAnimal =
                animalList.first()  //выбираем первое животное если поменяли тип животного
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .background(Color.White) //todo Добавление фона
        ) {
            Text("Animal Type", style = MaterialTheme.typography.bodyLarge)

            // выбор типа животного
            Row {
                RadioButton(
                    selected = selectedType == AnimalType.Mammal,
                    onClick = { selectedType = AnimalType.Mammal }
                )
                Text("Mammal", modifier = Modifier.padding(start = 8.dp))

                RadioButton(
                    selected = selectedType == AnimalType.Reptile,
                    onClick = { selectedType = AnimalType.Reptile }
                )
                Text("Reptile", modifier = Modifier.padding(start = 8.dp))
            }

            Spacer(modifier = Modifier.padding(vertical = 16.dp))

            Text("Animal", style = MaterialTheme.typography.bodyLarge)

            //выбор животного
            animalList.forEach { animal ->
                Row {
                    RadioButton(
                        selected = selectedAnimal == animal,
                        onClick = { selectedAnimal = animal }
                    )
                    Text(animal.name, modifier = Modifier.padding(start = 8.dp))
                }
            }

            Spacer(modifier = Modifier.padding(vertical = 16.dp))

            //поля для ввода
            //имя
            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )
            //цвет
            TextField(
                value = color,
                onValueChange = { color = it },
                label = { Text("Color") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )

            //todo кнопки Confirm и Cancel
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = {
                        when (selectedAnimal) {
                            AnimalType.Cat -> onSubmit(Cat(name, color))
                            AnimalType.Dog -> onSubmit(Dog(name, color))
                            AnimalType.Frog -> onSubmit(Frog(name, color))
                            AnimalType.Triton -> onSubmit(Triton(name, color))
                            else -> onSubmit(null)
                        }
                    },
                    enabled = isFormValid,
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp)
                ) {
                    Text("Confirm")
                }
                Button(
                    onClick = { onDismissRequest() },
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp)
                ) {
                    Text("Cancel")
                }
            }
        }
    }
}
