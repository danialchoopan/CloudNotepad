package ir.danialchoopan.cloudnotepad

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputLayout
import ir.danialchoopan.cloudnotepad.api.request.NoteRequests

class AddNoteActivity : AppCompatActivity() {
    lateinit var txtLayoutTitleNote: TextInputLayout
    lateinit var txtLayoutBodyNote: TextInputLayout
    lateinit var fbtnSaveNote: FloatingActionButton
    lateinit var progressBarAddNote: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)
        txtLayoutTitleNote = findViewById(R.id.txtLayoutTitleNote)
        txtLayoutBodyNote = findViewById(R.id.txtLayoutBodyNote)
        fbtnSaveNote = findViewById(R.id.fbtnSaveNote)
        progressBarAddNote = findViewById(R.id.progressBarAddNote)
        fbtnSaveNote.setOnClickListener {
            if (validateEditTexts()) {
                showProgressBarAddNote(true)
                NoteRequests(this@AddNoteActivity).addNote(
                    txtLayoutTitleNote.editText?.text.toString(),
                    txtLayoutBodyNote.editText?.text.toString()
                ) { requestResult ->
                    showProgressBarAddNote(false)
                    if (requestResult) {
                        Toast.makeText(
                            this@AddNoteActivity,
                            "نوشته شما با موفقیت ذخیره شد",
                            Toast.LENGTH_SHORT
                        ).show()
                        finish()
                    } else {
                        Toast.makeText(
                            this@AddNoteActivity,
                            "مشکلی پیش آمده است لطفا دوباره امتحان کنید",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun validateEditTexts(): Boolean {
        if (txtLayoutTitleNote.editText?.text?.isEmpty() == true) {
            txtLayoutTitleNote.isErrorEnabled = true
            txtLayoutTitleNote.error = "فیلد خالی مجاز نیست"
            return false
        }
        if (txtLayoutBodyNote.editText?.text?.isEmpty() == true) {
            txtLayoutBodyNote.isErrorEnabled = true
            txtLayoutBodyNote.error = "فیلد خالی مجاز نیست"
            return false
        }
        return true
    }

    private fun showProgressBarAddNote(visible: Boolean) {
        if (visible) {
            txtLayoutTitleNote.isEnabled = false
            txtLayoutBodyNote.isEnabled = false
            fbtnSaveNote.isEnabled = false
            progressBarAddNote.visibility = View.VISIBLE
        } else {

            txtLayoutTitleNote.isEnabled = true
            txtLayoutBodyNote.isEnabled = true
            fbtnSaveNote.isEnabled = true
            progressBarAddNote.visibility = View.GONE
        }
    }
}