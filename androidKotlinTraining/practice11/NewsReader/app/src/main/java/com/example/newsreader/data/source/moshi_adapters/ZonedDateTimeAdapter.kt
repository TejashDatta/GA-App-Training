package com.example.newsreader.data.source.moshi_adapters

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import org.threeten.bp.ZonedDateTime

class ZonedDateTimeAdapter {
  @ToJson
  fun toJson(zonedDateTime: ZonedDateTime) = zonedDateTime.toString()

  @FromJson
  fun fromJson(dateTimeString: String) = ZonedDateTime.parse(dateTimeString)
}
