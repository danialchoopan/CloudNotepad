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
import ir.danialchoopan.cloudnotepad.R
import ir.danialchoopan.cloudnotepad.ReadPublicNoteActivity
import ir.danialchoopan.cloudnotepad.adapter.RecyclerViewAdapterUserPrivateNotes
import ir.danialchoopan.cloudnotepad.api.request.NoteRequests

class PublicNotesFragment : Fragment() {
    lateinit var refreshRecyclerViewUserPublicNotes: SwipeRefreshLayout
    lateinit var txwRecyclerEmptyPublic: TextView
    lateinit var recyclerViewAdapterUserPrivateNotes: RecyclerViewAdapterUserPrivateNotes
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_public_notes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerViewUserPublicNotes =
            view.findViewById<RecyclerView>(R.id.recyclerViewUserPublicNotes)
        refreshRecyclerViewUserPublicNotes =
            view.findViewById(R.id.refreshRecyclerViewUserPublicNotes)
        txwRecyclerEmptyPublic = view.findViewById(R.id.txwRecyclerEmptyPublic)

        recyclerViewUserPublicNotes.layoutManager = LinearLayoutManager(context)

        recyclerViewAdapterUserPrivateNotes =
            context?.let { notNullContext ->
                RecyclerViewAdapterUserPrivateNotes(notNullContext) { user_note ->
                    //on item click
                    Intent(context, ReadPublicNoteActivity::class.java).also { intent_obj ->
                        intent_obj.putExtra("USER_NOTE_ID", user_note.id)
                        intent_obj.putExtra("USER_NOTE_TITLE", user_note.title)
                        intent_obj.putExtra("USER_NOTE_BODY", user_note.body)
                        intent_obj.putExtra("USER_NOTE_CREATED_AT", user_note.created_at)
                        startActivity(intent_obj)
                    }
                }
            }!!

        recyclerViewUserPublicNotes.adapter = recyclerViewAdapterUserPrivateNotes
        refreshRecyclerViewUserPublicNotes.setOnRefreshListener {
            getPublicNotes(recyclerViewAdapterUserPrivateNotes)
        }

        //get public notes
        getPublicNotes(recyclerViewAdapterUserPrivateNotes)

    }

    override fun onResume() {
        super.onResume()
        //get public notes
        getPublicNotes(recyclerViewAdapterUserPrivateNotes)
    }

    fun getPublicNotes(recyclerViewAdapterUserPrivateNotes: RecyclerViewAdapterUserPrivateNotes) {
        refreshRecyclerViewUserPublicNotes.isRefreshing = true
        context?.let { notNullContent ->
            NoteRequests(notNullContent).publicNotes { resultUserNotes, requestResult ->
                if (resultUserNotes.size == 0) {
                    txwRecyclerEmptyPublic.visibility = View.VISIBLE
                } else {
                    txwRecyclerEmptyPublic.visibility = View.GONE
                }
                if (requestResult) {
                    recyclerViewAdapterUserPrivateNotes.setData(resultUserNotes)
                } else {
                    Toast.makeText(
                        context,
                        "مشکلی پیش آمده است لطفا بعدا امتحان کنید",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                refreshRecyclerViewUserPublicNotes.isRefreshing = false
            }
        }
    }

}