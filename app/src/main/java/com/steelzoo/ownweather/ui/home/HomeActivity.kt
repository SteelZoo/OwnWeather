package com.steelzoo.ownweather.ui.home


import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.*
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.material.snackbar.Snackbar
import com.steelzoo.ownweather.databinding.ActivityHomeBinding
import com.steelzoo.ownweather.ui.home.fragment.NowcastFragment
import com.steelzoo.ownweather.ui.home.fragment.ShortForecastFragment
import com.steelzoo.ownweather.ui.home.fragment.WeatherFragmentAdapter
import com.steelzoo.ownweather.ui.home.recyclerview_shortforecast.ShortForecastAdapter
import com.steelzoo.ownweather.ui.util.AddressUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import java.util.*

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
    private val weatherFragmentAdapter = WeatherFragmentAdapter(this
        , listOf(NowcastFragment(),ShortForecastFragment()
    ))

    private fun showSnackbar(message: String) = Snackbar.make(binding.root,message,Snackbar.LENGTH_SHORT).show()
    private fun locationRequestLog(message: String) = Log.d("REQUEST_LOCATION",message)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewmodel = homeViewModel
        binding.lifecycleOwner = this

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

//        binding.rvShortforecast.adapter = shortForecastAdapter
//        binding.rvShortforecast.addItemDecoration(DividerItemDecoration(baseContext,LinearLayoutManager.HORIZONTAL))
        binding.viewpagerWeathercast.adapter = weatherFragmentAdapter

        setObserveLiveData()

        getDeniedPermissions(requirePermissions).let {
            if (it.isNotEmpty()) {
                requestPermission.launch(it)
            } else {
                getWeather_CurrentLocation()
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
                getWeather_CurrentLocation()
            } else {
                if(getDeniedPermissions(requirePermissions).isEmpty()){
                    getWeather_CurrentLocation()
                }
                //TODO 위치 권한 미허용 대응 기능 필요
                Toast.makeText(this,"권한이 필요합니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getWeather_CurrentLocation(){
        fusedLocationProviderClient.getCurrentLocation(createCurrentLocationRequest(), cancellationTokenSource.token)
            .addOnSuccessListener { location ->
                if (location != null){
                    runGetWeathers(location)
                    locationRequestLog("getNowWeatherWithCurrentLocation: success")
//                    showSnackbar("success ${location.latitude} ${location.longitude}")

                    setAddressText(location.latitude,location.longitude)
                } else {
                    locationRequestLog( "getNowWeatherWithCurrentLocation: success but fail")
                    getWeather_LastLocation()
                }
            }
            .addOnFailureListener { exception ->
                locationRequestLog("getNowWeatherWithCurrentLocation: fail")
                getWeather_LastLocation()
            }
            .addOnCanceledListener {
                locationRequestLog("getNowWeatherWithCurrentLocation: fail")
                Log.d("REQUEST_LOCATION", "getNowWeatherWithCurrentLocation: cancel")
                showSnackbar("위치정보 호출을 취소했습니다.")
            }
    }

    @SuppressLint("MissingPermission")
    private fun getWeather_LastLocation(){
        fusedLocationProviderClient.getLastLocation(
            LastLocationRequest.Builder().build()
        )
            .addOnSuccessListener {location ->
                runGetWeathers(location)
                setAddressText(location.latitude,location.longitude)
                showSnackbar("정확한 위치가 아닐 수 있습니다.")

//                getAddress_Location(location.latitude,location.longitude)
            }
            .addOnFailureListener {
                showSnackbar("위치정보 호출에 실패했습니다.")
            }
            .addOnCanceledListener {
                showSnackbar("위치정보 호출을 취소했습니다.")
            }
    }

    private fun runGetWeathers(location: Location){
        homeViewModel.getNowWeather(location.latitude, location.longitude)
        homeViewModel.getShortForecast(location.latitude, location.longitude)
    }

//    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun setAddressText(lat: Double, lng: Double){
        /**
         * Geocoder.GeocodeListener API 33 부터 사용가능
         */
        val geocoder = Geocoder(this,Locale.KOREA)
        if (Build.VERSION.SDK_INT < 33) {
            geocoder.getFromLocation(lat, lng, 10)?.let {
                binding.tvAddress.text = AddressUtil.getAddressStringFromListAddress(it)
            }
        } else {
            geocoder.getFromLocation(lat,lng,10) {
                /**
                 * runOnThread에서 실행하지 않으면 Main이 아닌 쓰레드에서 접근하려 해서 ui 버그 발생
                 */
                runOnUiThread {
                    binding.tvAddress.text = AddressUtil.getAddressStringFromListAddress(it)
                }
            }
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