package h04.swarms;

import fopbot.RobotFamily;
import h04.ProjectionMatrix;
import h04.Tetromino;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

/**
 * Swarm implementation for the I tetromino.
 */
@DoNotTouch
public class I extends Swarm{

    /**
     * Creates a new I swarm.
     *
     * @param x the x-coordinate of the swarm
     * @param y the y-coordinate of the swarm
     */
    public I(int x, int y) {
        super(x, y);
        //this.tetromino = Tetromino.I;
        this.projectionMatrix = new ProjectionMatrix(Tetromino.I);
    }

    /**
     * Spawns the swarm and assigns the I-piece robot family.
     */
    @Override
    public void spawn() {
        super.spawn();
        for (int i = 0; i < tetrobots.length; i++) {
            tetrobots[i].setRobotFamily(RobotFamily.SQUARE_AQUA);
        }
    }

    /**
     * Returns the tetromino name for this swarm.
     *
     * @return {@code "I"}
     */
    @Override
    public String toString() {
        return "I";
    }

}
