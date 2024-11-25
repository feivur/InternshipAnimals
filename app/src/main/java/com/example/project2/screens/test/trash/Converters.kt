//package com.example.project2.converters
//
//import androidx.room.TypeConverter
//import com.example.project2.screens.AnimalForm
//import com.example.project2.screens.selection.AnimalType
//
//class Converters {
//    @TypeConverter
//    fun fromAnimalType(value: AnimalType): String = value.name
//
//    @TypeConverter
//    fun toAnimalType(value: String): AnimalType = AnimalType.valueOf(value)
//
//    @TypeConverter
//    fun fromAnimalForm(value: AnimalForm): String = value.name
//
//    @TypeConverter
//    fun toAnimalForm(value: String): AnimalForm = AnimalForm.valueOf(value)
//}
