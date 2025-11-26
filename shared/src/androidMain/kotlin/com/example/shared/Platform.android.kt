package com.example.shared


import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import androidx.core.content.ContextCompat
import android.content.Context

actual fun platform() = "Android"



// You MUST provide access to a Context
lateinit var appContext: Context

actual fun openLink(url: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    ContextCompat.startActivity(appContext, intent, null)
}

actual fun sendEmail(address: String, subject: String?) {
    val intent = Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("mailto:$address")
        putExtra(Intent.EXTRA_SUBJECT, subject ?: "")
    }
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    ContextCompat.startActivity(appContext, intent, null)
}

actual fun shareVCard(name: String, email: String) {
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
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    ContextCompat.startActivity(appContext, intent, null)
}
