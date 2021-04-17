package ir.danialchoopan.cloudnotepad.fragment.auth

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import ir.danialchoopan.cloudnotepad.HomeActivity
import ir.danialchoopan.cloudnotepad.R
import ir.danialchoopan.cloudnotepad.api.request.AuthRequests
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope

class LoginFragment : Fragment() {
    val requestScope = CoroutineScope(CoroutineName("requestScope"))
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val txw_open_register = view.findViewById<TextView>(R.id.txw_open_register)
        val btnLogin = view.findViewById<Button>(R.id.btnLogin)
        val txtEmail = view.findViewById<EditText>(R.id.txtEmailLogin)
        val txtPassword = view.findViewById<EditText>(R.id.txtPasswordlLogin)
        val txwLogin = view.findViewById<TextView>(R.id.txwLogin)
        txw_open_register.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_loginFragment_to_registerFragment)
        }
        btnLogin.setOnClickListener {
            val progressDialog = ProgressDialog(context)
            progressDialog.setMessage("لطفا صبر کنید ...")
            progressDialog.show()
            context?.let { notNullContent ->
                AuthRequests(notNullContent).login(
                    txtEmail.text.toString(),
                    txtPassword.text.toString()
                ) { resultLogin ->
                    progressDialog.dismiss()
                    if (resultLogin) {
                        Intent(context, HomeActivity::class.java).also {
                            startActivity(it)
                        }
                        activity.let {
                            it?.finish()
                        }
                    } else {
                        Toast.makeText(
                            context,
                            "لطفا نام کاربری یا رمز عبور خود را برسی کنید !",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

    }
}