package com.example.project2

abstract class Reptile(val name: String, val color: String) : Animal {
    abstract fun crawl()
    abstract fun shedSkin()

    override fun describe() {
        println("$name is a $color reptile.")
    }
}
