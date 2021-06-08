package com.example.android.memeshare

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.isVisible
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var  memeUrl: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        progressBar.visibility = View.VISIBLE
        loadMeme()


        setSupportActionBar(my_toolbar)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#FFFFFF")))
        supportActionBar?.title = "Share Memes"
    }
     private fun loadMeme(){
         // Instantiate the RequestQueue.

         val url = "https://meme-api.herokuapp.com/gimme"

         val jsonObjectRequest = JsonObjectRequest(
             Request.Method.GET, url,null,
             { response ->
                 memeUrl = response.getString("url")
                 Glide.with(this).load(memeUrl).listener(object: RequestListener<Drawable>{

                     override fun onResourceReady(
                         resource: Drawable?,
                         model: Any?,
                         target: Target<Drawable>?,
                         dataSource: DataSource?,
                         isFirstResource: Boolean
                     ): Boolean {
                         progressBar.visibility = View.GONE
                         return false
                     }

                     override fun onLoadFailed(
                         e: GlideException?,
                         model: Any?,
                         target: Target<Drawable>?,
                         isFirstResource: Boolean
                     ): Boolean {
                         progressBar.visibility = View.GONE
                         return false
                     }
                 }).into(memeImage)

             },
             { Toast.makeText(this,"Something went wrong", Toast.LENGTH_SHORT).show() })

         // Add the request to the RequestQueue.
         MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
     }

    fun shareView(view: View) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.putExtra(Intent.EXTRA_TEXT,"Hey, checkout this funny meme, I found out on reddit\n$memeUrl")
        intent.type = "text/plain"
        val chooser = Intent.createChooser(intent, "Sharing your favourite meme")
        startActivity(chooser)
    }


    fun nextView(view: View) {
        progressBar.visibility = View.VISIBLE
        loadMeme()
    }


}

