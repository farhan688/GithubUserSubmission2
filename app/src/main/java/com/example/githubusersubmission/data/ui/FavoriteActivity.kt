package com.example.githubusersubmission.data.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubusersubmission.data.adapter.FavoritesAdapter
import com.example.githubusersubmission.data.viewmodel.FavoriteViewModel
import com.example.githubusersubmission.databinding.ActivityFavoriteBinding
import com.example.githubusersubmission.data.viewmodel.ViewModelFactory

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var viewModel: FavoriteViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager

        viewModel = obtainViewModel(this@FavoriteActivity)
        viewModel.getAllFavorite().observe(this) {
            val adapter = FavoritesAdapter()
            adapter.submitList(it)
            binding.rvUser.adapter = adapter
        }
        binding.btnBack.setOnClickListener {
            finish()
        }
    }
    private fun obtainViewModel(activity: AppCompatActivity): FavoriteViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(FavoriteViewModel::class.java)
    }
}