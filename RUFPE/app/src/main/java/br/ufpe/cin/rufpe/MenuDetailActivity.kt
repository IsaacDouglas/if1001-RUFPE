package br.ufpe.cin.rufpe

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_menu_detail.*
import kotlinx.android.synthetic.main.item_favorites.view.*
import kotlinx.android.synthetic.main.item_menu_detail.view.*

class MenuDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_detail)
    }

    override fun onResume() {
        super.onResume()

        menu_detail_recycler_view.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = ItemAdapter(context, meal)
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        }
    }

    companion object Factory {
        var meal = arrayOf("ONE", "TWO")
    }

    internal inner class ItemAdapter (
        var c: Context,
        var items: Array<String>) :  RecyclerView.Adapter<ItemAdapter.ItemHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
            val view = LayoutInflater.from(c).inflate(R.layout.item_menu_detail, parent, false)
            return ItemHolder(view)
        }

        override fun onBindViewHolder(holder: ItemHolder, position: Int) {
            val item = items[position]
            holder.name.text = item
        }

        override fun getItemCount(): Int {
            return items.size
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        internal inner class ItemHolder(val item: View) : RecyclerView.ViewHolder(item) {
            val name: TextView = item.item_menu_detail_title

            init {
                item.setOnClickListener { _ ->
                    Toast.makeText(c, name.text, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
