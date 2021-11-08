package com.example.newsreader.network.data_transfer_objects

import com.tickaroo.tikxml.TypeConverter
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter
import java.lang.Exception

@Xml(name = "item")
data class NetworkNewsItem(
  @PropertyElement val title: String,
  @PropertyElement val link: String,
  @PropertyElement(converter = DateConverter::class) val pubDate: ZonedDateTime
)

class DateConverter : TypeConverter<ZonedDateTime> {
  private val formatter = DateTimeFormatter.RFC_1123_DATE_TIME

  @Throws(Exception::class)
  override fun read(value: String): ZonedDateTime {
    return ZonedDateTime.parse(value, formatter)
            .withZoneSameInstant(ZoneId.systemDefault())
  }

  @Throws(Exception::class)
  override fun write(value: ZonedDateTime): String {
    return formatter.format(value)
  }
}
