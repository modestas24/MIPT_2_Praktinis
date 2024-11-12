package com.example.mipt_2_praktinis

import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import org.junit.Test
import org.junit.Rule


class WordIntegrationTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private val output = hasTestTag("CountOutput")
    private val input = hasTestTag("CountInput")
    private val submit = hasText("Count")

    @Test
    fun countValidInputTest0() {
        composeTestRule.onNode(input).performTextInput("aaa aaa")
        composeTestRule.onNode(submit).performClick()

        composeTestRule.onNode(output).assertTextEquals("2")
    }

    @Test
    fun countValidInputTest1() {
        composeTestRule.onNode(input).performTextInput("aaa aaa")
        composeTestRule.onNode(submit).performClick()

        composeTestRule.onNode(output).assertTextEquals("2")
    }

    @Test
    fun countValidInputTest2() {
        composeTestRule.onNode(input).performTextInput("aaa  aaa ")
        composeTestRule.onNode(hasText("words")).performClick()
        composeTestRule.onNode(hasText("letters")).performClick()
        composeTestRule.onNode(submit).performClick()

        composeTestRule.onNode(output).assertTextEquals("9")
    }

    @Test
    fun countInvalidInputTest() {
        composeTestRule.onNode(input).performTextInput("")
        composeTestRule.onNode(submit).performClick()

        composeTestRule.onNode(output).assertTextEquals("Empty")
    }
}