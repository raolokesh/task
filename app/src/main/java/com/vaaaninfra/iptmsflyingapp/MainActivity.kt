package com.vaaaninfra.iptmsflyingapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.vaaaninfra.iptmsflyingapp.adapter.StateCityAdapter
import com.vaaaninfra.iptmsflyingapp.databinding.ActivityMainBinding
import com.vaaaninfra.iptmsflyingapp.model.StateCityModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var adapter: StateCityAdapter
    private val stateCityList = ArrayList<StateCityModel>()

    private val stateCityMap = mapOf(
        "Haryana" to listOf(
            "Faridabad", "Gurgaon", "Hisar", "Rohtak", "Panipat", "Karnal"

        ),
        "Tamil Nadu" to listOf(
            "Chennai", "Coimbatore", "Madurai", "Tiruchirappalli", "Salem"

        ),
        "Madhya Pradesh" to listOf(
            "Indore", "Bhopal", "Jabalpur", "Gwalior", "Ujjain"

        ),
        "Rajasthan" to listOf(
            "Jaipur", "Jodhpur", "Kota", "Bikaner", "Ajmer"

        ),
        "Maharashtra" to listOf(
            "Mumbai", "Pune", "Nagpur", "Thane", "Nashik"

        ),


    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = StateCityAdapter(this, stateCityList,stateCityMap)
        binding.recyclerView.adapter = adapter

        binding.addMore.setOnClickListener {
            // Add a new empty item to the list and notify the adapter
            stateCityList.add(StateCityModel())
            adapter.notifyItemInserted(stateCityList.size - 1)
            binding.recyclerView.post {
                binding.recyclerView.smoothScrollToPosition(stateCityList.size - 1)
            }
        }

    }
}