package ir.danialchoopan.cloudnotepad.api.request

import android.content.Context
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import ir.danialchoopan.cloudnotepad.api.VolleySingleTon
import ir.danialchoopan.cloudnotepad.utails.EndPoints
import ir.danialchoopan.cloudnotepad.utails.UserSharedPreferences
import org.json.JSONObject

class AuthRequests(val mContent: Context) {
    val userSharedPreferences = UserSharedPreferences(mContent).instance()
    fun register(
        name: String,
        email: String,
        password: String,
        resultRegister: (resultRegister: Boolean) -> Unit
    ) {
        val registerRequest =
            object : StringRequest(Request.Method.POST, EndPoints.registerUrl,
                Response.Listener {
                    val resultJson = JSONObject(it)
                    if (resultJson.getBoolean("success")) {
                        userSharedPreferences.edit().apply {
                            putString("token", resultJson.getString("token"))
                            putString("name", resultJson.getJSONObject("user").getString("name"))
                            putString("email", resultJson.getJSONObject("user").getString("email"))
                            apply()
                        }
                        resultRegister(
                            resultJson.getBoolean("success")
                        )
                    } else {
                        resultRegister(
                            resultJson.getBoolean("success")
                        )
                    }
                },
                Response.ErrorListener {
                    it.printStackTrace()
                }) {
                override fun getParams(): MutableMap<String, String> {
                    val loginParams = HashMap<String, String>()
                    loginParams["name"] = name
                    loginParams["email"] = email
                    loginParams["password"] = password
                    return loginParams
                }
            }
        VolleySingleTon.getInstance(mContent).addToRequestQueue(registerRequest)
    }

    fun login(email: String, password: String, loginResult: (result: Boolean) -> Unit) {
        val registerRequest =
            object : StringRequest(Request.Method.POST, EndPoints.loginUrl,
                Response.Listener {
                    val resultJson = JSONObject(it)
                    if (resultJson.getBoolean("success")) {
                        userSharedPreferences.edit().apply {
                            putString("token", resultJson.getString("token"))
                            putString("name", resultJson.getJSONObject("user").getString("name"))
                            putString("email", resultJson.getJSONObject("user").getString("email"))
                            apply()
                        }
                        loginResult(
                            resultJson.getBoolean("success")
                        )
                    } else {
                        loginResult(
                            resultJson.getBoolean("success")
                        )
                    }
                },
                Response.ErrorListener {
                    it.printStackTrace()
                }) {
                override fun getParams(): MutableMap<String, String> {
                    val loginParams = HashMap<String, String>()
                    loginParams["email"] = email
                    loginParams["password"] = password
                    return loginParams
                }
            }
        VolleySingleTon.getInstance(mContent).addToRequestQueue(registerRequest)
    }

    fun changePassword(
        oldPassword: String,
        newPassword: String,
        changePasswordResult: (changePasswordResult: Boolean) -> Unit
    ) {
        val changePasswordRequest =
            object : StringRequest(Request.Method.POST, EndPoints.changePasswordUrl,
                Response.Listener {
                    val resultJson = JSONObject(it)
                    changePasswordResult(
                        resultJson.getBoolean("success")
                    )
                },
                Response.ErrorListener {
                    it.printStackTrace()
                }) {
                override fun getHeaders(): MutableMap<String, String> {
                    val token_access = userSharedPreferences.getString("token", "")
                    val requestHeaders = HashMap<String, String>()
                    requestHeaders["Authorization"] = "Bearer $token_access";
                    return requestHeaders
                }

                override fun getParams(): MutableMap<String, String> {
                    val changePasswordParams = HashMap<String, String>()
                    changePasswordParams["oldPassword"] = oldPassword
                    changePasswordParams["newPassword"] = newPassword
                    return changePasswordParams
                }
            }
        VolleySingleTon.getInstance(mContent).addToRequestQueue(changePasswordRequest)
    }

    fun checkToken(checkTokenResult: (checkTokenResult: Boolean) -> Unit) {
        val checkRequest =
            object : StringRequest(Request.Method.GET, EndPoints.checkTokenUrl,
                Response.Listener {
                    val resultJson = JSONObject(it)
                    checkTokenResult(
                        resultJson.getBoolean("success")
                    )
                },
                Response.ErrorListener {
                    it.printStackTrace()
                }) {
                override fun getHeaders(): MutableMap<String, String> {
                    val token_access = userSharedPreferences.getString("token", "")
                    val requestHeaders = HashMap<String, String>()
                    requestHeaders["Authorization"] = "Bearer $token_access";
                    return requestHeaders
                }
            }
        VolleySingleTon.getInstance(mContent).addToRequestQueue(checkRequest)
    }

    fun logout(logoutResult: (result: Boolean) -> Unit) {
        val LogoutRequest =
            object : StringRequest(Request.Method.POST, EndPoints.logoutUrl,
                Response.Listener {
                    val resultJson = JSONObject(it)
                    logoutResult(
                        resultJson.getBoolean("success")
                    )
                },
                Response.ErrorListener {
                    it.printStackTrace()
                }) {
                override fun getHeaders(): MutableMap<String, String> {
                    val token_access = userSharedPreferences.getString("token", "")
                    val requestHeaders = HashMap<String, String>()
                    requestHeaders["Authorization"] = "Bearer $token_access";
                    return requestHeaders
                }
            }
        VolleySingleTon.getInstance(mContent).addToRequestQueue(LogoutRequest)
    }
}