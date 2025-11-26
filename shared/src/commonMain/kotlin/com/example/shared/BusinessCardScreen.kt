package com.example.shared

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AccountCircle

import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch



// IMPORTANT:
// Compose Multiplatform does not bundle Material Icons by default.
// For shared code, you must provide your own icons.
// Below are placeholders you can replace:
object MPIcons {
    val Portfolio: ImageVector = Icons.Filled.AccountBox
    val Twitter: ImageVector = Icons.Filled.Share
    val LinkedIn: ImageVector = Icons.Filled.Face
    val GitHub: ImageVector = Icons.Filled.AccountCircle
    val Email: ImageVector = Icons.Filled.Email
    val Share: ImageVector = Icons.Filled.Share
}

/* ---------------------- TextCard ---------------------- */
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

/* ---------------------- IconCard ---------------------- */
@Composable
fun IconCard(
    text: String,
    icon: ImageVector?,
    onClick: () -> Unit,
) {
    if (icon != null) {
        Icon(
            imageVector = icon,
            contentDescription = text,
            modifier = Modifier.size(26.dp).clickable { onClick() },
            tint = Color(0xFF4CAF50)
        )
    }

    Text(
        text = text,
        color = Color.White,
        fontSize = 18.sp,
        modifier = Modifier.padding(start = 16.dp).clickable { onClick() },
        textAlign = TextAlign.Center
    )
}

/* ---------------------- IconColumn ---------------------- */
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
            scope.launch {
                snackbarHostState.showSnackbar("$text clicked!")
            }
            onIconClick(text)
        }
    }
    HorizontalDivider(color = Color.DarkGray)
}

/* ---------------------- ProfileCard ---------------------- */
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

/* ---------------------- Preview / Usage ---------------------- */
//@Composable
//fun ProfilePreview() {
//    val snackbarHostState = remember { SnackbarHostState() }
//    val scope = rememberCoroutineScope()
//
//    ProfileCard(
//        name = "Jessica Randall",
//        role = "Android Dev in Training",
//        image = image,
//        snackbarHostState = snackbarHostState,
//        scope = scope,
//        onIconClick = { println("Clicked $it") }
//    )
//}
