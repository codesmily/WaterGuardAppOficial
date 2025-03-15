package br.com.fiap.waterguardapp

import android.os.Bundle
import android.view.Menu
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import br.com.fiap.waterguardapp.ui.theme.WaterGuardAppTheme
import br.com.fiap.waterguardapp.screen.DicasScreen

import br.com.fiap.waterguardapp.screen.QualidadeScreen
import br.com.fiap.waterguardapp.screen.ImpactoScreen
import br.com.fiap.waterguardapp.screen.LoginScreen
import br.com.fiap.waterguardapp.screen.Navigation
import br.com.fiap.waterguardapp.screen.RegisterScreen
import com.fiap.waterguardapp.screen.DashboardScreen

@Composable
fun WaterGuardApp(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color(0xFFe3f2fd)), // Cor de fundo azul claro
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = { navController.navigate("dashboard") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3)) // Azul
        ) {
            Text(
                text = "Dashboard",
                style = MaterialTheme.typography.titleMedium,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { navController.navigate("qualidade") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF42a5f5)) // Azul claro
        ) {
            Text(
                text = "Qualidade da Água",
                style = MaterialTheme.typography.titleMedium,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(16.dp))


        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { navController.navigate("dicas") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1976D2)) // Azul mais escuro
        ) {
            Text(
                text = "Dicas",
                style = MaterialTheme.typography.titleMedium,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { navController.navigate("impacto") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1565C0)) // Azul mais escuro
        ) {
            Text(
                text = "Impacto",
                style = MaterialTheme.typography.titleMedium,
                color = Color.White
            )
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val userViewModel: UserViewModel = viewModel()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(navController = navController, userViewModel = userViewModel)
        }
        composable("register") {
            RegisterScreen(navController = navController, userViewModel = userViewModel)
        }
        composable("navigation") {
            // Aqui, o usuário será redirecionado para a tela de navegação após o login
            Navigation()  // Tela de navegação com várias opções
        }
    }
}





class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WaterGuardAppTheme {
                AppNavigation() // Configuração de navegação
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    WaterGuardAppTheme {
        Navigation() // Preview da navegação
    }
}
