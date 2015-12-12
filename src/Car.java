import java.util.EnumSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.lang.Iterable;

public class Car implements Iterable<int[]> {
    public enum _Direction {
        HORIZONTAL,
        VERTICAL
    }

    static private final int _GOAL_CAR_NUM = 1; 
    // Par convention la voiture goal va toujours être la première ajoutée.

    static private int _carNumber = _GOAL_CAR_NUM;
    private int _personalNum;

    private ArrayList<Integer> _x_position;
    private ArrayList<Integer> _y_position;
    private _Direction _dir;
    
        /* @desc Constructeur pour ne pas devoir faire de copie inutile.
     *      Celui-ci est utilisé lorsque l'on fait une copie de la voiture
     *      pour la placer dans un nouveau parking.
     */
    Car ( ArrayList<Integer> x_pos, ArrayList<Integer> y_pos,_Direction dir, int carNum) {
        this._x_position = x_pos;
        this._y_position = y_pos;

        this._personalNum = carNum;

        this._dir = dir;
    }

    /* @desc Constructeur de la voiture utilisé lors du lancement du
     *      programme. Il va calculer toutes informations nécessaires
     *      pour le bon fonctionnement de cette classe.
     */
    Car ( ArrayList<Integer> x_pos, ArrayList<Integer> y_pos) {
        this._x_position = x_pos;
        this._y_position = y_pos;

        this._personalNum = this._carNumber;
        ++this._carNumber;

        // Déduction de la_direction de la voiture.
        if (x_pos.get(0) == x_pos.get(1)) {
            // Conformément à l'énoncé toutes voiture doit au moins
            // être de taille 2.
            this._dir =_Direction.VERTICAL;
        } else {
            this._dir =_Direction.HORIZONTAL;
        }
    }

    public ArrayList<Integer> get_x_pos () {
        return this._x_position; 
    }

    public ArrayList<Integer> get_y_pos () {
        return this._y_position; 
    }

    public boolean equals (Car other) {
        return this.get_x_pos().equals(other.get_x_pos())
            && this.get_y_pos().equals(other.get_y_pos());
    }

    public boolean is_goal() {
        return this.get_num() == _GOAL_CAR_NUM;
    }        

    /* @desc Iterateur sur les coordonnées de la voiture.
     */
    @Override
    public Iterator<int[]> iterator () {
        Iterator<int[]> it = new Iterator<int[]> () {

            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < _x_position.size();
            }

            @Override
            public int[] next() {
                int[] result = { _x_position.get(currentIndex), _y_position.get(currentIndex) };
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
    
    /* @desc Permet de savoir quel est le numéro adressé à la voiture.
     *
     * @return Le numéro de la voiture.
     */
    public int get_num () {
        return this._personalNum;
    }

    /* @desc Renvoie la position de la voiture si on doit
     *      l'avancer.
     *
     * @return {Car} : Nouvelle voiture dans sa nouvelle position.
     */
    public Car forward () {
        Car result;
        if ( this._dir ==_Direction.HORIZONTAL ) {
            // Change la position de tout les éléments
            ArrayList<Integer> newXpos = new ArrayList<>();
            for ( int i = 0; i < this._x_position.size(); ++i ) {
                newXpos.add(this._x_position.get(i) + 1);
            }
            result = new Car( newXpos,
                              this._y_position, 
                              this._dir,
                              this._personalNum
                             );
        } else {
            // Change la position de tout les éléments
            ArrayList<Integer> newYpos = new ArrayList<>();
            for ( int i = 0; i < this._y_position.size(); ++i ) {
                newYpos.add(this._y_position.get(i) + 1);
            }
            result = new Car( this._x_position,
                              newYpos,
                              this._dir,
                              this._personalNum
                             );
        }
        return result;
    }

    /* @desc Renvoie la position de la voiture si on doit
     *      la reculer.
     *
     * @return {Car} : Nouvelle voiture dans sa nouvelle position.
     */
    public Car backward () {
        Car result;
        if ( this._dir ==_Direction.HORIZONTAL ) {
            // Change la position de tout les éléments
            ArrayList<Integer> newXpos = new ArrayList<>();
            for ( int i = 0; i < this._x_position.size(); ++i ) {
                newXpos.add(this._x_position.get(i) - 1);
            }

            result = new Car( newXpos,
                              this._y_position, 
                              this._dir,
                              this._personalNum
                             );
        } else {
            // Change la position de tout les éléments
            ArrayList<Integer> newYpos = new ArrayList<>();
            for ( int i = 0; i < this._y_position.size(); ++i ) {
                newYpos.add(this._y_position.get(i) - 1);
            }
            result = new Car( this._x_position,
                              newYpos,
                              this._dir,
                              this._personalNum
                             );
        }
        return result;
    }

    @Override
    public String toString() {
        return Integer.toString(this._personalNum);
    }
}
