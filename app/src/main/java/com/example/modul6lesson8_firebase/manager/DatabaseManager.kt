package com.example.modul6lesson8_firebase.manager

import com.example.modul6lesson8_firebase.model.Post
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DatabaseManager {
    companion object{
        private var database = FirebaseDatabase.getInstance().reference
        private var reference = database.child("posts")

        fun storePost(post : Post, handler : DatabaseHandler){
            val key = reference.push().key ?: return
            post.id = key
            reference.child(key).setValue(post)
                .addOnSuccessListener {
                    handler.onSuccess()
                }.addOnFailureListener{
                    handler.onError()
                }
        }

        fun apiLoadPosts(handler: DatabaseHandler){
            reference.addValueEventListener(object : ValueEventListener{
                var posts : ArrayList<Post> = ArrayList()
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if(dataSnapshot.exists()){
                        for (snapshot in dataSnapshot.children){
                            val post : Post? = snapshot.getValue(Post::class.java)
                            post.let {
                                posts.add(post!!)
                            }
                        }
                        handler.onSuccess(posts = posts)
                    }else{
                        handler.onSuccess(posts = ArrayList())
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    handler.onError()
                }
            })
        }


    }
}