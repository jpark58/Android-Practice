package com.example.mynote

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_start.*
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

class StartActivity : AppCompatActivity(),  EasyPermissions.PermissionCallbacks, EasyPermissions.RationaleCallbacks {
    private var READ_STORAGE_PERM = 123
    private var REQUEST_CODE_IMAGE = 456
    private var selectedImagePath = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("로그", "StartActivity - onCreate")
        setContentView(R.layout.activity_start)
        nameBtn.setOnClickListener {
            var intent = Intent(this, MainActivity::class.java)
            var username = username.text.toString()
            intent.putExtra("username", username)
            startActivity(intent)
        }

        imageView2.setOnClickListener {
            readStorageTask()
        }

    }

    private fun readStorageTask() {
        if (hasReadStoragePerm()){


            pickImageFromGallery()
        }else{
            EasyPermissions.requestPermissions(
                    this,
                    "permission",
                    READ_STORAGE_PERM,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
            )
        }
    }

    private fun hasReadStoragePerm(): Boolean{
        return EasyPermissions.hasPermissions(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    private fun hasWriteStoragePerm(): Boolean{
        return EasyPermissions.hasPermissions(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }

    private fun pickImageFromGallery(){
        var intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        if (intent.resolveActivity(this.packageManager) != null){
            startActivityForResult(intent,REQUEST_CODE_IMAGE)
        }
    }

    private fun getPathFromUri(contentUri: Uri): String? {
        var filePath:String? = null
        var cursor = this.contentResolver.query(contentUri,null,null,null,null)
        if (cursor == null){
            filePath = contentUri.path
        }else{
            cursor.moveToFirst()
            var index = cursor.getColumnIndex("_data")
            filePath = cursor.getString(index)
            cursor.close()
        }
        return filePath
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_IMAGE && resultCode == RESULT_OK){
            if (data != null){
                var selectedImageUrl = data.data
                if (selectedImageUrl != null){
                    try {
                        var inputStream = this.contentResolver.openInputStream(selectedImageUrl)
                        var bitmap = BitmapFactory.decodeStream(inputStream)
                        imageView2.setImageBitmap(bitmap)
                        selectedImagePath = getPathFromUri(selectedImageUrl)!!

                    }catch (e:Exception){
                        Toast.makeText(this,e.message, Toast.LENGTH_SHORT).show()
                    }

                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("로그", "StartActivity - onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("로그", "StartActivity - onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("로그", "StartActivity - onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("로그", "StartActivity - onStop")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("로그", "StartActivity - onRestart")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("로그", "StartActivity - onDestroy")
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults,this)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this,perms)){
            AppSettingsDialog.Builder(this).build().show()
        }
    }

    override fun onRationaleAccepted(requestCode: Int) {
    }

    override fun onRationaleDenied(requestCode: Int) {
    }
}