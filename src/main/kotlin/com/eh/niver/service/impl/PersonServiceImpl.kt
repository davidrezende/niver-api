package com.eh.niver.service.impl

import com.eh.niver.model.Person
import com.eh.niver.model.vo.RequestUpdatePasswordPerson
import com.eh.niver.model.vo.RequestUpdatePerson
import com.eh.niver.repository.PersonRepository
import com.eh.niver.service.AuthenticationService
import com.eh.niver.service.PersonService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.*

@Service
class PersonServiceImpl(val repository: PersonRepository,
                        @Lazy val authenticationService: AuthenticationService): PersonService {


    companion object{
        private val logger = LoggerFactory.getLogger(PersonServiceImpl::class.java)
    }

    override fun getPersonById(idPerson: Long): Person {
        logger.info("Procurando pessoa por ID: $idPerson")
        val person = repository.findByIdPerson(idPerson)
        if(person.isEmpty){
            throw Exception("Pessoa não encontrada.")
        }
        return person.get()
    }

    override fun getBirthdaysToday(): List<Person>? {
        logger.info("M=getBirthdaysToday msg=Buscando aniversariantes do dia")
        val today = LocalDate.now()
        return repository.findByBirthdaysForToday(today)
    }

    override fun getPersonByEmail(email: String): Optional<Person> {
        logger.info("Procurando pessoa por Email: $email")
        return repository.findByEmail(email)
    }

    override fun savePerson(person: Person): Person {
        logger.info("Salvando uma pessoa: $person")
        return repository.save(person)
    }

    override fun updatePerson(request: RequestUpdatePerson): Person {
        try{
            logger.info("M=updatePerson msg=init_update_person idPerson:${request.idPerson}")
            val personExists = getPersonById(request.idPerson)
            authenticationService.authenticate(personExists.email, request.confirmPassword)
            val personUpdated = personExists.apply {
                name = request.name
                email = request.email
                birthday = request.birthday
            }
            return repository.save(personUpdated)
        }catch (e: Exception){
            logger.error("M=updatePerson E=error_update_person idPerson:${request.idPerson} e:$e")
            throw e
        }
    }

    override fun updatePasswordPerson(request: RequestUpdatePasswordPerson): Person {
        try{
            logger.info("M=updatePasswordPerson msg=init_update_password_person idPerson:${request.idPerson}")
            val personExists = getPersonById(request.idPerson)
            authenticationService.authenticate(personExists.email, request.password)
            val personUpdated = personExists.apply {
                password = authenticationService.generateBCryptPassword(request.newPassword)
            }
            return repository.save(personUpdated)
        }catch (e: Exception){
            logger.error("M=updatePasswordPerson E=error_update_password_person idPerson:${request.idPerson} e:$e")
            throw e
        }
    }

    override fun deletePerson(personId: String) {
        logger.info("Deletando uma pessoa $personId")
        return repository.deleteById(personId.toLong())
    }

}