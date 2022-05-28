package com.scli.core

import com.github.stefanbirkner.systemlambda.SystemLambda.*
import com.scli.core.base.Stdin
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ConfirmationTest: Stdin("yes") {
    @Test
    fun testConfirmYes() {
        val confirmation = object {
            val confirm: Boolean by Confirmation("Are u ready?", ::readline)
        }

        val question = tapSystemOut {
            assertTrue(confirmation.confirm)
        }

        assertTrue(question.trim().contains("Are u ready?"))
    }

    @Test
    fun testConfirmNo() {
        setReturn("no")
        val confirmation = object {
            val confirm: Boolean by Confirmation("Are u ready?", ::readline)
        }

        val question = tapSystemOut {
            assertFalse(confirmation.confirm)
        }

        assertTrue(question.trim().contains("Are u ready?"))
    }
}