package com.example.gecp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class AddingWtsp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adding_wtsp)
      val numBtn = findViewById<Button>(R.id.numButton)
        val numText = findViewById<EditText>(R.id.numberInput)
         numBtn.setOnClickListener(){
            val Mainactivityintent  = Intent(this,MainActivity::class.java)
// val numData = numText.text.toString()
//             val datanum = numData
//
//             intent.putExtra("numData",datanum)
//
//savedInstanceState
            startActivity(Mainactivityintent)

//
//
        }



    }


}