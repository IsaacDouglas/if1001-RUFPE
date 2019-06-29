package br.ufpe.cin.walletshare.Activity.ui.main

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import br.ufpe.cin.walletshare.Fragment.CommandFriendsFragment
import br.ufpe.cin.walletshare.Fragment.CommandMainFragment
import br.ufpe.cin.walletshare.R

private val TAB_TITLES = arrayOf(
    R.string.title_tab_text_1,
    R.string.title_tab_text_2
)

class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).

        if (position == 1) {
            val commandFriendsFragment = CommandFriendsFragment.newInstance()
            Factory.commandFriendsFragment = commandFriendsFragment
            return commandFriendsFragment
        }else{
            val commandMainFragment = CommandMainFragment.newInstance()
            Factory.commandMainFragment = commandMainFragment
            return commandMainFragment
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
        lateinit var commandFriendsFragment :CommandFriendsFragment
        lateinit var commandMainFragment: CommandMainFragment
    }
}