package br.ufpe.cin.walletshare.Fragment

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
import android.widget.TextView
import br.ufpe.cin.walletshare.Activity.CommandActivity
import br.ufpe.cin.walletshare.Activity.ParticipantsActivity
import br.ufpe.cin.walletshare.R
import br.ufpe.cin.walletshare.entity.Command
import br.ufpe.cin.walletshare.util.Data
import br.ufpe.cin.walletshare.util.currencyFormatting
import br.ufpe.cin.walletshare.util.toSimpleString
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
            val list = Data.getInstance(context).commandDao.all().toMutableList()
            adapter = ItemAdapter(context, list)
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        }

        historic_action.setOnClickListener {
            val intent = Intent(context, ParticipantsActivity::class.java)
            startActivity(intent)
        }
    }

    companion object Factory {
        fun newInstance(): HistoricFragment = HistoricFragment()
    }

    internal inner class ItemAdapter (
        var c: Context,
        var items: MutableList<Command>) :  RecyclerView.Adapter<ItemAdapter.ItemHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
            val view = LayoutInflater.from(c).inflate(R.layout.item_historic, parent, false)
            return ItemHolder(view)
        }

        override fun onBindViewHolder(holder: ItemHolder, position: Int) {
            val item = items[position]
            holder.date.text = item.date.toSimpleString()
            holder.participants.text = item.people.map { it.name }.joinToString { it }
            holder.price.text = item.total().currencyFormatting()
            holder.command = items.get(position)
        }

        override fun getItemCount(): Int {
            return items.size
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        internal inner class ItemHolder(val item: View) : RecyclerView.ViewHolder(item) {
            val date: TextView = item.item_historic_date
            val participants: TextView = item.item_historic_participants
            val price: TextView = item.item_historic_price
            lateinit var command: Command

            init {
                item.setOnClickListener {
                    CommandActivity.command = command
                    val intent = Intent(c, CommandActivity::class.java)
                    intent.putExtra("isNew", false)
                    startActivity(intent)
                }
            }
        }
    }
}
