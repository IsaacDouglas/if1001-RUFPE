package br.ufpe.cin.walletshare.Fragment


import android.app.AlertDialog
import android.arch.persistence.room.Room
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import br.ufpe.cin.walletshare.R
import br.ufpe.cin.walletshare.db.AppDatabase
import br.ufpe.cin.walletshare.db.FriendDao
import br.ufpe.cin.walletshare.entity.Friend
import kotlinx.android.synthetic.main.dialog_input_text.view.*
import kotlinx.android.synthetic.main.fragment_friends.*
import kotlinx.android.synthetic.main.item_friends.view.*

class FriendsFragment : Fragment() {

    private lateinit var friendDao: FriendDao

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        val database = Room.databaseBuilder(requireContext(), AppDatabase::class.java,"wallet-database")
            .allowMainThreadQueries()
            .build()
        friendDao = database.friendDao()
        items = ArrayList(friendDao.all())

        return inflater.inflate(R.layout.fragment_friends, container, false)
    }

    override fun onResume() {
        super.onResume()

        friends_recycler_view.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = ItemAdapter(context, items)
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        }

        friends_action.setOnClickListener {
            dialogInputText("NEW FRIEND") { name ->
                val friend = Friend()
                friend.name = name
                friendDao.add(friend)
                items.add(friend)
            }
        }
    }

    companion object Factory {
        fun newInstance(): FriendsFragment =
            FriendsFragment()
        var items: ArrayList<Friend> = ArrayList()
    }

    internal inner class ItemAdapter (
        var c: Context,
        var items: ArrayList<Friend>) :  RecyclerView.Adapter<ItemAdapter.ItemHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
            val view = LayoutInflater.from(c).inflate(R.layout.item_friends, parent, false)
            return ItemHolder(view)
        }

        override fun onBindViewHolder(holder: ItemHolder, position: Int) {
            val item = items[position]
            holder.title.text = item.name
            holder.friend = item
        }

        override fun getItemCount(): Int {
            return items.size
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        internal inner class ItemHolder(val item: View) : RecyclerView.ViewHolder(item) {
            val title: TextView = item.item_friends_title
            private val button: Button = item.item_friends_button
            var friend: Friend? = null

            init {
                item.setOnClickListener {
                    Toast.makeText(c, title.text, Toast.LENGTH_SHORT).show()
                }
                button.setOnClickListener {
                    items.remove(friend)
                    friendDao.remove(friend!!)
                    friends_recycler_view.adapter?.notifyDataSetChanged()
                }
            }
        }
    }

    private fun dialogInputText(title: String, complete: (String) -> Boolean) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)

        val view = layoutInflater.inflate(R.layout.dialog_input_text, null)
        val editText = view.dialog_edit_text

        builder.setView(view)

        builder.setPositiveButton(android.R.string.ok) { dialog, _ ->
            val text = editText.text
            if (text == null || text.isBlank()) {
                editText.error = "Error"
            }else{
                complete(text.toString())
                dialog.dismiss()
            }
        }

        builder.setNegativeButton(android.R.string.cancel) { dialog, _ ->
            dialog.cancel()
        }
        builder.show()
    }

}
