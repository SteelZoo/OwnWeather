package com.steelzoo.ownweather.ui.home.fragment

import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.steelzoo.ownweather.databinding.FragmentNowcastBinding
import com.steelzoo.ownweather.ui.home.HomeViewModel
import com.steelzoo.ownweather.ui.util.AddressUtil
import java.util.Locale

class NowcastFragment : Fragment() {

    private val homeViewModel: HomeViewModel by activityViewModels()

    private var _binding: FragmentNowcastBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNowcastBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewmodel = homeViewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}