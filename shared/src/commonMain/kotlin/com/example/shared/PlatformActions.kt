package com.example.shared


expect fun platform(): String

// ---------------------- Platform action contract ----------------------
// Declare platform-specific actions

expect fun openLink(url: String)

expect fun sendEmail(address: String, subject: String? = null)

expect fun shareVCard(
    name: String = "Jessica Randall",
    email: String = "jess1998mat@gmail.com"
)
