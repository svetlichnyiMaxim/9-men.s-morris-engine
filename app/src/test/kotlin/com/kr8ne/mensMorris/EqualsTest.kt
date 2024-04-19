package com.kr8ne.mensMorris

import com.kr8ne.mensMorris.positions.Equals
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class EqualsTest : Equals() {
    /**
     * this tests we our copy method actually works
     */
    @Test
    fun `equals test`() {
        val position1 = position.copy()
        assertEquals(position, position1)
    }

    /**
     * this tests we our copy method actually works
     */
    @Test
    fun `not equals test`() {
        assertEquals(position, position2)
    }
}
