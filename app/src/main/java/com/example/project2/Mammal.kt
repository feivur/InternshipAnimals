package com.example.project2

abstract class Mammal(val name: String, val color: String) : Animal {
    abstract fun run()
    abstract fun jump()

    override fun describe() {
        println("$name is a $color mammal.")
    }
}
