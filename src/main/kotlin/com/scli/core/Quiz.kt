package com.scli.core

import com.scli.core.Cli.validateIsInt
import kotlin.reflect.KFunction0
import kotlin.reflect.KProperty

class Quiz(val question: String, vararg options: String) {
    private val optionsList = options
    private var read = ::readln
    fun setReadline(readline: KFunction0<String> = ::readln): Quiz {
        read = readline
        return this
    }
    operator fun getValue(thisRef: Any, property: KProperty<*>): Answer {
        return quiz()
    }

    fun quiz(): Answer {
        println(question)
        do {
            var i = 1
            for (option in optionsList) {
                println("[$i]: $option")
                i++
            }

            print("-> ")
            val answers = read().toIntOrNull()

            if (!validateIsInt(answers)) {
                println("Answer must be numbers of quiz!")
            } else if (optionsList.getOrNull(answers!! - 1) != null) {
                return Answer(answers, optionsList[answers - 1])
            }

        } while (true)
    }
}