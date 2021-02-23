package com.dino.clubhouse.ui.main.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dino.clubhouse.remote.model.ChannelListResponse
import com.dino.clubhouse.repository.remote.ChannelRemoteDataSource
import com.dino.library.ui.DinoViewModel
import com.dino.library.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val channelRemoteDataSource: ChannelRemoteDataSource,
) : DinoViewModel() {

    private val _channelList = MutableLiveData<List<ChannelListResponse.Channel>>()
    val channelList: LiveData<List<ChannelListResponse.Channel>> = _channelList

    private val _showChatEvent = MutableLiveData<Event<String>>()
    val showChatEvent: LiveData<Event<String>> = _showChatEvent

    init {
        loadChannelList()
    }

    private fun loadChannelList() {
        viewModelScope.launch {
            showLoading()
            _channelList.value = channelRemoteDataSource.getChannelList().channels
            hideLoading()
        }
    }

    fun refresh() {
        loadChannelList()
    }

    fun selectChannel(channel: String) {
        _showChatEvent.value = Event(channel)
    }

}


