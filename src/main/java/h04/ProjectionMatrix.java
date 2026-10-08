package h04;

import org.tudalgo.algoutils.student.annotation.DoNotTouch;

/**
 * A matrix-based projection for tetrominoes with SRS-style pivot rotation.
 */
@DoNotTouch
public class ProjectionMatrix implements Cloneable {

    /**
     * The shape of the projection matrix.
     * A value of {@code true} indicates a block, and {@code false} indicates empty space.
     */
    private boolean[][] shape;

    /**
     * The pivot X coordinate multiplied by 2.
     */
    private int pivotX2;

    /**
     * The pivot Y coordinate multiplied by 2.
     */
    private int pivotY2;

    /**
     * Creates a projection matrix from the given tetromino template.
     *
     * @param tetromino the tetromino template to create the projection matrix from
     */
    public ProjectionMatrix(Tetromino tetromino) {
        this.shape = tetromino.shape;
        this.pivotX2 = tetromino.getPivotX2();
        this.pivotY2 = tetromino.getPivotY2();
    }

    /**
     * Rotates the projection matrix 90 degrees clockwise around its pivot point.
     */
    public void rotateRight() {
        shape = rotateAroundPivot(true);
    }

    /**
     * Rotates the projection matrix 90 degrees counterclockwise around its pivot point.
     */
    public void rotateLeft() {
        shape = rotateAroundPivot(false);
    }

    /**
     * Projects the local matrix coordinates into world coordinates.
     *
     * @param worldX the world X coordinate of the matrix's bottom-left corner
     * @param worldY the world Y coordinate of the matrix's bottom-left corner
     * @return the projected world coordinates for each occupied cell, as {@code [x, y]} pairs
     */
    public int[][] projectToWorld(int worldX, int worldY) {
        int count = 0;
        for (int y = 0; y < shape.length; y++) {
            for (int x = 0; x < shape[y].length; x++) {
                if (shape[y][x]) {
                    count++;
                }
            }
        }

        int[][] projected = new int[count][2];
        int index = 0;
        for (int y = 0; y < shape.length; y++) {
            for (int x = 0; x < shape[y].length; x++) {
                if (shape[y][x]) {
                    projected[index][0] = worldX + x;
                    projected[index][1] = worldY + y;
                    index++;
                }
            }
        }
        return projected;
    }

    /**
     * Returns a deep copy of the matrix shape.
     *
     * @return a deep copy of the shape array
     */
    public boolean[][] getShape() {
        return Tetromino.deepCopy(shape);
    }

    /**
     * Returns the pivot X coordinate multiplied by 2.
     *
     * @return the pivot X coordinate multiplied by 2
     */
    public int getPivotX2() {
        return pivotX2;
    }

    /**
     * Returns the pivot Y coordinate multiplied by 2.
     *
     * @return the pivot Y coordinate multiplied by 2
     */
    public int getPivotY2() {
        return pivotY2;
    }

    /**
     * Rotates the projection matrix 90 degrees around its pivot point.
     *
     * @param clockwise if true, rotates the matrix 90 degrees to the right (clockwise); if false, rotates 90 degrees to the left (counterclockwise)
     * @return a new projection matrix with the rotated shape
     */
    private boolean[][] rotateAroundPivot(boolean clockwise) {
        boolean[][] rotated = new boolean[shape.length][shape[0].length];

        for (int y = 0; y < shape.length; y++) {
            for (int x = 0; x < shape[y].length; x++) {
                if (!shape[y][x]) {
                    continue;
                }

                int dx2 = 2 * x - pivotX2;
                int dy2 = 2 * y - pivotY2;

                int ndx2;
                int ndy2;
                if (!clockwise) {
                    ndx2 = -dy2;
                    ndy2 = dx2;
                } else {
                    ndx2 = dy2;
                    ndy2 = -dx2;
                }

                int rx2 = pivotX2 + ndx2;
                int ry2 = pivotY2 + ndy2;

                if ((rx2 & 1) != 0 || (ry2 & 1) != 0) {
                    throw new IllegalStateException("Invalid pivot/rotation state (half-cell mismatch)");
                }

                int rx = rx2 / 2;
                int ry = ry2 / 2;

                if (ry < 0 || ry >= rotated.length || rx < 0 || rx >= rotated[0].length) {
                    throw new IllegalStateException("Rotation moved block outside local template bounds");
                }

                rotated[ry][rx] = true;
            }
        }

        return rotated;
    }

    /**
     * Returns a deep copy of this projection matrix.
     *
     * @return a deep copy of this projection matrix
     */
    @Override
    public ProjectionMatrix clone() {
        try {
            ProjectionMatrix copy = (ProjectionMatrix) super.clone();
            copy.shape = Tetromino.deepCopy(this.shape);
            return copy;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
