package br.ufpe.cin.walletshare.Activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import br.ufpe.cin.walletshare.Activity.ui.main.SectionsPagerAdapter
import br.ufpe.cin.walletshare.R
import br.ufpe.cin.walletshare.entity.OrderSheet
import br.ufpe.cin.walletshare.util.Data
import kotlinx.android.synthetic.main.activity_order_sheet.*

class OrderSheetActivity : AppCompatActivity() {

    private var isDelete = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_sheet)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        view_pager.adapter = sectionsPagerAdapter
        tabs.setupWithViewPager(view_pager)

        val isNew = intent.getBooleanExtra ("isNew", true)

        if (isNew) {
            var orderSheet = OrderSheet()
            orderSheet.people = ParticipantsActivity.people
            OrderSheetActivity.orderSheet = orderSheet
        }
    }

    override fun onPause() {
        super.onPause()
        if (!isDelete) {
            orderSheet.id = Data.getInstance(baseContext).orderSheetDao.insert(orderSheet)
        }
    }

    companion object Factory {
        var orderSheet: OrderSheet = OrderSheet()
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
            Data.getInstance(baseContext).orderSheetDao.remove(orderSheet)
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