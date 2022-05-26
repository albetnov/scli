package com.scli.core

import kotlin.reflect.KProperty

class Confirmation(private val info: String) {
    operator fun getValue(thisRef: Any, property: KProperty<*>): Boolean {
      return confirmation()
    }

    fun confirmation(): Boolean {
        println("[C]: $info [Y/n]")
        print("-> ")
        return when(readln().lowercase()) {
            "y", "yes" -> true
            else -> false
        }
    }
}