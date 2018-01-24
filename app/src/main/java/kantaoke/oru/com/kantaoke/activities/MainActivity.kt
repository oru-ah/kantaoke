package kantaoke.oru.com.kantaoke.activities

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import kantaoke.oru.com.kantaoke.R

/**
 * Created by Oru on 19/01/2018.
 */
class MainActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        val viewPager = findViewById(R.id.pager) as ViewPager
        val pagerAdapter = kantaoke.oru.com.kantaoke.adapters.CustomPagerAdapter(supportFragmentManager)
        viewPager.adapter = pagerAdapter
        val tabLayout = findViewById(R.id.tab_layout) as TabLayout
        tabLayout.setupWithViewPager(viewPager)
    }
}