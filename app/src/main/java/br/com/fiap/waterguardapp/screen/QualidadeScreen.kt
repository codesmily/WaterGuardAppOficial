package br.com.fiap.waterguardapp.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

data class QualidadeAgua(
    val ph: Double,
    val turbidez: Double,
    val temperatura: Double,
    val timestamp: Long
)

class QualidadeViewModel : ViewModel() {

    private val _qualidade = MutableStateFlow<QualidadeAgua?>(null)
    val qualidade: StateFlow<QualidadeAgua?> = _qualidade

    init {
        simularDadosEmTempoReal()
    }

    private fun simularDadosEmTempoReal() {
        viewModelScope.launch {
            while (true) {
                delay(3000) // Atualiza a cada 3 segundos
                _qualidade.value = QualidadeAgua(
                    ph = (6..9).random() + Math.random(),
                    turbidez = (0..100).random().toDouble(),
                    temperatura = (20..30).random().toDouble(),
                    timestamp = System.currentTimeMillis()
                )
            }
        }
    }
}

@Composable
fun QualidadeScreen(viewModel: QualidadeViewModel = viewModel()) {
    val qualidadeState by viewModel.qualidade.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("*Qualidade da Água*", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(24.dp))

        qualidadeState?.let { qualidade ->
            Text("pH: ${"%.2f".format(qualidade.ph)}")
            Text("Turbidez: ${"%.2f".format(qualidade.turbidez)} NTU")
            Text("Temperatura: ${"%.2f".format(qualidade.temperatura)} °C")
            Text("Última atualização: ${formatarDataHora(qualidade.timestamp)}")
        } ?: run {
            CircularProgressIndicator()
        }
    }
}
fun formatarDataHora(timestamp: Long): String {
    val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
    return sdf.format(Date(timestamp))
}