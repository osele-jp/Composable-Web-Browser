package com.example.composable_web_browser

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.example.composable_web_browser.ui.theme.ComposableWebBrowserTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposableWebBrowserTheme {
                Scaffold( modifier = Modifier.fillMaxSize() ) { innerPadding ->
                    Greeting(

                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting( modifier: Modifier = Modifier) {
    var url by remember { mutableStateOf("") }
    var webView by remember { mutableStateOf<WebView?>(null)}


    Column(modifier = modifier.fillMaxHeight()){
        Text(
            text = "Enter Web Address",
            modifier = modifier
        )

        Row() {
            TextField(
                value = url,
                onValueChange = {url = it.toString()},
                singleLine = true
            )

            Button(onClick = {
                if (url.isNotBlank()){
                    webView?.loadUrl(parseUrl(url))
                }
            }
            ) {Text("GO") }

        }


        AndroidView(modifier = modifier, factory = {context ->
            WebView(context).apply {
                setBackgroundColor(111)
                settings.javaScriptEnabled = true
                webViewClient = WebViewClient()
                webView = this
            }
        })
    }


}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposableWebBrowserTheme {
        Greeting()
    }
}

fun parseUrl(url: String) : String {
    if(url.startsWith("https"))
        return url
    if (!url.startsWith("https"))
        return "https://${url}"

    return url
}