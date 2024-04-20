package com.kraskibrancini.sweatify.ui.features.challenge

import android.content.Context
import androidx.lifecycle.ViewModel
import com.kraskibrancini.sweatify.core.PreferencesManager
import com.kraskibrancini.sweatify.ui.features.challenge.create.Challenge
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChallengeViewModel @Inject constructor() : ViewModel() {

    fun createChallenge(challenge: Challenge, context: Context) {
        //TODO call API
        val installId = PreferencesManager(context).getString(PreferencesManager.INSTALL_ID)

    }
}