package com.eh.niver.service

import com.eh.niver.model.Group
import com.eh.niver.model.Person
import com.eh.niver.model.vo.RequestSaveGroup
import com.eh.niver.model.vo.RequestSaveMember
import com.eh.niver.model.vo.ResponseGroup

interface GroupService {
    fun saveGroup(group: RequestSaveGroup): Group
    fun deleteGroup(groupId: String)
    fun getGroupById(groupId: String): ResponseGroup
    fun updateGroup(group: RequestSaveGroup): Group
    fun saveMemberInGroup(member: RequestSaveMember)
    fun deleteMemberInGroup(idPerson: String, idGroup: String)
    fun searchAllGroupsByMember(personId: Long): List<ResponseGroup>
    fun searchAllMembersByGroup(groupId: Long): MutableList<Person>?
}