package com.example.android_level_1

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.android_level_1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getUserName(intent)
        binding.btnMyProfileLogOut.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.horiz_from_left_to_center, R.anim.horiz_from_center_to_right)
        }
    }

    // получение данных со страницы регистрации, первых 2 слова используется в качестве имени/фамилии
    private fun getUserName(intent: Intent?) {
        val receivedEmail = (intent?.getStringExtra(Const.EMAIL) ?: getString(R.string.unknown_user)).run {
            this.substringBefore('@')
        }
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

}