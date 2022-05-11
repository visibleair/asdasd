package com.example.testproject.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.testproject.R
import com.example.testproject.databinding.ActivityMainBinding
import com.example.testproject.fragments.FirstFragment
import com.example.testproject.fragments.SecindFragment
import com.example.testproject.model.Token
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException

private lateinit var binding: ActivityMainBinding
private var okHttpClient = OkHttpClient()
private lateinit var formBody: FormBody
private lateinit var gson: Gson
private lateinit var request: Request

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gson = Gson()
        binding = ActivityMainBinding.inflate(layoutInflater)
        formBody = FormBody.Builder()
            .add("email", "vasya@mail.com")
            .add("password", "qwerty")
            .build()
        request = Request.Builder().url("https://food.madskill.ru/auth/login").post(formBody).build()
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {

            }

            override fun onResponse(call: Call, response: Response) {
                //Log.i("tag", "onResponse: ${response.body!!.string()}}")
                Log.i("tag", "${gson.fromJson(response.body!!.string(), Token::class.java).token}")
            }

        })
        Regex("[a-zA-Z0-9]")
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView, FirstFragment()).commit()
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.first -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView, FirstFragment()).commit()
                    return@setOnItemSelectedListener true
                }
                R.id.second -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView, SecindFragment()).commit()
                    return@setOnItemSelectedListener true
                }
            }
            return@setOnItemSelectedListener false
        }

        setContentView(binding.root)
    }




}