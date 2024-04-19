package com.kr8ne.mensMorris

import com.kr8ne.mensMorris.positions.Memory
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
