package com.example.modul6lesson8_firebase.manager

import com.example.modul6lesson8_firebase.model.Post

interface DatabaseHandler {
    fun onSuccess(post: Post? = null, posts : ArrayList<Post> = ArrayList())
    fun onError()
}