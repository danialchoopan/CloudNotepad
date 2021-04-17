package ir.danialchoopan.cloudnotepad.fragment.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ir.danialchoopan.cloudnotepad.AddNoteActivity
import ir.danialchoopan.cloudnotepad.EditUserNoteActivity
import ir.danialchoopan.cloudnotepad.R
import ir.danialchoopan.cloudnotepad.adapter.RecyclerViewAdapterUserPrivateNotes
import ir.danialchoopan.cloudnotepad.api.model.UserNote
import ir.danialchoopan.cloudnotepad.api.request.NoteRequests

class PrivateNotesFragment : Fragment() {
    lateinit var refreshRecyclerViewUserPrivateNotes: SwipeRefreshLayout
    lateinit var txwRecyclerEmptyPrivate: TextView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_private_notes, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerViewUserPrivateNotes =
            view.findViewById<RecyclerView>(R.id.recyclerViewUserPrivateNotes)
        val fbtnAddNote = view.findViewById<FloatingActionButton>(R.id.fbtnAddNote)
        txwRecyclerEmptyPrivate = view.findViewById(R.id.txwRecyclerEmptyPrivate)
        //open add note activity
        fbtnAddNote.setOnClickListener {
            Intent(context, AddNoteActivity::class.java).also { intent ->
                startActivity(intent)
            }
        }


        refreshRecyclerViewUserPrivateNotes =
            view.findViewById(R.id.refreshRecyclerViewUserPrivateNotes)
        val recyclerViewAdapterUserPrivateNotes =
            context?.let { notNullContext ->
                RecyclerViewAdapterUserPrivateNotes(notNullContext) { userNote ->
                    Intent(context, EditUserNoteActivity::class.java).also { intent_obj ->
                        intent_obj.putExtra("USER_NOTE_ID", userNote.id)
                        intent_obj.putExtra("USER_NOTE_TITLE", userNote.title)
                        intent_obj.putExtra("USER_NOTE_BODY", userNote.body)
                        intent_obj.putExtra("USER_NOTE_CREATED_AT", userNote.created_at)
                        startActivity(intent_obj)
                    }
                }
            }
        //setup recyclerView
        recyclerViewUserPrivateNotes.layoutManager = LinearLayoutManager(context)
        recyclerViewUserPrivateNotes.adapter = recyclerViewAdapterUserPrivateNotes

        //on swipe refresh listener
        refreshRecyclerViewUserPrivateNotes.setOnRefreshListener {
            if (recyclerViewAdapterUserPrivateNotes != null) {
                getPrivateNotes(recyclerViewAdapterUserPrivateNotes)
            }
        }


        //get private notes
        if (recyclerViewAdapterUserPrivateNotes != null) {
            getPrivateNotes(recyclerViewAdapterUserPrivateNotes)
        }

    }


    fun getPrivateNotes(recyclerViewAdapterUserPrivateNotes: RecyclerViewAdapterUserPrivateNotes) {
        refreshRecyclerViewUserPrivateNotes.isRefreshing = true
        context?.let { notNullContext ->
            NoteRequests(notNullContext).userNotes { resultUserNotes, requestResult ->
                if (resultUserNotes.size == 0) {
                    txwRecyclerEmptyPrivate.visibility = View.VISIBLE
                } else {
                    txwRecyclerEmptyPrivate.visibility = View.GONE
                }
                if (requestResult) {
                    recyclerViewAdapterUserPrivateNotes.setData(resultUserNotes)
                } else {
                    Toast.makeText(
                        context,
                        "مشکلی پش آمده است لطفا دوباره امتحان کنید",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                refreshRecyclerViewUserPrivateNotes.isRefreshing = false
            }
        }
    }
}