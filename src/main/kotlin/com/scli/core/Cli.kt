package com.scli.core

object Cli {
    fun ask(message: String): Ask {
        return Ask(message)
    }

    fun confirm(message: String):Confirmation {
        return Confirmation(message)
    }

    fun quiz(question: String, vararg options: String): Quiz {
        return Quiz(question, *options)
    }
    fun <T> validateIsInt(value: T): Boolean {
        if (value is Int && value != 0) {
            return true
        }
        return false
    }
}