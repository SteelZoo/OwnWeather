package com.steelzoo.ownweather.ui.home


import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.*
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.material.snackbar.Snackbar
import com.steelzoo.ownweather.databinding.ActivityHomeBinding
import com.steelzoo.ownweather.ui.home.recyclerview_shortforecast.ShortForecastAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private val homeViewModel: HomeViewModel by viewModels()

    private var _binding: ActivityHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val cancellationTokenSource = CancellationTokenSource()

    private val requirePermissions = arrayOf(ACCESS_FINE_LOCATION)
    private val requestPermission = initPermissionActivityResultLauncher()

    private val shortForecastAdapter = ShortForecastAdapter()

    private fun showSnackbar(message: String) = Snackbar.make(binding.root,message,Snackbar.LENGTH_SHORT).show()
    private fun locationRequestLog(message: String) = Log.d("REQUEST_LOCATION",message)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewmodel = homeViewModel
        binding.lifecycleOwner = this

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        binding.rvShortforecast.adapter = shortForecastAdapter

        setObserveLiveData()

        getDeniedPermissions(requirePermissions).let {
            if (it.isNotEmpty()) {
                requestPermission.launch(it)
            } else {
                getNowWeatherWithCurrentLocation()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        cancellationTokenSource.cancel()
        _binding = null
    }

    private fun setObserveLiveData(){
        homeViewModel.shortForecast.observe(this){
            shortForecastAdapter.submitList(it)
        }
    }

    private fun getDeniedPermissions(permissionsArray: Array<String>): Array<String> {
        /**
         * 매개변수 : 확인할 권한 배열
         * 반환 : 거부 된 권한
         */
        val resultList = mutableListOf<String>()

        permissionsArray.forEach { permissionString ->
            if(ContextCompat.checkSelfPermission(applicationContext,permissionString) == PackageManager.PERMISSION_DENIED){
                Log.d("PERMISSION_CHECK", "getDeniedPermissions: $permissionString")
                resultList.add(permissionString)
            }
        }

        return resultList.toTypedArray()
    }


    private fun initPermissionActivityResultLauncher(): ActivityResultLauncher<Array<String>> {
        return registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ){ permissionResult ->
            if (permissionResult.values.all { it }){
                getNowWeatherWithCurrentLocation()
            } else {
                if(getDeniedPermissions(requirePermissions).isEmpty()){
                    getNowWeatherWithCurrentLocation()
                }
                //TODO 위치 권한 미허용 대응 기능 필요
                Toast.makeText(this,"권한이 필요합니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getNowWeatherWithCurrentLocation(){
        locationRequestLog("getNowWeatherWithCurrentLocation: start")
        fusedLocationProviderClient.getCurrentLocation(createCurrentLocationRequest(), cancellationTokenSource.token)
            .addOnCompleteListener {
                locationRequestLog("getNowWeatherWithCurrentLocation: complete")
                showSnackbar("위치정보 호출에 실패했습니다. complete")
            }
            .addOnSuccessListener { location ->
                if (location != null){
                    homeViewModel.getNowWeather(location.latitude, location.longitude)
                    homeViewModel.getShortForecast(location.latitude, location.longitude)
                    locationRequestLog("getNowWeatherWithCurrentLocation: success")
                    showSnackbar("success ${location.latitude} ${location.longitude}")
                } else {
                    locationRequestLog( "getNowWeatherWithCurrentLocation: success but fail")
                    showSnackbar("위치정보 호출에 실패했습니다. fail")
                }
            }
            .addOnFailureListener { exception ->
                locationRequestLog("getNowWeatherWithCurrentLocation: fail")
                showSnackbar("위치정보 호출에 실패했습니다. fail")
            }
            .addOnCanceledListener {
                locationRequestLog("getNowWeatherWithCurrentLocation: fail")
                Log.d("REQUEST_LOCATION", "getNowWeatherWithCurrentLocation: cancel")
                showSnackbar("위치정보 호출에 실패했습니다. cancel")
            }
    }

    private fun createCurrentLocationRequest(): CurrentLocationRequest =
        CurrentLocationRequest.Builder()
            .setDurationMillis(5000)
            .setGranularity(Granularity.GRANULARITY_PERMISSION_LEVEL)
            .setMaxUpdateAgeMillis(1000)
            .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
            .build()
}