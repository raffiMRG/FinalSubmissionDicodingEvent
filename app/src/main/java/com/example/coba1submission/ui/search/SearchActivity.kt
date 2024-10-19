package com.example.coba1submission.ui.search

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coba1submission.R
import com.example.coba1submission.data.response.ListEventsItem
import com.example.coba1submission.databinding.ActivitySearchBinding
import com.example.coba1submission.ui.adapter.Adapter

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        title = "Search"
        super.onCreate(savedInstanceState)

        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val layoutManager = LinearLayoutManager(this@SearchActivity)
        val itemDecoration = DividerItemDecoration(this@SearchActivity, layoutManager.orientation)
        binding.apply {
            recyclerView.layoutManager = layoutManager
            recyclerView.addItemDecoration(itemDecoration)
        }

        binding.search.clearFocus()
        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                dispalyed(newText)

                return true
            }
        })

    }

    private fun dispalyed(keyword: String){
        if(binding.search.query.isNotEmpty()){
            binding.test.visibility = View.INVISIBLE
            binding.recyclerView.visibility = View.VISIBLE
            val searchViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[SearchViewModel::class.java]
            searchViewModel.setQuery(keyword)
            searchViewModel.listEvents.observe(this) { events ->
                setReviewData(events)
            }
            searchViewModel.isLoading.observe(this) {
                showLoading(it)
            }
        }else{
            binding.test.text = getText(R.string.masukan_string)
            binding.test.visibility = View.VISIBLE
            binding.recyclerView.visibility = View.INVISIBLE
        }
    }

    private fun setReviewData(events: List<ListEventsItem>) {
        if (events.isEmpty()){
            binding.test.text = getText(R.string.event_notfound)
            binding.test.visibility = View.VISIBLE
            binding.recyclerView.visibility = View.INVISIBLE
        }else {
            binding.test.visibility = View.INVISIBLE
            binding.recyclerView.visibility = View.VISIBLE
            val adapter = Adapter()
            adapter.submitList(events)
            binding.recyclerView.adapter = adapter
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}