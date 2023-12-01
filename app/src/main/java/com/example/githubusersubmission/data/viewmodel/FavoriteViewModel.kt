package com.example.githubusersubmission.data.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.githubusersubmission.data.FavoriteRepo
import com.example.githubusersubmission.data.database.FavoriteUser

class FavoriteViewModel (application: Application): ViewModel(){
    private val mFavoriteRepository: FavoriteRepo = FavoriteRepo(application)
    fun getAllFavorite(): LiveData<List<FavoriteUser>> = mFavoriteRepository.getAllFavorite()
}