package com.example.project2.structure

abstract class Reptile(override val name: String, override val color: String) : Animal {
    abstract fun crawl()
    abstract fun shedSkin()

    override fun describe(): String {
        return "$name is a $color reptile."
    }
}
