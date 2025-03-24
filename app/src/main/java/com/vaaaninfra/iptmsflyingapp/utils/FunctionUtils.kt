package com.vaaaninfra.iptmsflyingapp.utils

import android.app.Activity
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.os.Build
import android.util.TypedValue
import androidx.core.content.ContextCompat
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.QRCodeWriter
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


fun getCurrentDateFormatted(): String {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH)
    val currentDate = Date()
    return dateFormat.format(currentDate)
}


fun getDateFormatted(date: Long) : String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
    return dateFormat.format(date)
}
fun getDateFormatted(date: Date) : String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
    return dateFormat.format(date)
}
fun <T : Serializable?> getSerializable(activity: Activity, name: String, clazz: Class<T>): T
{
    return if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
        activity.intent.getSerializableExtra(name, clazz)!!
    else
        activity.intent.getSerializableExtra(name) as T
}



// Get Current Date and time format function 03/12/2024 09:50:00 from input date
fun getDateTimeFormat(inputDate: String): String {
    val timestamp = inputDate.substringAfter("Date(").substringBefore(")").toLong()
    // Create a Date object from the timestamp
    val date = Date(timestamp)

    // Create a SimpleDateFormat instance with the desired format
    val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

    // Format the date
    val formattedDate = dateFormat.format(date)
    return formattedDate.toString()
}

fun getCurrentDateTimeTranId(): String {
    val dateFormat = SimpleDateFormat("ddMMyyyyHHmmss", Locale.ENGLISH)
    val currentDate = Date()
    return dateFormat.format(currentDate)
}

fun getJsonDateTimeFormat(): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
    val currentDate = Date()
    return dateFormat.format(currentDate)
}

fun getNextDate(daysAgo: Int): String {
    // Create a SimpleDateFormat with the specified format
    val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
    val currentDate = Date()

    // Create a Calendar instance and set the current date
    val calendar = Calendar.getInstance()
    calendar.time = currentDate

    // Subtract the specified number of days
    calendar.add(Calendar.DAY_OF_YEAR, +daysAgo) // Use negative to subtract days

    // Format the previous date
    return formatter.format(calendar.time)
}

fun createProfileImage(
    context: Context,
    username: String,
    size: Int,
    backgroundColorRes: Int
): Bitmap {
    // Get the first letter of the username
    val firstLetter = username.firstOrNull()?.uppercaseChar() ?: '?'

    // Create a bitmap to draw on
    val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)

    // Set up the background paint
    val backgroundPaint = Paint().apply {
        color = ContextCompat.getColor(context, backgroundColorRes)
        style = Paint.Style.FILL
    }

    // Draw the circular background
    val radius = size / 2f
    canvas.drawCircle(radius, radius, radius, backgroundPaint)

    // Set up the text paint
    val textPaint = Paint().apply {
        color = Color.WHITE // Text color
        textSize = size / 1.5f // Font size
        isAntiAlias = true
        textAlign = Paint.Align.CENTER
        typeface = Typeface.DEFAULT_BOLD
    }

    // Draw the first letter in the center
    val xPos = radius
    val yPos = radius - ((textPaint.descent() + textPaint.ascent()) / 2)
    canvas.drawText(firstLetter.toString(), xPos, yPos, textPaint)

    return bitmap
}

fun generateQRCode(text: String): Bitmap? {
    try {
        val qrCodeWriter = QRCodeWriter()
        val bitMatrix: BitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 300, 300)

        val width = bitMatrix.width
        val height = bitMatrix.height
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

        for (x in 0 until width) {
            for (y in 0 until height) {
                bitmap.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
            }
        }
        return bitmap
    } catch (e: WriterException) {
        e.printStackTrace()
        return null
    }
}


fun generateDates(): List<Date> {
    val today = Calendar.getInstance()
    val dateList = mutableListOf<Date>()

    for (i in 0 until 7) {
        dateList.add(today.time)
        today.add(Calendar.DAY_OF_MONTH, 1)
    }

    return dateList
}


// Get Color From Attribute
fun getColorFromAttribute(context: Context, attr: Int): Int {
    val typedValue = TypedValue()
    val a: TypedArray = context.obtainStyledAttributes(typedValue.data, intArrayOf(attr))
    val color = a.getColor(0, Color.BLACK) // Default color if not found
    a.recycle()
    return color
}

fun resolveColorAttribute(context: Context, attr: Int): Int {
    val typedValue = TypedValue()
    context.theme.resolveAttribute(attr, typedValue, true)
    return typedValue.data
}


