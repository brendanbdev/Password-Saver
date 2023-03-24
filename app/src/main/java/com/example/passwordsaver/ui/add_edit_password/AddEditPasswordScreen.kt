package com.example.passwordsaver.ui.add_edit_password

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.passwordsaver.util.UiEvent

@Composable
fun AddEditPasswordScreen(
    onPopBackStack: () -> Unit,
    viewModel: AddEditPasswordViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when(event) {
                is UiEvent.PopBackStack -> onPopBackStack()
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.action
                    )
                }
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
                viewModel.onEvent(AddEditPasswordEvent.OnSavePasswordClick)
            }) {
                Icon(
                    imageVector = Icons.Default.Check,
                    tint = MaterialTheme.colors.background,
                    contentDescription = "This is the button to save the password you input with the title you gave it."
                )
            }
        }
    ) { it ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            TextField(
                value = viewModel.title,
                onValueChange = {
                    viewModel.onEvent(AddEditPasswordEvent.OnTitleChange(it))
                },
                placeholder = {
                    Text(text = "Title")
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = viewModel.passwordValue,
                onValueChange = {
                    viewModel.onEvent(AddEditPasswordEvent.OnPasswordValueChange(it))
                },
                placeholder = {
                    Text(text = "Password")
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = false,
                maxLines = 5
            )
        }
    }
}
