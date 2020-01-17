package io.github.lettucech.android.studies.viewpager

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.view_tab.view.*

class MainActivity : AppCompatActivity() {

    private val mTabTitles = arrayOf("Vertical Pager", "Horizontal Pager")

    private val mPagerAdapter = object : FragmentStateAdapter(this) {
        override fun getItemCount(): Int {
            return 2
        }

        override fun createFragment(position: Int): Fragment {
            return PromotionBannersFragment()
        }
    }

    private lateinit var mOnPageChangeCallback: ViewPager2.OnPageChangeCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewPager.adapter = mPagerAdapter
        mOnPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                for (i in 0 until mPagerAdapter.itemCount) {
                    (tabLayout.getTabAt(i)?.customView as? TabView)?.apply {
                        if (i == position) {
                            textView_title.setTextColor(getColor(R.color.colorPrimary))
                            imageView_icon.setColorFilter(getColor(R.color.colorPrimary))
                        } else {
                            textView_title.setTextColor(Color.GRAY)
                            imageView_icon.setColorFilter(Color.GRAY)
                        }
                    }
                }
            }
        }
        viewPager.registerOnPageChangeCallback(mOnPageChangeCallback)

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.customView = TabView(this).also {
                it.textView_title.text = mTabTitles[position]
                it.textView_title.setTextColor(Color.GRAY)
                it.imageView_icon.setImageResource(R.drawable.ic_view_carousel_black_24dp)
                it.imageView_icon.setColorFilter(Color.GRAY)

                if (position == 1) {
                    it.imageView_icon.rotation = 90f
                }
            }
        }.attach()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewPager.unregisterOnPageChangeCallback(mOnPageChangeCallback)
    }
}
