package com.example.demoforajob.zzz_others

sealed class MyResponce<T> {
    class Loading<T>(var cond:Boolean) : MyResponce<T>()
    class Success<T>(var data: T) : MyResponce<T>()
    class Message<T>(var message: String) : MyResponce<T>()
    class Error<T>(var error: String) : MyResponce<T>()
}