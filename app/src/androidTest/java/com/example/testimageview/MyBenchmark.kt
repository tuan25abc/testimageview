package com.example.testimageview

import androidx.benchmark.BenchmarkRule
import androidx.benchmark.measureRepeated
import androidx.test.runner.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by TUANNP3 on 07,August,2019.
 */

@RunWith(AndroidJUnit4::class)
class MyBenchmark {
    @get:Rule
    val benchmarkRule = BenchmarkRule()

    @Test
    fun benchmarkSomeWork() = benchmarkRule.measureRepeated {
        Thread.sleep(5)
    }
}