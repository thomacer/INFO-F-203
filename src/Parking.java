import java.util.EnumSet;
import java.util.Iterator;
import java.util.ArrayList;

public class Parking implements Iterable<Car> {
    private enum _Direction {
        FORWARD,
        BACKWARD
    }

    private int x_size;
    private int y_size;
    private int[] _exit;
    private boolean _isWin;
    private Car goal_car;
    private ArrayList<Car> carList = new ArrayList<Car>(0);
    private boolean[][] parkingMatrix;

    public boolean is_won () {
        return this._isWin; 
    }

    /* http://stackoverflow.com/questions/5849154/can-we-write-our-own-iterator-in-java
     * @desc Iterateur sur les véhicules de la classe pour acceder
     *      au différents véhicules en dehors de la classe. 
     */
    @Override
    public Iterator<Car> iterator () {
        Iterator<Car> it = new Iterator<Car>() {

            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < carList.size();
            }

            @Override
            public Car next() {
                ++currentIndex;
                return carList.get(currentIndex);
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
        return it;
    }

    private int[] _get_head_f(Car carToGet) {
        int[] head = {0, 0};
        for ( int[] pos : carToGet ) {
            if ( (pos[0] > head[0]) || (pos[1] > head[1]) ) {
                head = pos;
            }
        }
        return head;
    }

    private int[] _get_head_b(Car carToGet) {
        int[] head = {this.parkingMatrix.length, this.parkingMatrix.length};
        for ( int[] pos : carToGet ) {
            if ( (pos[0] < head[0]) || (pos[1] < head[1]) ) {
                head = pos;
            }
        }
        return head;
    }

    /* @desc Cette fonction permet de vérifier si suite à un déplacement,
     *      l'on a fait l'on se trouve toujours dans une configuration valide
     *      du parking, càd dans les limites du parking et en ne touchant pas
     *      d'autre véhicule.
     *
     * @return {Boolean} Vrai si configuration correct, faux si configuration
     *      incorrect.
     */
    private boolean _check_movement( Car movedCar, _Direction dir ) {
        // On commence par recherche la "tête" du véhicule.
        int[] head = new int[2];
        if (dir == _Direction.FORWARD) {
            // Si FORWARD il faut trouver la plus grande coord.
            head = this._get_head_f(movedCar);
        } else {
            // Si BACKWARD il faut trouver la plus petite coord.
            head = this._get_head_b(movedCar);
        }
        return ( this.parkingMatrix[head[0]][head[1]] 
            && (0 <= head[0] && head[0] < this.x_size)
            && (0 <= head[1] && head[1] < this.y_size) );
    }

    /* @desc Renvoie un la manière dont le parking serait si
     *      la voiture {toMoveCar} était bougée en avant.
     *
     * @param {toMoveCar} : Voiture que l'on va devoir bouger.
     *
     * @return {Parking} : La nouvelle forme du parking.
     */
    public Parking move_forward( Car toMoveCar ) {
        // Vérification que la voiture sait avancer.
        Car newCarPos = toMoveCar.forward();

        if (!this._check_movement(toMoveCar, _Direction.FORWARD)) {
            return null;
        }

        // COPY de {carList} mais en remplaçant {toMoveCar}.
        ArrayList<Car> result = new ArrayList<Car>( this.carList.size() );

        for ( int i = 0; i < this.carList.size(); ++i ) {
            if ( this.carList.get(i) == toMoveCar ) {
                result.set(i, newCarPos);
            } else {
                result.set(i, this.carList.get(i)); // TODO .clone());
            }
        }

        return new Parking(this.x_size, this.y_size, this._exit, result);
    }

    /* @desc Renvoie un la manière dont le parking serait si
     *      la voiture {toMoveCar} était bougée en arrière.
     *
     * @param {toMoveCar} : Voiture que l'on va devoir bouger.
     *
     * @return {Parking} : La nouvelle forme du parking.
     */
    public Parking move_backward( Car toMoveCar ) {
        // Vérification que la voiture sait avancer.
        Car newCarPos = toMoveCar.backward();

        if (!this._check_movement(toMoveCar, _Direction.BACKWARD)) {
            return null;
        }

        // COPY de {carList} mais en remplaçant {toMoveCar}.
        ArrayList<Car> result = new ArrayList<Car>( this.carList.size() );

        for ( int i = 0; i < this.carList.size(); ++i ) {
            if ( this.carList.get(i) == toMoveCar ) {
                result.set(i, newCarPos);
            } else {
                result.set(i, this.carList.get(i)); // TODO .clone());
            }
        }

        return new Parking(this.x_size, this.y_size, this._exit, result);
    }

    public Car set_goal_car (ArrayList<Integer> xPos, ArrayList<Integer> yPos) {
        this.goal_car = this.add_car (xPos, yPos); 

        return this.goal_car;
    }

    public Car add_car (ArrayList<Integer> xPos, ArrayList<Integer> yPos) {
        Car newCar = new Car(xPos, yPos);
        for ( int[] pos : newCar ) {
            if (this.parkingMatrix[pos[0]][pos[1]]) {
                // throw "Position déjà occupée par une autre voiture.";
            } else {
                this.parkingMatrix[pos[0]][pos[1]] = true; 
            }
        }

        this.carList.add( newCar );

        return newCar;
    }

    Parking (int x_size, int y_size, int[] exit) {
        this.x_size = x_size;
        this.y_size = y_size;
        this._exit=exit.clone();

        this.parkingMatrix = new boolean[x_size][y_size];
    }

    Parking (int x_size, int y_size, int[] exit, ArrayList<Car> carList) {
        this.x_size = x_size;
        this.y_size = y_size;
        this.carList = carList;
        this._exit = exit.clone();

        // On place les voitures dans la matrice.
        this.parkingMatrix = new boolean[x_size][y_size];
        for ( Car car : carList ) {
            for ( int[] pos : car ) {
                if (this.parkingMatrix[pos[0]][pos[1]]) {
                    // throw "Position déjà occupée par une autre voiture.";
                } else {
                    this.parkingMatrix[pos[0]][pos[1]] = true;
                }
            }
        }

        // Ajout de la voiture "goal" 
        if (carList.size() > 0) {
            // La voiture "goal" qui est toujours dans la liste à l'index "0".
            this.goal_car = carList.get(0);
        }

        // Vérification que le parking est une configuration gagnant ou non.
        this._isWin = true;
        if (this._exit[0] == 0) {
            // Si la sortie à été définie à gauche. 
            int[] currentPos = this._get_head_b(this.goal_car);
            for (int i = currentPos[0]; i >= 0; --i) {
                if ( this.parkingMatrix[i][currentPos[1]] ) {
                    // Si il y a un obstacle sur le chemin on ne sait pas gagner
                    this._isWin = false;
                    break;
                }
            }
        } else {
            // Si la sortie à été définie à droite.
            int[] currentPos = this._get_head_f(this.goal_car);
            for (int i = currentPos[0]; i < this.x_size; ++i) {
                if ( this.parkingMatrix[i][currentPos[1]] ) {
                    // Si il y a un obstacle sur le chemin on ne sait pas gagner
                    this._isWin = false;
                    break;
                }
            }
        }
    }
}
