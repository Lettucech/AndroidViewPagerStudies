package io.github.lettucech.android.studies.viewpager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * Created by Brian Ho on 2020-01-22.
 */
class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        button_simple_static_pager.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_simpleStaticPagerFragment)
        }
        button_dynamic_pager.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_dynamicPagerFragment)
        }
        button_tab_layout_integration.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_tabLayoutFragment)
        }
    }
}