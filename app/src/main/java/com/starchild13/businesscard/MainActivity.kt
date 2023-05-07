package com.starchild13.businesscard


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.starchild13.businesscard.ui.theme.BusinessCardTheme

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
                    AnImage(name = "Jessica Randall", role = "Android Dev in Training")

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
fun AnImage(name: String,role:String) {
    val image = painterResource(R.drawable.android_logo)
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.background(Color.Black)
    ) {
        Image(
            painter = image,
            contentDescription = null,
            modifier = Modifier,
            contentScale = ContentScale.FillWidth
        )

        Text_card(name = name, role = role)

        Spacer(modifier = Modifier.height(height = 100.dp))


        icon_column()


    }


}

@Composable
fun icon_column(){
    Column (horizontalAlignment=Alignment.CenterHorizontally,modifier = Modifier.wrapContentWidth()){
        Divider(color = Color.Gray , modifier = Modifier.fillMaxWidth())
        icon_card_2("+27 81 5316830", Icons.Filled.Phone)
        Divider(color = Color.Gray , modifier = Modifier.fillMaxWidth())
        icon_card_2("@JustJessZA", Icons.Filled.Share)
        Divider(color = Color.Gray , modifier = Modifier.fillMaxWidth())
        icon_card_2("jess1998mat@gmail.com",Icons.Filled.Email)

    }
}

@Composable
fun icon_card_2(text: String, imagevector: ImageVector){
    Row( modifier = Modifier.padding(top = 8.dp,bottom = 8.dp).fillMaxWidth(0.6f)) {
        Icon(
            imageVector = imagevector,
            modifier = Modifier.size(size = 20.dp),
            contentDescription = "Email Address",
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
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
        BusinessCardTheme() {
            AnImage(name = "Jessica Randall", role = " Android Dev in Training")
        }

    }

















