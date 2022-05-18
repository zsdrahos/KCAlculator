package hu.bme.aut.android.kcalculator.network

import android.util.Log
import hu.bme.aut.android.kcalculator.fragments.NewItemDialogFragment.Companion.TAG
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.concurrent.TimeUnit

class API {
    companion object {
        private const val BASE_URL = "https://api.calorieninjas.com/v1/nutrition?query="
        private const val UTF_8 = "UTF-8"
    }

    private val client = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .build()

    private fun httpGet(url: String): String {
        val request = Request.Builder()
            .url(url)
            .header("X-Api-Key", "TvSVgZlj/cw+90ovXmnPHg==UmXFxM6KM9XAbdeh")
            .build()

        //The execute call blocks the thread
        val response = client.newCall(request).execute()
        return response.body?.string() ?: "EMPTY"
    }
    fun download(item: String): String
    {
        return try {
            val moveUserUrl = "$BASE_URL$item"

            Log.d(TAG, "Call to $moveUserUrl")
            httpGet(moveUserUrl)
        } catch (e: Exception) {
            e.printStackTrace()
            val RESPONSE_ERROR = "No such food or drink"
            RESPONSE_ERROR
        }
    }
}