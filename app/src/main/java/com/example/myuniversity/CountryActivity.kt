package com.example.myuniversity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.myuniversity.adapters.CountryAdapter
import com.example.myuniversity.databinding.ActivityCountryBinding

class CountryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCountryBinding
    private val myViewModel: CountryViewModel by viewModels()
    private lateinit var spinnerAdapter: CountryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCountryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        myViewModel.loadCountry()

        initViews()

        observerLiveData()

        doListeners()

    }

    private fun initViews(){
        spinnerAdapter = CountryAdapter()
        binding.spinner.adapter = spinnerAdapter
    }

    private fun observerLiveData(){
        myViewModel.progressBarLD.observe(this, Observer {
            if (it == false) {
                binding.progressBar.visibility = View.INVISIBLE
            } else {
                binding.progressBar.visibility = View.VISIBLE
            }
        })

        myViewModel.loadUniversityLD.observe(this, Observer {
            spinnerAdapter.listCountry = it
        })
    }

    private fun doListeners(){
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position > 0) {
                    val intent = DetailActivity().launchIntent(
                        this@CountryActivity,
                        binding.spinner.selectedItem.toString()
                    )
                    startActivity(intent)
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        binding.buttonFavorite.setOnClickListener {
            startActivity(Intent(FavoriteActivity().launchIntent(this)))
        }
    }

    override fun onResume() {
        super.onResume()
        binding.spinner.setSelection(0)
    }
}