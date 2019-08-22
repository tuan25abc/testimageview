package com.example.testimageview

import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import java.io.File


class MainActivity : AppCompatActivity() {

    companion object {
        const val REQUEST_CODE = 1
    }

    private lateinit var dl: DrawerLayout
    private lateinit var t: ActionBarDrawerToggle
    private lateinit var nv: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dl = findViewById(R.id.drawer_layout)
        t = ActionBarDrawerToggle(this, dl, R.string.open, R.string.close)

        dl.addDrawerListener(t)
        t.syncState()

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        nv = findViewById(R.id.navigation)
        val drawerSwitch =
            nv.menu.findItem(R.id.mycart).actionView as SwitchCompat
        drawerSwitch.setOnCheckedChangeListener { _, checked ->
            Log.e("tuan", "setOnCheckedChangeListener=$checked")
        }
        nv.setNavigationItemSelectedListener(object :
            NavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when (item.itemId) {
                    R.id.account -> Toast.makeText(
                        this@MainActivity,
                        "My Account",
                        Toast.LENGTH_SHORT
                    ).show()
                    R.id.settings -> Toast.makeText(
                        this@MainActivity,
                        "Settings",
                        Toast.LENGTH_SHORT
                    ).show()
                    R.id.mycart -> {
                        drawerSwitch.toggle()
                        Toast.makeText(
                            this@MainActivity,
                            "My Cart",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    else -> return true
                }
                return true

            }
        })


//        button.setOnClickListener {
//            //            startActivityForResult(Intent(Intent.ACTION_OPEN_DOCUMENT_TREE).apply {
////                flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or
////                        Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION
////            }, REQUEST_CODE)
//
//
//            val cv = ContentValues()
//            cv.put(MediaStore.MediaColumns.TITLE, "tuan image")
//            cv.put(MediaStore.MediaColumns.DISPLAY_NAME, "tuan image")
//            cv.put(MediaStore.MediaColumns.MIME_TYPE, "image/*")
//
//            val imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, cv)
//
//            if (imageUri != null) {
//                val os = contentResolver.openOutputStream(imageUri)
//                getDrawable(R.drawable.ic_launcher_background)?.toBitmap()
//                    ?.compress(Bitmap.CompressFormat.JPEG, 100, os)
//                os?.close()
//            }
//        }
//
//        button2.setOnClickListener {
//            val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
//                type = "application/vnd.ms-excel"
//                putExtra(Intent.EXTRA_TITLE, "xyz.csv")
//            }
//
//            startActivityForResult(intent, REQUEST_CODE)
//        }
//
//        button3.setOnClickListener {
//            queryBySchema("image.jpg")?.let { uri ->
//                contentResolver.openFileDescriptor(uri, "r", null)?.let {
//                    val options = BitmapFactory.Options()
//                    options.inPreferredConfig = Bitmap.Config.ARGB_8888
//                    val bitmap = BitmapFactory.decodeFileDescriptor(it.fileDescriptor)
//                    image_view.setImageBitmap(bitmap)
//                }
//            }
//        }
//        clear.setOnClickListener {
//            image_view.setImageBitmap(null)
//        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (t.onOptionsItemSelected(item)) true else super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            data?.data?.also { uri ->
                contentResolver.takePersistableUriPermission(
                    uri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )

                Log.i("tuan", "Uri: $uri")
//                val file = File(
//                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
//                    "image.jpg"//""IMG_20190729_171912.jpg"
//                )
//                Log.e("tuan", "path: ${file.absolutePath}")
//                Log.e("tuan", "file.exists: ${file.exists()}")
//                Log.e("tuan", "file.canRead: ${file.canRead()}")
//                Log.e("tuan", "file.canWrite: ${file.canWrite()}")
            }
        }
    }

    private fun handleUri(uri: Uri) {
        val cursor = contentResolver.query(uri, null, null, null, null, null)

        cursor?.use {
            if (it.moveToFirst()) {
                val path: String = it.getString(it.getColumnIndex("_data"))
                Log.e("tuan", "path: $path")
                val file = File(path)
                Log.e("tuan", "file.canRead: ${file.canRead()}")
                Log.e("tuan", "file.canWrite: ${file.canWrite()}")
            }
        }
    }

    private fun queryBySchema(title: String): Uri? {
        var result: Uri? = null
        var c: Cursor? = null
        try {
            c = contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                arrayOf(MediaStore.MediaColumns._ID),
                MediaStore.MediaColumns.DISPLAY_NAME + "=?",
                arrayOf(title), null
            )
            if (c != null) {
                if (c.moveToFirst())
                    result = Uri.parse(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI.toString() + "/" +
                                c.getLong(c.getColumnIndex(MediaStore.MediaColumns._ID))
                    )
            }
        } catch (e: Exception) {
        } finally {
            c?.close()
        }
        return result
    }
}
