package com.murray.entities.accounts

class UserSignUp(
    name: String,
    surname: String,
    email: Email,
    val tipoDeUsuario: EnumTipoUsuario,
    val visibilidad: EnumVisibilidad
) : User(name, surname, email)