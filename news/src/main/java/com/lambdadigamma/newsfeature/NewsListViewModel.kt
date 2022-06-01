package com.lambdadigamma.newsfeature

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.lambdadigamma.core.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewsListViewModel @Inject constructor(
    private val newsDao: NewsDao
) : ViewModel() {

    var news: LiveData<Resource<List<RssItem>?>>

    private var newsRepository: NewsRepository = NewsRepository(
        newsDao,
        NewsService.getRPService()
    )

    init {
        news = newsRepository.getNews()
        println(news.value)
    }

}
