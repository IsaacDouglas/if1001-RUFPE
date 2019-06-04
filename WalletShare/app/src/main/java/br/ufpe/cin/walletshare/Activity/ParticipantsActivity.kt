package br.ufpe.cin.walletshare.Activity

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import br.ufpe.cin.walletshare.R
import br.ufpe.cin.walletshare.entity.Friend
import br.ufpe.cin.walletshare.util.Data

import kotlinx.android.synthetic.main.activity_participants.*
import kotlinx.android.synthetic.main.item_participants.view.*

class ParticipantsActivity : AppCompatActivity() {

    var selected: MutableList<Boolean> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_participants)
        setSupportActionBar(toolbar)

        participants_action.hide()

        val list = Data.getInstance(applicationContext).friendDao.all().toMutableList()

        list.forEach { _ ->
            selected.add(false)
        }

        participants_recycler_view.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = ItemAdapter(context, list)
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        }

        participants_action.setOnClickListener {
            Toast.makeText(applicationContext, "Action", Toast.LENGTH_SHORT).show()
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

                if (selected.filter { it }.isEmpty()) {
                    participants_action.hide()
                } else {
                    participants_action.show()
                }
            }
        }
    }

}
