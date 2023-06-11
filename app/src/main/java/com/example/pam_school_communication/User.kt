package com.example.pam_school_communication

data class User(var imie :String ="", var nazwisko :String ="", var email :String ="", var rola :Role =Role.Uczeń,
                var id :String = "")

