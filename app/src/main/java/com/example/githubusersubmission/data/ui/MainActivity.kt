package com.example.githubusersubmission.data.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubusersubmission.R
import com.example.githubusersubmission.data.adapter.GitHubAdapter
import com.example.githubusersubmission.data.response.ItemsItem
import com.example.githubusersubmission.data.settings.SettingPreferences
import com.example.githubusersubmission.data.settings.dataStore
import com.example.githubusersubmission.data.viewmodel.MainViewModel
import com.example.githubusersubmission.data.viewmodel.SettingViewModel
import com.example.githubusersubmission.data.viewmodel.SettingViewModelFactory
import com.example.githubusersubmission.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread.sleep(3000)
        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = SettingPreferences.getInstance(application.dataStore)
        val settingViewModel =
            ViewModelProvider(this, SettingViewModelFactory(pref))[SettingViewModel::class.java]

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        settingViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        with(binding) {
            srView.setupWithSearchBar(srBar)
            srView.editText.setOnEditorActionListener { _, _, _ ->
                srBar.text = srView.text
                srView.hide()
                viewModel.findUser(srView.text.toString())
                viewModel.User.observe(this@MainActivity) { userList ->
                    if (userList.isNullOrEmpty()) {
                        ifUserNotFound(true)
                    } else {
                        ifUserNotFound(false)
                    }
                }
                false
            }
        }
        viewModel.isLoading.observe(this) {
            showLoading(it)
        }

        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager

        viewModel.User.observe(this) {
            if (it != null) {
                setUserData(it)
            }
        }

        binding.toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.favorites -> {
                    val intent = Intent(this@MainActivity, FavoriteActivity::class.java)
                    startActivity(intent)
                    true
                }

                R.id.settings -> {
                    val intent = Intent(this@MainActivity, SettingActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }

    private fun setUserData(dataUser: List<ItemsItem>) {
        val adapter = GitHubAdapter()
        adapter.submitList(dataUser)
        binding.rvUser.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun ifUserNotFound(isDataNotFound: Boolean) {
        binding.apply {
            rvUser.visibility = if (isDataNotFound) View.GONE else View.VISIBLE
            tvNotFound.visibility = if (isDataNotFound) View.VISIBLE else View.GONE
        }
    }
}
