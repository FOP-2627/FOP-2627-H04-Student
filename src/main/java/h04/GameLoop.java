package h04;

import fopbot.*;
import h04.swarms.*;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.tudalgo.algoutils.student.Student;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

/**
 * Main controller for the Tetris game.
 * Handles user input, gravity, swarm spawning, and row clearing.
 */
public class GameLoop {

    /**
     * The world in which the Tetris game is played.
     */
    @DoNotTouch
    private TetrisWorld tetrisWorld;

    /**
     * Moves the active swarm down if possible, or drops it into place otherwise.
     */
    @DoNotTouch
    protected void moveDownChecked() {
        if (tetrisWorld.getActiveSwarm().canMove(Direction.DOWN)) {
            tetrisWorld.getActiveSwarm().move(Direction.DOWN);
        } else {
            tetrisWorld.drop();
        }
    }

    /**
     * Moves the active swarm left or right if the move is possible.
     *
     * @param direction the direction to move, must be {@link Direction#LEFT} or
     *                  {@link Direction#RIGHT}
     */
    @DoNotTouch
    protected void moveLeftRightChecked(Direction direction) {
        if (tetrisWorld.getActiveSwarm().canMove(direction)) {
            tetrisWorld.getActiveSwarm().move(direction);
        }
    }

    /**
     * Rotates the active swarm if the rotated projection can be placed.
     *
     * @param clockwise {@code true} to rotate clockwise, {@code false} to rotate
     *                  counterclockwise
     */
    @StudentImplementationRequired("H4.3.2")
    protected void rotateChecked(boolean clockwise) {
        Student.crash();
    }

    /**
     * Handles input for controlling the active swarm.
     *
     * @param event the key press event to handle
     * @return {@code true} if the input should reset gravity progression, otherwise
     *         {@code false}
     */
    @StudentImplementationRequired("H4.3.1")
    public boolean handleInput(KeyPressEvent event) {
        return Student.crash();
    }

    /**
     * Runs the main game loop.
     * The loop creates the playing field, spawns new swarms, processes input,
     * applies gravity, and clears rows until no new swarm can be spawned.
     */
    @DoNotTouch
    public void gameLoop() {
        tetrisWorld = new TetrisWorld();
        World.setSize(TetrisWorld.WIDTH, TetrisWorld.HEIGHT);
        World.setDelay(0);
        World.setVisible(true);

        List<KeyPressEvent> keyPressEvents = Collections.synchronizedList(new LinkedList<>());
        InputHandler inputHandler = InputHandler.getInputHandler();
        inputHandler.addKeyPressListener(keyPressEvents::add);

        // Track the time since the last automatic drop to implement gravity.
        var lastDropTime = System.currentTimeMillis();

        boolean action = false;
        while (true) {
            if (tetrisWorld.getActiveSwarm() == null) {
                var randomTetromino = new Random().nextInt(7);
                Swarm newSwarm = switch (randomTetromino) {
                    case 0 -> new I(3, 17);
                    case 1 -> new O(4, 18);
                    case 2 -> new T(3, 17);
                    //TODO: uncomment after implementing L
                    //case 3 -> new L(3, 17, false);
                    //case 4 -> new L(3, 17, true);
                    //TODO: uncomment after implementing S
                    //case 5 -> new S(3, 17, false);
                    //case 6 -> new S(3, 17, true);
                    default -> throw new IllegalStateException("Unexpected value: " + randomTetromino);
                };

                // Stop when the next swarm cannot be placed.
                if (!tetrisWorld.canProject(newSwarm)) {
                    System.out.println("Game Over!");
                    break;
                }

                tetrisWorld.setActiveSwarm(newSwarm);
                tetrisWorld.getActiveSwarm().spawn();
            }

            if (!keyPressEvents.isEmpty()) {
                var gravityProgression = handleInput(keyPressEvents.getFirst());
                keyPressEvents.clear();

                // Reset the drop timer after player actions that should delay gravity.
                if (gravityProgression) {
                    lastDropTime = System.currentTimeMillis();
                }

                action = true;
            } else {
                // no input, therefore no user action, yeets the thread back to the os to save
                // on resources
                Thread.yield();
            }

            var newTime = System.currentTimeMillis();
            if (newTime - lastDropTime >= 1000) {
                moveDownChecked();
                lastDropTime = newTime;
                action = true;
            }

            if (action) {
                // tetrisWorld.debugPrint2();
                tetrisWorld.clearRows();
                action = false;
            }
        }
    }

    /**
     * Handles test input for controlling a robot.
     *
     * @param event the key press event to handle
     * @param robot the robot to control
     */
    @DoNotTouch
    public void handleTestInput(KeyPressEvent event, Robot robot) {
        Direction direction = switch (event.getKey()) {
            case UP -> Direction.UP;
            case DOWN -> Direction.DOWN;
            case LEFT -> Direction.LEFT;
            case RIGHT -> Direction.RIGHT;
            default -> robot.getDirection();
        };
        while (robot.getDirection() != direction) {
            robot.turnLeft();
        }
        robot.move();
    }
}
