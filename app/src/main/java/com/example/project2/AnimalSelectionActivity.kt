package com.example.project2

//import androidx.benchmark.perfetto.Row --
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

enum class AnimalType{
    Mammal, Cat,
//todo etc, задействовать везде вместо строк
}

@Composable
fun AnimalSelectionScreen(
    onDismissRequest: () -> Unit,
    onSubmit: (Animal?) -> Unit
) {


    var selectedType by remember { mutableStateOf("Mammal") }
    var selectedAnimal by remember { mutableStateOf("Cat") }
    var name by remember { mutableStateOf("") }
    var color by remember { mutableStateOf("") }
    val isFormValid = name.isNotEmpty() && color.isNotEmpty()

    Dialog(
        onDismissRequest = onDismissRequest
    ) {
        //          список животных в зависимости от выбранного типа
        val animalList = remember(selectedType) {
            if (selectedType == "Mammal") listOf("Cat", "Dog") else listOf("Frog", "Triton")
        }


        LaunchedEffect(selectedType) {
            selectedAnimal = animalList.first()  //выбираем первое животное если поменчли тип животного
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
            //todo длобавить фон
        ) {
            Text("Animal Type", style = MaterialTheme.typography.bodyLarge)

            //выбор типа животного
            Row {
                RadioButton(
                    selected = selectedType == "Mammal",
                    onClick = { selectedType = "Mammal" }
                )
                Text("Mammal", modifier = Modifier.padding(start = 8.dp))

                RadioButton(
                    selected = selectedType == "Reptile",
                    onClick = { selectedType = "Reptile" }
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
                    Text(animal, modifier = Modifier.padding(start = 8.dp))
                }
            }

            Spacer(modifier = Modifier.padding(vertical = 16.dp))

            //поле для ввода
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

            //кнопка ок
            Button(
                onClick = {
                    when (selectedAnimal) {
                        "Cat" -> onSubmit(Cat(name, color))
                        "Dog" -> onSubmit(Dog(name, color))
                        "Frog" -> onSubmit(Frog(name, color))
                        "Triton" -> onSubmit(Triton(name, color))
                    }
                },
                enabled = isFormValid,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Text("Confirm")
            }
            //todo добавить кнопку Cancel
        }
    }
}
