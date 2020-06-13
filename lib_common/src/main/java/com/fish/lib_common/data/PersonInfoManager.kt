package com.fish.lib_common.data


import com.fish.lib_common.bean.PersonInfo

class PersonInfoManager private constructor() {

    private var personInfo: PersonInfo? = null

    companion object {
        val instance: PersonInfoManager = Holder.instance
    }

    private object Holder {
        val instance = PersonInfoManager()
    }

    public fun setPersonInfo(personInfo: PersonInfo) {
        this.personInfo = personInfo
    }

    public fun getPersonInfo(): PersonInfo? {
        return personInfo
    }

    public fun getToken(): String {
        return personInfo?.token ?: ""
    }

}


