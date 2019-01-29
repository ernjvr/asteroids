package com.ernjvr.asteroids.data.service

import com.ernjvr.asteroids.model.Instruction

object DataService {

    val instructions = listOf(
        Instruction("Asteroids is a game where random asteroids move across the screen."),
        Instruction("The objective is to navigate your spaceship around the screen and avoid colliding with the moving asteroids."),
        Instruction("One score point is awarded for each successful move."),
        Instruction("Press the 'PLAY GAME' button to start the game."),
        Instruction("When the game starts, asteroids will be moving across the screen."),
        Instruction("Place your spaceship on the game play area by touching the screen."),
        Instruction("Move your spaceship around the game play area by touching and dragging the spaceship across the screen."),
        Instruction("You have 3 lives. When your spaceship collides with an asteroid, you lose a life and the spaceship disappears from the screen."),
        Instruction("Place your new spaceship on the game play area by touching the screen again."),
        Instruction("When all three lives are lost, the game is over. The final score and the 'GAME OVER' message will display."),
        Instruction("If a high score is achieved, a 'NEW HIGH SCORE!' message will also be displayed."),
        Instruction("Press the 'PLAY GAME' button to start another new game.")
    )
}