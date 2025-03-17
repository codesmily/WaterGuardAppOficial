package br.com.fiap.waterguardapp.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DicasScreen() {
    var searchQuery by remember { mutableStateOf("") }

    // Lista de dicas
    val tips = listOf(
        Tip("Feche a torneira", "Feche a torneira enquanto escova os dentes para economizar água."),
        Tip("Banhos rápidos", "Opte por banhos mais curtos para reduzir o consumo de água."),
        Tip("Use balde", "Utilize um balde para lavar o carro, economizando água em comparação com a mangueira."),
        Tip("Reaproveite a água", "Use água da chuva para regar plantas e limpar áreas externas."),
        Tip("Conserte vazamentos", "Verifique e conserte vazamentos para evitar desperdício de água."),
        Tip("Instale redutores de vazão", "Utilize dispositivos que limitam o fluxo de água para reduzir o consumo.")
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Dicas para economizar água") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* Ação para adicionar uma nova dica */ }
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Adicionar Dica")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Campo de pesquisa
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Pesquisar dica") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
            // Lista rolável de cards filtrados conforme o campo de pesquisa
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(tips.filter {
                    it.title.contains(searchQuery, ignoreCase = true) ||
                            it.description.contains(searchQuery, ignoreCase = true)
                }) { tip ->
                    TipCard(tipTitle = tip.title, tipDescription = tip.description)
                }
            }
        }
    }
}

data class Tip(val title: String, val description: String)

@Composable
fun TipCard(tipTitle: String, tipDescription: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = tipTitle,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = tipDescription,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            // Componente adicional: botão para marcar a dica como útil
            Button(
                onClick = { /* ação ao marcar a dica como útil */ },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Útil")
            }
        }
    }
}