package com.example.modul6lesson8_firebase.manager

import java.lang.Exception

interface StorageHandler {
    fun onSuccess(imgUrl : String)
    fun onError(exception: Exception?)
}