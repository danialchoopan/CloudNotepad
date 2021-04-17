package ir.danialchoopan.cloudnotepad.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import ir.danialchoopan.cloudnotepad.R
import ir.danialchoopan.cloudnotepad.api.model.UserNote
import ir.danialchoopan.cloudnotepad.api.request.NoteRequests

class RecyclerViewAdapterUserPrivateNotes(
    val context: Context,
    val onClickItem: (item: UserNote) -> Unit
) :
    RecyclerView.Adapter<RecyclerViewAdapterUserPrivateNotes.ViewHolder>() {
    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val txwTitle = view.findViewById<TextView>(R.id.txwTitleNote)
        val txwBody = view.findViewById<TextView>(R.id.txwBodyNote)
        val txwStuats = view.findViewById<TextView>(R.id.txwStatusNote)
    }

    val sharedPreferencesSetting = context.getSharedPreferences("setting", Context.MODE_PRIVATE)
    var list = ArrayList<UserNote>()

    fun setData(list: ArrayList<UserNote>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.row_recycler_view_private_notes, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val listPosition = list[position]
        holder.txwStuats.textSize = sharedPreferencesSetting.getFloat("STATUS_TEXT_SIZE", 12f)
        holder.txwBody.textSize = sharedPreferencesSetting.getFloat("BODY_TEXT_SIZE", 14f)
        holder.txwTitle.textSize = sharedPreferencesSetting.getFloat("BODY_TEXT_SIZE", 18f)
        holder.txwTitle.text = listPosition.title
        if (listPosition.body.length > 20) {
            holder.txwBody.text = listPosition.body.substring(0, 20)
        } else {
            holder.txwBody.text = listPosition.body
        }
        if (listPosition.public) {
            holder.txwStuats.text = "عمومی"
        } else {
            holder.txwStuats.text = ""
        }
        holder.view.setOnClickListener {
            onClickItem(listPosition)
        }
        holder.view.setOnLongClickListener {
            //popup menu for items
            val noteRequests = NoteRequests(context)
            val popupMenu = PopupMenu(context, holder.view)
            popupMenu.inflate(R.menu.menu_recycler_view_notes)
            popupMenu.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.menu_delete_item -> {
                        noteRequests.deleteNote(listPosition.id) { requestResult ->
                            if (requestResult) {
                                Toast.makeText(
                                    context,
                                    "نوشته شما با حذف گردید",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                Toast.makeText(
                                    context,
                                    "مشکلی پیش آمده است لطفا دوباره امتحان کنید",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                        notifyItemRemoved(position)
                    }
                    R.id.menu_change_status -> {
                        noteRequests.changeNoteStatus(
                            listPosition.id,
                            !listPosition.public
                        ) { requestResult ->
                            if (requestResult) {
                                Toast.makeText(
                                    context,
                                    "نوشته شما عمومی/ خصوصی شد",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                Toast.makeText(
                                    context,
                                    "مشکلی پیش آمده است لطفا دوباره امتحان کنید",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                        notifyDataSetChanged()
                    }
                }
                false
            }
            popupMenu.show()
            false
        }

    }

    override fun getItemCount(): Int = list.size
}

