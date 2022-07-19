package br.com.zup.chucknorrisjokeapi.ui.home.view

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseCloudService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        val broadcast = LocalBroadcastManager.getInstance(baseContext)
        val intent = Intent(CURRENT_TOKEN).apply {
            putExtra(TOKEN_KEY, token)
        }
        broadcast.sendBroadcast(intent)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        message.notification?.let {
            val broadcaster = LocalBroadcastManager.getInstance(baseContext)
            val intent = Intent(NEW_NOTIFICATION).apply {
                putExtra(NOTIFICATION_TITLE_KEY, it.title)
                putExtra(NOTIFICATION_BODY_KEY, it.body)
            }
            broadcaster.sendBroadcast(intent)
        }
    }

    companion object {
        const val NEW_NOTIFICATION = "NEW_NOTIFICATION"
        const val NOTIFICATION_TITLE_KEY = "NOTIFICATION_TITLE_KEY"
        const val NOTIFICATION_BODY_KEY = "NOTIFICATION_BODY_KEY"
        const val CURRENT_TOKEN = "CURRENT_TOKEN"
        const val TOKEN_KEY = "TOKEN_KEY"
    }
}