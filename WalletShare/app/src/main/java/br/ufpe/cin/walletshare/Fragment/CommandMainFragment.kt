package br.ufpe.cin.walletshare.Fragment

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
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
import br.ufpe.cin.walletshare.Activity.CommandActivity
import br.ufpe.cin.walletshare.Activity.ItemActivity
import br.ufpe.cin.walletshare.Activity.ui.main.SectionsPagerAdapter

import br.ufpe.cin.walletshare.R
import br.ufpe.cin.walletshare.entity.Item
import br.ufpe.cin.walletshare.util.currencyFormatting
import br.ufpe.cin.walletshare.util.percent
import kotlinx.android.synthetic.main.fragment_command_main.*
import kotlinx.android.synthetic.main.item_command_main.view.*

class CommandMainFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_command_main, container, false)
    }

    override fun onResume() {
        super.onResume()

        command_main_recycler_view.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = ItemAdapter(context, CommandActivity.command.items)
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        }

        command_main_action.setOnClickListener {
            val intent = Intent(context, ItemActivity::class.java)
            startActivity(intent)
        }

        reloadPrices()
    }

    private fun reloadPrices() {
        val value = CommandActivity.command.items.map { it.price }.sum()
        command_main_total.text = value.currencyFormatting()
        command_main_info.text = "+10%, " + value.percent(0.1).currencyFormatting()
    }

    companion object Factory {
        fun newInstance(): CommandMainFragment = CommandMainFragment()
    }

    internal inner class ItemAdapter (
        var c: Context,
        var items: MutableList<Item>) :  RecyclerView.Adapter<ItemAdapter.ItemHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
            val view = LayoutInflater.from(c).inflate(R.layout.item_command_main, parent, false)
            return ItemHolder(view)
        }

        override fun onBindViewHolder(holder: ItemHolder, position: Int) {
            val item = items[position]
            holder.title.text = item.name
            holder.subtitle.text = item.price.currencyFormatting()
            holder.item_ = item
        }

        override fun getItemCount(): Int {
            return items.size
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        internal inner class ItemHolder(val item: View) : RecyclerView.ViewHolder(item) {
            val title: TextView = item.item_command_main_title
            val subtitle: TextView = item.item_command_main_subtitle
            private val button: Button = item.item_command_main_button
            lateinit var item_: Item

            init {
                item.setOnClickListener {
                    ItemActivity.item = item_
                    val intent = Intent(context, ItemActivity::class.java)
                    intent.putExtra("isNew", false)
                    startActivity(intent)
                }

                button.setOnClickListener {
                    deleteDialog(item_)
                }
            }
        }

        private fun deleteDialog(item: Item) {
            val builder = AlertDialog.Builder(context)
            builder.setTitle(R.string.title_delete)
            builder.setMessage(item.name)

            builder.setPositiveButton(android.R.string.ok) {dialog, _ ->
                CommandActivity.command.remove(item)
                command_main_recycler_view.adapter?.notifyDataSetChanged()
                reloadPrices()
                SectionsPagerAdapter.commandFriendsFragment.reload()
                dialog.dismiss()
            }

            builder.setNegativeButton(android.R.string.cancel) {dialog, _ ->
                dialog.cancel()
            }
            builder.show()
        }
    }
}
