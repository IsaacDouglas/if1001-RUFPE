package br.ufpe.cin.walletshare.Activity.ui.main

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import br.ufpe.cin.walletshare.Fragment.OrderSheetFriendsFragment
import br.ufpe.cin.walletshare.Fragment.OrderSheetMainFragment
import br.ufpe.cin.walletshare.R

private val TAB_TITLES = arrayOf(
    R.string.order_sheet,
    R.string.friends
)

class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).

        if (position == 1) {
            val orderSheetFriendsFragment = OrderSheetFriendsFragment.newInstance()
            Factory.orderSheetFriendsFragment = orderSheetFriendsFragment
            return orderSheetFriendsFragment
        }else{
            val orderSheetMainFragment = OrderSheetMainFragment.newInstance()
            Factory.orderSheetMainFragment = orderSheetMainFragment
            return orderSheetMainFragment
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        // Show 2 total pages.
        return 2
    }

    companion object Factory {
        lateinit var orderSheetFriendsFragment :OrderSheetFriendsFragment
        lateinit var orderSheetMainFragment: OrderSheetMainFragment
    }
}