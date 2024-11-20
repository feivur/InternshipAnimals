package com.example.project2

//import com.example.project2.screens.test.TestScreen
import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.project2.db.RoomDB
import com.example.project2.navigation.AppNavigation
import com.example.project2.viewmodel.AnimalsViewModel
import com.example.project2.viewmodel.AnimalsViewModelFactory


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val animalsDao = Room.databaseBuilder(
            applicationContext,
            RoomDB::class.java, "animals_db"
        )
            .fallbackToDestructiveMigration()
            .build().animalsDao()

        val viewModelFactory = AnimalsViewModelFactory(application) //,animalsDao)
        val animalsViewModel: AnimalsViewModel by viewModels { viewModelFactory }


        setContent {
            val owner = LocalViewModelStoreOwner.current

            owner?.let {
                val viewModel: AnimalsViewModel = viewModel(
                    it,
                    "AnimalsViewModel",
                    AnimalsViewModelFactory(
                        LocalContext.current.applicationContext as Application,
                        //animalsDao = animalsDao
                    )

                )


            }
            val navController = rememberNavController()
            AppNavigation(
                animalsViewModel = animalsViewModel,
                navController = navController
            )
    }
}


}
