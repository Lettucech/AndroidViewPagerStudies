package io.github.lettucech.android.studies.viewpager

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.resources.TextAppearance
import kotlinx.android.synthetic.main.fragment_simple_static_pager.*

/**
 * Created by Brian Ho on 2020-01-17.
 */
class SimpleStaticPagerFragment : Fragment() {

    private val mPages = arrayOf(
        R.color.rainbow_red,
        R.color.rainbow_orange,
        R.color.rainbow_yellow,
        R.color.rainbow_green,
        R.color.rainbow_cyan,
        R.color.rainbow_blue,
        R.color.rainbow_purple
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_simple_static_pager, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewPager.adapter = object : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
            override fun onCreateViewHolder(
                parent: ViewGroup,
                viewType: Int
            ): RecyclerView.ViewHolder {
                val textView = TextView(parent.context).also {
                    it.layoutParams = RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.MATCH_PARENT)
                    it.gravity = Gravity.CENTER
                    it.textSize = 24f
                }
                return object : RecyclerView.ViewHolder(textView) {}
            }

            override fun getItemCount(): Int = mPages.size

            override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
                (holder.itemView as TextView).apply {
                    text = "Page ${position + 1}"
                    setBackgroundResource(mPages[position])
                }
            }
        }
    }
}