package com.scli.core

class Wrapper <T> (private val value: T) {
    fun fetch(): T {
        return value
    }

    fun repeatUntil(repeat: (T) -> Boolean): Wrapper<T> {
        do {
            if (repeat(value)) {
                break
            }
        } while (true)
        return this
    }
}