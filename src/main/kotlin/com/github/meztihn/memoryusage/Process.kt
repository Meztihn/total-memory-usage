package com.github.meztihn.memoryusage

data class Process(val name: String, val memory: Memory)

data class Memory(val size: Double, val unit: Unit) {
    constructor(size: Int, unit: Unit) : this(size.toDouble(), unit)

    operator fun plus(other: Memory): Memory {
        val commonUnit = minOf(this.unit, other.unit)
        return Memory(this.to(commonUnit).size + other.to(commonUnit).size, commonUnit)
    }

    fun to(newUnit: Unit): Memory = Memory(size * this.unit.multiplier / newUnit.multiplier, newUnit)

    override fun toString(): String = "${size.format(2)} $unit"

    enum class Unit(val multiplier: Int) {
        KB(1),
        MB(1024),
        GB(1024 * 1024)
    }
}

fun Double.format(precision: Int): String = String.format("%.${precision}f", this)