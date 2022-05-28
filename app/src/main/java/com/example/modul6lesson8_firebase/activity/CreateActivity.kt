package com.example.modul6lesson8_firebase.activity

import android.media.effect.EffectFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import com.example.modul6lesson8_firebase.R
import com.example.modul6lesson8_firebase.manager.DatabaseHandler
import com.example.modul6lesson8_firebase.manager.DatabaseManager
import com.example.modul6lesson8_firebase.manager.StorageHandler
import com.example.modul6lesson8_firebase.manager.StorageManager
import com.example.modul6lesson8_firebase.model.Post
import java.lang.Exception

class CreateActivity : BaseActivity() {

    lateinit var iv_photo : ImageView
    var pickedPhoto : Uri? = null
    var allPhotos = ArrayList<Uri>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)

        initViews()
    }

    private fun initViews() {
        val iv_close = findViewById<ImageView>(R.id.iv_close)
        val et_title = findViewById<EditText>(R.id.et_title)
        val et_body = findViewById<EditText>(R.id.et_body)
        val b_create = findViewById<Button>(R.id.b_create)
        val iv_camera = findViewById<ImageView>(R.id.iv_camera)

        iv_camera.setOnClickListener {
            pickUserPhoto()
        }

        iv_close.setOnClickListener {
            finish()
        }

        b_create.setOnClickListener {
            val title = et_title.text.toString().trim()
            val body = et_body.text.toString().trim()
            val post = Post(title, body)
            storeDatabase(post)
        }
    }

    private fun pickUserPhoto() {

    }

    fun storePost(post: Post){
        if(pickedPhoto != null){
            storeStorage(post)
        }else{
            storeDatabase(post)
        }
    }

    fun storeStorage(post: Post){
        showLoading(this)
        StorageManager.uploadPhoto(pickedPhoto!!, object : StorageHandler{
            override fun onSuccess(imgUrl: String) {
                post.img = imgUrl
                storeDatabase(post)
            }

            override fun onError(exception: Exception?) {
                storeDatabase(post)
            }

        })
    }

    fun storeDatabase(post: Post){
        DatabaseManager.storePost(post, object : DatabaseHandler{
            override fun onSuccess(post: Post?, posts: ArrayList<Post>) {
                Log.d("@@@", "post is saved")
                dismissLoading()
                finishIntent()
            }

            override fun onError() {
                dismissLoading()
                Log.d("@@@", "post is not saved")
            }
        })
    }

    private fun finishIntent() {
        val returnIntent = intent
        setResult(RESULT_OK, returnIntent)
        finish()

    }
}