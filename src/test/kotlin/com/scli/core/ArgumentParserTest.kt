package com.scli.core

import com.github.stefanbirkner.systemlambda.SystemLambda.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ArgumentParserTest {
    val argument = ArgumentParser()

    @Test
    fun testArgumentNotFound() {
        val output = tapSystemOut {
            val exit = catchSystemExit {
                ArgumentParser().markAsFinal().fill(arrayOf("not-found"))
            }
            assertEquals(0, exit)
        }
        assertEquals("Error: Arguments not found! see 'help' for details.", output.trim())
    }

    @Test
    fun testMultiRequiredArgument() {
        val output = tapSystemOut {
            val exit = catchSystemExit {
                ArgumentParser().markAsFinal().fill(arrayOf("version", "help", "abc"))
            }
            assertEquals(0, exit)
        }
        assertEquals("Error: More than one arguments founded.", output.trim())
    }

    @Test
    fun testAddRequiredArgument() {
        argument.addArgs(mapOf("hello" to "Show hello information")).markAsFinal()
        argument.fill(arrayOf("hello"))
        val output = argument.access("hello").check()
        assertTrue(output)
    }

    @Test
    fun testAddOptionalArgument() {
        argument.addOptionalArgs(mapOf("hello" to "Show hello information")).markAsFinal().fill(arrayOf("--hello"))

        val output = argument.access("--hello").check()
        assertTrue(output)
    }

    @Test
    fun testAccess() {
        argument.addArgs(mapOf("hello" to "Show hello info")).markAsFinal().fill(arrayOf("hello", "world"))

        assertTrue(argument.access("hello").check())

        val getNext = argument.access("hello").get { "not found!" }
        assertEquals("world", getNext)
    }

    @Test
    fun testAccessFailed() {
        argument.addArgs(mapOf("hello" to "hi")).markAsFinal().fill(arrayOf("hello"))

        assertFalse(argument.access("not-registered").check())

        val getNext = argument.access("not-registered").get { "not registered" }
        assertEquals("not registered", getNext)
    }

    @Test
    fun isNothing() {
        argument.markAsFinal().fill(arrayOf(""))

        assertTrue(argument.isNothing())
    }

    @Test
    fun isNotNothing() {
        argument.addArgs(mapOf("hello" to "hi")).markAsFinal().fill(arrayOf("hello"))

        assertFalse(argument.isNothing())
    }

    @Test
    fun mustRunWithArgs() {
        assertThrows<Throwable> {
            argument.markAsFinal().fill(arrayOf())
            argument.mustRunWithArgument()
        }
    }

    @Test
    fun testLogic() {
        val output = tapSystemOut {
            argument.addArgs(mapOf("hello" to "hi")).markAsFinal()
            argument.fill(arrayOf("hello"))
            argument.logic("hello") {
                println("Hello World")
            }
        }
        assertEquals("Hello World", output.trim())
    }

    @Test
    fun testLogicFailed() {
        assertThrows<IllegalArgumentException> {
            argument.markAsFinal()
            argument.fill(arrayOf())
            argument.logic("not-found") {
                println("I shall not be executed.")
            }
        }
    }

    @Test
    fun testFillFailed() {
        assertThrows<Exception> {
            argument.fill(arrayOf())
        }
    }

    @Test
    fun testHelpDefault() {
        val output = tapSystemOut {
            catchSystemExit {
                argument.markAsFinal().fill(arrayOf("help"))
            }
        }
        assertTrue(output.trim().contains("version"))
        assertTrue(output.trim().contains("Show app version"))
        assertTrue(output.trim().contains("help"))
        assertTrue(output.trim().contains("Show app available command"))
    }

    @Test
    fun testHelpDynamic() {
        val output = tapSystemOut {
            catchSystemExit {
                argument.addArgs(mapOf("hello" to "say hi")).markAsFinal().fill(arrayOf("help"))
            }
        }
        assertTrue(output.trim().contains("help"))
        assertTrue(output.trim().contains("Show app available command"))
        assertTrue(output.trim().contains("hello"))
        assertTrue(output.trim().contains("say hi"))
    }
}