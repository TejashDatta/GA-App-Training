package com.example.newsreader.data.validators

import android.util.Patterns

class AndroidUrlRegexMatcher: UrlRegexMatcher {
  override fun matches(url: String) = Patterns.WEB_URL.matcher(url).matches()
}
