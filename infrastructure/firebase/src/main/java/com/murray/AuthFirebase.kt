package com.murray

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.murray.entities.accounts.Account
import com.murray.entities.accounts.Email
import com.murray.network.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.lang.Exception

class AuthFirebase {

    private var authFirebase = FirebaseAuth.getInstance()

    suspend fun login(email: String, password: String): Resource {
        return withContext(Dispatchers.IO) {
            delay(2000)

            try {
                val authResult: AuthResult = authFirebase.signInWithEmailAndPassword(email, password).await()
                val firebaseUser = authResult.user

                val userId = firebaseUser?.uid ?: throw Exception("UID es null")
                val userEmail = firebaseUser.email ?: throw Exception("Email es null")
                val displayName = firebaseUser.displayName

                val account = Account.create(id = userId.toInt(), email = Email(userEmail), password = null, displayName = displayName)

                Resource.Success(account)
            } catch (e: Exception) {
                Resource.Error(e)
            }
        }
    }

}