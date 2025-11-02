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
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.starchild13.businesscard.ui.theme.BusinessCardTheme
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

        // Share Contact
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
    fun links_haveConsistentWidthAndAlignment() {
        val nodes = listOf("Portfolio", "LinkedIn", "GitHub").map {
            composeTestRule.onNodeWithText(it).fetchSemanticsNode()
        }
        val lefts = nodes.map { it.boundsInRoot.left }
        val widths = nodes.map { it.boundsInRoot.width }
        assert(lefts.distinct().size == 1) { "Links are not aligned" }
        assert(widths.distinct().size == 1) { "Links have inconsistent width" }
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
}
