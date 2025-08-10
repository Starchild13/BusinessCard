package com.starchild13.businesscard


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
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
                // A surface container using the 'background' color from the theme
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
fun Text_card(name: String, role: String){
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {

        Text(
            text = name,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            textAlign = TextAlign.Center,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(height = 15.dp))

        Text(
            text = role,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            color = Color.Green
        )
    }

}

@Composable
fun AnImage(name: String, role: String) {
    val image = painterResource(R.drawable.android_logo)
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        backgroundColor = Color.Black
    ) { padding ->
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(Color.Black)
                .padding(padding)
        ) {
            Image(
                painter = image,
                contentDescription = null,
                modifier = Modifier,
                contentScale = ContentScale.FillWidth
            )

            Text_card(name = name, role = role)

            Spacer(modifier = Modifier.height(100.dp))

            Icon_column(snackbarHostState, scope)
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
            .padding(top = 8.dp, bottom = 8.dp)
            .fillMaxWidth(0.6f)
            .clickable { function() }, // Makes entire row clickable
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = text,
            modifier = Modifier.size(20.dp),
            tint = Color.Green
        )

        Text(
            text = text,
            color = Color.White,
            fontSize = 15.sp,
            modifier = Modifier.padding(start = 16.dp)
        )
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
        // Create .vcf file in cache
        val fileName = "JessicaRandall.vcf"
        val file = File(context.cacheDir, fileName)
        file.writeText(vcardString)

        // Get URI using FileProvider
        val uri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            file
        )

        // Create share intent
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/x-vcard"
            putExtra(Intent.EXTRA_STREAM, uri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

        context.startActivity(Intent.createChooser(shareIntent, "Share contact via"))

    } catch (e: Exception) {
        Toast.makeText(context, "Failed to share contact", Toast.LENGTH_SHORT).show()
        e.printStackTrace()
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BusinessCardTheme() {
        AnImage(name = "Jessica Randall", role = " Android Dev in Training")
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
        Divider(color = Color.Gray, modifier = Modifier.fillMaxWidth())

        icon_card_2("Portfolio", Icons.Filled.AccountBox) {
            scope.launch {
                snackbarHostState.showSnackbar("Opening Portfolio...")
            }
            val intent = Intent(
                Intent.ACTION_VIEW,
                "https://sites.google.com/view/jessicarandall/home".toUri()
            )
            context.startActivity(intent)
        }

        Divider(color = Color.Gray, modifier = Modifier.fillMaxWidth())

        icon_card_2("@JustJessZA", imageVector = Icons.Filled.Share) {
            scope.launch {
                snackbarHostState.showSnackbar("Opening X...")
            }
            val intent = Intent(Intent.ACTION_VIEW, "https://x.com/JustJessZA".toUri())
            context.startActivity(intent)
        }

        Divider(color = Color.Gray, modifier = Modifier.fillMaxWidth())

        icon_card_2("LinkedIn", Icons.Filled.AccountCircle) {
            scope.launch {
                snackbarHostState.showSnackbar("Opening LinkedIn...")
            }
            val intent = Intent(
                Intent.ACTION_VIEW,
                "https://www.linkedin.com/in/jessica-randall-293ab9205/".toUri()
            )
            context.startActivity(intent)
        }

        Divider(color = Color.Gray, modifier = Modifier.fillMaxWidth())

        icon_card_2("GitHub", Icons.Filled.Face) {
            scope.launch {
                snackbarHostState.showSnackbar("Opening GitHub...")
            }
            val intent = Intent(Intent.ACTION_VIEW, "https://github.com/Starchild13".toUri())
            context.startActivity(intent)
        }

        Divider(color = Color.Gray, modifier = Modifier.fillMaxWidth())

        icon_card_2("jess1998mat@gmail.com", Icons.Filled.Email) {
            scope.launch {
                snackbarHostState.showSnackbar("Opening email app...")
            }
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = "mailto:jess1998mat@gmail.com".toUri()
                putExtra(Intent.EXTRA_SUBJECT, "Hello!")
            }
            context.startActivity(intent)
        }

        Divider(color = Color.Gray, modifier = Modifier.fillMaxWidth())

        icon_card_2("Share Contact", Icons.Filled.Share) {
            shareVCard(context)
            scope.launch {
                snackbarHostState.showSnackbar("Sharing contact...")
            }


        }

        Divider(color = Color.Gray, modifier = Modifier.fillMaxWidth())
    }
}



















