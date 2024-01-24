package com.gabrielsanchez.ac602.views

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import com.gabrielsanchez.ac602.viewmodels.NotasViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotasView(viewModel: NotasViewModel) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "AC602",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
            )
        }
    ) {
        ContentNota(it, viewModel)
    }
}

@SuppressLint("RememberReturnType")
@Composable
fun ContentNota(
    it: PaddingValues,
    viewModel: NotasViewModel,
    context: Context = LocalContext.current
) {
    val contenido: String by viewModel.contenido.observeAsState("")
    Column(
        modifier = Modifier
            .padding(it)
            .padding(top = 30.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = contenido,
            onValueChange = {
                viewModel.setContenido(it)
            },
            label = { Text(text = "Contenido") },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
                .padding(bottom = 15.dp)
        )

        ElevatedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
                .padding(bottom = 15.dp),
            onClick = {
                viewModel.viewModelScope.launch {
                    viewModel.guardarContenidoEnArchivo()
                }
                Toast.makeText(context, "Nota guardada", Toast.LENGTH_SHORT).show()
            },
        ) {
            Text(text = "Guardar nota")
        }
    }
}