package io.github.lettucech.android.studies.viewpager

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_tab_layout_integration.*
import kotlinx.android.synthetic.main.view_tab.view.*

/**
 * Created by Brian Ho on 2020-01-22.
 */
class TabLayoutFragment : Fragment() {

    private val mTabTitles = arrayOf("Horizontal Pager", "Vertical Pager")

    private val mPagerAdapter = object : FragmentStateAdapter(this) {
        override fun getItemCount(): Int {
            return 2
        }

        override fun createFragment(position: Int): Fragment {
            return SimpleStaticPagerFragment()
        }
    }

    private lateinit var mOnPageChangeCallback: ViewPager2.OnPageChangeCallback

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tab_layout_integration, container, false)

        viewPager.adapter = mPagerAdapter
        mOnPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                for (i in 0 until mPagerAdapter.itemCount) {
                    (tabLayout.getTabAt(i)?.customView as? TabView)?.apply {
                        if (i == position) {
                            textView_title.setTextColor(
                                ContextCompat.getColor(
                                    context,
                                    R.color.colorPrimary
                                )
                            )
                            imageView_icon.setColorFilter(
                                ContextCompat.getColor(
                                    context,
                                    R.color.colorPrimary
                                )
                            )
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
            tab.customView = TabView(context).also {
                it.textView_title.text = mTabTitles[position]
                it.textView_title.setTextColor(Color.GRAY)
                it.imageView_icon.setImageResource(R.drawable.ic_view_carousel_black_24dp)
                it.imageView_icon.setColorFilter(Color.GRAY)

                if (position == 1) {
                    it.imageView_icon.rotation = 90f
                }
            }
        }.attach()

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewPager.unregisterOnPageChangeCallback(mOnPageChangeCallback)
    }
}