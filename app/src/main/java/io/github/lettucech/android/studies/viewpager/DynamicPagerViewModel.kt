package io.github.lettucech.android.studies.viewpager

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.random.Random

/**
 * Created by Brian Ho on 2020-01-23.
 */
class DynamicPagerViewModel : ViewModel() {
    private val mAlphabets = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"

    private val mPageTags: MutableLiveData<ArrayList<String>> by lazy {
        MutableLiveData<ArrayList<String>>().also {
            it.value = ArrayList()
        }
    }

    fun getPageTags(): LiveData<ArrayList<String>> {
        return mPageTags
    }

    fun addPageTag(index: Int) {
        val newPageTag = (1..3).map { Random.nextInt(0, mAlphabets.length - 1) }
            .map { position -> mAlphabets[position] }
            .joinToString("")

        mPageTags.value?.apply {
            add(index, newPageTag)
            mPageTags.value = this
        }
    }

    fun removePageTag(index: Int) {
        mPageTags.value?.let {
            it.removeAt(index)
            mPageTags.value = it
        }
    }
}