package com.example.coba1submission.ui.details

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Html
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.coba1submission.data.response.ListEventsItem
import com.example.coba1submission.databinding.ActivityDetailsBinding
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        title = "Details"
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
        binding.title.text = dataEvent?.name
        binding.subTitle.text = dataEvent?.ownerName
        binding.quota.text = "Sisa Quota : ${dataEvent?.quota}"
        binding.startTime.text = "Mulai : ${dataEvent?.beginTime}"
        binding.endTime.text = "Selesai : ${dataEvent?.endTime}"
        binding.text.text = description

        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(dataEvent?.link))
        binding.goToWeb.setOnClickListener{
            startActivity(intent)
        }
    }
}