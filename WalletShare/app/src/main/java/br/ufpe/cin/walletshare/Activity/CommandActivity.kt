package br.ufpe.cin.walletshare.Activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import br.ufpe.cin.walletshare.Activity.ui.main.SectionsPagerAdapter
import br.ufpe.cin.walletshare.R
import br.ufpe.cin.walletshare.entity.Command
import br.ufpe.cin.walletshare.util.Data
import kotlinx.android.synthetic.main.activity_command.*

class CommandActivity : AppCompatActivity() {

    private var isDelete = false

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
        }
    }

    override fun onPause() {
        super.onPause()
        if (!isDelete) {
            command.id = Data.getInstance(baseContext).commandDao.insert(command)
        }
    }

    companion object Factory {
        var command: Command = Command()
    }

    @SuppressLint("ResourceType")
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId

        if (id == R.id.action_delete) {
            deleteDialog()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(android.R.string.dialog_alert_title)
        builder.setMessage(getString(R.string.delete_order_sheet))

        builder.setPositiveButton(android.R.string.ok) {dialog, _ ->
            Data.getInstance(baseContext).commandDao.remove(command)
            isDelete = true
            finish()
            dialog.dismiss()
        }

        builder.setNegativeButton(android.R.string.cancel) {dialog, _ ->
            dialog.cancel()
        }
        builder.show()
    }
}