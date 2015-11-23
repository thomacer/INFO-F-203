import java.util.EnumSet;
import java.util.Iterator;

public class Car {
    public enum Direction {
        HORIZONTAL,
        VERTICAL
    }

    private int[] x_position;
    private int[] y_position;
    private Direction dir;

    @Override
    public Iterator<int[2]> iterator () {
        Iterator<int[2]> it = new Iterator<int[2]>(); 

        private int currentIndex = 0;

        @Override
        public boolean hasNext() {
            return currentIndex < x_position.length;
        }

        @Override
        public int[2] next() {
            ++currentIndex;
            return { x_position[i], y_position[i] };
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
    
    /* @desc Renvoie la position de la voiture si on doit
     *      l'avancer.
     *
     * @return {Car} : Nouvelle voiture dans sa nouvelle position.
     */
    public Car forward () {
        Car result;
        if ( this.dir == HORIZONTAL ) {
            // Change la position de tout les éléments
            int newXpos[this.x_position.length];
            for ( int i = 0; i < this.x_position.length; ++i ) {
                newXpos[i] = (this.x_position[i] + 1);
            }
            result = new Car( newXpos,
                              this.y_position, 
                              this.dir
                             )
        } else {
            // Change la position de tout les éléments
            int newYpos[this.y_position.length];
            for ( int i = 0; i < this.y_position.length; ++i ) {
                newYpos[i] = (this.y_position[i] + 1);
            }
            result = new Car( this.x_position,
                              newYpos,
                              this.dir
                             )
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
        if ( this.dir == HORIZONTAL ) {
            // Change la position de tout les éléments
            int newXpos[this.x_position.length];
            for ( int i = 0; i < this.x_position.length; ++i ) {
                newXpos[i] = (this.x_position[i] - 1);
            }

            result = new Car( newXpos,
                              this.y_position, 
                              this.dir
                             )
        } else {
            // Change la position de tout les éléments
            int newYpos[this.y_position.length];
            for ( int i = 0; i < this.y_position.length; ++i ) {
                newYpos[i] = (this.y_position[i] - 1);
            }
            result = new Car( this.x_position,
                              newYpos,
                              this.dir
                             )
        }
        return result;
    }

    Car ( int[] x_pos, int[] y_pos, Direction dir) {
        this.x_position = x_pos;
        this.y_position = y_pos;
        this.dir = dir;
    }
}
