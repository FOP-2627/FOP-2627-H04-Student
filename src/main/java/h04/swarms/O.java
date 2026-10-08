package h04.swarms;

import fopbot.RobotFamily;
import h04.ProjectionMatrix;
import h04.Tetromino;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

/**
 * Swarm implementation for the O tetromino.
 */
@DoNotTouch
public class O extends Swarm{

    /**
     * Creates a new O swarm.
     *
     * @param x the x-coordinate of the swarm
     * @param y the y-coordinate of the swarm
     */
    public O(int x, int y) {
        super(x, y);
        //this.tetromino = Tetromino.O;
        this.projectionMatrix = new ProjectionMatrix(Tetromino.O);
    }

    /**
     * Spawns the swarm and assigns the O-piece robot family.
     */
    @Override
    public void spawn() {
        super.spawn();
        for (int i = 0; i < tetrobots.length; i++) {
            tetrobots[i].setRobotFamily(RobotFamily.SQUARE_YELLOW);
        }
    }

    /**
     * Returns the tetromino name for this swarm.
     *
     * @return {@code "O"}
     */
    @Override
    public String toString() {
        return "O";
    }

}
