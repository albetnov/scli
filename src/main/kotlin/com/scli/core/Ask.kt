package com.scli.core

import com.scli.core.Cli.validateIsInt
import kotlin.reflect.KFunction0
import kotlin.reflect.KProperty

class Ask(private val info: String, private val readline: KFunction0<String> = ::readln) {
    private var isNumber = false
    private var validationLoops = true
    fun expectNumbers(withLoops: Boolean = true): Ask {
        isNumber = true
        validationLoops = withLoops
        return this
    }

    operator fun getValue(thisRef: Any, property: KProperty<*>): String {
        return ask()
    }

    fun ask(): String {
        println("[Q]: ${info}")
        print("-> ")

        val userInput = readline()

        if(isNumber) {
            do {
                val converted = userInput.toIntOrNull()
                if (!validateIsInt(converted)) {
                    println("Error: Answer must be numbers!")
                    if(!validationLoops) {
                        break
                    }
                } else {
                    break
                }
            } while (true)
        }

        return userInput
    }
}