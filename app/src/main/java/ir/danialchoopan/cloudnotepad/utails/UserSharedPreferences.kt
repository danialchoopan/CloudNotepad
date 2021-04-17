package ir.danialchoopan.cloudnotepad.utails

import android.content.Context
import android.content.SharedPreferences

class UserSharedPreferences(val mContent: Context) {
    fun instance(): SharedPreferences {
        return mContent.getSharedPreferences("user", Context.MODE_PRIVATE)
    }
}