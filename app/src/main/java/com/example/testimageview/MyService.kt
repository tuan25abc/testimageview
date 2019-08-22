package com.example.testimageview

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.signature.ObjectKey
import java.util.*

/**
 * Created by TUANNP3 on 01,July,2019.
 */
class MyService : Service() {

    override fun onCreate() {
        super.onCreate()
        Log.e("t", "MyService onCreate")
        Toast.makeText(this, "MyService onCreate", Toast.LENGTH_SHORT).show()
        Thread {
            val bitmap = Glide.with(this)
                .asBitmap()
                .load("https://zmp3-photo.zadn.vn/banner/7/5/75eb55f670c4919371fc99edc0fbf8b7_1518002587.png")
                .apply(RequestOptions().signature(ObjectKey(UUID.randomUUID())))
                .submit()
                .get()
            stopSelf()
        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("t", "MyService onDestroy")
        Toast.makeText(this, "MyService onDestroy", Toast.LENGTH_SHORT).show()
    }

    override fun onBind(p0: Intent?): IBinder {
        return Binder()
    }
}