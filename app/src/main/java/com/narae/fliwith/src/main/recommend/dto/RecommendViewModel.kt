package com.narae.fliwith.src.main.recommend.dto

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.narae.fliwith.src.main.recommend.RecommendApi
import kotlinx.coroutines.launch

private const val TAG = "RecommendViewModel_싸피"
class RecommendViewModel : ViewModel() {
    private val _selectedRegionButtonText = MutableLiveData<String>()
    private val _selectedTypeButtonText = MutableLiveData<String>()
    private val _selectedDisableButtonText = MutableLiveData<String>()
    private val _selectedMemberButtonText = MutableLiveData<String>()
    private val _selectedDate = MutableLiveData<String>()

    private val _tourData = MutableLiveData<TourResponse?>()
    val tourData: MutableLiveData<TourResponse?> get() = _tourData

    private val _tourRequest = MutableLiveData<TourRequest>()
    val tourRequest: LiveData<TourRequest> get() = _tourRequest

    fun setTourRequest(request: TourRequest) {
        _tourRequest.value = request
    }

    val selectedRegionButtonText: LiveData<String> get() = _selectedRegionButtonText
    val selectedTypeButtonText: LiveData<String> get() = _selectedTypeButtonText
    val selectedDisableButtonText: LiveData<String> get() = _selectedDisableButtonText
    val selectedMemberButtonText: LiveData<String> get() = _selectedMemberButtonText
    val selectDate: LiveData<String> get() = _selectedDate
    fun setSelectedRegionButtonText(text: String) {
        _selectedRegionButtonText.value = text
    }

    fun setSelectedTypeButtonText(text: String) {
        _selectedTypeButtonText.value = text
    }

    fun setSelectedMemberButtonText(text: String) {
        _selectedMemberButtonText.value = text
    }

    // 장애 선택
    fun setSelectedDisableButtonText(text: String) {
        _selectedDisableButtonText.value = text
    }

    fun fetchTourData(request: TourRequest, callback: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                val response = RecommendApi.recommendService.selectAll(request)
                if (response.isSuccessful) {
                    val tourData = response.body()
                    _tourData.value = tourData
                    callback(true)
                } else {
                    Log.e(TAG, "Response not successful: ${response.errorBody()}")
                    callback(false)
                }
            } catch (e: Exception) {
                Log.e(TAG, "API call failed", e)
                callback(false)
            }
        }
    }


    fun setSelectDate(text: String) {
        _selectedDate.value = text
    }

    fun getPeopleNum(text: String): Int {
        return when (text) {
            "1인" -> 1
            "2인" -> 2
            "3인" -> 3
            "4인 이상" -> 4
            else -> 0 // 기본 값 (알 수 없는 경우)
        }
    }


}