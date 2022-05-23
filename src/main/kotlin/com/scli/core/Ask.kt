package com.scli.core

import com.scli.core.Cli.validateIsInt
import java.awt.SystemColor.info
import kotlin.reflect.KProperty

class Ask(private val info: String) {
    private var isNumber = false
    fun expectNumbers() {
        isNumber = true
    }

    operator fun getValue(thisRef: Any, property: KProperty<*>): String {
        println("[Q]: $info")
        print("-> ")

        do {
            val userInput = readln()

            if (isNumber) {
                val converted = userInput.toIntOrNull()
                if (!validateIsInt(converted)) {
                    println("Error: Answer must be numbers!")
                } else {
                    return userInput
                }
            }
        } while (true)
    }
}