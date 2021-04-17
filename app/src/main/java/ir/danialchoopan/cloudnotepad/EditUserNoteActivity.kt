package ir.danialchoopan.cloudnotepad

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import ir.danialchoopan.cloudnotepad.api.request.NoteRequests

class EditUserNoteActivity : AppCompatActivity() {
    lateinit var txtLayoutTitleNoteEdit: TextInputLayout
    lateinit var txtLayoutBodyNoteEdit: TextInputLayout
    lateinit var fbtnUpdateNote: FloatingActionButton
    lateinit var progressBarEditNote: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_user_note)

        //get intent extras from home activity
        val note_id = intent.extras?.getInt("USER_NOTE_ID")
        val note_title = intent.extras?.getString("USER_NOTE_TITLE")
        val note_body = intent.extras?.getString("USER_NOTE_BODY")
        val note_create_at = intent.extras?.getString("USER_NOTE_CREATED_AT")

        //init cast views
        txtLayoutTitleNoteEdit = findViewById(R.id.txtLayoutTitleNoteEdit)
        txtLayoutBodyNoteEdit = findViewById(R.id.txtLayoutBodyNoteEdit)
        fbtnUpdateNote = findViewById(R.id.fbtnUpdateNote)
        progressBarEditNote = findViewById(R.id.progressBarEditNote)

        //set note
        txtLayoutTitleNoteEdit.editText?.setText(note_title)
        txtLayoutBodyNoteEdit.editText?.setText(note_body)

        //update note
        fbtnUpdateNote.setOnClickListener {
            showProgressBarAddNote(true)
            if (note_id != null) {
                NoteRequests(this@EditUserNoteActivity)
                    .updateNote(
                        note_id,
                        txtLayoutTitleNoteEdit.editText?.text.toString(),
                        txtLayoutBodyNoteEdit.editText?.text.toString()
                    ) { requestResult ->
                        showProgressBarAddNote(false)
                        if (requestResult) {
                            Toast.makeText(
                                this@EditUserNoteActivity,
                                "نوشته شما با موفقیت ذخیره شد",
                                Toast.LENGTH_SHORT
                            ).show()
                            finish()
                        } else {
                            Toast.makeText(
                                this@EditUserNoteActivity,
                                "مشکلی پیش آمده است لطفا دوباره امتحان کنید",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }
        }

    }


    private fun validateEditTexts(): Boolean {
        if (txtLayoutTitleNoteEdit.editText?.text?.isEmpty() == true) {
            txtLayoutBodyNoteEdit.isErrorEnabled = true
            txtLayoutTitleNoteEdit.error = "فیلد خالی مجاز نیست"
            return false
        }
        if (txtLayoutBodyNoteEdit.editText?.text?.isEmpty() == true) {
            txtLayoutBodyNoteEdit.isErrorEnabled = true
            txtLayoutBodyNoteEdit.error = "فیلد خالی مجاز نیست"
            return false
        }
        return true
    }

    private fun showProgressBarAddNote(visible: Boolean) {
        if (visible) {
            txtLayoutTitleNoteEdit.isEnabled = false
            txtLayoutBodyNoteEdit.isEnabled = false
            fbtnUpdateNote.isEnabled = false
            progressBarEditNote.visibility = View.VISIBLE
        } else {

            txtLayoutTitleNoteEdit.isEnabled = true
            txtLayoutBodyNoteEdit.isEnabled = true
            fbtnUpdateNote.isEnabled = true
            progressBarEditNote.visibility = View.GONE
        }
    }
}