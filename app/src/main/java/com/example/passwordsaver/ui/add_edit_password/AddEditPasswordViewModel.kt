package com.example.passwordsaver.ui.add_edit_password

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.passwordsaver.data.Password
import com.example.passwordsaver.data.PasswordRepository
import com.example.passwordsaver.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditPasswordViewModel @Inject constructor(
    private val repository: PasswordRepository,
    savedStateHandle: SavedStateHandle,
): ViewModel() {

    var password by mutableStateOf<Password?>(null)
        private set

    var title by mutableStateOf("")
        private set

    var passwordValue by mutableStateOf("")
        private set

    private val _uiEvent =  Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        val passwordId = savedStateHandle.get<Int>("passwordId")!!
        if(passwordId != -1) {
            viewModelScope.launch {
                repository.getPasswordById(passwordId)?.let { password ->
                    title = password.title
                    passwordValue = password.passwordValue
                    this@AddEditPasswordViewModel.password = password
                }
            }
        }
    }

    fun onEvent(event: AddEditPasswordEvent) {
        when(event) {
            is AddEditPasswordEvent.OnTitleChange -> {
                title = event.title
            }
            is AddEditPasswordEvent.OnPasswordValueChange -> {
                passwordValue = event.passwordValue
            }
            is AddEditPasswordEvent.OnSavePasswordClick -> {
                viewModelScope.launch {
                    if(title.isBlank() || passwordValue.isBlank()) {
                        sendUiEvent(UiEvent.ShowSnackbar(
                            message = "Please fill in the title and password."
                        ))
                        return@launch
                    }
                    repository.insertPassword(
                        Password(
                            title = title,
                            passwordValue = passwordValue,
                            isVisible = password?.isVisible ?: false,
                            id = password?.id
                        )
                    )
                    sendUiEvent(UiEvent.PopBackStack)
                }
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}
