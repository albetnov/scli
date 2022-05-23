package com.scli.core

import com.scli.core.Cli.validateIsInt
import kotlin.reflect.KProperty

class Quiz(val question: String, vararg options: String) {
    private val optionsList = options
    operator fun getValue(thisRef: Any, property: KProperty<*>): String {
        println(optionsList[0])
        do {
            var i = 1
            for (option in optionsList) {
                println("[$i]: $option")
                i++
            }

            print("-> ")
            val answers = readln().toIntOrNull()

            if (!validateIsInt(answers)) {
                println("Answer must be numbers of quiz!")
            } else if (optionsList.getOrNull(answers!! - 1) != null) {
                return optionsList[answers - 1]
            }

        } while (true)


    }
}