
# Business Card Application

This is a simple Android app, built with Kotlin and Jetpack Compose, that displays a business card with a person's name, role, contact information, and a logo image.


## Installation

1. Clone the repository:

```

  git clone https://github.com/yourusername/BusinessCardApp.git

```


 2. Open the project in Android Studio.

 3. Run the app on an emulator or a physical device.
 
## Usage

The MainActivity class is the entry point of the app. It sets the content of the activity using the BusinessCardTheme and AnImage composable functions. The AnImage function displays the logo and the business card with the given name and role.

To customize the business card, modify the parameters passed to the AnImage function in MainActivity:

AnImage(name = "Your Name", role = "Your Role")

Replace "Your Name" and "Your Role" with the desired name and role for the business card. If needed, you can also modify the contact information displayed on the card in the icon_column function.


## Preview

You can preview the app design using the DefaultPreview function in the code. It uses the BusinessCardTheme and AnImage composable functions to render a preview of the business card.
```
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BusinessCardTheme() {
        AnImage(name = "Jessica Randall", role = "Android Dev in Training")
    }
}
```

The preview will be visible in Android Studio's Design or Code view.
## Features

The Business Card Application has the following features:

Customizable business card with name, role, and contact information.

Built with Jetpack Compose.

Simple and clean UI.

Preview function.

## Acknowledgements

 The app was built following the Jetpack Compose tutorials, which provide an excellent introduction to the new toolkit.


## License

MIT License

Copyright (c) [2023] [Jessica Randall]

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.


## Authors

- [@Starchild13](https://github.com/Starchild13)

