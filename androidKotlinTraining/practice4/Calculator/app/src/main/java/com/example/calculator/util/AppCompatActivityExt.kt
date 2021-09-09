package com.example.calculator.util

import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

fun AppCompatActivity.replaceFragmentInActivity(@IdRes frameId: Int, fragment: Fragment) {
  supportFragmentManager.beginTransaction().apply {
    replace(frameId, fragment)
  }.commit()
}
