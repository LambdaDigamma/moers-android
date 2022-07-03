package com.lambdadigamma.moers.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.lambdadigamma.newsfeature.NewsDao
import com.lambdadigamma.newsfeature.NewsRepository
import com.lambdadigamma.newsfeature.NewsService
import com.lambdadigamma.newsfeature.RssItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RssItemDetailViewModel @Inject constructor(
    private val newsDao: NewsDao
) : ViewModel() {

    lateinit var item: LiveData<RssItem>

    private var newsRepository: NewsRepository = NewsRepository(
        newsDao,
        NewsService.getRPService()
    )

    fun configure(id: Long) {
        item = newsDao.getRssItem(id)
    }

}
