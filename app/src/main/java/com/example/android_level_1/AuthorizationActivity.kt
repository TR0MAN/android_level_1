package com.example.android_level_1

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.util.Patterns
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.android_level_1.databinding.ActivityAuthorizationBinding

class AuthorizationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthorizationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthorizationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        customSymbolTextInputForm()

        emailFormObserver()
        passwordFormObserver()

    }

    // отслеживание изменений в поле e-mail, с последующей валидацией
    private fun emailFormObserver() {
        binding.textInputEmailForm.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }
            override fun afterTextChanged(s: Editable?) {
                if (!emailValidator()) {
                    binding.textInputEmailContainer.helperText =
                        getString(R.string.response_wrong_email)
                    binding.textInputEmailContainer.setHelperTextColor(ColorStateList
                        .valueOf(getColor(R.color.profile_contact_button_orange)))
                }
                else {
                    binding.textInputEmailContainer.helperText = getString(R.string.response_ok)
                    binding.textInputEmailContainer.setHelperTextColor(ColorStateList.valueOf(Color.GREEN))
                }
            }
        } )
    }

    // валидация введенного текста, для почты
    private fun emailValidator(): Boolean {
        val email = binding.textInputEmailForm.text.toString()
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return false
        }
        return true
    }

    // отслеживание изменений в поле password, с последующей валидацией
    private fun passwordFormObserver() {
        binding.textInputPasswordForm.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }
            override fun afterTextChanged(s: Editable?) {
                if (passwordValidator() != "All fine") {
                    binding.textInputPasswordContainer.helperText = passwordValidator()
                    binding.textInputPasswordContainer.setHelperTextColor(ColorStateList
                        .valueOf(getColor(R.color.profile_contact_button_orange)))
                } else {
                    binding.textInputPasswordContainer.helperText = passwordValidator()
                    binding.textInputPasswordContainer.setHelperTextColor(ColorStateList.valueOf(Color.GREEN))
                }
            }
        } )
    }

    // валидация введенного текста, для почты
    private fun passwordValidator(): String {
        val password = binding.textInputPasswordForm.text.toString()
        if (password.length < 8) {
            return getString(R.string.error_min_8_symbols_password)
        }
        if (!password.matches(".*[A-Z].*".toRegex())) {
            Log.d("TAG", "REGEX = ${password.matches(".*[A-Z].*".toRegex())}")
            return getString(R.string.error_upper_case_symbol)
        }
        if (!password.matches(".*[a-z].*".toRegex())) {
            Log.d("TAG", "REGEX = ${password.matches(".*[A-Z].*".toRegex())}")
            return getString(R.string.error_lower_case_symbol)
        }
        if (!password.matches(".*[0-9].*".toRegex())) {
            Log.d("TAG", "REGEX = ${password.matches(".*[A-Z].*".toRegex())}")
            return getString(R.string.error_contain_number)
        }
        if (!password.matches(".*[!@#\$%^&*].*".toRegex())) {
            Log.d("TAG", "REGEX = ${password.matches(".*[A-Z].*".toRegex())}")
            return getString(R.string.error_contain_special_symbol)
        }
        if (password.matches(".*[~`()_+|\\?/.{}\\[\\],<>=\\-].*".toRegex())) {
            Log.d("TAG", "REGEX = ${password.matches(".*[A-Z].*".toRegex())}")
            return getString(R.string.error_include_wrong_spec_symbols)
        }
        // TODO ДОДЕЛАТЬ
        if (password.matches(".*[ ].*".toRegex())) {
            return getString(R.string.error_include_white_space)
        }
        return getString(R.string.response_ok)
    }


    // замена стандартного символа скрытия буквы пароля, на большой символ ('●' '⬤')
    private fun customSymbolTextInputForm() {
        binding.textInputPasswordForm.transformationMethod = object : PasswordTransformationMethod() {
            override fun getTransformation(source: CharSequence, view: View): CharSequence {
                val transformation = super.getTransformation(source, view)
                return object : CharSequence by transformation {
                    override fun get(index: Int): Char {
                        return if (transformation[index] == '\u2022') {
                            '●'
                        } else {
                            transformation[index]
                        }
                    }
                }
            }
        }
    }
}

