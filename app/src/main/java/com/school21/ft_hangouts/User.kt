package com.school21.ft_hangouts

class User {
    var id = 0
    var name : String = ""
    var surname : String = ""
    var phone : String = ""
    var organization : String = ""
    var email : String = ""

    override fun toString(): String {
        return "$id $name $phone"
    }
}
