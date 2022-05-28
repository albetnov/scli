package com.scli.core

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class WrapperTest {
    @Test
    fun testFetch() {
        val wrapper = Wrapper("Hello World!")
        assertEquals("Hello World!", wrapper.fetch())
    }

    @Test
    fun testRepeatUntil() {
        val wrapper = Wrapper(3).repeatUntil {
            it == 3
        }
        assertEquals(3, wrapper.fetch())
    }
}