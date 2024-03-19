package com.example.mensmorris

import com.example.mensmorris.positions.Copy
import com.example.mensmorris.utils.AnalyzeUtils.resetAnalyze
import org.junit.Test

class CopyTest : Copy() {
    /**
     * this tests we our copy method actually works
     */
    @Test
    fun `copy test1`() {
        val positionCopy = position.copy()
        positionCopy.positions[0] = null
        assert(position != positionCopy)
        resetAnalyze()
    }

    /**
     * this tests we our copy method actually works
     */
    @Test
    fun `copy test2`() {
        val positionCopy = position.copy()
        positionCopy.positions[10] = null
        assert(position != positionCopy)
        resetAnalyze()
    }
}
