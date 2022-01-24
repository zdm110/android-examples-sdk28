package com.example.ktcomposelayoutsexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ktcomposelayoutsexample.ui.theme.KtComposeLayoutsExampleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KtComposeLayoutsExampleTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Composable
fun PhotographerCard(modifier: Modifier = Modifier) {
    // use a default empty modifier
    // chaining multiple modifiers by using factory-extension functions
    // the order of the modifier matters
    Row(modifier
        .padding(8.dp)
        .clip(RoundedCornerShape(4.dp))
        .background(MaterialTheme.colors.surface)
        .clickable{ /**/}
        .padding(16.dp) // order matters

    ) {
        Surface (
            modifier = Modifier.size(50.dp),
            shape = CircleShape,
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f)
        ) {

        }
        Column (
            modifier = Modifier
                .padding(start=8.dp)
                .align(Alignment.CenterVertically)
                ){
            Text("Alfred Sisley", fontWeight = FontWeight.Bold)
            // LocalContentAlpha is defining opacity level of its children
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text("3 minutes ago", style = MaterialTheme.typography.body2)
            }
        }

    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    KtComposeLayoutsExampleTheme {
        Greeting("Android")
    }
}

@Preview(showBackground = true)
@Composable
fun PhotographerCardPreview() {
    KtComposeLayoutsExampleTheme {
        PhotographerCard()
    }
}