package br.ufpe.cin.walletshare.Fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import br.ufpe.cin.walletshare.Activity.CommandActivity

import br.ufpe.cin.walletshare.R
import br.ufpe.cin.walletshare.entity.Friend
import br.ufpe.cin.walletshare.util.currencyFormatting
import br.ufpe.cin.walletshare.util.percent
import kotlinx.android.synthetic.main.fragment_command_friends.*
import kotlinx.android.synthetic.main.item_command_friends.view.*

class CommandFriendsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_command_friends, container, false)
    }

    override fun onResume() {
        super.onResume()

        command_friends_recycler_view.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = ItemAdapter(context, CommandActivity.command.people)
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        }
    }

    companion object Factory {
        fun newInstance(): CommandFriendsFragment = CommandFriendsFragment()
    }

    internal inner class ItemAdapter (
        var c: Context,
        var items: MutableList<Friend>) :  RecyclerView.Adapter<ItemAdapter.ItemHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
            val view = LayoutInflater.from(c).inflate(R.layout.item_command_friends, parent, false)
            return ItemHolder(view)
        }

        override fun onBindViewHolder(holder: ItemHolder, position: Int) {
            val item = items[position]
            holder.title.text = item.name

            val normal = CommandActivity.command.valueFor(item)
            val divided = CommandActivity.command.split()

            holder.normal1.text = normal.currencyFormatting()
            holder.normal2.text = "+10%, " + normal.percent(0.1).currencyFormatting()

            holder.divided1.text = divided.currencyFormatting()
            holder.divided2.text = "+10%, " + divided.percent(0.1).currencyFormatting()
        }

        override fun getItemCount(): Int {
            return items.size
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        internal inner class ItemHolder(val item: View) : RecyclerView.ViewHolder(item) {
            val title: TextView = item.item_command_friends_name
            val normal1: TextView = item.item_command_friends_normal_1
            val normal2: TextView = item.item_command_friends_normal_2
            val divided1: TextView = item.item_command_friends_divided_1
            val divided2: TextView = item.item_command_friends_divided_2

            init {
                item.setOnClickListener {
                    Toast.makeText(c, title.text, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
