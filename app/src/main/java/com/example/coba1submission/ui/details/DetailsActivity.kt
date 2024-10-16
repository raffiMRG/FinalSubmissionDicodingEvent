package com.example.coba1submission.ui.details

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.coba1submission.R
import com.example.coba1submission.data.database.Event
import com.example.coba1submission.data.helper.ViewModelFactory
import com.example.coba1submission.data.response.ListEventsItem
import com.example.coba1submission.databinding.ActivityDetailsBinding
import kotlinx.coroutines.launch
//import com.example.coba1submission.data.helper.ViewModelFactory
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding
    private var isFavorite = false
    private var event: Event? = null
    private lateinit var detailsViewModel: DetailsViewModel

    companion object{
        const val ALERT_DIALOG_CLOSE = 10
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        title = "Details"
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        detailsViewModel = obtainViewModel(this@DetailsActivity)

        val dataEvent = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra<ListEventsItem>("key_hero", ListEventsItem::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra<ListEventsItem>("key_hero")
        }

        val document: Document = Jsoup.parse(dataEvent?.description)
        document.select("img").remove() // Menghapus semua tag <img>
        val descriptionWithoutImages = document.html()

        var description = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(descriptionWithoutImages, Html.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(descriptionWithoutImages)
        }
        Glide.with(binding.root)
            .load("${dataEvent?.mediaCover}")
            .into(binding.image)

        val sisaQuota = dataEvent?.registrants?.let {
            dataEvent.quota.minus(
                it
            )
        }
        binding.title.text = dataEvent?.name
        binding.subTitle.text = dataEvent?.ownerName
        binding.quota.text = "Sisa Quota : ${sisaQuota}"
        binding.startTime.text = "Mulai : ${dataEvent?.beginTime}"
        binding.endTime.text = "Selesai : ${dataEvent?.endTime}"
        binding.text.text = description

        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(dataEvent?.link))
        binding.goToWeb.setOnClickListener{
            startActivity(intent)
        }

//            val id = dataEvent?.id
//            val title = dataEvent?.name
        val id = dataEvent?.id ?: 0
        val title = dataEvent?.name ?: "Title is empty yo!!!"
        val existingItem = detailsViewModel.getItemById(id)
        if (existingItem){
            binding.fabLike.setImageResource(R.drawable.baseline_favorite_24)
        }

        binding.fabLike.setOnClickListener {
            Log.d("titleIsertDb", "title: ${title} \n id ${id}")
            lifecycleScope.launch {
                val item = Event(id, title)

                Log.d("existingItem", "item : ${existingItem}")
                if (existingItem) {
                    detailsViewModel.delete(item)
//                    button.text = "Add"
                    binding.fabLike.setImageResource(R.drawable.baseline_favorite_border_24)
                    showToast(getString(R.string.disLiked))
                } else {
                    detailsViewModel.insert(item)
//                    button.text = "Delete"
                    binding.fabLike.setImageResource(R.drawable.baseline_favorite_24)
                    showToast(getString(R.string.liked))
                }
            }



//            detailsViewModel.insert(event as Event)

//            ========================================================

//            if (isFavorite) {
//                binding.fabLike.setImageResource(R.drawable.baseline_favorite_border_24)
//            } else {
//                binding.fabLike.setImageResource(R.drawable.baseline_favorite_24)
//            }
//            isFavorite = !isFavorite // Toggle status
        }

//        EVENT KETKA TOMBOL KEMBALI DI TEKAN
//        onBackPressedDispatcher.addCallback(this, object: OnBackPressedCallback(true) {
//            override fun handleOnBackPressed() {
//                showAlertDialog(ALERT_DIALOG_CLOSE)
//            }
//        })
    }
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showAlertDialog(type: Int) {
        val isDialogClose = type == ALERT_DIALOG_CLOSE
        val dialogTitle: String
        val dialogMessage: String
        if (isDialogClose) {
            dialogTitle = getString(R.string.cancel)
            dialogMessage = getString(R.string.message_cancel)
        } else {
            dialogMessage = getString(R.string.message_delete)
            dialogTitle = getString(R.string.delete)
        }
        val alertDialogBuilder = AlertDialog.Builder(this)
        with(alertDialogBuilder) {
            setTitle(dialogTitle)
            setMessage(dialogMessage)
            setCancelable(false)
            setPositiveButton(getString(R.string.yes)) { _, _ ->
                if (!isDialogClose) {
                    detailsViewModel.delete(event as Event)
                    showToast(getString(R.string.deleted))
                }
                finish()
            }
            setNegativeButton(getString(R.string.no)) { dialog, _ -> dialog.cancel() }
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    private fun obtainViewModel(activity: AppCompatActivity): DetailsViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(DetailsViewModel::class.java)
    }
}