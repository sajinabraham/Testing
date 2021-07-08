package `in`.sajin.tab.view

import `in`.sajin.tab.BuildConfig
import `in`.sajin.tab.R
import `in`.sajin.tab.room.MyViewModel
import `in`.sajin.tab.room.Person
import android.Manifest
import android.annotation.TargetApi
import android.app.Activity
import android.content.ContentUris
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import kotlinx.coroutines.launch
import java.io.File
import java.util.*


class CameraActivity : AppCompatActivity() {
    //Our variables
    private var mImageView: ImageView? = null
    private var mUri: Uri? = null

    //Our widgets
    private var btnCapture: Button? = null
    private var etFirstname: EditText? = null
    private var etSurname: EditText? = null
    private var etYearOfBirth: EditText? = null
    private var btnSave: Button? = null

    //Our constants
    private val OPERATION_CAPTURE_PHOTO = 1
    private val OPERATION_CHOOSE_PHOTO = 2
    private lateinit var myViewModel: MyViewModel
    var bitmap: Bitmap? = null


    private fun initializeWidgets() {
        btnCapture = findViewById(R.id.btnCapture)
        mImageView = findViewById(R.id.mImageView)
        etFirstname = findViewById(R.id.etFirstname)
        etSurname = findViewById(R.id.etSurname)
        etYearOfBirth = findViewById(R.id.etYearOfBirth)
        btnSave = findViewById(R.id.btnSave)
    }

    private fun show(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun capturePhoto() {
        val capturedImage = File(externalCacheDir, "My_Captured_Photo.jpg")
        if (capturedImage.exists()) {
            capturedImage.delete()
        }
        capturedImage.createNewFile()
        mUri = if (Build.VERSION.SDK_INT >= 24) {
            FileProvider.getUriForFile(
                Objects.requireNonNull(applicationContext),
                BuildConfig.APPLICATION_ID + ".provider", capturedImage
            );
        } else {
            Uri.fromFile(capturedImage)
        }

        val intent = Intent("android.media.action.IMAGE_CAPTURE")
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mUri)
        startActivityForResult(intent, OPERATION_CAPTURE_PHOTO)
    }

    private fun openGallery() {
        val intent = Intent("android.intent.action.GET_CONTENT")
        intent.type = "image/*"
        startActivityForResult(intent, OPERATION_CHOOSE_PHOTO)
    }

    private fun renderImage(imagePath: String?) {
        if (imagePath != null) {
            val bitmap = BitmapFactory.decodeFile(imagePath)
            mImageView?.setImageBitmap(bitmap)
        } else {
            show("ImagePath is null")
        }
    }

    private fun getImagePath(uri: Uri?, selection: String?): String {
        var path: String? = null
        val cursor = contentResolver.query(uri!!, null, selection, null, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA))
            }
            cursor.close()
        }
        return path!!
    }

    @TargetApi(19)
    private fun handleImageOnKitkat(data: Intent?) {
        var imagePath: String? = null
        val uri = data!!.data
        //DocumentsContract defines the contract between a documents provider and the platform.
        if (DocumentsContract.isDocumentUri(this, uri)) {
            val docId = DocumentsContract.getDocumentId(uri)
            if (uri != null) {
                if ("com.android.providers.media.documents" == uri.authority) {
                    val id = docId.split(":")[1]
                    val selsetion = MediaStore.Images.Media._ID + "=" + id
                    imagePath = getImagePath(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        selsetion
                    )
                } else if ("com.android.providers.downloads.documents" == uri.authority) {
                    val contentUri = ContentUris.withAppendedId(
                        Uri.parse(
                            "content://downloads/public_downloads"
                        ), java.lang.Long.valueOf(docId)
                    )
                    imagePath = getImagePath(contentUri, null)
                }
            }
        } else if ("content".equals(uri?.scheme, ignoreCase = true)) {
            imagePath = getImagePath(uri, null)
        } else if ("file".equals(uri?.scheme, ignoreCase = true)) {
            imagePath = uri?.path
        }
        renderImage(imagePath)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantedResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantedResults)
        when (requestCode) {
            1 ->
                if (grantedResults.isNotEmpty() && grantedResults[0] ==
                    PackageManager.PERMISSION_GRANTED
                ) {
                    capturePhoto()
                } else {
                    show("Unfortunately You are Denied Permission to Perform this Operataion.")
                }
        }
    }

    // Getting File bath and bitmap save to room DB
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            OPERATION_CAPTURE_PHOTO ->
                if (resultCode == Activity.RESULT_OK) {
                    bitmap = BitmapFactory.decodeStream(
                        contentResolver.openInputStream(mUri!!)
                    )
                    mImageView!!.setImageBitmap(bitmap)
                    btnSave!!.setOnClickListener {
                        lifecycleScope.launch {
                            val person = Person(
                                etFirstname?.text.toString(),
                                etSurname?.text.toString(),
                                etYearOfBirth?.text.toString(),
                                getBitmap()
                            )
                            myViewModel.insertPerson(person)
                        }

                        if (bitmap != null && etFirstname?.text != null && etSurname?.text != null && etYearOfBirth
                                ?.text != null
                        ) {
                            Toast.makeText(
                                this,
                                "Saved to Room DB Successfully",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else
                            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                        finish()
                    }
//                    CoroutineScope(Dispatchers.IO).launch {
//                        runCatching{
//                            getBitmap(bitmap)
//                        }
//                    }

                }
            OPERATION_CHOOSE_PHOTO ->
                if (resultCode == Activity.RESULT_OK) {
                    if (Build.VERSION.SDK_INT >= 19) {
                        handleImageOnKitkat(data)
                    }
                }
        }
    }

    private suspend fun getBitmap(): Bitmap {
        val loading = ImageLoader(this)
        val request = ImageRequest.Builder(this)
            .data(bitmap)
            .build()

        val result = (loading.execute(request) as SuccessResult).drawable
        return (result as BitmapDrawable).bitmap
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        initializeWidgets()
        myViewModel = ViewModelProvider(this).get(MyViewModel::class.java)

        btnCapture?.setOnClickListener {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.CAMERA),
                1
            )
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}