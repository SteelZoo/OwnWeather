package com.steelzoo.ownweather.ui.util

import android.location.Address
import android.util.Log

object AddressUtil {

    private val addressRegex = Regex(" ([가-힣]+[시|군|구] [가-힣]+\\d*[읍|면|동]) ")


    fun getAddressStringFromListAddress(addressList: List<Address>): String{
        var resultString = ""

        for (address in addressList){
            Log.d("GEOCODER", "$address")
            addressRegex.find(address.getAddressLine(0))?.let {
                resultString = it.value
            }
        }

        return resultString
    }
}

