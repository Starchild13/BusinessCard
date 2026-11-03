/**
 * Copyright (c) 2023-2025 Jessica Randall (Starchild13)
 * Licensed under the MIT License.
 *
 * BusinessCardScreen.kt contains the core UI components, layout structures, and action handlers
 * for the Business Card application built with Jetpack Compose and Material 3, including
 * in-app WebView navigation, in-app email composer, and in-app contact sharing with
 * dedicated back button support.
 */
package com.starchild13.businesscard

import android.content.Intent
import android.net.Uri
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Object holding custom [ImageVector] mappings for professional social and contact links,
 * providing accessible icons for Sessionize, Twitter/X, LinkedIn, GitHub, Email, and Sharing.
 */
object MPIcons {
    val Sessionize: ImageVector = Icons.Filled.Event
    val Twitter: ImageVector = Icons.Filled.Share
    val LinkedIn: ImageVector = Icons.Filled.Face
    val GitHub: ImageVector = Icons.Filled.AccountCircle
    val Email: ImageVector = Icons.Filled.Email
    val Share: ImageVector = Icons.Filled.Share
}

/**
 * Root composable function for the business card screen.
 * Manages the [Scaffold], snackbar states, coroutine scope for UI notifications,
 * and handles in-app WebView navigation, in-app Email Composer, and in-app Contact Sharing
 * with dedicated Back buttons to return to the business card.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BusinessCardScreen() {
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // State variables for in-app web view navigation
    var currentUrl by remember { mutableStateOf<String?>(null) }
    var currentTitle by remember { mutableStateOf<String?>(null) }

    // State variables for in-app email composer screen
    var showEmailComposer by remember { mutableStateOf(false) }
    var emailSubject by remember { mutableStateOf("Business Inquiry") }
    var emailBody by remember { mutableStateOf("") }

    // State variables for in-app contact sharing screen
    var showShareScreen by remember { mutableStateOf(false) }

    when {
        // 1. In-app WebView Screen
        currentUrl != null -> {
            BackHandler {
                currentUrl = null
            }
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text(currentTitle ?: "Web View", color = Color.White) },
                        navigationIcon = {
                            IconButton(onClick = { currentUrl = null }) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Back to Business Card",
                                    tint = Color.White
                                )
                            }
                        },
                        actions = {
                            IconButton(onClick = {
                                currentUrl?.let { url ->
                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                                    context.startActivity(intent)
                                }
                            }) {
                                Icon(
                                    imageVector = Icons.Filled.OpenInBrowser,
                                    contentDescription = "Open in Browser",
                                    tint = Color.White
                                )
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.DarkGray)
                    )
                },
                containerColor = Color.Black
            ) { paddingValues ->
                AndroidView(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    factory = { ctx ->
                        WebView(ctx).apply {
                            webViewClient = WebViewClient()
                            settings.javaScriptEnabled = true
                            settings.domStorageEnabled = true
                            currentUrl?.let { loadUrl(it) }
                        }
                    },
                    update = { view ->
                        currentUrl?.let { view.loadUrl(it) }
                    }
                )
            }
        }

        // 2. In-app Email Composer Screen with Back Button
        showEmailComposer -> {
            BackHandler {
                showEmailComposer = false
            }
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text("Send Email", color = Color.White) },
                        navigationIcon = {
                            IconButton(onClick = { showEmailComposer = false }) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Back to Business Card",
                                    tint = Color.White
                                )
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.DarkGray)
                    )
                },
                containerColor = Color.Black
            ) { paddingValues ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "To: jess1998mat@gmail.com",
                        color = Color.White,
                        fontSize = 16.sp
                    )

                    OutlinedTextField(
                        value = emailSubject,
                        onValueChange = { emailSubject = it },
                        label = { Text("Subject") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF4CAF50),
                            unfocusedBorderColor = Color.Gray,
                            focusedLabelColor = Color(0xFF4CAF50),
                            unfocusedLabelColor = Color.Gray,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        )
                    )

                    OutlinedTextField(
                        value = emailBody,
                        onValueChange = { emailBody = it },
                        label = { Text("Message") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF4CAF50),
                            unfocusedBorderColor = Color.Gray,
                            focusedLabelColor = Color(0xFF4CAF50),
                            unfocusedLabelColor = Color.Gray,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        )
                    )

                    Button(
                        onClick = {
                            val intent = Intent(Intent.ACTION_SENDTO).apply {
                                data = Uri.parse("mailto:jess1998mat@gmail.com")
                                putExtra(Intent.EXTRA_SUBJECT, emailSubject)
                                putExtra(Intent.EXTRA_TEXT, emailBody)
                            }
                            context.startActivity(intent)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
                    ) {
                        Text("Send Email", color = Color.Black, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // 3. In-app Share Contact Screen with Back Button
        showShareScreen -> {
            BackHandler {
                showShareScreen = false
            }
            val clipboardManager = LocalClipboardManager.current
            val vcardText = """
                BEGIN:VCARD
                VERSION:3.0
                FN:Jessica Randall
                TITLE:Junior Kotlin Dev
                EMAIL:jess1998mat@gmail.com
                END:VCARD
            """.trimIndent()

            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text("Share Contact", color = Color.White) },
                        navigationIcon = {
                            IconButton(onClick = { showShareScreen = false }) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Back to Business Card",
                                    tint = Color.White
                                )
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.DarkGray)
                    )
                },
                containerColor = Color.Black
            ) { paddingValues ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Digital Business Card vCard",
                        color = Color(0xFF4CAF50),
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center
                    )

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E))
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(text = "Name: Jessica Randall", color = Color.White, fontSize = 16.sp)
                            Text(text = "Role: Junior Kotlin Dev", color = Color.White, fontSize = 16.sp)
                            Text(text = "Email: jess1998mat@gmail.com", color = Color.White, fontSize = 16.sp)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = "vCard Preview:", color = Color.Gray, fontSize = 14.sp)
                            Text(
                                text = vcardText,
                                color = Color(0xFF4CAF50),
                                fontSize = 13.sp
                            )
                        }
                    }

                    Button(
                        onClick = {
                            val intent = Intent(Intent.ACTION_SEND).apply {
                                type = "text/x-vcard"
                                putExtra(Intent.EXTRA_TEXT, vcardText)
                            }
                            context.startActivity(Intent.createChooser(intent, "Share Contact"))
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
                    ) {
                        Text("Share via System Sheet", color = Color.Black, fontWeight = FontWeight.Bold)
                    }

                    OutlinedButton(
                        onClick = {
                            clipboardManager.setText(AnnotatedString(vcardText))
                            scope.launch {
                                snackbarHostState.showSnackbar("vCard copied to clipboard!")
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFF4CAF50))
                    ) {
                        Text("Copy vCard to Clipboard", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // 4. Default Business Card View
        else -> {
            Scaffold(
                snackbarHost = { SnackbarHost(snackbarHostState) },
                containerColor = Color.Black
            ) { paddingValues ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    ProfileCard(
                        name = "Jessica Randall",
                        role = "Junior Kotlin Dev",
                        image = painterResource(R.drawable.android_logo),
                        snackbarHostState = snackbarHostState,
                        scope = scope,
                        onIconClick = { type ->
                            when (type) {
                                "Sessionize" -> {
                                    currentTitle = "Sessionize Profile"
                                    currentUrl = "https://sessionize.com/Jessica_Randall/"
                                }
                                "@JustJessZA" -> {
                                    currentTitle = "X (Twitter)"
                                    currentUrl = "https://x.com/JustJessZA"
                                }
                                "LinkedIn" -> {
                                    currentTitle = "LinkedIn"
                                    currentUrl = "https://www.linkedin.com/in/jessica-randall-293ab9205/"
                                }
                                "GitHub" -> {
                                    currentTitle = "GitHub"
                                    currentUrl = "https://github.com/Starchild13"
                                }
                                "Email Me" -> {
                                    showEmailComposer = true
                                }
                                "Share Contact" -> {
                                    showShareScreen = true
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}

/**
 * Displays the name and role title of the profile owner with styled typography and spacing.
 *
 * @param name The full name displayed prominently on the card.
 * @param role The professional title or role description.
 */
@Composable
fun TextCard(name: String, role: String) {
    Text(
        text = name,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        textAlign = TextAlign.Center,
        color = Color(0xFF4CAF50)
    )

    Spacer(modifier = Modifier.height(12.dp))

    Text(
        text = role,
        fontSize = 22.sp,
        textAlign = TextAlign.Center,
        color = Color(0xFF4CAF50)
    )

    Spacer(modifier = Modifier.height(12.dp))
}

/**
 * Renders an individual clickable contact or social row containing an icon and label text.
 *
 * @param text The display label for the action/link.
 * @param icon The optional [ImageVector] displayed alongside the text.
 * @param onClick Callback invoked when the row is tapped.
 */
@Composable
fun IconCard(
    text: String,
    icon: ImageVector?,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 8.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = text,
                modifier = Modifier.size(26.dp),
                tint = Color(0xFF4CAF50)
            )
        }

        Text(
            text = text,
            color = Color.White,
            fontSize = 18.sp,
            modifier = Modifier.padding(start = 16.dp),
            textAlign = TextAlign.Center
        )
    }
}

/**
 * Renders a column of interactive contact and social rows separated by dividers.
 * Triggers snackbars and handles click delegations for each item.
 *
 * @param snackbarHostState State controller for displaying feedback Snackbars.
 * @param scope Coroutine scope used to launch snackbar display tasks.
 * @param onIconClick Callback receiving the label identifier of the clicked item.
 */
@Composable
fun IconColumn(
    snackbarHostState: SnackbarHostState,
    scope: CoroutineScope,
    onIconClick: (String) -> Unit,
) {
    val icons = listOf(
        "Sessionize" to MPIcons.Sessionize,
        "@JustJessZA" to MPIcons.Twitter,
        "LinkedIn" to MPIcons.LinkedIn,
        "GitHub" to MPIcons.GitHub,
        "Email Me" to MPIcons.Email,
        "Share Contact" to MPIcons.Share
    )

    icons.forEach { (text, icon) ->
        HorizontalDivider(color = Color.DarkGray)
        IconCard(text = text, icon = icon) {
            val message = when(text) {
                "Sessionize" -> "Opening Sessionize..."
                "@JustJessZA" -> "Opening X..."
                "LinkedIn" -> "Opening LinkedIn..."
                "GitHub" -> "Opening GitHub..."
                "Email Me" -> "Opening email composer..."
                "Share Contact" -> "Opening contact sharing..."
                else -> "$text clicked!"
            }
            scope.launch {
                snackbarHostState.showSnackbar(message)
            }
            onIconClick(text)
        }
    }
    HorizontalDivider(color = Color.DarkGray)
}

/**
 * Composable container assembling the profile image, header title, name/role text, and action links.
 *
 * @param name Profile owner name.
 * @param role Professional role description.
 * @param image Profile or logo painter resource.
 * @param snackbarHostState Snackbar host state for feedback notifications.
 * @param scope Coroutine scope for launching snackbar emissions.
 * @param onIconClick Click listener for contact options.
 */
@Composable
fun ProfileCard(
    name: String,
    role: String,
    image: Painter,
    snackbarHostState: SnackbarHostState,
    scope: CoroutineScope,
    onIconClick: (String) -> Unit
) {
    Text(
        text = "Business Card",
        fontSize = 18.sp,
        color = Color.White,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth(),
    )

    Image(
        painter = image,
        contentDescription = "Profile Image",
        modifier = Modifier.size(220.dp),
        contentScale = ContentScale.Fit
    )

    Spacer(modifier = Modifier.height(16.dp))

    TextCard(name, role)

    Spacer(modifier = Modifier.height(24.dp))

    IconColumn(
        snackbarHostState = snackbarHostState,
        scope = scope,
        onIconClick = onIconClick,
    )
}
