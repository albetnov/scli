package com.scli.core

import com.github.stefanbirkner.systemlambda.SystemLambda.*
import com.scli.tester.IostdTester
import com.scli.tester.stdOut
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ArgumentParserTest {
    private val argument = ArgumentParser().debug()

    @Test
    fun testArgumentNotFound() {
        val exit = catchSystemExit {
            argument.markAsFinal().fill(arrayOf("not-found"))
            assertEquals("Error: Arguments not found! see 'help' for details.", IostdTester.getOutput())
        }
        assertEquals(0, exit)
    }

    @Test
    fun testMultiRequiredArgument() {
        val exit = catchSystemExit {
            argument.markAsFinal().fill(arrayOf("version", "help", "abc"))
            assertEquals("Error: More than one arguments founded.", IostdTester.getOutput())
        }
        assertEquals(0, exit)
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
        IostdTester.cleanOutput()
        argument.addArgs(mapOf("hello" to "hi")).markAsFinal()
        argument.fill(arrayOf("hello"))
        argument.logic("hello") {
            stdOut("Hello World")
        }

        assertEquals("Hello World", IostdTester.getOutput())
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
        IostdTester.cleanOutput()
        catchSystemExit {
            argument.markAsFinal().fill(arrayOf("help"))
        }

        val output = IostdTester.getOutput()
        assertTrue(output.contains("version"))
        assertTrue(output.contains("Show app version"))
        assertTrue(output.contains("help"))
        assertTrue(output.contains("Show app available command"))
    }

    @Test
    fun testHelpDynamic() {
        catchSystemExit {
            argument.addArgs(mapOf("hello" to "say hi")).markAsFinal().fill(arrayOf("help"))
        }

        val output = IostdTester.getOutput()
        assertTrue(output.contains("help"))
        assertTrue(output.contains("Show app available command"))
        assertTrue(output.contains("hello"))
        assertTrue(output.contains("say hi"))
    }
}