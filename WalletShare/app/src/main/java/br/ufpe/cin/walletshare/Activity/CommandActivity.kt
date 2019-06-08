package br.ufpe.cin.walletshare.Activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import br.ufpe.cin.walletshare.Activity.ui.main.SectionsPagerAdapter
import br.ufpe.cin.walletshare.R
import br.ufpe.cin.walletshare.entity.Command
import br.ufpe.cin.walletshare.entity.Item
import kotlinx.android.synthetic.main.activity_command.*
import java.util.*

class CommandActivity : AppCompatActivity() {

    lateinit var command: Command

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_command)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        view_pager.adapter = sectionsPagerAdapter
        tabs.setupWithViewPager(view_pager)

        val tt = Item()
        tt.name = "Frango frito"
        tt.price = 100.0

        val tt2 = Item()
        tt2.name = "Boi na brasa"
        tt2.price = 70.0


        var command = Command()
        command.people = ParticipantsActivity.people
        command.date = Date()
        command.items = mutableListOf(tt, tt2)

        CommandActivity.command = command
    }

    companion object Factory {
        var command: Command = Command()
    }
}