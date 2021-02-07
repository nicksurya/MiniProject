package com.testing.miniproject.presentation.detail.component.entities

class SpinnerComponent (id : String, title : String, validations : List<Validations>, enabledChain : Boolean, val hint : String, val dataList : List<String>)
    : BaseComponent(id, title, validations, enabledChain)