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

class RegisterFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val txtName = view.findViewById<EditText>(R.id.txtName)
        val txtEmail = view.findViewById<EditText>(R.id.txtEmail)
        val txtPassword = view.findViewById<EditText>(R.id.txtPassword)
        val btnRegister = view.findViewById<Button>(R.id.btnRegister)
        btnRegister.setOnClickListener {
            val progressDialog = ProgressDialog(context)
            progressDialog.setMessage("لطفا صبر کنید ..")
            progressDialog.show()
            context?.let { notNullContent ->
                AuthRequests(notNullContent).register(
                    txtName.text.toString(),
                    txtEmail.text.toString(),
                    txtPassword.text.toString()
                ) { resultRegister ->
                    progressDialog.dismiss()
                    if (resultRegister) {

                        Intent(context, HomeActivity::class.java).also {
                            startActivity(it)
                        }
                        activity.let {
                            it?.finish()
                        }
                    } else {

                        Toast.makeText(
                            context,
                            "مشکلی پیش آمده لطفا دوباره سعی کنید",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

            }
        }
        val button_open_login = view.findViewById<TextView>(R.id.txw_open_register)
        button_open_login.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_registerFragment_to_loginFragment)
        }


    }
}