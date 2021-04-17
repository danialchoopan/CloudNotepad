package ir.danialchoopan.cloudnotepad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import ir.danialchoopan.cloudnotepad.api.request.AuthRequests
import ir.danialchoopan.cloudnotepad.utails.UserSharedPreferences

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userSharedPreferences = UserSharedPreferences(this@MainActivity).instance()
        if (userSharedPreferences.getString("token", "") != "") {
            AuthRequests(this@MainActivity).checkToken { checkTokenResult ->
                if (checkTokenResult) {
                    Intent(this@MainActivity, HomeActivity::class.java).also {
                        startActivity(it)
                    }
                    finish()
                } else {
                    userSharedPreferences.edit().clear().apply()
                    Intent(this@MainActivity, AuthActivity::class.java).also {
                        startActivity(it)
                    }
                    finish()
                }
            }
        }

        setContentView(R.layout.activity_main)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val btnRegister = findViewById<Button>(R.id.btnRegister)

        btnLogin.setOnClickListener {
            intentAuthActivity("login").also {
                startActivity(it)
            }

            finish()
        }

        btnRegister.setOnClickListener {
            intentAuthActivity("login").also {
                startActivity(it)
            }

            finish()
        }


    }

    private fun intentAuthActivity(mode: String): Intent {
        return Intent(this@MainActivity, AuthActivity::class.java).also {
            it.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            it.putExtra("mode", mode)
        }
    }
}