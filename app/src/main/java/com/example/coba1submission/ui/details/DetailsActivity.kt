package com.example.coba1submission.ui.details

import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.coba1submission.R
import com.example.coba1submission.data.response.ListEventsItem
import com.example.coba1submission.databinding.ActivityDetailsBinding
import com.example.coba1submission.databinding.ActivityMainBinding
import com.example.coba1submission.databinding.FragmentDashboardBinding

class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dataEvent = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra<ListEventsItem>("key_hero", ListEventsItem::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra<ListEventsItem>("key_hero")
//            intent.getSerializableExtra("key_review") as? ListEventsItem
        }

        // Ganti teks dari TextView dengan ID 'text'
//        binding.text.text = "Teks baru dari View Binding!"
        binding.text.text = dataEvent?.name
    }
}