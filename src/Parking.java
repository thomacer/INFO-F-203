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
    private Car goal_car;
    private ArrayList<Car> carList = new ArrayList<Car>(0);
    private boolean[][] parkingMatrix;

    public boolean[][] get_parkingMatrix () {
        return this.parkingMatrix;
    }

    public boolean equals(Parking other) {
        return this.get_parkingMatrix() == other.get_parkingMatrix();
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
            head[0] = 0;
            head[1] = 0;
            for ( int[] pos : movedCar ) {
                if ( (pos[0] > head[0]) || (pos[1] > head[1]) ) {
                    head = pos;
                }
            }
        } else {
            // Si BACKWARD il faut trouver la plus petite coord.
            head[0] = this.parkingMatrix.length;
            head[1] = this.parkingMatrix.length;
            for ( int[] pos : movedCar ) {
                if ( (pos[0] < head[0]) || (pos[1] < head[1]) ) {
                    head = pos;
                }
            }
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

        return new Parking(this.x_size, this.y_size, result);
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

        return new Parking(this.x_size, this.y_size, result);
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

    Parking (int x_size, int y_size) {
        this.x_size = x_size;
        this.y_size = y_size;

        this.parkingMatrix = new boolean[x_size][y_size];
    }

    Parking (int x_size, int y_size, ArrayList<Car> carList) {
        this.x_size = x_size;
        this.y_size = y_size;
        this.carList = carList;

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
    }
}
