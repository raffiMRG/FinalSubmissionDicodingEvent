package com.example.coba1submission.ui.active
import android.os.Bundle
import android.util.Log
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
        activeViewModel = ViewModelProvider(this)[ActiveViewModel::class.java]

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)

        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvReview.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
        binding.rvReview.addItemDecoration(itemDecoration)

        activeViewModel.isFailure.observe(viewLifecycleOwner){
            Log.d("failedCondition", "$it")
            if(it){
                Log.d("failedCondition", "sudah masuk ke failed condition")
                binding.messageStatus.visibility = View.VISIBLE
                binding.rvReview.visibility = View.INVISIBLE
                activeViewModel.responseMessage.observe(viewLifecycleOwner){message ->
                    binding.messageStatus.text = message
                    Log.d("messageStatus", message)
                }
            }
        }

        activeViewModel.listEvents.observe(viewLifecycleOwner) { events ->
            setReviewData(events)
        }

        activeViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        activeViewModel.findEvent()

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