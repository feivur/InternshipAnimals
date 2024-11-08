package com.example.project2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.project2.ui.theme.Project2Theme
import android.util.Log

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Project2Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }

        // Запуск зоопарка
        runZoo()
    }

    private fun runZoo() {
        // Создаем экземпляры животных
        val cat = Cat("Whiskers", "gray")
        val dog = Dog("Rex", "brown")
        val frog = Frog("Freddy", "green")
        val triton = Triton("Timmy", "blue")

        // Создаем коллекции животных
        val animalList: List<Animal> = listOf(cat, dog, frog, triton)
        val animalSet: Set<Animal> = setOf(cat, dog, frog, triton)
        val animalMap: Map<String, Animal> = mapOf("cat" to cat, "dog" to dog, "frog" to frog, "triton" to triton)

        // Выводим действия животных в лог
        animalList.forEach {
            if (it is Mammal) {
                it.run()
                it.jump()
            }
            if (it is Reptile) {
                it.crawl()
                it.shedSkin()
            }
            if (it is Speaker) {
                it.speak()
            }
            it.describe()
        }

        // вывод коллекций в log
        Log.d("ZooLog", "Animal List: ${animalList.map { it::class.simpleName }}")
        Log.d("ZooLog", "Animal Set: ${animalSet.map { it::class.simpleName }}")
        Log.d("ZooLog", "Animal Map: ${animalMap.map { it.key to it.value::class.simpleName }}")
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Project2Theme {
        Greeting("Android")
    }
}
