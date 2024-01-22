package com.murray.firebase

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.murray.data.accounts.Account
import com.murray.data.accounts.AccountId
import com.murray.data.accounts.AccountState
import com.murray.data.accounts.Email
import com.murray.networkstate.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.lang.Exception

class AuthFirebase {
    private var authFirebase  = FirebaseAuth.getInstance()
    suspend fun login(email : String, password: String) : Resource {
        var resource:Resource
        //Este código se ejecuta en un hilo especifico para acciones de E/S
        withContext(Dispatchers.IO) {
            //delay(3000) //Simulamos que esperamos respuesta del servidor
            //Se ejecutará el código necesario para realizar la consulta a Firebase que podría tardar más de 5 segundos.
            // Si eso pasa se obtiene el error ANR (Android Not Responding) porque podría bloquear la vista al usuario
            try {
                val authResult: AuthResult = authFirebase.signInWithEmailAndPassword(email, password).await()
                val user = authResult.user
                val account:Account = Account.create(id = AccountId(user.hashCode()),email = Email(email), password = password, displayName = user!!.displayName, state = AccountState.VERIFIED, 1)
                resource = Resource.Success(account)
            }catch (e:Exception){
                resource = Resource.Error(e)
            }

        }
        return resource
        //Resource.Error(Exception("La contraseña es incorrecta"))
    }
}