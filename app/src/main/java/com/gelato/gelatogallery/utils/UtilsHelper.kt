package com.gelato.gelatogallery.utils

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Matrix
import android.location.Address
import android.location.Geocoder
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.RequiresApi
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.io.IOException
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

object UtilsHelper : KoinComponent {

    val context: Context by inject()

    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    fun isOnline(c: Context): Boolean {
        val connectivityManager: ConnectivityManager =
            c.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            with(connectivityManager) {
                val networkCapabilities = getNetworkCapabilities(activeNetwork)
                return networkCapabilities != null &&
                        (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN))
            }
        } else {
            val activeNetwork = connectivityManager.activeNetworkInfo
            return activeNetwork != null && activeNetwork.isConnected &&
                    (activeNetwork.type == ConnectivityManager.TYPE_WIFI || activeNetwork.type == ConnectivityManager.TYPE_MOBILE
                            || activeNetwork.type == ConnectivityManager.TYPE_VPN)
        }
    }

    fun getString(resource: Int, context: Context) = context.getString(resource)

    fun doSeparate(passedValue: String, locale: Locale?): String? {
        var formattedValue = ""
        val pattern = "#,##0.###"
        val decimalFormat: DecimalFormat = NumberFormat.getNumberInstance(locale) as DecimalFormat
        decimalFormat.applyPattern(pattern)
        try {
            formattedValue = decimalFormat.format(passedValue.toDouble())
        } catch (nfe: NumberFormatException) {
            nfe.printStackTrace()
        }
        return formattedValue
    }

    inline fun locationToAddress(
        context: Context?,
        latitude: Double,
        longitude: Double
    ): String? {
        val addresses: List<Address>
        val geoCoder = Geocoder(context, Locale.getDefault())
        var address = ""
        try {
            addresses = geoCoder.getFromLocation(
                latitude,
                longitude,
                1
            ) // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            if (addresses.isNotEmpty()) {
                address = if (addresses[0].locality != null) {
                    addresses[0].getAddressLine(0) + "," + addresses[0].locality + "," + addresses[0].countryName
                } else {
                    addresses[0].getAddressLine(0) + "," + addresses[0].countryName // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return address
    }

    @RequiresApi(Build.VERSION_CODES.O)
    inline fun stringToLocalDate(date: String): LocalDate {
        val formatter: DateTimeFormatter =
            DateTimeFormatter.ofPattern(
                "dd/MM/yyyy",
                Locale.ENGLISH
            )      //convert String to LocalDate
        //convert String to LocalDate
        return LocalDate.parse(date, formatter)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    inline fun stringToLocalDateReservation(date: String): LocalDate {
        val formatter: DateTimeFormatter =
            DateTimeFormatter.ofPattern(
                "dd/MM/yyyy HH:mm",
                Locale.ENGLISH
            )      //convert String to LocalDate
        //convert String to LocalDate
        return LocalDate.parse(date, formatter)
    }

    fun stringDateFormat(dateString: String, inPattern: String, outPattern: String): String {
        val inDateFormat = SimpleDateFormat(inPattern, Locale.ENGLISH)
        val outDateFormat = SimpleDateFormat(outPattern, Locale.ENGLISH)
        val date = inDateFormat.parse(dateString)
        return outDateFormat.format(date)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    inline fun localDateToString(date: LocalDate): String {
        val formatter: DateTimeFormatter =
            DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ENGLISH)
        return date.format(formatter)
    }

    inline fun convertDpToPixel(dp: Float): Int {
        val r: Resources = context.resources
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            r.displayMetrics
        ).toInt()

        /*val resources = context.resources
        val metrics = resources.displayMetrics
        return dp * (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)*/
    }

    inline fun stringToDate(date: String): Date {
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
        //formatter.timeZone = TimeZone.getTimeZone("UTC")
        return formatter.parse(date)
    }

    inline fun longToDateString(date: Long): String {
        val formatter: SimpleDateFormat =
            SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)      //convert String to LocalDate
        //formatter.timeZone = TimeZone.getTimeZone("UTC")
        return formatter.format(Date(date)).toString()//LocalDate.parse(date, formatter)
    }

    fun rotateImage(degree: Float, bitmap: Bitmap): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(degree) // Degrees calculated in Step 2
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }

    fun getResizedBitmap(image: Bitmap, maxSize: Int): Bitmap? {
        var width = image.width
        var height = image.height
        val bitmapRatio = width.toFloat() / height.toFloat()
        if (bitmapRatio > 1) {
            width = maxSize
            height = (width / bitmapRatio).toInt()
        } else {
            height = maxSize
            width = (height * bitmapRatio).toInt()
        }
        return Bitmap.createScaledBitmap(image, width, height, true)
    }

    inline fun showHideKeyboardFrom(
        context: Context,
        view: View,
        flag: Int
    ) {
        val imm =
            context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, flag)
    }

    enum class Week(weekDay: Int) {
        SATURDAY(7),
        SUNDAY(1),
        MONDAY(2),
        TUESDAY(3),
        WEDNESDAY(4),
        THURSDAY(5),
        FRIDAY(6)
    }
}