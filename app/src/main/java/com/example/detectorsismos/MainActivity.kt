package com.example.detectorsismos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.detectorsismos.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var eqAdapter: EqAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvListEarth.layoutManager = LinearLayoutManager(this)


        val viewModel: MainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.eqList.observe(this, Observer { eqList ->
            setupRecycler()
            eqAdapter.submitList(eqList)

        })

    }

    private fun setupRecycler() {
        eqAdapter = EqAdapter()
        binding.rvListEarth.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = eqAdapter
        }

        eqAdapter.onItemClickListener = {
            Snackbar.make(binding.root, it.place, Snackbar.LENGTH_LONG).show()
        }
    }

}