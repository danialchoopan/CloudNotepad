package ir.danialchoopan.cloudnotepad.api.request

import android.content.Context
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import ir.danialchoopan.cloudnotepad.api.VolleySingleTon
import ir.danialchoopan.cloudnotepad.api.model.UserNote
import ir.danialchoopan.cloudnotepad.utails.EndPoints
import ir.danialchoopan.cloudnotepad.utails.UserSharedPreferences
import org.json.JSONObject
import java.lang.reflect.Method

class NoteRequests(val mContent: Context) {
    val userSharedPreferences = UserSharedPreferences(mContent).instance()
    fun userNotes(resultUserNotes: (resultUserNotes: ArrayList<UserNote>, requestResult: Boolean) -> Unit) {
        val userNotesRequest = object : StringRequest(
            Request.Method.GET,
            EndPoints.userNotesUrl,
            Response.Listener {
                val jsonResult = JSONObject(it)
                val UserNotes = ArrayList<UserNote>()
                if (jsonResult.getBoolean("success")) {
                    val jsonArrayNotes = jsonResult.getJSONArray("notes")
                    for (NoteIndex in 0 until jsonArrayNotes.length()) {
                        val jsonNote = jsonArrayNotes.getJSONObject(NoteIndex)
                        UserNotes.add(
                            UserNote(
                                jsonNote.getInt("id"),
                                jsonNote.getInt("user_id"),
                                jsonNote.getString("title"),
                                jsonNote.getString("body"),
                                jsonNote.getString("public").toInt() == 1,
                                jsonNote.getString("created_at"),
                                jsonNote.getString("updated_at")

                            )
                        )
                    }
                }
                resultUserNotes(UserNotes, jsonResult.getBoolean("success"))
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
        VolleySingleTon.getInstance(mContent).addToRequestQueue(userNotesRequest)
    }

    fun publicNotes(resultUserNotes: (resultUserNotes: ArrayList<UserNote>, requestResult: Boolean) -> Unit) {
        val userNotesRequest = object : StringRequest(
            Request.Method.GET,
            EndPoints.publicNotesUrl,
            Response.Listener {
                val jsonResult = JSONObject(it)
                val UserNotes = ArrayList<UserNote>()
                if (jsonResult.getBoolean("success")) {
                    val jsonArrayNotes = jsonResult.getJSONArray("notes")
                    for (NoteIndex in 0 until jsonArrayNotes.length()) {
                        val jsonNote = jsonArrayNotes.getJSONObject(NoteIndex)
                        UserNotes.add(
                            UserNote(
                                jsonNote.getInt("id"),
                                jsonNote.getInt("user_id"),
                                jsonNote.getString("title"),
                                jsonNote.getString("body"),
                                jsonNote.getString("public").toInt() == 1,
                                jsonNote.getString("created_at"),
                                jsonNote.getString("updated_at")

                            )
                        )
                    }
                }
                resultUserNotes(UserNotes, jsonResult.getBoolean("success"))
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
        VolleySingleTon.getInstance(mContent).addToRequestQueue(userNotesRequest)
    }

    fun addNote(
        title: String,
        body: String,
        public: Boolean = false,
        resultAddNote: (requestResult: Boolean) -> Unit
    ) {
        val addNoteRequest = object : StringRequest(
            Request.Method.POST,
            EndPoints.addNoteUrl,
            Response.Listener {
                val jsonResult = JSONObject(it)
                resultAddNote(jsonResult.getBoolean("success"))
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
                val addNoteParams = HashMap<String, String>()
                addNoteParams["title"] = title
                addNoteParams["body"] = body
                if (public) {
                    addNoteParams["public"] = public.toString()
                }
                return addNoteParams
            }

        }
        VolleySingleTon.getInstance(mContent).addToRequestQueue(addNoteRequest)
    }


    fun updateNote(
        noteId: Int,
        title: String,
        body: String,
        public: Boolean = false,
        resultUpdateNote: (requestResult: Boolean) -> Unit
    ) {
        val updateNoteRequest = object : StringRequest(
            Request.Method.PUT,
            EndPoints.updateNoteUrl + noteId,
            Response.Listener {
                val jsonResult = JSONObject(it)
                resultUpdateNote(jsonResult.getBoolean("success"))
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
                val addNoteParams = HashMap<String, String>()
                addNoteParams["title"] = title
                addNoteParams["body"] = body
                if (public) {
                    addNoteParams["public"] = public.toString()
                }
                return addNoteParams
            }

        }
        VolleySingleTon.getInstance(mContent).addToRequestQueue(updateNoteRequest)
    }


    fun deleteNote(
        noteId: Int,
        resultUpdateNote: (requestResult: Boolean) -> Unit
    ) {
        val deleteNoteRequest = object : StringRequest(
            Request.Method.DELETE,
            EndPoints.deleteNoteUrl + noteId,
            Response.Listener {
                val jsonResult = JSONObject(it)
                resultUpdateNote(jsonResult.getBoolean("success"))
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
        VolleySingleTon.getInstance(mContent).addToRequestQueue(deleteNoteRequest)
    }

    fun changeNoteStatus(
        noteId: Int,
        public: Boolean,
        resultUpdateNote: (requestResult: Boolean) -> Unit
    ) {
        val changeNoteStatusRequest = object : StringRequest(
            Request.Method.POST,
            EndPoints.changeNoteStatusRequest + noteId,
            Response.Listener {
                val jsonResult = JSONObject(it)
                resultUpdateNote(jsonResult.getBoolean("success"))
            },
            Response.ErrorListener {
                it.printStackTrace()
            }) {


            override fun getParams(): MutableMap<String, String> {
                val addNoteParams = HashMap<String, String>()
                addNoteParams["public"] = (if (public) 1 else 0).toString()
                return addNoteParams
            }

            override fun getHeaders(): MutableMap<String, String> {
                val token_access = userSharedPreferences.getString("token", "")
                val requestHeaders = HashMap<String, String>()
                requestHeaders["Authorization"] = "Bearer $token_access";
                return requestHeaders
            }

        }
        VolleySingleTon.getInstance(mContent).addToRequestQueue(changeNoteStatusRequest)
    }


}