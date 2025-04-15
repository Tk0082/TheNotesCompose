package com.betrend.cp.thenotes.data.remote.auth

import com.betrend.cp.thenotes.data.remote.entities.UserData

// Estados da aplicação
sealed class AuthState {
    data object Login : AuthState()
    data class LoggedIn(val user: UserData) : AuthState()
}