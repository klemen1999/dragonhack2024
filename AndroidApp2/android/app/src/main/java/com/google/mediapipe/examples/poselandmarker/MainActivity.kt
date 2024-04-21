/*
 * Copyright 2023 The TensorFlow Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.mediapipe.examples.poselandmarker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.mediapipe.examples.poselandmarker.compose.ComposeMainActivity
import com.google.mediapipe.examples.poselandmarker.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var activityMainBinding: ActivityMainBinding
    private val viewModel : MainViewModel by viewModels()

    //register listeer for stateFlowOfColors in viewmodel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //getIntent extras
        val extras = intent.extras
        val challengeId = extras?.getString("challengeId")
        val userId = extras?.getInt("userId")

        viewModel.userId = userId ?: 0
        viewModel.challengeId = challengeId ?: ""

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        //find @+id/button_send_data and setOnClickListener
        activityMainBinding.buttonSendData.setOnClickListener {
            viewModel.sendData()
            val intent = Intent(this, ComposeMainActivity::class.java)
            startActivity(intent)
        }



        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        //set background to fragment_container
        navHostFragment.view?.setBackgroundColor(resources.getColor(R.color.mp_color_secondary))

        viewModel.stateFlowOfColors.observe(this, Observer {
            if (!it) {
                activityMainBinding.navigation.setBackgroundColor(resources.getColor(R.color.mp_color_error))
                //setcolor of navbar
            } else {
                activityMainBinding.navigation.setBackgroundColor(resources.getColor(R.color.mp_color_secondary))
            }
            //find @+id/textView
            activityMainBinding.textView.text = "${viewModel.counter.toInt()}"
        })
        val navController = navHostFragment.navController
        activityMainBinding.navigation.setupWithNavController(navController)
        activityMainBinding.navigation.setOnNavigationItemReselectedListener {
            // ignore the reselection
        }
    }

    override fun onBackPressed() {
        finish()
    }
}