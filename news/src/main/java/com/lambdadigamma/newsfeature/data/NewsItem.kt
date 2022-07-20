package com.lambdadigamma.newsfeature.data;

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.simpleframework.xml.Root
import java.util.*

@Root(name = "item")
@Entity(tableName = "news")
data class NewsItem(
    @PrimaryKey(autoGenerate = true) var id: Int,
    var title: String? = null,
    var description: String? = null,
    var pubDate: Date? = null,
    var link: String? = null,
    var author: String? = null
)