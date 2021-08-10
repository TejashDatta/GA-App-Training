/*
 * Copyright 2019, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.example.android.marsrealestate.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.marsrealestate.network.MarsApi
import com.example.android.marsrealestate.network.MarsProperty
import kotlinx.coroutines.launch
import java.lang.Exception

class OverviewViewModel : ViewModel() {
  private val _response = MutableLiveData<String>()
  val response: LiveData<String>
    get() = _response

  private val _properties = MutableLiveData<List<MarsProperty>>()
  val properties: LiveData<List<MarsProperty>>
    get() = _properties

  init {
    getMarsRealEstateProperties()
  }

  private fun getMarsRealEstateProperties() {
    viewModelScope.launch {
      try {
        _properties.value = MarsApi.retrofitService.getProperties()
        _response.value = "Success: Mars properties retrieved"
      }
      catch (e: Exception) {
        _response.value = "Failure: ${e.message}"
      }
    }
  }
}
