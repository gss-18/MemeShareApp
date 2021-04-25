package com.example.memeshare

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*
import com.android.volley.toolbox.JsonObjectRequest
import com.bumptech.glide.request.RequestListener

class MainActivity : AppCompatActivity() {
    var currentImageUrl: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    private fun loadMeme(){

        val queue = Volley.newRequestQueue(this)
        currentImageUrl = "https://meme-api.herokuapp.com/gimme"


        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, currentImageUrl, null,
            { response ->
                val url = response.getString("url")
                Glide.with(this).load(url).into(memeImage)
            },
            { error ->
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show()
            }
        )
        queue.add(jsonObjectRequest)
    }

    fun nextMeme(view: View) {
        loadMeme()
    }

    fun shareMeme(view: View) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type="text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"Hey,Checkout this cool meme $currentImageUrl")
        val chooser=Intent.createChooser(intent,"Share this meme using")
        startActivity(chooser)
    }
}