package com.gabrielsanchez.ac602.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.File
import java.io.FileReader
import java.io.FileWriter

class NotasViewModel(private val context: Context) : ViewModel() {

    private val _contenido = MutableLiveData<String>()

    val contenido: LiveData<String>
        get() = _contenido

    fun setContenido(contenido: String) {
        _contenido.value = contenido
    }

    init {
        // Cargar datos al inicializar el ViewModel
        viewModelScope.launch {
            leerContenidoDesdeArchivo()
        }
    }

    suspend fun guardarContenidoEnArchivo() {
        withContext(Dispatchers.IO) {
            try {
                val archivo = File(context.filesDir, "notas.txt")
                val writer = BufferedWriter(FileWriter(archivo))
                writer.use {
                    it.write(_contenido.value ?: "")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    suspend fun leerContenidoDesdeArchivo() {
        withContext(Dispatchers.IO) {
            try {
                val archivo = File(context.filesDir, "notas.txt")
                val reader = BufferedReader(FileReader(archivo))
                val contenido = StringBuilder()
                var linea: String? = reader.readLine()
                while (linea != null) {
                    contenido.append(linea).append("\n")
                    linea = reader.readLine()
                }
                reader.close()
                _contenido.postValue(contenido.toString())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
