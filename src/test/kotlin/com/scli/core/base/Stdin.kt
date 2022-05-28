package com.scli.core.base

abstract class Stdin(private var value: String = "test") {
    fun setReturn(value: String): Stdin {
        this.value = value
        return this
    }

    fun readline(): String {
        return value
    }
}