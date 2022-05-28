package com.scli.core

import kotlin.reflect.KFunction0
import kotlin.reflect.KProperty

class Confirmation(private val info: String, private val readline: KFunction0<String> = ::readln) {
    operator fun getValue(thisRef: Any, property: KProperty<*>): Boolean {
      return confirmation()
    }

    fun confirmation(): Boolean {
        println("[C]: $info [Y/n]")
        print("-> ")
        return when(readline().lowercase()) {
            "y", "yes" -> true
            else -> false
        }
    }
}