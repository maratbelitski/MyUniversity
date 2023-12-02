package com.example.myuniversity

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.myuniversity.adapters.UniversityAdapter
import com.example.myuniversity.databinding.ActivityDetailBinding
import com.example.myuniversity.pojo.University

private const val COUNTRY = "country"

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val myViewModel: DetailViewModel by viewModels()
    private lateinit var listFromAPI: List<University>
    private var listFromDB: List<University> = mutableListOf()
    private lateinit var universityAdapter: UniversityAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        myViewModel.loadCountryUniversity(intent.getStringExtra(COUNTRY)!!)

        initViews()

        observerLiveData()

        doListeners()

    }

    fun launchIntent(context: Context, country: String): Intent {
        val intent = Intent(context, DetailActivity::class.java)
        intent.putExtra(COUNTRY, country)
        return intent
    }
    private fun observerLiveData(){
        myViewModel.progressBarLD.observe(this, Observer {
            if (it == false) {
                binding.progressBar.visibility = View.INVISIBLE
            } else {
                binding.progressBar.visibility = View.VISIBLE
            }
        })

        myViewModel.loadFavoriteUniversity().observe(this, Observer {
            listFromDB = it
        })

        myViewModel.loadCountryUniversityLD.observe(this, androidx.lifecycle.Observer {

            listFromAPI = it

            for (country: University in listFromAPI.listIterator())
                for (db: University in listFromDB.listIterator()) {
                    if (country.name == db.name) {
                        country.isFavorite = true
                    }
                }

            universityAdapter.listUniversity = listFromAPI
        })
    }
    private fun initViews(){
        universityAdapter = UniversityAdapter()
        binding.recycleUniversity.adapter = universityAdapter
    }

    private fun doListeners() {
        universityAdapter.myClickListener = object : UniversityAdapter.MyClickListener {
            override fun myClick(university: University) {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(university.webPages[0])
                startActivity(intent)
            }

            override fun myClickFavorite(university: University) {
                val favorite = university.isFavorite
                if (!favorite) {
                    university.isFavorite = true
                    myViewModel.insertUniversityDB(university)
                } else {
                    university.isFavorite = false
                    myViewModel.removeFavoriteUniversity(university)
                }
            }
        }
    }
}