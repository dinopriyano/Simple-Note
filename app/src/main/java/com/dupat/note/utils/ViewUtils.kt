package com.dupat.note.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.NavOptions
import com.dupat.note.R
import com.google.android.material.snackbar.Snackbar
import com.pd.chocobar.ChocoBar

fun Context.toast(msg: String)
{
    Toast.makeText(this,msg, Toast.LENGTH_LONG).show()
}

fun View.snackbar(msg: String)
{
    Snackbar.make(this,msg, Snackbar.LENGTH_LONG).also { snackbar ->
        snackbar.setActionTextColor(resources.getColor(R.color.primary))
        snackbar.setAction("OK"){
            snackbar.dismiss()
        }
    }.show()
}

fun Int.navOption() : NavOptions
{
    return NavOptions.Builder().setPopUpTo(this, true).build()
}

fun EditText.showKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

fun EditText.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(this.windowToken, 0)
}

fun Activity.chocobar(msg: String)
{
    ChocoBar.builder().also { cb ->
        cb.setActivity(this)
        cb.setActionText("OK")
        cb.setActionTextColor(resources.getColor(R.color.primary))
        cb.setText(msg)
        cb.setDuration(ChocoBar.LENGTH_LONG)
    }.build().show()
}
