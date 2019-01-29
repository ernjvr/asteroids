package com.ernjvr.asteroids.model

data class Instruction(val number: Int, val description: String) {

    override fun toString(): String {
        return description
    }
}