package com.kraskibrancini.sweatify.ui.features.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.kraskibrancini.sweatify.core.PreferencesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
) : ViewModel() {


    private val _userAuthResult = MutableStateFlow<AuthResult?>(null)
    val userAuthResult: StateFlow<AuthResult?> = _userAuthResult

    fun googleSignIn(credentials: AuthCredential) {
        viewModelScope.launch {
            val result = FirebaseAuth.getInstance().signInWithCredential(credentials).await()
            _userAuthResult.value = result
        }
    }
}