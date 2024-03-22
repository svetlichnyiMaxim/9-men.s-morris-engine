package com.example.mensmorris

import com.example.mensmorris.positions.Benchmark
import com.example.mensmorris.utils.CacheUtils.occurredPositions
import org.junit.Test
import kotlin.system.measureTimeMillis

class BenchmarkTest : Benchmark() {
    @Test
    fun benchmark() {
        val tries = 10
        var timeSum = 0L
        repeat(tries) {
            val time = measureTimeMillis {
                benchmark1.solve(6u)
            }
            timeSum += time
            occurredPositions.clear()
        }
        val average = timeSum / tries
        // TODO: replace with JMH tests
        println(average)
        //TestCase.assertTrue(average <= 8_000L)
    }

    /*
7541
5821
6222
5445
6389
5787
6349
5757
5541
5810
average - 6066
     */

    @Test
    fun benchmark2() {
        val tries = 10
        var timeSum = 0L
        repeat(tries) {
            val time = measureTimeMillis {
                benchmark2.solve(4u).second
            }
            timeSum += time
            occurredPositions.clear()
        }
        val average = timeSum / tries
        // TODO: replace with JMH tests
        println(average)
        //TestCase.assertTrue(average <= 2_000L)
    }

    /*
1367
879
856
871
837
848
844
866
855
880
average - 910
     */
}
