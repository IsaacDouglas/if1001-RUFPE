package br.ufpe.cin.walletshare.Activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import br.ufpe.cin.walletshare.Activity.ui.main.SectionsPagerAdapter
import br.ufpe.cin.walletshare.Fragment.HistoricFragment
import br.ufpe.cin.walletshare.R
import br.ufpe.cin.walletshare.entity.Command
import kotlinx.android.synthetic.main.activity_command.*

class CommandActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_command)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        view_pager.adapter = sectionsPagerAdapter
        tabs.setupWithViewPager(view_pager)

        val isNew = intent.getBooleanExtra ("isNew", true)

        if (isNew) {
            var command = Command()
            command.people = ParticipantsActivity.people

            CommandActivity.command = command
            HistoricFragment.commands.add(command)
        }
    }

    companion object Factory {
        var command: Command = Command()
    }
}