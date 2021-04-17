package ir.danialchoopan.cloudnotepad

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputLayout
import ir.danialchoopan.cloudnotepad.api.request.AuthRequests

class ChangePasswordActivity : AppCompatActivity() {
    lateinit var txtLayoutOldPassword: TextInputLayout
    lateinit var txtLayoutNewPassword: TextInputLayout
    lateinit var txtLayoutReNewPassword: TextInputLayout
    lateinit var btnChangePassword: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        txtLayoutOldPassword = findViewById(R.id.txtLayoutOldPassword)
        txtLayoutNewPassword = findViewById(R.id.txtLayoutNewPassword)
        txtLayoutReNewPassword = findViewById(R.id.txtLayoutReNewPassword)
        btnChangePassword = findViewById(R.id.btnChangePassword)

        btnChangePassword.setOnClickListener {
            val progressDialog = ProgressDialog(this@ChangePasswordActivity)
            progressDialog.setMessage("لطفا صبر کنید ..")
            progressDialog.show()
            if (validateEditTexts()) {
                AuthRequests(this@ChangePasswordActivity).changePassword(
                    txtLayoutOldPassword.editText?.text.toString(),
                    txtLayoutNewPassword.editText?.text.toString()
                ) { changePasswordResult ->
                    progressDialog.dismiss()
                    if (changePasswordResult) {
                        Toast.makeText(
                            this@ChangePasswordActivity,
                            "رمزعبور شما با موفقیت تغییر کرد", Toast.LENGTH_SHORT
                        ).show()
                        finish()
                    } else {
                        Toast.makeText(
                            this@ChangePasswordActivity,
                            "رمزعبور شما با رمزعبور قبلی شما برابر نیست", Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

    }


    private fun validateEditTexts(): Boolean {
        if (txtLayoutOldPassword.editText?.text?.isEmpty() == true) {
            txtLayoutOldPassword.isErrorEnabled = true
            txtLayoutOldPassword.error = "فیلد خالی مجاز نیست"
            return false
        }
        if (txtLayoutNewPassword.editText?.text?.isEmpty() == true) {
            txtLayoutNewPassword.isErrorEnabled = true
            txtLayoutNewPassword.error = "فیلد خالی مجاز نیست"
            return false
        }
        if (txtLayoutNewPassword.editText?.text == txtLayoutReNewPassword.editText?.text) {
            txtLayoutNewPassword.error = "تکرار رمزعبور شما با رمزعبور شما برابر نیست"
            return false
        }
        return true
    }

}