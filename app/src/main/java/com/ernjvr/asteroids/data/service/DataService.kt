package com.ernjvr.asteroids.data.service

import com.ernjvr.asteroids.model.Instruction

object DataService {

    val instructions = listOf(
        Instruction(1, "Asteroids is a game where random asteroids move across the screen."),
        Instruction(2,"The objective is to navigate your spaceship around the screen and avoid colliding with the moving Asteroids."),
        Instruction(3,"Press the 'PLAY GAME' button to start the game."),
        Instruction(4,"When the game starts, asteroids will be moving across the screen."),
        Instruction(5,"Place your spaceship on the game play area by touching the screen."),
        Instruction(6,"Move your spaceship around the game play area by touching and dragging the spaceship across the screen."),
        Instruction(7,"You have 3 lives. When your spaceship collides with an asteroid, you lose a life and the spaceship disappears from the screen."),
        Instruction(8,"Place your new spaceship on the game play area by touching the screen."),
        Instruction(9,"When all three lives are lost, the game is over, the Game Over screen will be displayed with the final score and the 'GAME OVER' message."),
        Instruction(10,"If a high score is achieved, a 'NEW HIGH SCORE!' message will be displayed."),
        Instruction(11,"Press the 'PLAY GAME' button to start the game to start a new game again.")
    )
}