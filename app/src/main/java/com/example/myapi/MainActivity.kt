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
    var heroName = "Carlo"
    var fullName = ""
    var publisher = ""
    var tkn = "6033535076749096"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.nextButton)
        val imageView = findViewById<ImageView>(R.id.imageView)
        val nameText = findViewById<TextView>(R.id.heroName)
        val fullNameText = findViewById<TextView>(R.id.fullName)
        var publisher = ""
        button.setOnClickListener{
            getHeroURL()

            Glide.with(this)
                .load(heroImageURL)
                .fitCenter()
                .into(imageView)
            nameText.text = heroName
            fullNameText.text = fullName
        }

        getNextImage(button,imageView)
        
    }
    private fun getHeroURL() {

        val client = AsyncHttpClient()
        val random = Random.nextInt(1,731)
        val heroJSON ="https://superheroapi.com/api/"+tkn+"/"+random

        client[heroJSON, object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JsonHttpResponseHandler.JSON) {
                Log.d("Hero", "response successful$json")


                heroImageURL = json.jsonObject.getJSONObject("image").getString("url")
                heroName = json.jsonObject.getString("name")
                fullName = json.jsonObject.getJSONObject("biography").getString("full-name")
                publisher = json.jsonObject.getJSONObject("biography").getString("publisher")
                Log.d("petImageURL", "hero image URL set")

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


//var petImageURL = ""
//
//override fun onCreate(savedInstanceState: Bundle?) {
//    super.onCreate(savedInstanceState)
//    setContentView(R.layout.activity_main)
//
//    val imageView = findViewById<ImageView>(R.id.imageView)
//
//    val button = findViewById<Button>(R.id.petButton)
//    button.setOnClickListener{
//        getDogImageURL()
//
//        Glide.with(this)
//            .load(petImageURL)
//            .fitCenter()
//            .into(imageView)
//    }
//    getNextImage(button,imageView)
//}
//private fun getDogImageURL(){
//    val client = AsyncHttpClient()
//    client["https://dog.ceo/api/breeds/image/random", object : JsonHttpResponseHandler() {
//        override fun onSuccess(statusCode: Int, headers: Headers, json: JsonHttpResponseHandler.JSON) {
//            Log.d("Dog", "response successful$json")
//            petImageURL = json.jsonObject.getString("message")
//            Log.d("petImageURL", "pet image URL set")
//
//        }
//
//        override fun onFailure(
//            statusCode: Int,
//            headers: Headers?,
//            errorResponse: String,
//            throwable: Throwable?
//        ) {
//            Log.d("Dog Error", errorResponse)
//        }
//    }]
//
//}
//private fun getNextImage(button: Button, imageView: ImageView) {
//
//}