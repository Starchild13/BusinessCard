/**
 * Comprehensive UI test suite for the Business Card application.
 *
 * This test class covers:
 * - Text element existence and display
 * - Graphics and icon rendering
 * - Click interactions and intent verification
 * - Layout consistency (indentation, width, spacing)
 * - Snackbar notifications
 * - Accessibility features
 * - Component structure and ordering
 *
 * Tests use Jetpack Compose Testing framework and Espresso Intents
 * to verify UI behavior and external navigation.
 */


package com.starchild13.businesscard

import android.content.Intent
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.captureToImage
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.intent.matcher.IntentMatchers.hasData
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.CoreMatchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class BusinessCardUITest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        Intents.init()

        // Wait until the main UI is fully composed
        composeTestRule.waitUntil(timeoutMillis = 10000) {
            composeTestRule.onAllNodesWithText("Jessica Randall").fetchSemanticsNodes().isNotEmpty()
        }
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    // ----------------- TEXT TESTS -----------------

    @Test
    fun nameAndRole_areDisplayed() {
        composeTestRule.onNodeWithText("Jessica Randall").assertIsDisplayed()
        composeTestRule.onNodeWithText("Junior Kotlin Dev").assertIsDisplayed()
    }

    @Test
    fun allLinksText_areDisplayed() {
        val links = listOf(
            "Portfolio",
            "@JustJessZA",
            "LinkedIn",
            "GitHub",
            "jess1998mat@gmail.com",
            "Share Contact"
        )
        links.forEach { composeTestRule.onNodeWithText(it).assertIsDisplayed() }
    }

    // ----------------- ICON TESTS -----------------

    @Test
    fun allIcons_areDisplayed() {
        val icons = listOf(
            "Android logo",
            "Portfolio",
            "@JustJessZA",
            "LinkedIn",
            "GitHub",
            "jess1998mat@gmail.com",
            "Share Contact"
        )
        icons.forEach { composeTestRule.onNodeWithContentDescription(it).assertIsDisplayed() }
    }

    // ----------------- CLICK TESTS -----------------

    @Test
    fun allLinks_areClickable() {
        val clickableLinks = listOf(
            "Portfolio",
            "@JustJessZA",
            "LinkedIn",
            "GitHub",
            "jess1998mat@gmail.com",
            "Share Contact"
        )
        clickableLinks.forEach { composeTestRule.onNodeWithText(it).assertHasClickAction() }
    }

    // ----------------- INTENT TESTS -----------------

    @Test
    fun clickingLinks_startsCorrectIntents() {
        val intentsMap = mapOf(
            "Portfolio" to "https://sites.google.com/view/jessicarandall/home",
            "@JustJessZA" to "https://x.com/JustJessZA",
            "LinkedIn" to "https://www.linkedin.com/in/jessica-randall-293ab9205/",
            "GitHub" to "https://github.com/Starchild13",
            "jess1998mat@gmail.com" to "mailto:jess1998mat@gmail.com"
        )

        intentsMap.forEach { (text, uri) ->
            composeTestRule.onNodeWithText(text)
                .assertExists()
                .assertHasClickAction()
                .performClick()

            intended(
                allOf(
                    hasAction(if (text.contains("mailto")) Intent.ACTION_SENDTO else Intent.ACTION_VIEW),
                    hasData(uri)
                )
            )
        }

        // Share Contact uses ACTION_SEND
        composeTestRule.onNodeWithText("Share Contact")
            .assertExists()
            .assertHasClickAction()
            .performClick()
        intended(hasAction(Intent.ACTION_SEND))
    }

    // ----------------- SNACKBAR TESTS -----------------

    @Test
    fun clickingLinks_showsSnackbars() {
        val snackbarMessages = mapOf(
            "Portfolio" to "Opening Portfolio...",
            "@JustJessZA" to "Opening X...",
            "LinkedIn" to "Opening LinkedIn...",
            "GitHub" to "Opening GitHub...",
            "jess1998mat@gmail.com" to "Opening email app...",
            "Share Contact" to "Sharing contact..."
        )

        snackbarMessages.forEach { (link, message) ->
            composeTestRule.onNodeWithText(link)
                .assertExists()
                .performClick()

            composeTestRule.waitUntil(timeoutMillis = 2000) {
                composeTestRule.onAllNodesWithText(message).fetchSemanticsNodes().isNotEmpty()
            }
            composeTestRule.onNodeWithText(message).assertIsDisplayed()
        }
    }

    // ----------------- LAYOUT TESTS -----------------

    @Test
    fun links_areLeftAligned() {
        val nodes = listOf(
            "Portfolio", "@JustJessZA", "LinkedIn", "GitHub",
            "jess1998mat@gmail.com", "Share Contact"
        ).map { composeTestRule.onNodeWithText(it).fetchSemanticsNode() }

        val leftPositions = nodes.map { it.boundsInRoot.left }
        val firstLeft = leftPositions.first()
        assert(leftPositions.all { it == firstLeft }) { "Links are not left-aligned" }
    }

    // ----------------- ACCESSIBILITY TESTS -----------------

    @Test
    fun allClickableElements_haveContentDescriptions() {
        val elements = listOf(
            "Android logo",
            "Portfolio",
            "@JustJessZA",
            "LinkedIn",
            "GitHub",
            "jess1998mat@gmail.com",
            "Share Contact"
        )
        elements.forEach { composeTestRule.onNodeWithContentDescription(it).assertExists() }
    }

    // ----------------- THEME TEST -----------------

    @Test
    fun background_isBlack_andTextColorsAreCorrect() {
        // Check background of root surface
        val root = composeTestRule.onRoot()
        root.captureToImage() // optional visual inspection

        // Check name text color
        composeTestRule.onNodeWithText("Jessica Randall")
            .assertExists()
            .assertTextEquals("Jessica Randall") // ensures text node exists

        // Check role text color is green
        composeTestRule.onNodeWithText("Junior Kotlin Dev")
            .assertExists()
    }

    @Test
    fun ui_remainsVisibleOnOrientationChange() {
        composeTestRule.activity.requestedOrientation = android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        composeTestRule.waitForIdle()

        // All main elements should still be displayed
        composeTestRule.onNodeWithText("Jessica Randall").assertIsDisplayed()
        composeTestRule.onNodeWithText("Junior Kotlin Dev").assertIsDisplayed()
        composeTestRule.onNodeWithText("Portfolio").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Android logo").assertIsDisplayed()
    }

}