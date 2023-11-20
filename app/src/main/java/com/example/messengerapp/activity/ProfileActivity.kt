package com.example.messengerapp.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.messengerapp.MainActivity1
import com.example.messengerapp.R
import com.example.messengerapp.databinding.ActivityProfileBinding
import com.example.messengerapp.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.util.Date

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var storage: FirebaseStorage
    private lateinit var selectedImg: Uri
    private lateinit var dialog: AlertDialog.Builder
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dialog = AlertDialog.Builder(this)
            .setMessage("Updating the Profile...")
            .setCancelable(false)

        database = FirebaseDatabase.getInstance()
        storage = FirebaseStorage.getInstance()
        auth = FirebaseAuth.getInstance()

        binding.userImage.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "image/*"
            startActivityForResult(intent,1)
        }

        binding.continueBtn.setOnClickListener {
            if(binding.userNameEt.text!!.isEmpty()){
                Toast.makeText(this,"Please Enter Your Name",Toast.LENGTH_SHORT).show()
            }else if(selectedImg==null){
                Toast.makeText(this,"Please Select Your Image",Toast.LENGTH_SHORT).show()
            }else uploadData()
        }
    }

    private fun uploadData() {
        val reference = storage.reference.child("profile").child(Date().time.toString())
        reference.putFile(selectedImg).addOnCompleteListener {
            if(it.isSuccessful){
                reference.downloadUrl.addOnSuccessListener {task ->
                    uploadInfo(task.toString())
                }
            }
        }
    }

    private fun uploadInfo(imgUrl: String) {
        val user = UserModel(auth.uid.toString(),binding.userNameEt.text.toString(),auth.currentUser!!.phoneNumber.toString(),imgUrl)
        database.reference.child("users")
            .child(auth.uid.toString())
            .setValue(user)
            .addOnSuccessListener {
                Toast.makeText(this,"Data Uploaded",Toast.LENGTH_SHORT).show()
                startActivity(Intent(this,MainActivity1::class.java))
                finish()
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(data != null){
            if(data.data != null){
                selectedImg = data.data!!
                binding.userImage.setImageURI(selectedImg)
            }
        }

    }
}