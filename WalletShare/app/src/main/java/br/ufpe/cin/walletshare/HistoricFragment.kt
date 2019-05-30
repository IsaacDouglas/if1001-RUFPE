package br.ufpe.cin.walletshare


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
import kotlinx.android.synthetic.main.fragment_historic.*
import kotlinx.android.synthetic.main.item_historic.view.*

class HistoricFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_historic, container, false)
    }

    override fun onResume() {
        super.onResume()

        historic_recycler_view.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = ItemAdapter(context, items)
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        }

        historic_action.setOnClickListener {
            Toast.makeText(context, "Action", Toast.LENGTH_SHORT).show()
        }
    }

    companion object Factory {
        fun newInstance(): HistoricFragment = HistoricFragment()
        var items = arrayOf("ONE", "TWO")
    }

    internal inner class ItemAdapter (
        var c: Context,
        var items: Array<String>) :  RecyclerView.Adapter<ItemAdapter.ItemHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
            val view = LayoutInflater.from(c).inflate(R.layout.item_historic, parent, false)
            return ItemHolder(view)
        }

        override fun onBindViewHolder(holder: ItemHolder, position: Int) {
            val item = items[position]
            holder.title.text = item
        }

        override fun getItemCount(): Int {
            return items.size
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        internal inner class ItemHolder(val item: View) : RecyclerView.ViewHolder(item) {
            val title: TextView = item.item_historic_title

            init {
                item.setOnClickListener { _ ->
                    Toast.makeText(c, title.text, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
