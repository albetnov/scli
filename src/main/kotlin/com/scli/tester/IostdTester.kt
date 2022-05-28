package com.scli.tester

fun stdOut(value: String) {
    return IostdTester.stdOut(value)
}

fun stdIn(): String {
    return IostdTester.stdIn()
}

fun stdOutSingle(value: String) {
    return IostdTester.stdOutSingle(value)
}

object IostdTester {
    var stdIn = "test"
    private var currentString: MutableList<String> = mutableListOf()

    fun stdIn(): String {
        return stdIn
    }

    private fun register(value: String) {
        currentString.add(value)
    }

    fun stdOut(value: String) {
        println(value)
        register(value)
    }

    fun stdOutSingle(value: String) {
        print(value)
        register(value)
    }

    fun cleanOutput(): IostdTester {
        currentString.clear()
        return this
    }

    fun getOutput(): String {
        if (currentString.isNotEmpty()) {
            val output: List<String> = currentString.toList()
            currentString.clear()
            return output.joinToString(" ")
        }
        throw Exception("No output has been registered.")
    }
}