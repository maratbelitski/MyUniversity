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
import com.example.myuniversity.databinding.ActivityFavoriteBinding
import com.example.myuniversity.pojo.University

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var favoriteAdapter: UniversityAdapter
    private val myViewModel: FavoriteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()

        observerLiveData()

        doListeners()
    }

    fun launchIntent(context: Context): Intent {
        return Intent(context, FavoriteActivity::class.java)
    }

    private fun initViews() {
        favoriteAdapter = UniversityAdapter()
        binding.favoriteRecycler.adapter = favoriteAdapter
    }

    private fun observerLiveData() {
        myViewModel.loadFavoriteUniversity().observe(this, Observer {
            favoriteAdapter.listUniversity = it

            val isEmpty = favoriteAdapter.listUniversity.isEmpty()
            if (isEmpty) {
                binding.textIsEmpty.visibility = View.VISIBLE
            } else {
                binding.textIsEmpty.visibility = View.INVISIBLE
            }
        })
    }

    private fun doListeners() {
        favoriteAdapter.myClickListener = object : UniversityAdapter.MyClickListener {
            override fun myClick(university: University) {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(university.webPages[0])
                startActivity(intent)
            }

            override fun myClickFavorite(university: University) {
                university.isFavorite = false
                myViewModel.removeFavoriteUniversity(university)
            }
        }
    }
}