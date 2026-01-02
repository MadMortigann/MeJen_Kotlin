package com.example.mejen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.example.mejen.ui.theme.MeJenTheme
import com.example.mejen.workrequests.FfmpegWorkRequest


class MainActivity : ComponentActivity() {

    private fun testFfmpeg() {
        val inputData = Data.Builder()
            .putString("Suck it Trebek", "https://example.com/image.jpg")
            .build()

        val uploadWorkRequest = OneTimeWorkRequest.Builder(FfmpegWorkRequest::class)
            .setInputData(inputData)
            .build()

        WorkManager.getInstance(this).enqueue(uploadWorkRequest)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MeJenTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    Greeting("Android")
                }
            }
        }
        testFfmpeg()
    }
}

@Composable
fun Greeting(
    name: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = "Hello $name!",
        modifier = modifier,
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MeJenTheme {
        Greeting("Android")
    }
}
