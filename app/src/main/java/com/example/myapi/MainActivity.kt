package com.example.myapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    var heroImageURL = "https://www.superherodb.com/pictures2/portraits/10/100/10060.jpg"
    var heroName = "Hero Name"
    var fullName = "First Apperance"
    var publisher = "Publisher"
    var tkn = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.nextButton)
        val imageView = findViewById<ImageView>(R.id.imageView)
        val nameText = findViewById<TextView>(R.id.heroName)
        val fullNameText = findViewById<TextView>(R.id.apperance)
        val publisherText = findViewById<TextView>(R.id.publisher)

        nameText.text = heroName
        fullNameText.text = fullName
        publisherText.text = publisher

        button.setOnClickListener{
            getHeroURL()

            nameText.text = heroName
            fullNameText.text = fullName
            publisherText.text = publisher

            Glide.with(this)
                .load(heroImageURL)
                .fitCenter()
                .into(imageView)
                Log.d("heroImageURL", "image URL set")

        }

        getNextImage(button,imageView)
        
    }
    private fun getHeroURL() {

        val client = AsyncHttpClient()
        val random = Random.nextInt(1,731)
        val heroJSON = "https://superheroapi.com/api/$tkn/$random"

        client[heroJSON, object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JsonHttpResponseHandler.JSON) {
                Log.d("Hero", "response successful$json")

                heroImageURL = json.jsonObject.getJSONObject("image").getString("url")
                heroName = json.jsonObject.getString("name")
                fullName = json.jsonObject.getJSONObject("biography").getString("first-appearance")
                publisher = json.jsonObject.getJSONObject("biography").getString("publisher")
                Log.d("heroImageURL", "hero image URL set")

            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                errorResponse: String,
                throwable: Throwable?
            ) {
                Log.d("Hero Error", errorResponse)
            }
        }]

    }
    private fun getNextImage(button: Button, imageView: ImageView) {

    }

}

