package com.kr8ne.mensMorris

import com.kr8ne.mensMorris.common.gameBoard.utils.CacheUtils.occurredPositions
import com.kr8ne.mensMorris.positions.Benchmark
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.RepeatedTest
import kotlin.system.measureTimeMillis

class BenchmarkTest : Benchmark() {
    @RepeatedTest(10)
    fun benchmark() {
        val time = measureTimeMillis {
            benchmark1.solve(6u)
        }
        // TODO: replace with JMH tests
        println(time)
    }

    /*
    6222
    5411
    5372
    5223
    5259
    5288
    5307
    5614
    5325
    5305
    average - 5432
     */

    @RepeatedTest(10)
    fun benchmark2() {
        val time = measureTimeMillis {
            benchmark1.solve(4u)
        }
        // TODO: replace with JMH tests
        println(time)
    }

    /*
    1455
    837
    828
    830
    793
    810
    813
    836
    801
    787
    average - 879
     */

    @AfterEach
    fun clearCache() {
        occurredPositions.clear()
    }
}
