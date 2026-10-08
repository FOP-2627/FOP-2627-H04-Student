package h04;

import fopbot.Direction;
import fopbot.FieldEntity;
import fopbot.World;
import h04.swarms.Swarm;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

/**
 * Logical Tetris board with the standard dimensions of 10 by 20.
 *
 * The board stores fixed tetromino cells and keeps at most one active swarm.
 */
@DoNotTouch
public class TetrisWorld {

	/**
	 * The width of the Tetris board.
	 */
	public static final int WIDTH = 10;

	/**
	 * The height of the Tetris board.
	 */
	public static final int HEIGHT = 20;

	/**
	 * The active swarm on the Tetris board.
	 */
	private Swarm activeSwarm;

	/**
	 * Sets the active swarm on the Tetris board.
	 *
	 * @param swarm the swarm to set as active
	 */
	public void setActiveSwarm(Swarm swarm) {
		this.activeSwarm = swarm;
	}

	/**
	 * Returns the currently active swarm.
	 *
	 * @return the active swarm, or {@code null} if there is no active swarm
	 */
	public Swarm getActiveSwarm() {
		return activeSwarm;
	}

	/**
	 * Clears the active swarm from the Tetris board.
	 */
	public void clearActiveSwarm() {
		this.activeSwarm = null;
	}

	/**
	 * Checks whether the cell at the given coordinates is part of the current
	 * active swarm's projection footprint.
	 *
	 * @param x x-coordinate of the cell to check
	 * @param y y-coordinate of the cell to check
	 * @return {@code true} if the cell is part of the active swarm's projection
	 *         footprint, {@code false} otherwise
	 */
	private boolean isActiveSwarmCell(int x, int y) {
		if (activeSwarm == null) {
			return false;
		}
		int[][] points = activeSwarm.getProjectionMatrix().projectToWorld(activeSwarm.getX(), activeSwarm.getY());
		for (int[] point : points) {
			if (point[0] == x && point[1] == y) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks whether the bot at the given coordinates is blocking the projection of
	 * the active swarm.
	 *
	 * @param x x-coordinate of the cell to check
	 * @param y y-coordinate of the cell to check
	 * @return {@code true} if a blocking bot is found, {@code false} otherwise
	 */
	private boolean hasBlockingBotAt(int x, int y) {
		for (FieldEntity entity : Utils.getEntitiesAt(x, y)) {
            //TODO: uncomment after implementing Tetrobot
			//if (entity instanceof Tetrobot && !isActiveSwarmCell(x, y)) {
			//	return true;
			//}
		}
		return false;
	}

	/**
	 * Checks whether the given projection matrix can be placed at the specified
	 * world coordinates.
	 *
	 * <p>
	 * The projection must stay within the board and must not overlap any fixed
	 * bots.
	 * </p>
	 *
	 * @param projectionMatrix the projection matrix to check
	 * @param worldX           the world x-coordinate where the projection should be
	 *                         placed
	 * @param worldY           the world y-coordinate where the projection should be
	 *                         placed
	 * @return {@code true} if the projection can be placed, otherwise {@code false}
	 */
	public boolean canProject(ProjectionMatrix projectionMatrix, int worldX, int worldY) {
		int[][] points = projectionMatrix.projectToWorld(worldX, worldY);
		for (int[] point : points) {
			int x = point[0];
			int y = point[1];

			if (!isInside(x, y)) {
				return false;
			}
			if (hasBlockingBotAt(x, y)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Checks whether the given swarm can be placed at its current coordinates.
	 *
	 * <p>
	 * The swarm must stay within the board and must not overlap any fixed bots.
	 * </p>
	 *
	 * @param swarm the swarm to check
	 * @return {@code true} if the swarm can be placed, otherwise {@code false}
	 */
	public boolean canProject(Swarm swarm) {
		return swarm != null && canProject(swarm.getProjectionMatrix(), swarm.getX(), swarm.getY());
	}

	/**
	 * Fixes the active swarm in place on the Tetris board if it can be projected at
	 * its current coordinates.
	 *
	 * @return {@code true} if the active swarm was fixed in place, otherwise
	 *         {@code false}
	 */
	public boolean fixActiveSwarm() {
		if (activeSwarm == null || !canProject(activeSwarm)) {
			return false;
		}

		clearActiveSwarm();
		return true;
	}

	/**
	 * Checks whether the given coordinates are within the bounds of the Tetris
	 * board.
	 *
	 * @param x the x-coordinate to check
	 * @param y the y-coordinate to check
	 * @return {@code true} if the coordinates are within bounds, otherwise
	 *         {@code false}
	 */
	private static boolean isInside(int x, int y) {
		return x >= 0 && x < WIDTH && y >= 0 && y < HEIGHT;
	}

	/**
	 * Moves the active swarm down if possible, or drops it into place otherwise.
	 */
	public void drop() {
		while (activeSwarm != null && activeSwarm.canMove(Direction.DOWN)) {
			activeSwarm.move(Direction.DOWN);
		}
		fixActiveSwarm();
		clearActiveSwarm();
	}

	/**
	 * Clears any full rows from the Tetris board and shifts rows above them down.
	 */
	public void clearRows() {
		for (int y = 0; y < HEIGHT; y++) {
			if (!isFullRow(y)) {
				continue;
			}

			clearRowEntities(y);
			shiftRowsDownFrom(y + 1);
			y--; // Recheck the same row after shifting rows above it down.
		}
	}

	/**
	 * Checks whether the specified row is full.
	 *
	 * @param y the index of the row to check
	 * @return {@code true} if the row is full, otherwise {@code false}
	 */
	private boolean isFullRow(int y) {
		for (int x = 0; x < WIDTH; x++) {
			if (!hasBlockingBotAt(x, y)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Clears all entities from the specified row and marks the cells as undefined.
	 *
	 * @param y the index of the row to clear
	 */
	private void clearRowEntities(int y) {
		for (int x = 0; x < WIDTH; x++) {
			for (FieldEntity entity : Utils.getEntitiesAt(x, y)) {
                //TODO: uncomment after implementing Tetrobot
				//if (entity instanceof Tetrobot) {
				//	Utils.removeEntity(entity, World.getGlobalWorld());
				//}
			}
		}
	}

	/**
	 * Shifts all rows starting from the specified source row down by one.
	 *
	 * @param sourceY the index of the first row above the cleared row
	 */
	private void shiftRowsDownFrom(int sourceY) {
		for (int readY = sourceY; readY < HEIGHT; readY++) {
			int targetY = readY - 1;
			for (int x = 0; x < WIDTH; x++) {
				for (FieldEntity entity : Utils.getEntitiesAt(x, readY)) {
                    //TODO: uncomment after implementing Tetrobot
					//if (entity instanceof Tetrobot) {
					//	((Tetrobot) entity).setY(targetY);
					//}
				}
			}
		}
	}
}
