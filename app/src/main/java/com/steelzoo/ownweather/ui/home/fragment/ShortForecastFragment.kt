package com.steelzoo.ownweather.ui.home.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.steelzoo.ownweather.databinding.FragmentShortForecastBinding
import com.steelzoo.ownweather.ui.home.HomeViewModel
import com.steelzoo.ownweather.ui.home.recyclerview_shortforecast.ShortForecastAdapter

class ShortForecastFragment : Fragment() {

    val homeViewModel: HomeViewModel by activityViewModels()

    private var _binding: FragmentShortForecastBinding? = null
    private val binding get() = _binding!!

    private lateinit var shortForecastAdapter: ShortForecastAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentShortForecastBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        shortForecastAdapter = ShortForecastAdapter()
        binding.rvShortforecast.adapter = shortForecastAdapter

        setObserve()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun setObserve() {
        homeViewModel.shortForecast.observe(viewLifecycleOwner) {
            shortForecastAdapter.submitList(it)
        }
    }
}