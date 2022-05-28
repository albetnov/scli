package com.scli.core

import com.scli.tester.stdOut
import kotlin.reflect.KFunction1
import kotlin.system.exitProcess

class ArgumentParser {

    private val argsList: MutableMap<String, String> = mutableMapOf(
        "version" to "Show app version",
        "help" to "Show app available command"
    )
    var arguments: Array<String> = arrayOf("")
    private val optionalArgs: MutableMap<String, String> = mutableMapOf()
    private var argExist: Int? = null
    var VERSION: Double = 1.0
    var APP_NAME: String = "MyApplication"
    var AUTHOR: String = "John Doe"
    private var finalized = false
    private var stdOut: KFunction1<String, Unit> = ::println

    fun debug(stdOut: KFunction1<String, Unit> = ::stdOut): ArgumentParser {
        this.stdOut = stdOut
        return this
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

    fun get(default: () -> String? = { null }): String? {
        if (argExist != null) {
            val value: String? = arguments.getOrNull(argExist!! + 1)
            argExist = null
            if (value != null) {
                return value
            }
        }
        return default()
    }

    fun check(): Boolean {
        if (argExist != null) {
            argExist = null
            return true
        }
        return false
    }

    fun markAsFinal(): ArgumentParser {
        finalized = true
        return this
    }

    fun isNothing(): Boolean = arguments.isEmpty() || arguments[0].isBlank()

    fun mustRunWithArgument(throwErr: Boolean = true) {
        if (isNothing()) {
            if (throwErr) {
                throw Throwable("Error: Arguments Required!")
            }
            stdOut("Error: Arguments Required!")
            exitProcess(0)
        }
    }

    fun logic(argument: String, logic: (ArgumentParser) -> Unit) {
        val argValue = access(argument)
        if (argValue.check()) {
            return logic(argValue)
        }
        throw IllegalArgumentException("Error: Argument not found.")
    }

    fun fill(argument: Array<String>) {
        if (!finalized) {
            throw Exception("Error: Please use markAsFinal() method to finalizing all your arguments contents.")
        }
        arguments = argument

        val required = arguments.filterNot { it.startsWith("--") }

        var count = 0
        required.forEach {
            if (count > 1) {
                stdOut("Error: More than one arguments founded.")
                exitProcess(0)
            }
            if (argsList.containsKey(it)) {
                count++
            }
        }

        val optional = argument.filter { it.startsWith("--") }
        var commandFound = false
        optional.forEach {
            if (optionalArgs.containsKey(it)) {
                commandFound = true
            }
        }

        if (!isNothing() && count == 0 && !commandFound) {
            stdOut("Error: Arguments not found! see 'help' for details.")
            exitProcess(0)
        }

        if (access("help").check()) {
            stdOut("-----$APP_NAME Required Parameters-----")
            argsList.forEach {
                stdOut(":: ${it.key}")
                stdOut(" -> ${it.value}")
            }
            stdOut("-----$APP_NAME Optional Parameters-----")
            optionalArgs.forEach {
                stdOut(">> ${it.key}")
                stdOut(" -> ${it.value}")
            }
            exitProcess(0)
        }

        if (access("version").check()) {
            stdOut("${APP_NAME}:${AUTHOR} Version $VERSION")
            exitProcess(0)
        }
    }
}