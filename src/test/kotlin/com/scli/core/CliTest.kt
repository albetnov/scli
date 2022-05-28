package com.scli.core

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.MockedStatic
import org.mockito.Mockito

class CliTest {
    companion object {
        @JvmStatic
        private val mock: MockedStatic<Cli> = Mockito.mockStatic(Cli::class.java)
        @JvmStatic
        private val mockedCli: Cli = Mockito.mock(Cli::class.java)
    }

    @Test
    fun testAsk() {
        mock.`when`<Wrapper<String>> { mockedCli.ask("Ur name?") }.thenReturn(Wrapper("Asep"))

        assertEquals("Asep", mockedCli.ask("Ur name?").fetch())
        Mockito.verify(mockedCli).ask("Ur name?")
    }

    @Test
    fun testConfirm() {
        mock.`when`<Wrapper<Boolean>> { mockedCli.confirm("Are u sure?") }.thenReturn(Wrapper(true))

        assertTrue(mockedCli.confirm("Are u sure?").fetch())
        Mockito.verify(mockedCli).confirm("Are u sure?")
    }

    @Test
    fun testQuiz() {
        mock.`when`<Wrapper<Answer>> { mockedCli.quiz("Menu", "first", "second") }
            .thenReturn(Wrapper(Answer(1, "first")))

        assertEquals(1, mockedCli.quiz("Menu", "first", "second").fetch().number)
        assertEquals("first", mockedCli.quiz("Menu", "first", "second").fetch().value)

        Mockito.verify(mockedCli, Mockito.times(2)).quiz("Menu", "first", "second")
    }

    @Test
    fun testValidateIsIntSuccess() {
        val isInt = Cli.validateIsInt(10)
        assertTrue(isInt)
    }

    @Test
    fun testValidateIsIntFailed() {
        val isNotInt = Cli.validateIsInt(null)
        assertFalse(isNotInt)
    }
}