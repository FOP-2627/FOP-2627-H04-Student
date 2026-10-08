package h04.swarms;

import fopbot.RobotFamily;
import h04.ProjectionMatrix;
import h04.Tetromino;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

/**
 * Swarm implementation for the T tetromino.
 */
@DoNotTouch
public class T extends Swarm{

    /**
     * Creates a new T swarm.
     *
     * @param x the x-coordinate of the swarm
     * @param y the y-coordinate of the swarm
     */
    public T(int x, int y) {
        super(x, y);
        //this.tetromino = Tetromino.T;
        this.projectionMatrix = new ProjectionMatrix(Tetromino.T);
    }

    /**
     * Spawns the swarm and assigns the T-piece robot family.
     */
    @Override
    public void spawn() {
        super.spawn();
        for (int i = 0; i < tetrobots.length; i++) {
            tetrobots[i].setRobotFamily(RobotFamily.SQUARE_PURPLE);
        }
    }

    /**
     * Returns the tetromino name for this swarm.
     *
     * @return {@code "T"}
     */
    @Override
    public String toString() {
        return "T";
    }

}
