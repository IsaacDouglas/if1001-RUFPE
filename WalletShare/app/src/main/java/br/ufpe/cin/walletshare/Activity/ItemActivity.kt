package br.ufpe.cin.walletshare.Activity

import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import br.ufpe.cin.walletshare.R
import br.ufpe.cin.walletshare.entity.Friend
import br.ufpe.cin.walletshare.entity.Item

import kotlinx.android.synthetic.main.activity_item.*
import kotlinx.android.synthetic.main.activity_item.toolbar
import kotlinx.android.synthetic.main.activity_participants.*
import kotlinx.android.synthetic.main.item_participants.view.*

class ItemActivity : AppCompatActivity() {

    var selected: MutableList<Boolean> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item)
        setSupportActionBar(toolbar)

        CommandActivity.command.people.forEach { _ ->
            selected.add(false)
        }

        item_recycler_view.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = ItemAdapter(context, CommandActivity.command.people)
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        }

        item_action.setOnClickListener {

            var selectedFriend: MutableList<Friend> = mutableListOf()
            for (i in 0 until selected.count()) {
                if (selected[i]) {
                    selectedFriend.add(CommandActivity.command.people[i])
                }
            }

            val name = item_name_edit_text.text.toString()
            val price = item_price_edit_text.text.toString()

            if (selectedFriend.isEmpty()) {
                Snackbar.make(it, "Selecione no minimo um amigo", Snackbar.LENGTH_LONG).show()
            }else if (name.isEmpty()) {
                Snackbar.make(it, "Preencha o nome do item", Snackbar.LENGTH_LONG).show()
            }else{
                val item = Item()
                item.price = 20.0
                item.name = name
                item.people = selectedFriend
                CommandActivity.command.items.add(item)

                finish()
            }
        }
    }

    internal inner class ItemAdapter (
        var c: Context,
        var items: MutableList<Friend>) :  RecyclerView.Adapter<ItemAdapter.ItemHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
            val view = LayoutInflater.from(c).inflate(R.layout.item_participants, parent, false)
            return ItemHolder(view)
        }

        override fun onBindViewHolder(holder: ItemHolder, position: Int) {
            val item = items[position]
            holder.title.text = item.name
            holder.index = position
        }

        override fun getItemCount(): Int {
            return items.size
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        internal inner class ItemHolder(val item: View) : RecyclerView.ViewHolder(item) {
            val title: TextView = item.item_participants_title
            private val checkBox: CheckBox = item.item_participants_check_box
            var index: Int = 0

            init {
                item.setOnClickListener {
                    checkBox.isChecked = checkBox.isChecked.not()
                    buttonAction()
                }

                checkBox.setOnClickListener {
                    buttonAction()
                }
            }

            private fun buttonAction() {
                selected.set(index, checkBox.isChecked)
            }
        }
    }

}
