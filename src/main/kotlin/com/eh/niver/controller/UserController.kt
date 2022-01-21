package com.eh.niver.controller

import com.eh.niver.model.Person
import com.eh.niver.model.vo.RequestLogin
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("gasaigay/api")
class UserController {

    @GetMapping("/calcu/{a}/{op}/{b}")
    fun somar(@PathVariable a: Int, @PathVariable op: String, @PathVariable b: Int): String {
        try {
            val operacao: Int = when (op) {
                "+" -> (a + b)
                "-" -> (a - b)
                "*" -> (a * b)
                "div" -> (a / b)
                "res" -> (a % b)
                else -> throw NullPointerException("Erro na op :)")
            }
            return operacao.toString()
        } catch (e: Exception) {
            return "Pow deu erro: ${e.message}."
        }

    }

    @PostMapping("/logar")
    fun login(@RequestBody dados: RequestLogin): String {
        return ("Usuário: ${dados.user}  Senha: ${dados.password}  Cpf: ${dados.cpf}")
    }


}
