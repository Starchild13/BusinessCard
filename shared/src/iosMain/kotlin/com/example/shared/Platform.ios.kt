package com.example.shared

import platform.Foundation.NSURL
import platform.UIKit.UIApplication

actual fun platform() = "iOS"


actual fun openLink(url: String) {
    val nsUrl = NSURL.URLWithString(url)
    if (nsUrl != null) {
        UIApplication.sharedApplication.openURL(nsUrl)
    } else {
        println("Invalid URL: $url")
    }
}

actual fun sendEmail(address: String, subject: String?) {
    // iOS requires UIViewController to present MFMailComposeViewController
    println("Send email to $address with subject $subject — implement in iOS host app")
}

actual fun shareVCard(name: String, email: String) {
    // iOS requires UIViewController to present UIActivityViewController
    println("Share vCard $name <$email> — implement in iOS host app")
}



