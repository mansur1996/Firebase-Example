package com.example.modul6lesson8_firebase.manager

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask

class StorageManager {
    companion object{
        val stotrage = FirebaseStorage.getInstance()
        var storageRef = stotrage.getReference()
        var photoRef = storageRef.child("photos")

        fun uploadPhoto(uri : Uri, handler: StorageHandler){
            val filename = System.currentTimeMillis().toString() + ".png"
            val uploadTask : UploadTask = photoRef.child(filename).putFile(uri)
            uploadTask.addOnSuccessListener { it ->
                val result = it.metadata!!.reference!!.downloadUrl
                result.addOnSuccessListener {
                    var imgUrl = it.toString()
                    handler.onSuccess(imgUrl)
                }.addOnFailureListener{ e ->
                    handler.onError(e)
                }.addOnFailureListener{ e ->
                    handler.onError(e)
                }
            }
        }
    }
}