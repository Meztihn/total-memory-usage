package com.github.meztihn.memoryusage

import com.github.meztihn.memoryusage.Memory.Unit.*
import com.github.meztihn.memoryusage.OS.Type.*

fun main(args: Array<String>) {
    args.firstOrNull()?.let { processName ->
        val memory = processes().filter { it.name.contains(processName, ignoreCase = true) }.map { it.memory }.reduce { total, current -> total + current }
        println("$processName reserved ${memory.to(GB)} of memory.")
    } ?: println("Please specify process name.")
}

private fun processes(): List<Process> = when (OS.type) {
    WINDOWS -> Runtime.getRuntime().exec("tasklist /fo csv /nh").inputStream.use { stream ->
        stream.bufferedReader().lineSequence().map { line -> parse(line) }.toList()
    }
    else -> throw UnsupportedOSException()
}

private val nonDigit = Regex("[^\\d]")

private fun parse(line: String): Process {
    return line.split(",").map { it.removeSurrounding("\"") }.let { columns ->
        val name = columns[0]
        val memory = columns[4].replace(nonDigit, "").toInt()
        Process(name, Memory(memory, KB))
    }
}