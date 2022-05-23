package com.scli.example
import com.scli.core.Cli

class Question {
    val ask: String by Cli.ask("How are u today?")
    val confirmation: Boolean by Cli.confirm("Do you want some pasta?")
    val quiz: String by Cli.quiz("ayam", "minuman", "apalagi bebas")
}