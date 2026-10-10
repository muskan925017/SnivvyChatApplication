package com.example.snivvy.present.registrationscreen

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("fake_auth", Context.MODE_PRIVATE)

    fun saveLogin(phone: String) {
        prefs.edit().putString("user_phone", phone).apply()
    }

    fun isLoggedIn(): Boolean {
        return prefs.getString("user_phone", null) != null
    }

    fun getUserPhone(): String? = prefs.getString("user_phone", null)

    fun logout() {
        prefs.edit().clear().apply()
    }
}
