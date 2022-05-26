package com.scli.core

object Cli {
    fun ask(message: String): Wrapper<String> {
        return Wrapper(Ask(message).ask())
    }

    fun confirm(message: String): Wrapper<Boolean> {
        return Wrapper(Confirmation(message).confirmation())
    }

    fun quiz(question: String, vararg options: String): Wrapper<Answer> {
        return Wrapper(Quiz(question, *options).quiz())
    }

    fun <T> validateIsInt(value: T): Boolean {
        if (value is Int && value != 0) {
            return true
        }
        return false
    }
}

fun main() {
    val hello = Cli.ask("Contoh").repeatUntil {
        it == "oke"
    }.fetch()
}