package com.starchild13.businesscard

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

object MPIcons {
    val Portfolio: ImageVector = Icons.Filled.AccountBox
    val Twitter: ImageVector = Icons.Filled.Share
    val LinkedIn: ImageVector = Icons.Filled.Face
    val GitHub: ImageVector = Icons.Filled.AccountCircle
    val Email: ImageVector = Icons.Filled.Email
    val Share: ImageVector = Icons.Filled.Share
}

@Composable
fun BusinessCardScreen() {
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

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
                        "Portfolio" -> openLink(context, "https://sites.google.com/view/jessicarandall/home")
                        "@JustJessZA" -> openLink(context, "https://x.com/JustJessZA")
                        "LinkedIn" -> openLink(context, "https://www.linkedin.com/in/jessica-randall-293ab9205/")
                        "GitHub" -> openLink(context, "https://github.com/Starchild13")
                        "Email Me" -> sendEmail(context, "jess1998mat@gmail.com", "Business Inquiry")
                        "Share Contact" -> shareVCard(context, "Jessica Randall", "jess1998mat@gmail.com")
                    }
                }
            )
        }
    }
}

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

@Composable
fun IconColumn(
    snackbarHostState: SnackbarHostState,
    scope: CoroutineScope,
    onIconClick: (String) -> Unit,
) {
    val icons = listOf(
        "Portfolio" to MPIcons.Portfolio,
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
                "Portfolio" -> "Opening Portfolio..."
                "@JustJessZA" -> "Opening X..."
                "LinkedIn" -> "Opening LinkedIn..."
                "GitHub" -> "Opening GitHub..."
                "Email Me" -> "Opening email app..."
                "Share Contact" -> "Sharing contact..."
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
        onIconClick = onIconClick
    )
}

fun openLink(context: android.content.Context, url: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    context.startActivity(intent)
}

fun sendEmail(context: android.content.Context, address: String, subject: String?) {
    val intent = Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("mailto:$address")
        putExtra(Intent.EXTRA_SUBJECT, subject ?: "")
    }
    context.startActivity(intent)
}

fun shareVCard(context: android.content.Context, name: String, email: String) {
    val vcard = """
        BEGIN:VCARD
        VERSION:3.0
        FN:$name
        EMAIL:$email
        END:VCARD
    """.trimIndent()

    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/x-vcard"
        putExtra(Intent.EXTRA_TEXT, vcard)
    }
    context.startActivity(Intent.createChooser(intent, "Share Contact"))
}
