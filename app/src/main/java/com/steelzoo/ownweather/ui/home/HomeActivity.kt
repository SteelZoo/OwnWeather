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
import com.google.android.gms.location.*
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.material.snackbar.Snackbar
import com.steelzoo.ownweather.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val homeViewModel: HomeViewModel by viewModels()

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val cancellationTokenSource = CancellationTokenSource()

    private val requirePermissions = arrayOf(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION)
    private val requestPermission = initPermissionActivityResultLauncher()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewmodel = homeViewModel
        binding.lifecycleOwner = this

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

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
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
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
                finish()
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getNowWeatherWithCurrentLocation(){
        fusedLocationProviderClient.getCurrentLocation(createCurrentLocationRequest(), cancellationTokenSource.token)
            .addOnCompleteListener {

            }
            .addOnSuccessListener { location ->
                homeViewModel.getNowWeather(location.latitude, location.longitude)
            }
            .addOnFailureListener { exception ->
                Snackbar.make(binding.root, "위치정보 호출에 실패했습니다.", Snackbar.LENGTH_SHORT).show()
            }
            .addOnCanceledListener {
                Snackbar.make(binding.root, "위치정보 호출에 실패했습니다.", Snackbar.LENGTH_SHORT).show()
            }
    }

    private fun createCurrentLocationRequest(): CurrentLocationRequest =
        CurrentLocationRequest.Builder()
            .setDurationMillis(3000)
            .setGranularity(Granularity.GRANULARITY_PERMISSION_LEVEL)
            .setMaxUpdateAgeMillis(1000)
            .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
            .build()
}