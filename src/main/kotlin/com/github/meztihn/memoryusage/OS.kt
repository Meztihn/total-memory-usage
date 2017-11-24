package com.github.meztihn.memoryusage

import com.github.meztihn.memoryusage.OS.Type.*

object OS {
    val type: Type = with(System.getProperty("os.name").toLowerCase()) {
        when {
            contains("win") -> WINDOWS
            contains("nix") || contains("nux") || contains("aix") -> LINUX
            contains("mac") -> MAC
            else -> throw UnsupportedOSException()
        }
    }

    enum class Type {
        WINDOWS,
        LINUX,
        MAC
    }
}

class UnsupportedOSException : RuntimeException("Sorry but your operating system is not supported")