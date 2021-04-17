package ir.danialchoopan.cloudnotepad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView

class ReadPublicNoteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read_public_note)

        //get intent extras from home activity
        val note_id = intent.extras?.getInt("USER_NOTE_ID")
        val note_title = intent.extras?.getString("USER_NOTE_TITLE")
        val note_body = intent.extras?.getString("USER_NOTE_BODY")
        val note_create_at = intent.extras?.getString("USER_NOTE_CREATED_AT")

        //init cast views
        val txwTitle = findViewById<TextView>(R.id.txwTitlePublicNote)
        val txwBody = findViewById<TextView>(R.id.txwBodyPublicNote)
        val txwDate = findViewById<TextView>(R.id.txwDatePublicNote)
        val imgBackArrow = findViewById<ImageButton>(R.id.btnImgBackArrow)
        imgBackArrow.setOnClickListener {
            Intent(this@ReadPublicNoteActivity, HomeActivity::class.java).also { intent ->
                intent.putExtra("MENU", "PUBLIC_NOTES_MENU")
                startActivity(intent)
                finish()
            }
        }
        //set text textViews to intent extras from home activity
        txwTitle.text = note_title
        txwBody.text = note_body
        txwDate.text = note_create_at

    }
}