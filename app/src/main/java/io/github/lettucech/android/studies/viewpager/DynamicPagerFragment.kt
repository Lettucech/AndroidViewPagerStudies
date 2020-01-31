package io.github.lettucech.android.studies.viewpager

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.setPadding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.fragment_dynamic_pager.*
import kotlinx.android.synthetic.main.layout_dynamic_pager_setting_sheet.*
import kotlinx.android.synthetic.main.view_dynamic_pager_page.view.*

/**
 * Created by Brian Ho on 2020-01-22.
 */
class DynamicPagerFragment : Fragment() {
    private lateinit var mViewModel: DynamicPagerViewModel
    private val mPagerAdapter = PagerAdapter()
    private lateinit var bottomSheetBehavior : BottomSheetBehavior<ConstraintLayout>

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mViewModel = ViewModelProviders.of(this).get(DynamicPagerViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dynamic_pager, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewPager.adapter = mPagerAdapter

        mViewModel.getPageTags().observe(viewLifecycleOwner, Observer { pageTags ->
            btn_remove_page.isEnabled = pageTags.size > 0
            mPagerAdapter.setPageTags(pageTags)
        })

        bottomSheetBehavior = BottomSheetBehavior.from(layout_setting_sheet)

        layout_sheet_handle.setOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }

        btn_add_page.setOnClickListener {
            mViewModel.addPageTag(if (mPagerAdapter.itemCount == 0) 0 else viewPager.currentItem + 1)
        }

        btn_remove_page.setOnClickListener {
            mViewModel.removePageTag(viewPager.currentItem)
        }

        textView_paddings_value.text =
            (viewPager.paddingStart / resources.displayMetrics.density).toInt().toString()
        seekBar_paddings.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                textView_paddings_value.text = progress.toString()
                viewPager.setPadding((resources.displayMetrics.density * progress).toInt())
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

        })

        checkBox_clip_to_padding.isChecked = viewPager.clipToPadding
        checkBox_clip_to_padding.setOnCheckedChangeListener { _, b ->
            viewPager.clipToPadding = b
        }

        checkBox_clip_children.isChecked = viewPager.clipChildren
        checkBox_clip_children.setOnCheckedChangeListener { _, b ->
            viewPager.clipChildren = b
        }

        textView_offscreen_page_limit_value.text = viewPager.offscreenPageLimit.toString()
        seekBar_offscreen_page_limit.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (progress == 0) {
                    textView_offscreen_page_limit_value.text = ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT.toString()
                    viewPager.offscreenPageLimit = ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT
                } else {
                    textView_offscreen_page_limit_value.text = progress.toString()
                    viewPager.offscreenPageLimit = progress
                }
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }
        })

        textView_page_spacing_value.text = "0"
        seekBar_page_spacing.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                textView_page_spacing_value.text = progress.toString()
                viewPager.setPageTransformer(MarginPageTransformer((resources.displayMetrics.density * progress).toInt()))
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }
        })
    }

    inner class PagerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        private val mPageTags = ArrayList<String>()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.view_dynamic_pager_page, parent, false)
            return object : RecyclerView.ViewHolder(itemView) {}
        }

        override fun getItemCount(): Int {
            return mPageTags.size
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            holder.itemView.apply {
                textView.text = "Page Tag ${mPageTags[position]}"
            }
        }

        fun setPageTags(pageTags: List<String>) {
            PageTagsDiffCallback(mPageTags, pageTags).let {
                DiffUtil.calculateDiff(it).dispatchUpdatesTo(this)
            }

            mPageTags.clear()
            mPageTags.addAll(pageTags)
        }
    }

    inner class PageTagsDiffCallback(
        private val mOldList: List<String>,
        private val mNewList: List<String>
    ) : DiffUtil.Callback() {

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return mOldList[oldItemPosition] == mNewList[newItemPosition]
        }

        override fun getOldListSize(): Int {
            return mOldList.size
        }

        override fun getNewListSize(): Int {
            return mNewList.size
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return mOldList[oldItemPosition] == mNewList[newItemPosition]
        }
    }
}