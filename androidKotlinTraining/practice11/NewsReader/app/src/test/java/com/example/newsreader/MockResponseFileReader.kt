package com.example.newsreader

import java.io.InputStreamReader

class MockResponseFileReader(path: String) {
  val content: String

  init {
    InputStreamReader(this.javaClass.classLoader?.getResourceAsStream(path)).use {
      content = it.readText()
    }
  }
}
