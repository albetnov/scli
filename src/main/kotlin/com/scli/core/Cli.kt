package com.scli.core

object Cli {
    fun ask(message: String): Ask {
        return Ask(message)
    }

    fun confirm(message: String):Confirmation {
        return Confirmation(message)
    }

    fun quiz(vararg questions: String): Quiz {
        return Quiz(*questions)
    }
}