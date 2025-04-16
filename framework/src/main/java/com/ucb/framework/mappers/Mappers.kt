package com.ucb.framework.mappers

import com.ucb.domain.Book
import com.ucb.framework.bookDB.BookTable
import com.ucb.framework.dto.BookDto



fun BookDto.toModel(): Book {
    return Book(
        titulo = title ?: "Sin título",
        autor = author_name ?: listOf("Autor desconocido"),
        anio = first_publish_year ?: -1
    )
}

fun BookTable.toModel(): Book {
    return Book(
        titulo = titulo,
        autor = autor,
        anio = anio
    )
}

fun Book.toEntity(): BookTable {
    return BookTable(
        titulo = titulo,
        autor = autor,
        anio = anio
    )
}