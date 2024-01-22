package com.murray.data.accounts

class UserSignUp(
    name: String,
    surname: String,
    email: Email,
    val tipoDeUsuario: EnumTipoUsuario,
    val visibilidad: EnumVisibilidad
) : User(name, surname, email)