package com.scli.core

import kotlin.system.exitProcess

class ArgumentParser(private val arguments: Array<String>) {

    private val argsList: MutableMap<String, String> = mutableMapOf(
        "version" to "Show app version",
        "help" to "Show app available command"
    )
    private val optionalArgs: MutableMap<String, String> = mutableMapOf()
    private var argExist: Int? = null
    val VERSION: Double = 1.0
    val APP_NAME: String = "MyApplication"
    val AUTHOR: String = "John Doe"

    init {
        val required = arguments.filterNot { it.startsWith("--") }

        var count = 0
        required.forEach {
            if (argsList.containsKey(it)) {
                count++
            }
        }

        if (count > 1) {
            println("Error: More than one arguments founded.")
            exitProcess(0)
        }

        if (access("version").get()) {
            println("${APP_NAME}:${AUTHOR} Version $VERSION")
            exitProcess(0)
        }
    }

    fun addArgs(args: Map<String, String>): ArgumentParser {
        args.forEach { t, u ->
            argsList[t] = u
        }
        return this
    }

    fun addOptionalArgs(args: Map<String, String>): ArgumentParser {
        args.forEach {
            optionalArgs["--${it.key}"] = it.value
        }
        return this
    }

    fun access(arg: String): ArgumentParser {
        if (arguments.contains(arg)) {
            argExist = arguments.indexOf(arg)
        }
        return this
    }

    fun get(takeValue: Boolean): String? {
        if (takeValue && argExist != null) {
            val value: String? = arguments.getOrNull(argExist!! + 1)
            argExist = null
            if (value != null) {
                return value
            }
        }
        return null
    }

    fun get(): Boolean {
        if (argExist != null) {
            argExist = null
            return true
        }
        return false
    }

    fun markAsFinal() {
        if (access("help").get()) {
            println("-----$APP_NAME Required Parameters-----")
            argsList.forEach {
                println(":: ${it.key}")
                println(" -> ${it.value}")
            }
            println("-----$APP_NAME Optional Parameters-----")
            optionalArgs.forEach {
                println(">> ${it.key}")
                println(" -> ${it.value}")
            }
            exitProcess(0)
        }
    }
}