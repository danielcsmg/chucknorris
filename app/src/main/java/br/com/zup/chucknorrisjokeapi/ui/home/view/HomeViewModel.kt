package br.com.zup.chucknorrisjokeapi.ui.home.view

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import br.com.zup.chucknorrisjokeapi.ui.home.view.MyFirebaseCloudService.Companion.CURRENT_TOKEN
import br.com.zup.chucknorrisjokeapi.ui.home.view.MyFirebaseCloudService.Companion.NEW_NOTIFICATION
import br.com.zup.chucknorrisjokeapi.ui.home.view.MyFirebaseCloudService.Companion.NOTIFICATION_BODY_KEY
import br.com.zup.chucknorrisjokeapi.ui.home.view.MyFirebaseCloudService.Companion.NOTIFICATION_TITLE_KEY
import br.com.zup.chucknorrisjokeapi.ui.home.view.MyFirebaseCloudService.Companion.TOKEN_KEY

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private var _currentToken = MutableLiveData<String>()
    val currentToken: LiveData<String> = _currentToken

    private var _lastNotification = MutableLiveData<Notification>()
    val lastNotification: LiveData<Notification> = _lastNotification

    private var context = application

    private var tokenReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            try {
                _currentToken.value = intent?.getStringExtra(TOKEN_KEY).toString()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private var notificationReciever = object : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            try {
                val title = intent?.getStringExtra(NOTIFICATION_TITLE_KEY).toString()
                val body = intent?.getStringExtra(NOTIFICATION_BODY_KEY).toString()
                val notification = Notification(
                    title = title,
                    body = body
                )
                _lastNotification.value = notification
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

    init {
        LocalBroadcastManager.getInstance(context)
            .registerReceiver((tokenReceiver), IntentFilter(CURRENT_TOKEN))

        LocalBroadcastManager.getInstance(context)
            .registerReceiver((notificationReciever), IntentFilter(NEW_NOTIFICATION))
    }
}