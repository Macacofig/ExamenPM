package com.ucb.ucbtest.book

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ucb.ucbtest.R
import kotlinx.coroutines.delay

@Composable
fun BookUI(viewModel: BookViewModel = hiltViewModel()) {
    val scrollState = rememberScrollState()
    var titulo by remember { mutableStateOf("") }

    val libros by viewModel.libros.collectAsState()
    val likeMessage by viewModel.likeMessage.collectAsState()
    val errorState by viewModel.state.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Campo de búsqueda
            OutlinedTextField(
                value = titulo,
                onValueChange = { titulo = it },
                label = { Text("Ingrese el título del libro") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                shape = RoundedCornerShape(12.dp),
                singleLine = true
            )

            // Botón de búsqueda
            OutlinedButton(
                onClick = { viewModel.buscarLibros(titulo) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(text = stringResource(id = R.string.gitalias_btn_find))
            }

            // Mensaje de "like"
            likeMessage?.let { message ->
                LaunchedEffect(message) {
                    delay(2000)
                    viewModel.clearMessage()
                }
                Text(
                    text = message,
                    color = Color(0xFF2E7D32),
                    modifier = Modifier
                        .padding(vertical = 12.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }

            // Mostrar libros encontrados
            if (libros.isNotEmpty()) {
                libros.forEach { libro ->
                    var isLiked by remember { mutableStateOf(false) }

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(6.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFFDFDFD))
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = libro.titulo,
                                    style = TextStyle(
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold
                                    ),
                                    modifier = Modifier.padding(bottom = 4.dp)
                                )

                                Text(
                                    text = libro.autor.joinToString(", "),
                                    style = TextStyle(
                                        fontSize = 14.sp,
                                        fontStyle = FontStyle.Italic,
                                        color = Color.DarkGray
                                    ),
                                    modifier = Modifier.padding(bottom = 2.dp)
                                )

                                Text(
                                    text = "Año: ${libro.anio}",
                                    style = TextStyle(fontSize = 13.sp)
                                )
                            }

                            IconButton(
                                onClick = {
                                    isLiked = !isLiked
                                    viewModel.likeLibro(libro)
                                }
                            ) {
                                Icon(
                                    imageVector = if (isLiked) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                                    contentDescription = "Me gusta",
                                    tint = if (isLiked) Color.Red else Color.Gray
                                )
                            }
                        }
                    }
                }
            } else {
                if (errorState !is BookViewModel.BookState.Error) {
                    Text(
                        text = "Esperando resultados de búsqueda o no se encontraron libros",
                        modifier = Modifier.padding(vertical = 16.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }

            // Botón para ver favoritos
            OutlinedButton(
                onClick = { viewModel.getLibros() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(top = 16.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Ver lista de favoritos")
            }

            // Mostrar error si existe
            if (errorState is BookViewModel.BookState.Error) {
                val errorMessage = (errorState as BookViewModel.BookState.Error).message
                Text(
                    text = "Error: $errorMessage",
                    color = Color.Red,
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
