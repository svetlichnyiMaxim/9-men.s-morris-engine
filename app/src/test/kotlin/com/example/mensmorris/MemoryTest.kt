package com.example.mensmorris

import com.example.mensmorris.positions.Memory
import org.junit.jupiter.api.Test

class MemoryTest : Memory() {
    /**
     * @throws OutOfMemoryError if failed
     */
    @Test
    fun `depth test`() {
        wonPosition.solve(5u).second
    }
}
