package com.scli.core

import com.github.stefanbirkner.systemlambda.SystemLambda.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ArgumentParserTest {
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
        val argument = ArgumentParser()
        argument.addArgs(mapOf("hello" to "Show hello information")).markAsFinal()
        argument.fill(arrayOf("hello"))
        val output = argument.access("hello").check()
        assertTrue(output)
    }

    @Test
    fun testAddOptionalArgument() {
        val argument = ArgumentParser()
        argument.addOptionalArgs(mapOf("hello" to "Show hello information")).markAsFinal().fill(arrayOf("--hello"))

        val output = argument.access("--hello").check()
        assertTrue(output)
    }

    @Test
    fun testAccess() {
        val argument = ArgumentParser()
        argument.addArgs(mapOf("hello" to "Show hello info")).markAsFinal().fill(arrayOf("hello", "world"))

        assertTrue(argument.access("hello").check())

        val getNext = argument.access("hello").get { "not found!" }
        assertEquals("world", getNext)
    }

    @Test
    fun testAccessFailed() {
        val argument = ArgumentParser()
        argument.addArgs(mapOf("hello" to "hi")).markAsFinal().fill(arrayOf("hello"))

        assertFalse(argument.access("not-registered").check())

        val getNext = argument.access("not-registered").get { "not registered" }
        assertEquals("not registered", getNext)
    }

    @Test
    fun isNothing() {
        val argument = ArgumentParser()
        argument.markAsFinal().fill(arrayOf(""))

        assertTrue(argument.isNothing())
    }

    @Test
    fun isNotNothing() {
        val argument = ArgumentParser()
        argument.addArgs(mapOf("hello" to "hi")).markAsFinal().fill(arrayOf("hello"))

        assertFalse(argument.isNothing())
    }

    @Test
    fun mustRunWithArgs() {
        assertThrows<Throwable> {
            val argument = ArgumentParser()
            argument.markAsFinal().mustRunWithArgument()
            argument.fill(arrayOf())
        }
    }
}