package kantaoke.oru.com.kantaoke.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import kantaoke.oru.com.kantaoke.fragments.DrawFragment
import kantaoke.oru.com.kantaoke.fragments.SongListFragment

/**
 * Created by Oru on 19/01/2018.
 */

class CustomPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private val tabTitles = arrayOf("Estrazione", "Lista Canzoni")

    override fun getItem(position: Int): Fragment {
        val fragmentClass: Class<*> = when (position) {
            0 -> DrawFragment::class.java
            1 -> SongListFragment::class.java
            else -> DrawFragment::class.java
        }
        return fragmentClass.newInstance() as Fragment
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence {
        return tabTitles[position]
    }
}