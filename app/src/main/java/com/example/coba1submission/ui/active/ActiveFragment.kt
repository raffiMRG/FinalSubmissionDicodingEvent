package com.example.coba1submission.ui.active
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coba1submission.data.response.ListEventsItem
import com.example.coba1submission.databinding.FragmentDashboardBinding
import com.example.coba1submission.ui.adapter.Adapter

class ActiveFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private lateinit var activeViewModel: ActiveViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // instansiasi model untuk fragment
        activeViewModel = ViewModelProvider(this).get(ActiveViewModel::class.java)

        // binding untuk fragment
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)

        // buat layout manager untuk resicle view dengan model Linear (simple standar)
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvReview.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
        binding.rvReview.addItemDecoration(itemDecoration)

        // atur list event yang akan di masukan kedalam adapter
        activeViewModel.listEvents.observe(viewLifecycleOwner) { events ->
            setReviewData(events)
        }

        // atur progres bar (loading) agar muncul dan menmenghilang ketika proses selesai
        activeViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        activeViewModel.findEvent()

        // return fragmet yang sudah berisi resicle view utuk di tampilkan di main fragment
        return binding.root
    }

    private fun setReviewData(events: List<ListEventsItem>) {
        val adapter = Adapter()
        adapter.submitList(events)
        binding.rvReview.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}