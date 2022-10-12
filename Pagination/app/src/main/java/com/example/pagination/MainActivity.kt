package com.example.pagination

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainActivity : AppCompatActivity(), PaginationButtonAdapter.Listener {
    private lateinit var viewModel: MainViewModel
    private var pageBtnRecyclerView: RecyclerView? = null
    private var pageItemRecyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        initViews()
        subscribeToViewModel()
    }

    private fun initViews() {
        pageBtnRecyclerView = findViewById<RecyclerView>(R.id.pageBtnRecycler)
            .apply {
                adapter = PaginationButtonAdapter(this@MainActivity)
                layoutManager = LinearLayoutManager(
                    this@MainActivity,
                    RecyclerView.HORIZONTAL,
                    false
                )
            }
        pageItemRecyclerView = findViewById<RecyclerView>(R.id.recycler).apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = PageItemAdapter()
        }
    }

    private fun subscribeToViewModel() {
        viewModel.isNextButtonDisabled
            .onEach(this::disableNextButton)
            .launchIn(CoroutineScope(Dispatchers.Main))
        viewModel.isPreviousButtonDisabled
            .onEach(this::disablePreviousButton)
            .launchIn(CoroutineScope(Dispatchers.Main))
        viewModel.pageButtonValues
            .onEach(this::setPaginationButtons)
            .launchIn(CoroutineScope(Dispatchers.Main))
        viewModel.currentPageItems
            .onEach(this::setCurrentPageItems)
            .launchIn(CoroutineScope(Dispatchers.Main))
        viewModel.currentPage
            .onEach(this::setPage)
            .launchIn(CoroutineScope(Dispatchers.Main))
    }

    private fun disableNextButton(isDisabled: Boolean) {
        val adapter = pageBtnRecyclerView?.adapter as PaginationButtonAdapter?
        adapter?.nextButtonDisabled = isDisabled
        adapter?.notifyDataSetChanged()
    }

    private fun disablePreviousButton(isDisabled: Boolean) {
        val adapter = pageBtnRecyclerView?.adapter as PaginationButtonAdapter?
        adapter?.prevButtonDisabled = isDisabled
        adapter?.notifyDataSetChanged()
    }

    private fun setPaginationButtons(list: List<String>) {
        val adapter = pageBtnRecyclerView?.adapter as PaginationButtonAdapter?
        adapter?.buttonList = list.toMutableList().apply {
            add(0, "<")
            add(">")
        }
        adapter?.notifyDataSetChanged()
    }

    private fun setCurrentPageItems(items: List<Int>) {
        val adapter = pageItemRecyclerView?.adapter as PageItemAdapter?
        adapter?.pageItems = items.map { num -> num.toString() }
        adapter?.notifyDataSetChanged()
    }

    private fun setPage(currentPage: Int) {
        val adapter = pageBtnRecyclerView?.adapter as PaginationButtonAdapter?
        adapter?.selected = currentPage.toString()
        adapter?.notifyDataSetChanged()
    }

    override fun nextPage() = viewModel.nextPage()
    override fun prevPage() = viewModel.prevPage()
    override fun setCurrentPage(nextPage: Int) = viewModel.setPage(nextPage)
}