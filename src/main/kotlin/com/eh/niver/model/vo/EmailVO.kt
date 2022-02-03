package com.eh.niver.model.vo

data class EmailVO(
    val to: String,
    val name: String,
    val subject: String = "Feliz aniversário $name!",
    val from: String = "niverapi@gmail.com",
    val text: String = "Parabéns!")