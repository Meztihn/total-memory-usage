package com.github.meztihn.memoryusage

import com.github.meztihn.memoryusage.Memory.Unit.*
import com.winterbe.expekt.should
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on

class MemoryTest : Spek({
    describe("a memory") {
        val memory = Memory(1, MB)
        on("converting") {
            it("size should be equal to old size considering units") {
                memory.to(KB).size.should.be.closeTo(1024.0)
                memory.to(GB).size.should.be.closeTo(1 / 1024.0)
            }
        }
        on("adding") {
            val second = Memory(2, MB)
            val total = memory + second
            it("total size should be equal to sum of terms sizes") {
                total.size.should.be.closeTo(memory.size + second.size)
            }
        }
        on("comparing") {
            val same = Memory(1024, KB)
            val different = Memory(1025, KB)
            it("should be equatable considering unit types") {
                memory.should.be.equal(same)
                memory.should.be.not.equal(different)
            }
        }
    }
})