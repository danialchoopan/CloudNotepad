package ir.danialchoopan.cloudnotepad.fragment.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import ir.danialchoopan.cloudnotepad.AuthActivity
import ir.danialchoopan.cloudnotepad.ChangePasswordActivity
import ir.danialchoopan.cloudnotepad.R
import ir.danialchoopan.cloudnotepad.api.request.AuthRequests
import ir.danialchoopan.cloudnotepad.utails.UserSharedPreferences

class SettingFragment : Fragment() {
    lateinit var txwUserNameSetting: TextView
    lateinit var txwUserEmailSetting: TextView
    lateinit var btnChangePasswordSetting: Button
    lateinit var rGroupBtnTextSize: RadioGroup
    lateinit var rBtnTextSizeSmall: RadioButton
    lateinit var rBtnTextSizeMedium: RadioButton
    lateinit var rBtnTextSizeLarge: RadioButton
    lateinit var btnLogoutSetting: Button
    lateinit var progressSetting: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        txwUserNameSetting = view.findViewById(R.id.txwUserNameSetting)
        txwUserEmailSetting = view.findViewById(R.id.txwUserEmailSetting)
        btnChangePasswordSetting = view.findViewById(R.id.btnChangePasswordSetting)
        rGroupBtnTextSize = view.findViewById(R.id.rGroupBtnTextSize)
        rBtnTextSizeSmall = view.findViewById(R.id.rBtnTextSizeSmall)
        rBtnTextSizeMedium = view.findViewById(R.id.rBtnTextSizeMedium)
        rBtnTextSizeLarge = view.findViewById(R.id.rBtnTextSizeLarge)
        btnLogoutSetting = view.findViewById(R.id.btnLogoutSetting)
        progressSetting = view.findViewById(R.id.progressSetting)

        //Shared Preferences user setting
        val userSharedPreferences =
            context?.let { notNullContext -> UserSharedPreferences(notNullContext).instance() }
        val sharedPreferencesSetting =
            context?.getSharedPreferences("setting", Context.MODE_PRIVATE)

        txwUserNameSetting.text = userSharedPreferences!!.getString("name", "error")
        txwUserEmailSetting.text = userSharedPreferences.getString("email", "error")

        btnLogoutSetting.setOnClickListener {
            sharedPreferencesSetting!!.edit().clear().apply()
            it.isEnabled = false
            userSharedPreferences.edit().clear().apply()
            progressSetting.visibility = View.VISIBLE
            context?.let { notNullContext ->
                AuthRequests(notNullContext).logout { resultLogout ->
                    it.isEnabled = true
                    progressSetting.visibility = View.GONE
                    Intent(context, AuthActivity::class.java).also { intent ->
                        startActivity(intent)
                    }
                    activity?.finish()
                }
            }
        }

        btnChangePasswordSetting.setOnClickListener {
            Intent(context, ChangePasswordActivity::class.java).also { intent ->
                startActivity(intent)
            }
        }

        when (sharedPreferencesSetting!!.getString("TEXT_SIZE", "medium")) {
            "small" -> {
                rBtnTextSizeSmall.isChecked = true
            }
            "medium" -> {
                rBtnTextSizeMedium.isChecked = true
            }
            "large" -> {
                rBtnTextSizeLarge.isChecked = true
            }
        }

        rGroupBtnTextSize.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rBtnTextSizeSmall -> {
                    sharedPreferencesSetting.edit().apply {
                        putString("TEXT_SIZE", "small")
                        putFloat("STATUS_TEXT_SIZE", 10f)
                        putFloat("BODY_TEXT_SIZE", 12f)
                        putFloat("BODY_TEXT_SIZE", 16f)
                        apply()
                    }
                }
                R.id.rBtnTextSizeMedium -> {
                    sharedPreferencesSetting.edit().apply {
                        putString("TEXT_SIZE", "medium")
                        putFloat("STATUS_TEXT_SIZE", 12f)
                        putFloat("BODY_TEXT_SIZE", 14f)
                        putFloat("BODY_TEXT_SIZE", 18f)
                        apply()
                    }
                }
                R.id.rBtnTextSizeLarge -> {
                    sharedPreferencesSetting.edit().apply {
                        putString("TEXT_SIZE", "large")
                        putFloat("STATUS_TEXT_SIZE", 14f)
                        putFloat("BODY_TEXT_SIZE", 16f)
                        putFloat("BODY_TEXT_SIZE", 19f)
                        apply()
                    }
                }
            }
        }

    }
}