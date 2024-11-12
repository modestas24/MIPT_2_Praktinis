package com.example.mipt_2_praktinis

import org.junit.Test
import org.junit.Assert.*


class WordUnitTest {

    // function countText without referenced state.
    private fun testCountText(text: String, option: String = "letters"): Int {
        return if (text.isEmpty()) 0
        else if (option == "letters") text.length
        else if (option == "words") text.trim().replace("\\s+".toRegex(), " ").split(" ").count()
        else 0
    }

    @Test
    fun countIsCorrect0() {
        assertEquals(4, testCountText("aaaa"))
    }

    @Test
    fun countIsCorrect1() {
        assertEquals(5, testCountText("aa aa"))
    }

    @Test
    fun countIsCorrect2() {
        assertEquals(6, testCountText("aa  aa"))
    }

    @Test
    fun countIsCorrect3() {
        assertEquals(2, testCountText("aa   aa", "words"))
    }

    @Test
    fun countIsCorrect4() {
        assertEquals(2, testCountText("aa   aa      \n  ", "words"))
    }

    @Test
    fun countIsCorrect5() {
        assertEquals(3, testCountText("  aa   aa _", "words"))
    }
}