package com.example.githubusersubmission.data.database

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
@Parcelize
class FavoriteUser (
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "name_user")
    var username : String = "",
    @ColumnInfo(name = "pic_user")
    var avatarUrl : String? = null
) : Parcelable