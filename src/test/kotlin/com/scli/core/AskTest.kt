package com.scli.core

import com.github.stefanbirkner.systemlambda.SystemLambda.*
import com.scli.core.base.Stdin
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class AskTest: Stdin() {

    @Test
    fun testAsk() {
        val ask = object {
            val question: String by Ask("Ur name?", ::readline)
        }

        val question = tapSystemOut {
            assertEquals("test", ask.question)
        }

        assertTrue(question.trim().contains("Ur name?"))
    }

    @Test
    fun testNumberAnswerOnly() {
        val ask = object {
            val question: String by Ask("Your age?", ::readline).expectNumbers(false)
        }

        val output = tapSystemOut {
            println(ask.question)
        }

        assertTrue(output.trim().contains("must be number"))
    }
}