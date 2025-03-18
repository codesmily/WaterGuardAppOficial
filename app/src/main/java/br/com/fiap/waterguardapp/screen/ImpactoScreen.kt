package br.com.fiap.waterguardapp.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ImpactoScreen() {
    var showInputDialog by remember { mutableStateOf(false) } // Controle de exibição do input dialog
    var tempoBanho by remember { mutableStateOf(TextFieldValue()) } // Tempo do banho
    var tempoLouça by remember { mutableStateOf(TextFieldValue()) } // Tempo na louça
    var tempoMaquina by remember { mutableStateOf(TextFieldValue()) } // Tempo na máquina de lavar
    var resultadoGasto by remember { mutableStateOf("") } // Resultado do cálculo de gasto
    var avisoGasto by remember { mutableStateOf("") } // Aviso sobre o gasto de água
    var avisoCor by remember { mutableStateOf(Color.Transparent) } // Cor do aviso (verde ou vermelho)

    // Função para calcular o gasto de água com base no tempo
    fun calcularGasto() {
        val tempoBanhoInt = tempoBanho.text.toIntOrNull() ?: 0
        val tempoLouçaInt = tempoLouça.text.toIntOrNull() ?: 0
        val tempoMaquinaInt = tempoMaquina.text.toIntOrNull() ?: 0

        // Cálculos baseados no tempo (valores mais realistas)
        val gastoBanho = tempoBanhoInt * 12 // 12 litros por minuto no banho (valor médio)
        val gastoLouça = tempoLouçaInt * 6 // 6 litros por minuto lavando a louça (valor médio)
        val gastoMaquina = tempoMaquinaInt * 50 // 50 litros por ciclo na máquina de lavar (valor médio)

        val gastoTotal = gastoBanho + gastoLouça + gastoMaquina
        val gastoMedioSustentavel = 300 // Quantidade recomendada de água para consumo sustentável por dia (ajustado para valores realistas)

        // Exibe o resultado
        resultadoGasto = "Gasto total de água: $gastoTotal litros"

        // Verifica se ultrapassou o gasto médio sustentável
        if (gastoTotal > gastoMedioSustentavel) {
            avisoGasto = "Você ultrapassou a quantidade recomendada de água!"
            avisoCor = Color.Red
        } else {
            avisoGasto = "Seu consumo está dentro da quantidade recomendada."
            avisoCor = Color.Green
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 80.dp, bottom = 80.dp, start = 25.dp, end = 25.dp) // Margens ajustadas
    ) {
        // Mostrar o Card apenas se o formulário não estiver visível
        if (!showInputDialog) {
            // Card principal
            Card(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxWidth()
                    .padding(bottom = 32.dp), // Ajuste de distância abaixo do Card
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                shape = MaterialTheme.shapes.medium
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Impacto do Consumo de Água",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = "A quantidade máxima recomendada de água que uma pessoa pode consumir de forma sustentável é cerca de 300 litros por dia, considerando atividades como banho, lavar louça e roupas.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }

        // Mostrar o resultado abaixo do Card, apenas se não estiver mostrando o formulário
        if (resultadoGasto.isNotEmpty() && !showInputDialog) {
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
            ) {
                // Gasto total com fundo
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                        .background(Color.LightGray.copy(alpha = 0.3f), shape = MaterialTheme.shapes.medium)
                        .padding(16.dp)
                ) {
                    Text(
                        text = resultadoGasto,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier
                    )
                }

                // Aviso sobre o consumo de água com fundo
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 90.dp)
                        .background(avisoCor.copy(alpha = 0.1f), shape = MaterialTheme.shapes.medium)
                        .padding(16.dp)
                ) {
                    Text(
                        text = avisoGasto,
                        color = Color.Black,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier
                    )
                }
            }
        }

        // Botão flutuante para abrir o input
        FloatingActionButton(
            onClick = {
                showInputDialog = true
                // Limpa qualquer resultado anterior ao abrir o formulário
                resultadoGasto = ""
                avisoGasto = ""
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
        }

        // Input Dialog
        if (showInputDialog) {
            // Formulário para inserir tempos
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp)
                    .background(MaterialTheme.colorScheme.background, shape = MaterialTheme.shapes.large)
                    .align(Alignment.Center)
            ) {
                Text("Insira o tempo gasto em minutos")

                TextField(
                    value = tempoBanho,
                    onValueChange = { tempoBanho = it },
                    label = { Text("Tempo no Banho (minutos)") },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                )

                TextField(
                    value = tempoLouça,
                    onValueChange = { tempoLouça = it },
                    label = { Text("Tempo lavando Louça (minutos)") },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                )

                TextField(
                    value = tempoMaquina,
                    onValueChange = { tempoMaquina = it },
                    label = { Text("Tempo na Máquina de Lavar (minutos)") },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                )

                Button(
                    onClick = {
                        calcularGasto()
                        showInputDialog = false // Fechar o input após cálculo
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 16.dp)
                ) {
                    Text("Calcular")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewImpactoScreen() {
    ImpactoScreen()
}