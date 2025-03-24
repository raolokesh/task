package com.vaaaninfra.iptmsflyingapp.network

import android.util.Log
import com.vaaaninfra.iptmsflyingapp.utils.ApiException
import com.vaaaninfra.iptmsflyingapp.utils.NoInternetException
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response

abstract class SafeApiWithOutSuspend {

    fun <T : Any> apiRequest(call: () -> Response<T>): T {
        try {
            val response = call.invoke()
            if (response.isSuccessful) {
                return response.body()!!
            } else {
                val error = response.errorBody()?.string()
                val message = StringBuffer()
                error?.let {
                    try {
                        message.append(JSONObject(it).getString("message"))
                    } catch (e: JSONException) {
                        message.append("\n")
                    }

                }
                message.append("Error Code: ${response.code()}")
                throw ApiException(message.toString())
            }
        } catch (e: Exception) {
            Log.d("Error", e.message.toString())
            throw NoInternetException(e.message.toString())
        }
    }
}