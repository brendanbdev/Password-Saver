package com.example.passwordsaver

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/*
This gives Hilt access to the application, allowing Hilt to use the application
context to provide dependencies.
*/
@HiltAndroidApp
class PasswordSaverApp: Application()
