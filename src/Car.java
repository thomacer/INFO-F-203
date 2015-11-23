import java.util.EnumSet;
import java.util.Iterator;
import java.lang.Iterable;

public class Car implements Iterable<int[]> {
    public enum Direction {
        HORIZONTAL,
        VERTICAL
    }

    private int[] x_position;
    private int[] y_position;
    private Direction dir;

    @Override
    public Iterator<int[]> iterator () {
        Iterator<int[]> it = new Iterator<int[]> () {

            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < x_position.length;
            }

            @Override
            public int[] next() {
                ++currentIndex;
                int[] result = { x_position[currentIndex], y_position[currentIndex] };
                return result;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
        return it;
    }
    
    /* @desc Renvoie la position de la voiture si on doit
     *      l'avancer.
     *
     * @return {Car} : Nouvelle voiture dans sa nouvelle position.
     */
    public Car forward () {
        Car result;
        if ( this.dir == Direction.HORIZONTAL ) {
            // Change la position de tout les éléments
            int[] newXpos = new int[this.x_position.length];
            for ( int i = 0; i < this.x_position.length; ++i ) {
                newXpos[i] = (this.x_position[i] + 1);
            }
            result = new Car( newXpos,
                              this.y_position, 
                              this.dir
                             );
        } else {
            // Change la position de tout les éléments
            int[] newYpos = new int[this.y_position.length];
            for ( int i = 0; i < this.y_position.length; ++i ) {
                newYpos[i] = (this.y_position[i] + 1);
            }
            result = new Car( this.x_position,
                              newYpos,
                              this.dir
                             );
        }
        return result;
    }

    /* @desc Renvoie la position de la voiture si on doit
     *      l'avancer.
     *
     * @return {Car} : Nouvelle voiture dans sa nouvelle position.
     */
    public Car backward () {
        Car result;
        if ( this.dir == Direction.HORIZONTAL ) {
            // Change la position de tout les éléments
            int[] newXpos = new int[this.x_position.length];
            for ( int i = 0; i < this.x_position.length; ++i ) {
                newXpos[i] = (this.x_position[i] - 1);
            }

            result = new Car( newXpos,
                              this.y_position, 
                              this.dir
                             );
        } else {
            // Change la position de tout les éléments
            int[] newYpos = new int[this.y_position.length];
            for ( int i = 0; i < this.y_position.length; ++i ) {
                newYpos[i] = (this.y_position[i] - 1);
            }
            result = new Car( this.x_position,
                              newYpos,
                              this.dir
                             );
        }
        return result;
    }

    Car ( int[] x_pos, int[] y_pos, Direction dir) {
        this.x_position = x_pos;
        this.y_position = y_pos;
        this.dir = dir;
    }

    Car ( int[] x_pos, int[] y_pos) {
        this.x_position = x_pos;
        this.y_position = y_pos;

        if (x_pos[0] == x_pos[1]) {
            this.dir = Direction.VERTICAL;
        } else {
            this.dir = Direction.HORIZONTAL;
        }
    }
}
