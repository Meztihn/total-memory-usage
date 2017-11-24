package com.github.meztihn.memoryusage

import com.github.meztihn.memoryusage.Memory.Unit.*
import com.github.meztihn.memoryusage.OS.Type.*

fun main(args: Array<String>) {
    if (args.isEmpty()) {
        println("Please specify process name.")
    } else {
        val processName = args.first()
        val memory = processes().filter { it.name.contains(processName, ignoreCase = true) }.map { it.memory }.reduce { total, current -> total + current }
        println("$processName reserved ${memory.to(GB)} of memory.")
    }
}

private fun processes(): List<Process> = Runtime.getRuntime().run {
    when (OS.type) {
        WINDOWS -> exec("tasklist /fo csv /nh").inputStream.use { stream ->
            stream.bufferedReader().lineSequence().map { line -> parse(line) }.toList()
        }
        else -> throw UnsupportedOSException()
    }
}

private val nonDigit = Regex("[^\\d]")

private fun parse(line: String): Process = line.split(",").map { it.removeSurrounding("\"") }.let { columns ->
    val name = columns[0]
    val memory = columns[4].replace(nonDigit, "").toInt()
    Process(name, Memory(memory, KB))
}