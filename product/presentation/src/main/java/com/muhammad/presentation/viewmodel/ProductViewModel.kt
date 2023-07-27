package com.muhammad.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.core.core_ui.Resource
import com.muhammad.domain.usecase.ProductUseCase
import com.muhammad.presentation.state.ProductState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(private val productUseCase: ProductUseCase) :
    ViewModel() {

    private val _productStateFlow = mutableStateOf(ProductState())
    val productStateFlow : State<ProductState> = _productStateFlow

    init {
        getProductListing()
    }

    private fun getProductListing() {
        productUseCase().onEach {
            when (it) {
                is Resource.Loading -> {
                    _productStateFlow.value = ProductState(loading  = true)
                }
                is Resource.Error -> {
                    _productStateFlow.value = ProductState(error = it.message)
                }
                is Resource.Success -> {
                    _productStateFlow.value = ProductState(product = it.data)
                }
            }

        }.launchIn(viewModelScope)
    }

}