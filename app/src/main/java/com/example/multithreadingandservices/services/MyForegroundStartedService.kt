package com.example.multithreadingandservices.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.multithreadingandservices.R


class MyForegroundStartedService : Service() {

    val MY_TAG = "foreground_tag"
    val NOTIFICATION_CHANNEL_ID = "com.example.ankit"
    val channelName = "My Background Service"


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        Toast.makeText(this,"Service Strated",Toast.LENGTH_LONG).show()

        //showNotification()

        val thread = Thread(Runnable {

            Log.d(MY_TAG, "run : Starting dowload")

            var i = 0

            while (i <= 10) {

                Log.d(MY_TAG, "run : Progress is: ${i + 1}")
                Thread.sleep(1000)
                i++

            }

            Log.d(MY_TAG, "run : download completed")

            //stopForeground(true)
           // stopSelf()
           // showSuccessNotification()
           // showErrorNotification()
        })

        thread.start()

        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }


    fun showNotification(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            createNotificationChannel(NOTIFICATION_CHANNEL_ID, channelName)
        }

        var builder = NotificationCompat.Builder(this,NOTIFICATION_CHANNEL_ID)
        builder.setSmallIcon(R.mipmap.ic_launcher)
            .setContentText("Leave workflow is intiating")
            .setContentTitle("Intiating Worklow")
            .setProgress(100,0,true)

        val notification = builder.build()
        startForeground(123,notification)

    }

    fun showSuccessNotification(){

        var  builder = NotificationCompat.Builder(this,NOTIFICATION_CHANNEL_ID)
        builder.setSmallIcon(R.mipmap.ic_launcher)
            .setContentText("Leave workflow Intited Successfully")
            .setContentTitle("Intiating Worklow")

        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(0,builder.build())


    }


    fun showErrorNotification(){

        var  builder = NotificationCompat.Builder(this,NOTIFICATION_CHANNEL_ID)
        builder.setSmallIcon(R.mipmap.ic_launcher)
            .setContentText("Error in Leave workflow Intited")
            .setContentTitle("Error Intiating Worklow")

        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(0,builder.build())


    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(
        channelId: String,
        channelName: String
    ): String? {
        val chan = NotificationChannel(
            channelId,
            channelName, NotificationManager.IMPORTANCE_NONE
        )
        chan.lightColor = Color.BLUE
        chan.lockscreenVisibility = Notification.VISIBILITY_PRIVATE

        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(chan)
        return channelId
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(MY_TAG, "run : onDestroyCalled")
    }
}
