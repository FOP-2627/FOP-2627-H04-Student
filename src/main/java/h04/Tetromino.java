package h04;

import org.tudalgo.algoutils.student.annotation.DoNotTouch;

/**
 * Represents the seven standard tetromino shapes used in Tetris.
 *
 * <p>Each tetromino is defined by a 2D boolean array for its shape and a pivot point for rotation.</p>
 */
@DoNotTouch
public enum Tetromino {

    I(new boolean[][] {
            { false, false, false, false },
            { false, false, false, false },
            { true, true, true, true },
            { false, false, false, false } }, 3, 3),
    O(new boolean[][] {
            { true, true },
            { true, true } }, 1, 1),
    T(new boolean[][] {
            { false, false, false },
            { true, true, true },
            { false, true, false } }, 2, 2),
    L(new boolean[][] {
            { false, false, false },
            { true, true, true },
            { true, false, false } }, 2, 2),
    J(new boolean[][] {
            { false, false, false },
            { true, true, true },
            { false, false, true } }, 2, 2),
    S(new boolean[][] {
            { false, false, false },
            { true, true, false },
            { false, true, true } }, 2, 2),
    Z(new boolean[][] {
            { false, false, false },
            { false, true, true },
            { true, true, false } }, 2, 2);

    /**
     * The shape of the tetromino.
     *
     * <p>{@code true} indicates an occupied cell and {@code false} indicates empty space.
     * The first dimension represents rows and the second dimension represents columns.
     * Index {@code 0} in the row dimension corresponds to the bottom row of the shape.
     * This matches the bottom-left origin used by the FOPBot world.</p>
     */
    public final boolean[][] shape;

    /**
     * The X coordinate of the pivot point for rotation, multiplied by 2.
     */
    private final int pivotX2;

    /**
     * The Y coordinate of the pivot point for rotation, multiplied by 2.
     */
    private final int pivotY2;

    /**
     * Constructs a new Tetromino with the specified shape and pivot point.
     *
     * @param shape the 2D boolean array representing the shape of the tetromino
     * @param pivotX2 the X coordinate of the pivot point multiplied by 2
     * @param pivotY2 the Y coordinate of the pivot point multiplied by 2
     */
    Tetromino(boolean[][] shape, int pivotX2, int pivotY2) {
        this.shape = shape;
        this.pivotX2 = pivotX2;
        this.pivotY2 = pivotY2;
    }

    /**
     * Returns a deep copy of the shape matrix.
     *
     * @return a deep copy of the shape matrix
     */
    public boolean[][] getShape() {
        return deepCopy(shape);
    }

    /**
     * Returns the X coordinate of the pivot point for rotation, multiplied by 2.
     *
     * @return the X coordinate of the pivot point multiplied by 2
     */
    public int getPivotX2() {
        return pivotX2;
    }

    /**
     * Returns the Y coordinate of the pivot point for rotation, multiplied by 2.
     *
     * @return the Y coordinate of the pivot point multiplied by 2
     */
    public int getPivotY2() {
        return pivotY2;
    }

    /**
     * Returns the mirrored version of this tetromino.
     *
     * <p>The I and O tetrominoes are symmetric and therefore return themselves.
     * The L and J tetrominoes are mirrored versions of each other, as are the S and Z tetrominoes.</p>
     *
     * @return the mirrored tetromino
     */
    public Tetromino mirrored() {
        return this == L ? J : this == J ? L : this == S ? Z : this == Z ? S : this;
    }

    /**
     * Creates a deep copy of the given 2D boolean array.
     *
     * @param matrix the 2D boolean array to copy
     * @return a deep copy of the given matrix
     */
    static boolean[][] deepCopy(boolean[][] matrix) {
        boolean[][] copy = new boolean[matrix.length][];
        for (int i = 0; i < matrix.length; i++) {
            copy[i] = matrix[i].clone();
        }
        return copy;
    }
}
