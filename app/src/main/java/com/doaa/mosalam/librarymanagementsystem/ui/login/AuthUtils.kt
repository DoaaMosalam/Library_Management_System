package com.doaa.mosalam.librarymanagementsystem.ui.login

import android.app.Activity
import android.content.Intent
import com.doaa.mosalam.librarymanagementsystem.BuildConfig
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions


fun getGoogleRequestIntent(context: Activity): Intent {
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(BuildConfig.CLIENT_SERVER_ID)
//        .requestIdToken(default_web_client_id.toString())
        .requestEmail()
        .requestProfile()
        .requestServerAuthCode(BuildConfig.CLIENT_SERVER_ID).build()

    val googleSignInClient = GoogleSignIn.getClient(context, gso)
    googleSignInClient.signOut()
    return googleSignInClient.signInIntent
}