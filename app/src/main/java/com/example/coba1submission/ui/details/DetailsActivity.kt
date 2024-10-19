package com.example.coba1submission.ui.details

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.text.Html
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.coba1submission.R
import com.example.coba1submission.data.database.Event as DbEvent
import com.example.coba1submission.data.response.Event
import com.example.coba1submission.data.helper.ViewModelFactory
import com.example.coba1submission.data.response.ListEventsItem
import com.example.coba1submission.databinding.ActivityDetailsBinding
import com.example.coba1submission.ui.settings.SettingPreferences
import com.example.coba1submission.ui.settings.SettingsViewModel
import com.example.coba1submission.ui.settings.SettingsViewModelFactory
import com.example.coba1submission.ui.settings.dataStore
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import kotlin.properties.Delegates

class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding
    private lateinit var detailsViewModel: DetailsViewModel
    private lateinit var description: String
    private lateinit var name: String
    private lateinit var ownerName: String
    private lateinit var mediaCover: String
    private lateinit var beginTime: String
    private lateinit var endTime: String
    private lateinit var link: String
    private var registrants by Delegates.notNull<Int>()
    private var quota by Delegates.notNull<Int>()
    private var id by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        title = "Details"
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        detailsViewModel = obtainViewModel(this@DetailsActivity)

        val tryIntent = intent.getParcelableExtra<Parcelable>("key_hero")
        if(tryIntent is ListEventsItem){
            val dataEvent = getIntentValue()
            description = dataEvent?.description ?: "Description"
            name = dataEvent?.name ?: "Name"
            ownerName = dataEvent?.ownerName ?: "Owner Name"
            mediaCover = dataEvent?.mediaCover ?: "Medi Cover"
            beginTime = dataEvent?.beginTime ?: "Begning Time"
            endTime = dataEvent?.endTime ?: "End Time"
            link = dataEvent?.link ?: "Link"
            registrants = dataEvent?.registrants ?: 0
            quota = dataEvent?.quota ?: 0
            id = dataEvent?.id ?: 0
        }else{
            val dataEvent = getIntentValueNew()
            description = dataEvent?.description ?: "description"
            name = dataEvent?.name ?: "Name"
            ownerName = dataEvent?.ownerName ?: "Owner Name"
            mediaCover = dataEvent?.mediaCover ?: "Medi Cover"
            beginTime = dataEvent?.beginTime ?: "Begning Time"
            endTime = dataEvent?.endTime ?: "End Time"
            link = dataEvent?.link ?: "Link"
            registrants = dataEvent?.registrants ?: 0
            quota = dataEvent?.quota ?: 0
            id = dataEvent?.id ?: 0
        }

        val document: Document = Jsoup.parse(description)
        document.select("img").remove() // Menghapus semua tag <img>
        val descriptionWithoutImages = document.html()

        val description =
            Html.fromHtml(descriptionWithoutImages, Html.FROM_HTML_MODE_LEGACY)
        Glide.with(binding.root)
            .load(mediaCover)
            .into(binding.image)

        val sisaQuota = registrants.let {
            quota.minus(
                it
            )
        }
        binding.title.text = name
        binding.subTitle.text = ownerName
        binding.quota.text = StringBuilder("Sisa Quota : ").append(sisaQuota)
        binding.startTime.text = StringBuilder("Mulai : ").append(beginTime)
        binding.endTime.text = StringBuilder("Selesai : ").append(endTime)
        binding.text.text = description

        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
        binding.goToWeb.setOnClickListener{
            startActivity(intent)
        }

        val id = id
        val title = name
        val existingItem = detailsViewModel.getItemById(id)
        if (existingItem){
            binding.fabLike.setImageResource(R.drawable.baseline_favorite_24)
        }

        binding.fabLike.setOnClickListener {
            Log.d("titleIsertDb", "title: $title \n id $id")
            lifecycleScope.launch {
                val item = DbEvent(id, title)

                Log.d("existingItem", "item : $existingItem")
                if (existingItem) {
                    detailsViewModel.delete(item)
                    binding.fabLike.setImageResource(R.drawable.baseline_favorite_border_24)
                    showToast(getString(R.string.disLiked))
                } else {
                    detailsViewModel.insert(item)
                    binding.fabLike.setImageResource(R.drawable.baseline_favorite_24)
                    showToast(getString(R.string.liked))
                }
            }
        }

        val pref = SettingPreferences.getInstance(application.dataStore)
        val mainViewModel = ViewModelProvider(this, SettingsViewModelFactory(pref))[SettingsViewModel::class.java]

        mainViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                binding.goToWeb.setTextColor(ContextCompat.getColor(this, R.color.primary_text_night))
            } else {
                binding.goToWeb.setTextColor(ContextCompat.getColor(this, R.color.primary_surfice))
            }
        }
    }

    private fun getIntentValue(): ListEventsItem?{
        val data = if (Build.VERSION.SDK_INT >= 33) {
            //            intent.getParcelableExtra<ListEventsItem>("key_hero", ListEventsItem::class.java)
            intent.getParcelableExtra("key_hero", ListEventsItem::class.java)
        } else {
            @Suppress("DEPRECATION")
            //            intent.getParcelableExtra<ListEventsItem>("key_hero")
            intent.getParcelableExtra("key_hero")
        }
        return data
    }

    private fun getIntentValueNew(): Event?{
        val data = if (Build.VERSION.SDK_INT >= 33) {
            //            intent.getParcelableExtra<ListEventsItem>("key_hero", ListEventsItem::class.java)
            intent.getParcelableExtra("key_hero", Event::class.java)
        } else {
            @Suppress("DEPRECATION")
            //            intent.getParcelableExtra<ListEventsItem>("key_hero")
            intent.getParcelableExtra("key_hero")
        }
        return data
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun obtainViewModel(activity: AppCompatActivity): DetailsViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[DetailsViewModel::class.java]
    }
}