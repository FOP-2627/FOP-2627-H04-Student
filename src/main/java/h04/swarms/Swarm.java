package h04.swarms;

import fopbot.Direction;
import fopbot.Robot;
import h04.*;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

/**
 * Represents a swarm of Tetrobots that forms a tetromino in the Tetris
 * world.
 *
 * <p>
 * The swarm position corresponds to the bottom-left corner of the tetromino
 * shape.
 * The swarm can move left, right, and down, and it can rotate left and right.
 * The arrangement of the robots is defined by the associated
 * {@link ProjectionMatrix}.
 * </p>
 */
@DoNotTouch
public abstract class Swarm {

    /**
     * The x-coordinate of the bottom-left corner of the swarm in the Tetris world.
     */
    protected int x;

    /**
     * The y-coordinate of the bottom-left corner of the swarm in the Tetris world.
     */
    protected int y;

    /**
     * The robots that make up the swarm.
     */
    //TODO: change to Tetrobot[] after implementing Tetrobot
    protected Robot[] tetrobots;

    /**
     * The projection matrix that defines the swarm shape.
     */
    protected ProjectionMatrix projectionMatrix;

    /**
     * Returns the x-coordinate of the swarm position.
     *
     * @return the x-coordinate of the swarm position
     */
    public int getX() {
        return x;
    }

    /**
     * Returns the y-coordinate of the swarm position.
     *
     * @return the y-coordinate of the swarm position
     */
    public int getY() {
        return y;
    }

    /**
     * Returns the projection matrix associated with this swarm.
     *
     * @return the projection matrix of this swarm
     */
    public ProjectionMatrix getProjectionMatrix() {
        return projectionMatrix;
    }

    /**
     * Sets the projection matrix for this swarm.
     *
     * @param projectionMatrix the projection matrix to set
     */
    public void setProjectionMatrix(ProjectionMatrix projectionMatrix) {
        this.projectionMatrix = projectionMatrix;
    }

    /**
     * Constructs a new swarm at the specified position.
     *
     * @param x the x-coordinate of the swarm
     * @param y the y-coordinate of the swarm
     */
    public Swarm(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Moves the swarm in the specified direction.
     *
     * @param direction the direction to move
     * @throws IllegalArgumentException if {@code direction} is {@link Direction#UP}
     */
    public void move(Direction direction) throws IllegalArgumentException {
        if (direction == Direction.UP)
            throw new IllegalArgumentException("Direction UP is not supported for Swarm movement.");

        for (int i = 0; i < tetrobots.length; i++) {
            while (!tetrobots[i].getDirection().equals(direction)) {
                tetrobots[i].turnLeft();
            }
        }
        for (int i = 0; i < tetrobots.length; i++) {
            tetrobots[i].move();
        }
        x += direction.getDx();
        y += direction.getDy();
    }

    /**
     * Checks whether the swarm can move in the specified direction.
     *
     * @param direction the direction to check for movement
     * @return {@code true} if the swarm can move in the specified direction,
     *         otherwise {@code false}
     */
    public boolean canMove(Direction direction) {
        if (direction == Direction.UP)
            return false;

        for (int i = 0; i < tetrobots.length; i++) {
            while (!tetrobots[i].getDirection().equals(direction)) {
                tetrobots[i].turnLeft();
            }
            //TODO: uncomment after implementing Tetrobot
            //if (!tetrobots[i].isFrontClear(tetrobots)) {
            //    return false;
            //}
        }

        return true;
    }

    /**
     * Spawns the swarm in the Tetris world.
     */
    public void spawn() {
        if (this.tetrobots != null) {
            for (int i = 0; i < tetrobots.length; i++) {
                Utils.removeRobot(tetrobots[i]);
            }
        }

        int[][] projected = projectionMatrix.projectToWorld(x, y);
        //TODO: uncomment after implementing Tetrobot
        //Tetrobot[] newTetrobots = new Tetrobot[projected.length];
        //for (int i = 0; i < projected.length; i++) {
        //    newTetrobots[i] = new Tetrobot(projected[i][0], projected[i][1]);
        //}
        //this.tetrobots = newTetrobots;
    }

    /**
     * Commits the current SRS rotation to the robots in the world.
     */
    public void commitSRSRotation() {
        int[][] projected = projectionMatrix.projectToWorld(x, y);
        for (int i = 0; i < tetrobots.length; i++) {
            int newX = projected[i][0];
            int newY = projected[i][1];
            tetrobots[i].setX(newX);
            tetrobots[i].setY(newY);
        }
    }

    /**
     * Rotates the swarm 90 degrees clockwise.
     */
    public void turnRight() {
        projectionMatrix.rotateRight();
        commitSRSRotation();
    }

    /**
     * Rotates the swarm 90 degrees counterclockwise.
     */
    public void turnLeft() {
        projectionMatrix.rotateLeft();
        commitSRSRotation();
    }
}
