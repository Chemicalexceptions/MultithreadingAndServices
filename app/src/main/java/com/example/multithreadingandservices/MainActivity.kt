package com.example.multithreadingandservices

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.multithreadingandservices.services.MyDownloadJob
import com.example.multithreadingandservices.services.MyForegroundStartedService

class MainActivity : AppCompatActivity() {

    lateinit var btnRunCode: Button
    val MY_TAG = "Job_Scheduler_api"
    val jobId = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnRunCode = findViewById(R.id.btn_run_code)
        btnRunCode.setOnClickListener({

            runCode()

        })


    }

    fun scheduleService(view: View) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            val jobScheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
            val componentName = ComponentName(this, MyDownloadJob::class.java)

            val jobInfo: JobInfo.Builder = JobInfo.Builder(jobId, componentName)
            jobInfo.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                jobInfo.setMinimumLatency(5000)
            } else {
                jobInfo.setPeriodic(5000)
            }

            jobInfo.setPersisted(true)

            val result = jobScheduler.schedule(jobInfo.build())

            if (result == JobScheduler.RESULT_SUCCESS) {

                Log.d(MY_TAG, "scheduleService : Job Scheduled")


            } else {

                Log.d(MY_TAG, "scheduleService : Job not Scheduled")
            }


        }

    }

    fun cancelService(view: View) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            val jobScheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
            jobScheduler.cancel(jobId)
            Log.d(MY_TAG, "scheduleService : Job Cancelled")
        }


    }

    fun runCode() {

        val intent = Intent(this@MainActivity, MyForegroundStartedService::class.java)
        startService(intent)

    }

}
