package com.example.bicycleapp.viewodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bicycleapp.repository.AuthenticationRepository
import com.google.firebase.auth.FirebaseUser


class LoginViewModel(application: Application) : ViewModel() {

    private var repository: AuthenticationRepository = AuthenticationRepository(application)
    private var userData: MutableLiveData<FirebaseUser?>?  = repository.getFirebaseUserMutableLiveData()
    //private var loggedStatus: MutableLiveData<Boolean?>? = repository.getUserLoggedMutableLiveData()

    fun getUserData(): MutableLiveData<FirebaseUser?>? {
        return userData
    }

    fun register(email: String?, pass: String?) {
        repository.register(email, pass)
    }

    fun signIn(email: String?, pass: String?) {
        repository.login(email, pass)
    }

    /*
    fun signOut() {
        repository.signOut()
    }

    fun getLoggedStatus(): MutableLiveData<Boolean?>? {
        return loggedStatus
    }
    */
}

