package com.scli.core

import kotlin.reflect.KProperty

class Ask(private val info: String) {
    operator fun getValue(thisRef: Any, property: KProperty<*>): String {
        println("[Q] $info")
        print("-> ")
        return readln()
    }
}