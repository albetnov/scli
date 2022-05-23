package com.scli.core

import kotlin.reflect.KProperty

class Quiz(vararg questions: String) {
    private val questionsList = questions
    operator fun getValue(thisRef: Any, property: KProperty<*>): String {
        println("Question List")
        do {
            var i = 1
            for (question in questionsList) {
                println("[$i]: $question")
                i++
            }

            print("-> ")
            val answers = readln().toIntOrNull()

            if (!validateIsInt(answers)) {
                println("Answer must be numbers of quiz!")
            } else if (questionsList.getOrNull(answers!! - 1) != null) {
                return questionsList[answers - 1]
            }

        } while (true)


    }

    private fun <T> validateIsInt(value: T): Boolean {
        if (value is Int && value != 0) {
                return true
        }
        return false
    }
}