package io.github.lettucech.android.studies.viewpager

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout

/**
 * Created by Brian Ho on 2020-01-17.
 */
class TabView : ConstraintLayout {
    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {
        inflate(context, R.layout.view_tab, this)
    }
}