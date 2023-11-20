package com.example.messengerapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.messengerapp.MainActivity1
import com.example.messengerapp.R
import com.example.messengerapp.databinding.ActivityNumberBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth

class NumberActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNumberBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNumberBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        if (auth.currentUser!=null){
            startActivity(Intent(this,MainActivity1::class.java))
            finish()
        }

        binding.button.setOnClickListener {
            if(binding.phoneNumber.text!!.isEmpty()){
                Toast.makeText(this,"Please enter your number",Toast.LENGTH_LONG).show()
            }else{
                var intent = Intent(this,OTPActivity::class.java)
                intent.putExtra("number",binding.phoneNumber.text!!.toString())
                startActivity(intent)
            }
        }

    }
}