package com.lutech.literaryapp.screen.signin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.google.firebase.database.FirebaseDatabase
import com.lutech.literaryapp.R
import com.lutech.literaryapp.base.BaseActivity
import com.lutech.literaryapp.screen.home.activity.HomeActivity
import com.lutech.literaryapp.utils.Utils
import com.lutech.literaryapp.utils.errorToast
import com.lutech.literaryapp.utils.gone
import com.lutech.literaryapp.utils.sharedPreference
import com.lutech.literaryapp.utils.successToast
import com.lutech.literaryapp.utils.visible
import kotlinx.android.synthetic.main.activity_sign_in.clSignIn
import kotlinx.android.synthetic.main.activity_sign_in.edtAccount
import kotlinx.android.synthetic.main.activity_sign_in.edtPassword
import kotlinx.android.synthetic.main.activity_sign_in.layoutLoadingSignIn
import kotlinx.android.synthetic.main.activity_sign_in.tvForgotPass
import kotlinx.android.synthetic.main.activity_sign_in.tvSignIn
import kotlinx.android.synthetic.main.activity_sign_in.tvTitleWarningAccount
import kotlinx.android.synthetic.main.activity_sign_in.tvTitleWarningPass
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignInActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        initData()
        initView()
        handleEvent()
    }

    private fun initData() {

    }

    private fun initView() {

    }

    private fun handleEvent() {
        edtAccount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (edtAccount.text.toString().isEmpty()) {
                    tvTitleWarningAccount.apply {
                        isVisible = true
                        text = getString(R.string.txt_cannot_be_empty)
                    }
                } else {
                    tvTitleWarningAccount.isVisible = false
                }
            }

            override fun afterTextChanged(p0: Editable?) {}
        })

        edtPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (edtPassword.text.toString().isEmpty()) {
                    tvTitleWarningPass.apply {
                        isVisible = true
                        text = getString(R.string.txt_cannot_be_empty)
                    }
                } else {
                    tvTitleWarningPass.isVisible = false
                }
            }

            override fun afterTextChanged(p0: Editable?) {}
        })

        tvSignIn.setOnClickListener {
            checkCondition(edtAccount.text.toString(), edtPassword.text.toString())
            Utils.hideKeyboardFrom(this, clSignIn)
        }

        tvForgotPass.setOnClickListener {
//            startActivity(Intent(this, ForgotPassActivity::class.java))
        }
    }
    private fun checkCondition(account: String, pass: String) {
        if (account.isEmpty()) {
            tvTitleWarningAccount.apply {
                isVisible = true
                text = getString(R.string.txt_cannot_be_empty)
            }
        } else {
            if (pass.isEmpty()) {
                tvTitleWarningPass.apply {
                    isVisible = true
                    text = getString(R.string.txt_cannot_be_empty)
                }
            } else {
                checkDataAccount(account.uppercase().trim(), pass.trim())
            }
        }
    }

    private fun checkDataAccount(userAccount: String, pass: String) {
        visible(layoutLoadingSignIn)
        FirebaseDatabase.getInstance().getReference("taikhoan/${if(userAccount.contains("HSK")) "HS" else "GV"}/${userAccount}").get()
            .addOnSuccessListener {
                if (it.exists()) {
                    if (it.child("matKhau").value.toString() == pass) {
                        val intent = Intent(this, HomeActivity::class.java)

                        lifecycleScope.launch(Dispatchers.Main) {
                            withContext(Dispatchers.IO) {
                                sharedPreference.apply {
                                    nameAccount = userAccount
                                    passwordAccount = pass
                                    roleAccount = it.child("idChucVu").value.toString()
                                }
                            }
                            startActivity(intent)
                            successToast(getString(R.string.txt_sign_in_success))
                            gone(layoutLoadingSignIn)
                            finish()
                        }
                    } else {
                        tvTitleWarningPass.apply {
                            isVisible = true
                            text = getString(R.string.txt_wrong_pass)
                        }
                        gone(layoutLoadingSignIn)
                    }
                } else {
                    tvTitleWarningAccount.apply {
                        isVisible = true
                        text = getString(R.string.txt_account_not_exist)
                    }
                    gone(layoutLoadingSignIn)
                }
            }.addOnCanceledListener {

            }.addOnFailureListener {
                errorToast(getString(R.string.txt_find_account_fail))
                gone(layoutLoadingSignIn)
            }
    }
}