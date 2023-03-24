package com.example.passwordsaver.ui.password_list

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.passwordsaver.util.UiEvent

@Composable
fun PasswordListScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: PasswordListViewModel = hiltViewModel()
) {
    val passwords = viewModel.passwords.collectAsState(initial = emptyList())
    val scaffoldState = rememberScaffoldState()
    /*
    This launched effect is to create a coroutine scope so that all of the code
    in this launched effect (in the coroutine scope) is executed independently
    from the composable that it is in.
    */
    LaunchedEffect(key1 = true) {
        /*
        We can use the .collect function on a Flow, because uiEvent is
        receiving data as a flow with .receiveAsFlow() (in the view model)
        from the private _uiEvent Channel (also in the view model). Whenever
        PasswordListViewModel's sendUiEvent function is used (it is used in
        PasswordListViewModel's onEvent function, which is used throughout the
        app), PasswordListViewModel's _uiEvent.send is executed and that will
        trigger this .collect function and should check to see if the event
        matches something in a "when" statement.
        */
        viewModel.uiEvent.collect { event ->
            when(event) {
                is UiEvent.ShowSnackbar -> {
                    val result = scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.action
                    )
                    if(result == SnackbarResult.ActionPerformed) {
                        viewModel.onEvent(PasswordListEvent.OnUndoDeleteClick)
                    }
                }
                is UiEvent.Navigate -> onNavigate(event)
                else -> Unit
            }
        }
    }
    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(
                backgroundColor = MaterialTheme.colors.onBackground,
                onClick = {
                viewModel.onEvent(PasswordListEvent.OnAddPasswordClick)
            }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    tint = MaterialTheme.colors.background,
                    contentDescription = "This is the button to add a password to save."
                )
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            items(passwords.value) { password ->
                PasswordItem(
                    password = password,
                    onEvent = viewModel::onEvent,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }
        }
    }
}
