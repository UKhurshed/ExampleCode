package com.amazoni.islandot.refacContent.refFragments.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RoundViewModel : ViewModel() {

    private val _controller = MutableLiveData<Int>()
    val controller: LiveData<Int>
        get() = _controller

    private val _bet = MutableLiveData<Int>()
    val bet: LiveData<Int>
        get() = _bet

    private val _total = MutableLiveData<Int>()
    val total: LiveData<Int>
        get() = _total

    private val _mini = MutableLiveData<Int>()
    val mini: LiveData<Int>
        get() = _mini

    init {
        _controller.value = 0
        _bet.value = 0
        _total.value = 0
        _mini.value = 0
    }

    fun addBet(bet: Int) {
        _bet.value = (_bet.value)?.plus(bet)
    }

    fun addMini(mini: Int) {
        _mini.value = (_mini.value)?.plus(mini)
    }

    fun addTotal(total: Int) {
        _total.value = (_total.value)?.plus(total)
    }

    fun zeroFields() {
        _bet.value = 0
        _controller.value = 0
        _total.value = 0
        _mini.value = 0
    }

    fun addController() {
        _controller.value = (_controller.value)?.plus(1)
    }

    fun minusController() {
        if (_controller.value == 0) {
            _controller.value = 0
        } else {
            _controller.value = (_controller.value)?.minus(1)
        }
    }
}