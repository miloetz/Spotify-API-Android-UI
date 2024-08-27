package com.example.csc567_application

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.csc567_application.ui.theme.CSC567_ApplicationTheme
import okhttp3.Call
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.Callback
import org.json.JSONObject
import java.io.IOException

class MainActivity : ComponentActivity() {

    private lateinit var editTextUsername: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonLogin: Button

    private val CLIENT_ID = "78e6e9233de048e6af971e8316ad0818"
    private val CLIENT_SECRET = "f6aded9455c646e5a089861730d05e61"
    private val REDIRECT_URI = "spotifyapp1://auth"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextUsername = findViewById(R.id.editTextUsername)
        editTextPassword = findViewById(R.id.editTextPassword)
        buttonLogin = findViewById(R.id.buttonLogin)

        // Set click listener for login button
        buttonLogin.setOnClickListener {
            // Retrieve username and password entered by the user
            val username = editTextUsername.text.toString()
            val password = editTextPassword.text.toString()

            // Construct authorization request URL
            val authUrl = "https://accounts.spotify.com/authorize" +
                    "?client_id=$CLIENT_ID" +
                    "&response_type=code" +
                    "&redirect_uri=$REDIRECT_URI" +
                    "&scope=user-read-private%20user-read-email"

            // Open authorization URL in a web browser

            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(authUrl))
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        val uri: Uri? = intent.data
        if (uri != null && uri.toString().startsWith(REDIRECT_URI)) {
            setContent {
                AppScreen()
            }
        }
    }
    private fun exchangeCodeForToken(code: String) {
        val client = OkHttpClient()
        val formBody = FormBody.Builder()
            .add("grant_type", "authorization_code")
            .add("code", code)
            .add("redirect_uri", REDIRECT_URI)
            .add("client_id", CLIENT_ID)
            .add("client_secret", CLIENT_SECRET)
            .build()
        val request = Request.Builder()
            .url("https://accounts.spotify.com/api/token")
            .post(formBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                val jsonData = response.body?.string()
                val jsonObject = JSONObject(jsonData)
                val accessToken = jsonObject.getString("access_token")
                // Store the access token securely and use it to access Spotify's resources
            }
        })
    }

    private fun authenticateWithSpotify(username: String, password: String) {
        // TODO: Implement authentication with Spotify API
        // Make requests to Spotify's authentication and token endpoints
        // Handle responses and exchange authorization code for access token
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CSC567_ApplicationTheme {
        Greeting("Android")
    }
}