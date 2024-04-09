package com.example.mensmorris

import com.example.mensmorris.positions.Benchmark
import com.example.mensmorris.common.utils.CacheUtils.occurredPositions
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
            println(time)
        }
        val average = timeSum / tries
        // TODO: replace with JMH tests
        println("average - $average")
        //TestCase.assertTrue(average <= 8_000L)
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
            println(time)
        }
        val average = timeSum / tries
        // TODO: replace with JMH tests
        println("average - $average")
        //TestCase.assertTrue(average <= 2_000L)
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
}
