package com.example.passwordsaver.ui.password_list

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.passwordsaver.data.Password
import com.example.passwordsaver.data.PasswordRepository
import com.example.passwordsaver.util.Routes
import com.example.passwordsaver.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/*
With Hilt, the repository dependency for this view model is defined in the
module AppModule. Also, Hilt will use this definition of a view model to create
instances for me wherever I use it as a dependency.
*/
@HiltViewModel
class PasswordListViewModel @Inject constructor(
    @ApplicationContext appContext: Context,
    private val repository: PasswordRepository,
): ViewModel() {

    val passwords = repository.getPasswords()

    /*
    This is for one time events, and I am using a channel instead of a shared
    flow because I only have one observer. UiEvent is a sealed class in the
    util package defining the ui events that I want.
    */
    private val _uiEvent =  Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    //To cache the recently deleted to-do
    private var deletedPassword: Password? = null

    private val clipboard = appContext.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

    /*
    Like UiEvent, PasswordListEvent is a sealed class. This sealed class is in this
    same package, and it defines the ui events for this screen. Those
    definitions are implemented here.

    Some of these PasswordListEvent implementations launch a coroutine scope. That
    is because the repository functions are suspend functions. The repository
    functions are suspend functions because they are making requests to a
    database.
    */
    fun onEvent(event: PasswordListEvent) {
        when(event) {
            is PasswordListEvent.OnPasswordCopiedClick -> {
                viewModelScope.launch {
                    val clip: ClipData = ClipData.newPlainText(
                        event.password.title + " password copied.",
                        event.password.passwordValue
                    )
                    clipboard.setPrimaryClip(clip)
                    sendUiEvent(
                        UiEvent.ShowSnackbar(
                            message = event.password.title + " password copied."
                        )
                    )
                }
            }
            is PasswordListEvent.OnPasswordVisibleClick -> {
                viewModelScope.launch {
                    repository.insertPassword(
                        event.password.copy(
                            isVisible = event.isVisible
                        )
                    )
                }
            }
            is PasswordListEvent.OnPasswordEditClick -> {
                sendUiEvent(UiEvent.Navigate(
                    Routes.ADD_EDIT_PASSWORD + "?passwordId=${event.password.id}")
                )
            }
            is PasswordListEvent.OnPasswordDeleteClick -> {
                viewModelScope.launch {
                    deletedPassword = event.password
                    repository.deletePassword(event.password)
                    sendUiEvent(UiEvent.ShowSnackbar(
                        message = event.password.title + " password deleted.",
                        action = "Undo"
                    ))
                }
            }
            is PasswordListEvent.OnAddPasswordClick -> {
                sendUiEvent(UiEvent.Navigate(Routes.ADD_EDIT_PASSWORD))
            }
            is PasswordListEvent.OnUndoDeleteClick -> {
                deletedPassword?.let { password ->
                    viewModelScope.launch {
                        repository.insertPassword(password)
                    }
                }
            }
        }
    }

    /*
    _uiEvent is a channel, and the channel's send function is a suspend
    function, so it needs to be executed in a coroutine.
    */
    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}
