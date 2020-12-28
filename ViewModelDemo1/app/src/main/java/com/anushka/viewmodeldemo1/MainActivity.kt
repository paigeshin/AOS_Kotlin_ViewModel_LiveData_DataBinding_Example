package com.anushka.viewmodeldemo1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.anushka.viewmodeldemo1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainActivityViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        /** Data Binding with LiveData **/
        binding.lifecycleOwner = this

        /** Data Binding with ViewModel **/
        binding.myViewModel = viewModel


//        viewModel.count.observe(this, Observer {
//          binding.countText.text = it.toString()
//        })
        // => This code is replaced by `android:text="@{String.valueOf(myViewModel.count)}"`



//        binding.button.setOnClickListener {
//           viewModel.updateCount()
//        }
        // => This code is replaced by `android:onClick="@{()-> myViewModel.updateCount()}"`

    }
}
