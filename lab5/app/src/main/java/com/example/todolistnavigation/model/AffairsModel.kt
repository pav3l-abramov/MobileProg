package com.example.todolistnavigation.model

class AffairsModel () {
    var id: Int = 0
        set(value){
            field = value
        }
        get(){
            return field
        }
    var status: Boolean = false
        set(value){
            field = value
        }
        get(){
            return field
        }
    var affairs: String = ""
        set(value){
            field = value
        }
        get(){
            return field
        }
}