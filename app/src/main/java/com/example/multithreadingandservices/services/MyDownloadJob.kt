package com.example.multithreadingandservices.services

import android.annotation.TargetApi
import android.app.job.JobParameters
import android.app.job.JobService
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi


@TargetApi(Build.VERSION_CODES.LOLLIPOP)
class MyDownloadJob : JobService() {


    val MY_TAG = "job_tag"
    var isJobCanceled = false
    var mSuccess = false

    override fun onStopJob(p0: JobParameters?): Boolean {

        Log.d(MY_TAG, "onStopJob : called")
        isJobCanceled = true
        return false

    }

    override fun onStartJob(p0: JobParameters?): Boolean {

        Log.d(MY_TAG, "onStartJob : called")
        Log.d(MY_TAG, "onStartJob : Thread name : "+ Thread.currentThread().name)

        Thread(Runnable {

            Log.d(MY_TAG, "onStartJob : Starting dowload")
            var i = 0
            while (i <10) {

                if(isJobCanceled)
                    return@Runnable

                Log.d(MY_TAG, "onStartJob : Progress is: ${i + 1}")
                Thread.sleep(1000)
                i++

            }
            Log.d(MY_TAG, "onStartJob : download completed")
            mSuccess = true
            jobFinished(p0,mSuccess)


        }).start()


        /*
         * return false if there is no background thread inside job
         * return true if there is background thread is there inside job
         */

        return true

    }


}
