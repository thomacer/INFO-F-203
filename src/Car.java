import java.util.EnumSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.lang.Iterable;

public class Car implements Iterable<int[]> {
    public enum Direction {
        HORIZONTAL,
        VERTICAL
    }

    private ArrayList<Integer> x_position;
    private ArrayList<Integer> y_position;
    private Direction dir;

    @Override
    public Iterator<int[]> iterator () {
        Iterator<int[]> it = new Iterator<int[]> () {

            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < x_position.size();
            }

            @Override
            public int[] next() {
                int[] result = { x_position.get(currentIndex), y_position.get(currentIndex) };
                ++currentIndex;
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
            ArrayList<Integer> newXpos = new ArrayList<>(this.x_position.size());
            for ( int i = 0; i < this.x_position.size(); ++i ) {
                newXpos.set(i, this.x_position.get(i) + 1);
            }
            result = new Car( newXpos,
                              this.y_position, 
                              this.dir
                             );
        } else {
            // Change la position de tout les éléments
            ArrayList<Integer> newYpos = new ArrayList<>(this.y_position.size());
            for ( int i = 0; i < this.y_position.size(); ++i ) {
                newYpos.set(i, this.y_position.get(i) + 1);
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
            ArrayList<Integer> newXpos = new ArrayList<>(this.x_position.size());
            for ( int i = 0; i < this.x_position.size(); ++i ) {
                newXpos.set(i, this.x_position.get(i) - 1);
            }

            result = new Car( newXpos,
                              this.y_position, 
                              this.dir
                             );
        } else {
            // Change la position de tout les éléments
            ArrayList<Integer> newYpos = new ArrayList<>(this.y_position.size());
            for ( int i = 0; i < this.y_position.size(); ++i ) {
                newYpos.set(i, this.y_position.get(i) - 1);
            }
            result = new Car( this.x_position,
                              newYpos,
                              this.dir
                             );
        }
        return result;
    }

    Car ( ArrayList<Integer> x_pos, ArrayList<Integer> y_pos, Direction dir) {
        this.x_position = x_pos;
        this.y_position = y_pos;
        this.dir = dir;
    }

    Car ( ArrayList<Integer> x_pos, ArrayList<Integer> y_pos) {
        this.x_position = x_pos;
        this.y_position = y_pos;

        if (x_pos.get(0) == x_pos.get(1)) {
            // Conformément à l'énoncé toutes voiture doit au moins
            // être de taille 2.
            this.dir = Direction.VERTICAL;
        } else {
            this.dir = Direction.HORIZONTAL;
        }
    }
}
