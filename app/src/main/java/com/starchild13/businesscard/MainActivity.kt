

package com.starchild13.businesscard


import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import com.starchild13.businesscard.ui.theme.BusinessCardTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.io.File





class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BusinessCardTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    AnImage(name = "Jessica Randall", role = "Junior Kotlin Dev")
                }
            }
        }
    }
}

@Composable
fun Text_card(name: String, role: String) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = name,
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp,
            textAlign = TextAlign.Center,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = role,
            fontSize = 22.sp,
            textAlign = TextAlign.Center,
            color = Color(0xFF4CAF50)
        )
    }
}

@Composable
fun AnImage(name: String, role: String) {
    val image = painterResource(R.drawable.android_logo)
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val configuration = LocalConfiguration.current

    var lastOrientation by remember { mutableStateOf(configuration.orientation) }

    // Detect orientation changes
    LaunchedEffect(configuration.orientation) {
        if (configuration.orientation != lastOrientation) {
            lastOrientation = configuration.orientation
            val orientationText = if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE)
                "Landscape Mode" else "Portrait Mode"

            scope.launch {
                snackbarHostState.showSnackbar(
                    message = "Switched to $orientationText",
                    duration = SnackbarDuration.Long
                )
            }
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState) { data ->
                Snackbar(
                    backgroundColor = Color.DarkGray,
                    modifier = Modifier.padding(12.dp)
                ) {
                    Text(
                        text = data.message,
                        fontSize = 18.sp,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        },
        backgroundColor = Color.Black
    ) { padding ->
        val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

        val layoutModifier = Modifier
            .background(Color.Black)
            .padding(padding)
            .fillMaxSize()

        if (isLandscape) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = layoutModifier
            ) {
                Image(
                    painter = image,
                    contentDescription = null,
                    modifier = Modifier
                        .size(200.dp)
                        .padding(8.dp),
                    contentScale = ContentScale.Fit
                )
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text_card(name = name, role = role)
                    Spacer(modifier = Modifier.height(24.dp))
                    Icon_column(snackbarHostState, scope)
                }
            }
        } else {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = layoutModifier
            ) {
                Image(
                    painter = image,
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(220.dp)
                        .padding(8.dp)
                )

                Text_card(name = name, role = role)
                Spacer(modifier = Modifier.height(60.dp))
                Icon_column(snackbarHostState, scope)
            }
        }
    }
}

@Composable
fun icon_card_2(
    text: String,
    imageVector: ImageVector,
    function: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(vertical = 10.dp)
            .fillMaxWidth(0.7f)
            .clickable { function() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = text,
            modifier = Modifier.size(26.dp),
            tint = Color(0xFF4CAF50)
        )

        Text(
            text = text,
            color = Color.White,
            fontSize = 18.sp,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

@Composable
fun Icon_column(
    snackbarHostState: SnackbarHostState,
    scope: CoroutineScope
) {
    val context = LocalContext.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.wrapContentWidth()
    ) {
        val icons = listOf(
            Triple("Portfolio", Icons.Filled.AccountBox, "https://sites.google.com/view/jessicarandall/home"),
            Triple("@JustJessZA", Icons.Filled.Share, "https://x.com/JustJessZA"),
            Triple("LinkedIn", Icons.Filled.AccountCircle, "https://www.linkedin.com/in/jessica-randall-293ab9205/"),
            Triple("GitHub", Icons.Filled.Face, "https://github.com/Starchild13")
        )

        icons.forEach { (text, icon, link) ->
            Divider(color = Color.Gray)
            icon_card_2(text, icon) {
                scope.launch {
                    snackbarHostState.showSnackbar("Opening $text...", duration = SnackbarDuration.Long)
                }
                val intent = Intent(Intent.ACTION_VIEW, link.toUri())
                context.startActivity(intent)
            }
        }

        Divider(color = Color.Gray)

        icon_card_2("Email Me", Icons.Filled.Email) {
            scope.launch {
                snackbarHostState.showSnackbar("Opening email app...", duration = SnackbarDuration.Long)
            }
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = "mailto:jess1998mat@gmail.com".toUri()
                putExtra(Intent.EXTRA_SUBJECT, "Hello!")
            }
            context.startActivity(intent)
        }

        Divider(color = Color.Gray)

        icon_card_2("Share Contact", Icons.Filled.Share) {
            shareVCard(context)
            scope.launch {
                snackbarHostState.showSnackbar("Sharing contact...", duration = SnackbarDuration.Long)
            }
        }
        Divider(color = Color.Gray)
    }
}

fun shareVCard(context: Context) {
    val vcardString = """
        BEGIN:VCARD
        VERSION:3.0
        N:Randall;Jessica;;;
        FN:Jessica Randall
        ORG:Mentorlst
        TITLE:Android Developer / Android Engineer
        EMAIL;TYPE=INTERNET:jess1998mat@gmail.com
        URL: https://github.com/Starchild13
        URL:https://sites.google.com/view/jessicarandall/home
        END:VCARD
    """.trimIndent()

    try {
        val fileName = "JessicaRandall.vcf"
        val file = File(context.cacheDir, fileName)
        file.writeText(vcardString)

        val uri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            file
        )

        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/x-vcard"
            putExtra(Intent.EXTRA_STREAM, uri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

        context.startActivity(Intent.createChooser(shareIntent, "Share contact via"))
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BusinessCardTheme {
        AnImage(name = "Jessica Randall", role = "Android Dev in Training")
    }
}
