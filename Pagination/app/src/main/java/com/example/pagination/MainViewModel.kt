package com.example.pagination

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlin.math.ceil

class MainViewModel : ViewModel() {
    private val _listItems = MutableStateFlow<List<Int>>(listOf())

    init {
        _listItems.value = mutableListOf<Int>().apply {
            for (i in 1..100) add(i)
        }
    }

    private val _totalPages = _listItems.map {
        ceil(it.size.toDouble() / ITEMS_PER_PAGE).toInt()
    }.stateIn(
        CoroutineScope(Dispatchers.Main),
        SharingStarted.WhileSubscribed(),
        1
    )

    private val _currentPage = MutableStateFlow(1)
    val currentPage = _currentPage.asStateFlow()

    val isNextButtonDisabled = combine(_totalPages, _currentPage) { total, current ->
        current == total
    }.stateIn(
        CoroutineScope(Dispatchers.Main),
        SharingStarted.WhileSubscribed(),
        false
    )

    val isPreviousButtonDisabled = _currentPage.map {
        it == 1
    }.stateIn(
        CoroutineScope(Dispatchers.Main),
        SharingStarted.WhileSubscribed(),
        true
    )

    val currentPageItems = combine(_listItems, _currentPage, _totalPages) { list, current, total ->
        var start = ITEMS_PER_PAGE * (current - 1)
        if (current == 1) start = 0
        var end = start + ITEMS_PER_PAGE
        if (current == total) end = list.size
        list.subList(start, end)
    }.stateIn(
        CoroutineScope(Dispatchers.Main),
        SharingStarted.WhileSubscribed(),
        listOf()
    )

    val pageButtonValues = combine(_totalPages, _currentPage) { total, current ->
        var start = current - 2
        var end = current + 2
        if (start <= 0) {
            end -= start - 1
            start = 1
        }
        end = end.coerceAtMost(total)
        val pagesList = mutableListOf<String>()
        if (start > 1) pagesList.apply {
            add("1")
            if (start != 2)
                add("...")
        }
        for (i in start..end) pagesList.add(i.toString())
        if (end < total) pagesList.apply {
            if (end != total - 1)
                add("...")
            add(total.toString())
        }
        pagesList
    }.stateIn(
        CoroutineScope(Dispatchers.Main),
        SharingStarted.WhileSubscribed(),
        listOf("1")
    )

    fun setPage(page: Int) {
        if (page > _totalPages.value)
            throw IllegalArgumentException("Invalid page given. Page should be lesser than total pages")
        _currentPage.value = page
    }

    fun nextPage() {
        _currentPage.value = _currentPage.value + 1
    }

    fun prevPage() {
        _currentPage.value = _currentPage.value - 1
    }

    companion object {
        const val ITEMS_PER_PAGE = 5
        const val MAX_PAGE_BUTTONS = 6
    }
}