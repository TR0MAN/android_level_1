package com.example.android_level_1

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.android_level_1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getUserName(intent)
        binding.btnMyProfileLogOut.setOnClickListener { finish() }

    }

    // получение данных со страницы регистрации, первых 2 слова использутся в качестве имени/фамилии
    private fun getUserName(intent: Intent?) {
        val receivedEmail = (intent?.getStringExtra(Const.EMAIL) ?: getString(R.string.unknown_user)).run {
            this.substringBefore('@')
        }
        Log.d("TAG", receivedEmail)
//        val partOfEmail = receivedEmail.substringBefore('@')
        if (receivedEmail.contains('_') || receivedEmail.contains('-')
            || receivedEmail.contains('.')) {
            // TODO проверить не стоит ли спец символ первым или последним
            // TODO не работает нормально при несколькоих спецсимволах подряд
            val data = receivedEmail.split('.','_','-')
            binding.tvMyProfileName.text = "${data[0].replaceFirstChar {it.uppercaseChar()}}" +
                    " ${data[1].replaceFirstChar {it.uppercaseChar()}}"
        } else {
            binding.tvMyProfileName.text = getString(R.string.unknown_user)
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Toast.makeText(this, "NOW ORIENTATION PORTRAIT", Toast.LENGTH_SHORT).show()
        }
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "NOW ORIENTATION LANDSCAPE", Toast.LENGTH_SHORT).show()
        }
    }
}