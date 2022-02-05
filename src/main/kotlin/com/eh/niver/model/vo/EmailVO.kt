package com.eh.niver.model.vo

data class EmailVO(
    val to: String,
    val name: String,
    val subject: String = "Feliz aniversário ${name.split(" ").first()}!",
    val from: String = "niverapi@gmail.com",
    val text: String =
            "Hoje vai ser uma festa\n" +
            "Bolo e guaraná\n" +
            "Muito doce pra você\n" +
            "\n" +
            "É o seu aniversário\n" +
            "Vamos festejar\n" +
            "E os amigos receber\n" +
            "\n" +
            "Mil felicidades\n" +
            "E amor no coração\n" +
            "Que a sua vida seja\n" +
            "Sempre doce e emoção\n" +
            "\n" +
            "Bate, bate palma\n" +
            "Que é hora de cantar\n" +
            "Agora todos juntos\n" +
            "Vamos lá!\n" +
            "\n" +
            "Parabéns, parabéns!\n" +
            "Hoje é o seu dia\n" +
            "Que dia mais feliz\n" +
            "\n" +
            "Parabéns, parabéns!\n" +
            "Cante novamente\n" +
            "Que a gente pede bis\n" +
            "\n" +
            "É big, é big\n" +
            "É big, é big, é big\n" +
            "É hora, é hora\n" +
            "É hora, é hora, é hora\n" +
            "Rá-tim-bum! ")