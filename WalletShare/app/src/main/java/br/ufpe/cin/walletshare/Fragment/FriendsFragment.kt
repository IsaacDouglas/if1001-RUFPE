package br.ufpe.cin.walletshare.Fragment


import android.app.AlertDialog
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
import br.ufpe.cin.walletshare.entity.Friend
import br.ufpe.cin.walletshare.util.Data
import kotlinx.android.synthetic.main.dialog_input_text.view.*
import kotlinx.android.synthetic.main.fragment_friends.*
import kotlinx.android.synthetic.main.item_friends.view.*

class FriendsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_friends, container, false)
    }

    override fun onResume() {
        super.onResume()

        friends_recycler_view.apply {
            layoutManager = LinearLayoutManager(context)
            val list = Data.getInstance(context).friendDao.all().toMutableList()
            adapter = ItemAdapter(context, list)
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        }

        friends_action.setOnClickListener {
            dialogInputText("New friend") { name ->
                val friend = Friend()
                friend.name = name
                Data.getInstance(requireContext()).friendDao.add(friend)
            }
        }
    }

    companion object Factory {
        fun newInstance(): FriendsFragment = FriendsFragment()
    }

    internal inner class ItemAdapter (
        var c: Context,
        var items: MutableList<Friend>) :  RecyclerView.Adapter<ItemAdapter.ItemHolder>() {

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
                    Data.getInstance(c).friendDao.remove(friend!!)
                    friends_recycler_view.adapter?.notifyDataSetChanged()
                }
            }
        }
    }

    private fun dialogInputText(title: String, complete: (String) -> Unit) {
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
