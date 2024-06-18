package com.kroune.mensMorris.data.local

import com.kr8ne.mensMorris.cache.Cache
import com.kr8ne.mensMorris.gameStartPosition
import com.kroune.mensMorris.data.local.impl.game.GameAnalyzeData
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.Duration

class GameAnalyzeDataTest {
    @Test
    fun depthValue() {
        val instance = GameAnalyzeData(MutableStateFlow(gameStartPosition))
        for (i in 3 downTo 0) {
            assert(instance.dataState.value.depth == i)
            instance.decreaseDepth()
        }
        assert(instance.dataState.value.depth == 0)
        instance.decreaseDepth()
        assert(instance.dataState.value.depth == 0)
        for (i in 0..20) {
            assert(instance.dataState.value.depth == i)
            instance.increaseDepth()
        }
    }

    @Test
    fun analyzeWorks() {
        val inst = GameAnalyzeData(MutableStateFlow(gameStartPosition))
        // check if it stops
        inst.startAnalyze()
        assert(inst.analyzeJob?.isActive == true)
        runBlocking {
            delay(4000)
        }
        assert(inst.analyzeJob?.isActive == false)
    }

    @Test
    fun analyzeStops1() {
        val inst = GameAnalyzeData(MutableStateFlow(gameStartPosition))
        inst.startAnalyze()
        assert(inst.analyzeJob?.isActive == true)
        inst.decreaseDepth()
        assert(inst.analyzeJob?.isActive == false)
    }

    @Test
    fun analyzeStops2() {
        val inst = GameAnalyzeData(MutableStateFlow(gameStartPosition))
        inst.startAnalyze()
        assert(inst.analyzeJob?.isActive == true)
        inst.increaseDepth()
        assert(inst.analyzeJob?.isActive == false)
    }

    @Test
    fun analyze4() {
        val inst = GameAnalyzeData(MutableStateFlow(gameStartPosition))
        Assertions.assertTimeout(Duration.ofSeconds(10)) {
            runBlocking {
                inst.startAnalyze()
                assert(inst.analyzeJob?.isActive == true)
                inst.analyzeJob?.join()
                inst.startAnalyze()
                assert(inst.analyzeJob?.isActive == false)
            }
        }
    }
}
