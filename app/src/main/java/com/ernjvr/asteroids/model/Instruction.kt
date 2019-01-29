package com.ernjvr.asteroids.model

data class Instruction(val description: String) {

    override fun toString(): String {
        return description
    }
}