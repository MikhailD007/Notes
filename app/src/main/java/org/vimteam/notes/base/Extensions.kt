package org.vimteam.notes.base

import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager

fun Fragment.vibratePhone() {
    val vibrator = context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    if (Build.VERSION.SDK_INT >= 26) {
        vibrator.vibrate(VibrationEffect.createOneShot(20, VibrationEffect.DEFAULT_AMPLITUDE))
    } else {
        @Suppress("DEPRECATION")
        vibrator.vibrate(20)
    }
}

fun Activity.hideKeyboard() {
    val view = currentFocus ?: View(this)
    (getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
        view.windowToken,
        0
    )
}

fun Activity.showKeyboard() {
    val view = currentFocus ?: View(this)
    (getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager).showSoftInput(
        view,
        InputMethodManager.SHOW_IMPLICIT
    )
}

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

fun Array<String>.toSimpleString(): String {
    var returnString = ""
    for (str in this) returnString = "$returnString$str, "
    return returnString.substring(0, returnString.length - 2)
}

fun Activity.setThemeFromPreferences() {
//    if (PreferenceManager.getDefaultSharedPreferences(this)
//            .getBoolean(getString(R.string.dark_theme_selector_key), false)
//    )
//        setTheme(R.style.AppThemeDark)
//    else
//        setTheme(R.style.AppThemeLight)
}