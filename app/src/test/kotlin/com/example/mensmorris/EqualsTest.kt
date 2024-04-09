package com.example.mensmorris

import com.example.mensmorris.positions.Equals
import junit.framework.TestCase
import org.junit.Test

class EqualsTest : Equals() {
    /**
     * this tests we our copy method actually works
     */
    @Test
    fun `equals test`() {
        val position1 = position.copy()
        TestCase.assertEquals(position, position1)
    }

    /**
     * this tests we our copy method actually works
     */
    @Test
    fun `not equals test`() {
        TestCase.assertEquals(position, position2)
    }
}
