package com.example.modul6lesson8_firebase.manager

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class AuthManager {
    companion object{
        val firebaseAuth : FirebaseAuth = FirebaseAuth.getInstance()
        val firebaseUser : FirebaseUser? = firebaseAuth.currentUser

        fun isSignedIn() : Boolean{
            return firebaseUser != null
        }

        fun signIn(email : String, password : String, handler : AuthHandler){
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener{ task ->
                    if(task.isSuccessful){

                        val user = firebaseAuth.currentUser
                        Log.d("@@@", "signInWithEmail:success ${user.toString()}")

                        handler.onSuccess()
                    }else{
                        handler.onError(task.exception)
                    }
            }
        }

        fun signUp(email: String, password: String, handler: AuthHandler){
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener{task ->
                    if(task.isSuccessful){

                        val user = firebaseAuth.currentUser
                        Log.d("@@@", "signUpWithEmail:success ${user.toString()}")

                        handler.onSuccess()
                    }else{
                        handler.onError(task.exception)
                    }
            }
        }

        fun signOut(){
            firebaseAuth.signOut()
        }
    }
}