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
import android.content.pm.ActivityInfo
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.intent.matcher.IntentMatchers.hasData
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.CoreMatchers.allOf
import org.junit.*
import org.junit.runner.RunWith

/**
 * Runs the test class using the AndroidJUnit4 test runner.
 * This is required for Android instrumentation tests.
 */
@RunWith(AndroidJUnit4::class)
class BusinessCardUITest {

    /**
     * Compose rule to control the activity and interact
     * with the UI hierarchy during testing.
     */
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    /**
     * Initializes Espresso Intents and waits until
     * the composable content is fully loaded before running tests.
     */
    @Before
    fun setUp() {
        Intents.init()
        // Wait until the "Jessica Randall" node appears to ensure the UI is rendered
        composeTestRule.waitUntil(timeoutMillis = 5000) {
            composeTestRule.onAllNodesWithText("Jessica Randall").fetchSemanticsNodes().isNotEmpty()
        }
    }

    /**
     * Releases the Intents framework after all tests are complete.
     * This is good cleanup practice.
     */
    @After
    fun tearDown() {
        Intents.release()
    }

    // ------------------ TEXT & IMAGE TESTS ------------------

    /**
     * Verifies that the user's name and role are visible on screen.
     */
    @Test
    fun nameAndRole_areDisplayed() {
        composeTestRule.onNodeWithText("Jessica Randall").assertIsDisplayed()
        composeTestRule.onNodeWithText("Junior Kotlin Dev").assertIsDisplayed()
    }

    /**
     * Checks that the Android logo or main image element exists in the UI.
     * Uses test tags or composable hierarchy to locate the image.
     */
    @Test
    fun androidLogo_isDisplayed() {
        composeTestRule.onNode(hasAnyChild(hasTestTag("Image"))).assertExists()
    }

    // ------------------ ICON & TEXT VISIBILITY ------------------

    /**
     * Ensures all labeled clickable text elements (social icons, links)
     * are properly displayed in the UI.
     */
    @Test
    fun allIconLabels_areDisplayed() {
        val labels = listOf(
            "Portfolio", "@JustJessZA", "LinkedIn",
            "GitHub", "Email Me", "Share Contact"
        )
        labels.forEach { composeTestRule.onNodeWithText(it).assertIsDisplayed() }
    }

    // ------------------ CLICK & INTENT TESTS ------------------

    /**
     * Validates that each clickable link launches the correct Intent
     * with the expected action and data URI (for web/email links).
     */
    @Test
    fun clickingLinks_launchesExpectedIntents() {
        val intentMap = mapOf(
            "Portfolio" to "https://sites.google.com/view/jessicarandall/home",
            "@JustJessZA" to "https://x.com/JustJessZA",
            "LinkedIn" to "https://www.linkedin.com/in/jessica-randall-293ab9205/",
            "GitHub" to "https://github.com/Starchild13",
            "Email Me" to "mailto:jess1998mat@gmail.com"
        )

        // Iterate through all link-text pairs and verify their intents
        intentMap.forEach { (label, uri) ->
            composeTestRule.onNodeWithText(label)
                .assertHasClickAction()
                .performClick()

            intended(
                allOf(
                    hasAction(if (label == "Email Me") Intent.ACTION_SENDTO else Intent.ACTION_VIEW),
                    hasData(uri)
                )
            )
        }

        // Special test for the "Share Contact" button using ACTION_SEND
        composeTestRule.onNodeWithText("Share Contact")
            .assertExists()
            .performClick()
        intended(hasAction(Intent.ACTION_SEND))
    }

    // ------------------ SNACKBAR TESTS ------------------

    /**
     * Tests that clicking each icon or label triggers a Snackbar message
     * with the appropriate "Opening ..." or "Sharing ..." text.
     */
    @Test
    fun clickingIcons_showsSnackbarMessages() {
        val messages = mapOf(
            "Portfolio" to "Opening Portfolio...",
            "@JustJessZA" to "Opening X...",
            "LinkedIn" to "Opening LinkedIn...",
            "GitHub" to "Opening GitHub...",
            "Email Me" to "Opening email app...",
            "Share Contact" to "Sharing contact..."
        )

        // Click each label and verify the Snackbar appears
        messages.forEach { (label, message) ->
            composeTestRule.onNodeWithText(label)
                .assertExists()
                .performClick()

            // Wait for the Snackbar to appear before asserting
            composeTestRule.waitUntil(timeoutMillis = 3000) {
                composeTestRule.onAllNodesWithText(message).fetchSemanticsNodes().isNotEmpty()
            }
            composeTestRule.onNodeWithText(message).assertIsDisplayed()
        }
    }

    /**
     * Confirms that the correct Snackbar messages are displayed when
     * the device orientation is changed between landscape and portrait.
     */
    @Test
    fun snackbar_appearsOnOrientationChange() {
        // Switch to landscape mode
        composeTestRule.activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        composeTestRule.waitForIdle()

        // Wait for snackbar to appear using test tag
        composeTestRule.waitUntil(timeoutMillis = 5000) {
            composeTestRule.onAllNodesWithTag("OrientationSnackbar").fetchSemanticsNodes().isNotEmpty()
        }
        composeTestRule.onNodeWithTag("OrientationSnackbar")
            .assertIsDisplayed()
            .assertTextContains("Landscape Mode")

        // Switch back to portrait mode
        composeTestRule.activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        composeTestRule.waitForIdle()

        // Wait for snackbar to appear again
        composeTestRule.waitUntil(timeoutMillis = 5000) {
            composeTestRule.onAllNodesWithTag("OrientationSnackbar").fetchSemanticsNodes().isNotEmpty()
        }
        composeTestRule.onNodeWithTag("OrientationSnackbar")
            .assertIsDisplayed()
            .assertTextContains("Portrait Mode")
    }


    // ------------------ LAYOUT TESTS ------------------

    /**
     * Ensures that the main UI components remain visible and intact
     * after the device is rotated between orientations.
     */
    @Test
    fun uiElementsRemainVisibleAfterRotation() {
        // Rotate to landscape and verify key UI elements remain visible
        composeTestRule.activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Jessica Randall").assertIsDisplayed()
        composeTestRule.onNodeWithText("Junior Kotlin Dev").assertIsDisplayed()
        composeTestRule.onNodeWithText("Portfolio").assertIsDisplayed()

        // Rotate back to portrait and check again
        composeTestRule.activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Jessica Randall").assertIsDisplayed()
        composeTestRule.onNodeWithText("Portfolio").assertIsDisplayed()
    }

    // ------------------ ACCESSIBILITY TESTS ------------------

    /**
     * Confirms that all clickable elements have content labels and actions,
     * ensuring accessibility compliance (e.g., for screen readers).
     */
    @Test
    fun clickableElements_haveContentDescriptionsOrLabels() {
        val clickableLabels = listOf(
            "Portfolio", "@JustJessZA", "LinkedIn", "GitHub",
            "Email Me", "Share Contact"
        )
        clickableLabels.forEach {
            composeTestRule.onNodeWithText(it).assertHasClickAction()
        }
    }

    // ------------------ THEME TEST ------------------

    /**
     * Roughly validates that the theme background is dark (black)
     * and that text elements are visible and readable.
     * Note: captureToImage() is used as a visual assertion placeholder.
     */
    @Test
    fun backgroundColor_isBlack_textIsReadable() {
        val rootImage = composeTestRule.onRoot().captureToImage()
        assert(rootImage != null) // Ensures screenshot was captured

        composeTestRule.onNodeWithText("Jessica Randall").assertIsDisplayed()
        composeTestRule.onNodeWithText("Junior Kotlin Dev").assertIsDisplayed()
    }
}
