package com.scli.core

import com.github.stefanbirkner.systemlambda.SystemLambda.*
import com.scli.core.base.Stdin
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class QuizTest : Stdin("1") {
    @Test
    fun testQuiz() {
        val quiz = object {
            val question: Answer by Quiz("What ar u gonna do?", "boker", "do nothing").setReadline(::readline)
        }

        val output = tapSystemOut {
            val answer = quiz.question
            assertEquals(Answer(1, "boker"), answer)
            assertEquals(1, answer.number)
            assertEquals("boker", answer.value)
        }
        assertTrue(output.trim().contains("What ar u gonna do?"))
        assertTrue(output.trim().contains("boker"))
        assertTrue(output.trim().contains("do nothing"))
    }
}