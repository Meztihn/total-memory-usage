package com.github.meztihn.memoryusage

fun Double.format(precision: Int): String = String.format("%.${precision}f", this)