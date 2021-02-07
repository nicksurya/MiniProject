package com.testing.miniproject.presentation.detail.component.entities

class TextComponent(id : String, title : String, validations : List<Validations>, enabledChain : Boolean, val hint : String, val inputType : TextInputType)
    : BaseComponent(id, title, validations, enabledChain)