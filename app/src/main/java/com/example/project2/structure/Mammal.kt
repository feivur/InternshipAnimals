package com.example.project2.structure

abstract class Mammal(override val name: String, override val color: String) : Animal {
    abstract fun run()
    abstract fun jump()

    override fun describe(): String {
        return "$name is a $color mammal."
    }
}
