package com.lutech.literaryapp.screen.home.fragment

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.lutech.literaryapp.R
import com.lutech.literaryapp.screen.home.activity.HomeActivity
import com.lutech.literaryapp.screen.signin.SignInActivity
import com.lutech.literaryapp.utils.Utils
import com.lutech.literaryapp.utils.sharedPreference
import kotlinx.android.synthetic.main.dialog_option_sign_out.tvNOSignOut
import kotlinx.android.synthetic.main.dialog_option_sign_out.tvYESSignOut
import kotlinx.android.synthetic.main.fragment_user.imgAvatarUser
import kotlinx.android.synthetic.main.fragment_user.llSignOut
import kotlinx.android.synthetic.main.fragment_user.tvGrade
import kotlinx.android.synthetic.main.fragment_user.tvIDUser
import kotlinx.android.synthetic.main.fragment_user.tvNameUser
import kotlinx.android.synthetic.main.fragment_user.tvRoleUser

class UserFragment : Fragment() {

    private var mContext: Context? = null

    private lateinit var databaseUser: DatabaseReference

    private var mSignOutDialog : Dialog? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mContext = requireContext()
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initView()
        handleEvent()
    }

    private fun initData() {
        databaseUser = FirebaseDatabase.getInstance().reference
    }

    private fun initView() {
        initViewUser(if(mContext!!.sharedPreference.nameAccount!!.uppercase().contains("GV"))
            "nhanVien/${mContext!!.sharedPreference.nameAccount!!.uppercase()}"
        else "hocsinh/${mContext!!.sharedPreference.nameAccount!!.uppercase()}")
    }

    private fun handleEvent() {
        llSignOut.setOnClickListener {
            initSignOutDialog()
        }
    }

    private fun initSignOutDialog() {
        if(mSignOutDialog != null) {
            mSignOutDialog!!.show()
        } else {
            mSignOutDialog = Utils.onCreateDialog(mContext!!, R.layout.dialog_option_sign_out, false)

            mSignOutDialog!!.apply {
                tvNOSignOut.setOnClickListener {
                    mSignOutDialog!!.dismiss()
                }

                tvYESSignOut.setOnClickListener {
                    mContext!!.sharedPreference.apply {
                        nameAccount = "abc"
                        passwordAccount = "abc"
                        roleAccount = "abc"
                    }
                    val intent = Intent(mContext, SignInActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK.or(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(intent)
                }

                show()
            }
        }
    }

    private fun initViewUser(infoUser: String) {
        databaseUser.child(infoUser)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {

                    Glide.with(mContext!!).asBitmap().load(dataSnapshot.child("anh")
                        .getValue(String::class.java)).into(imgAvatarUser)

                    tvIDUser.text = dataSnapshot.child("idNguoiDung")
                        .getValue(String::class.java)

                    tvNameUser.text = dataSnapshot.child("tenNguoiDung")
                        .getValue(String::class.java)

                    tvRoleUser.text = dataSnapshot.child("chucVu")
                        .getValue(String::class.java)

                    tvGrade.text = dataSnapshot.child("lop")
                        .getValue(String::class.java)
                }

                override fun onCancelled(databaseError: DatabaseError) {}
            })

    }
}