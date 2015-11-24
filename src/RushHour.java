import java.util.ArrayList;

public class RushHour {
    private Parking baseParking;

    private ArrayList<ArrayList<Integer>> parkingGraph = new ArrayList(new ArrayList(0));
    // Chaques configuration de parking vont être stockée ici.
    private ArrayList<Parking> parkingList = new ArrayList<Parking>(0);


    // TODO Mettre les opération sur les matrices dans une classe ou quelque
    // chose comme ça pour une lecture plus facile du code.
    /* @desc Ajoute une entrée (pour le sommet) dans le graph.
     */
    private void _add_entry_to_graph () {
        // Ajout à chaque colonne d'une nouvelle entrée.
        for ( int i = 0; i < parkingGraph.size(); ++i ) {
            parkingGraph.get(i).add(false);
        }

        // Création de la nouvelle colonne
        int newSize = (parkingGraph.size() + 1);
        ArrayList<Integer> newColumn = new ArrayList(newSize);
        for ( int i = 0; i < newColumn.size(); ++i ) {
            newColumn.set(i, false);
        }
        parkingGraph.add(newColumn);
    }

    /* @desc Place dans le graph que l'on vient de générer.
     *
     * @param {newParking} : Nouveau parking à placer dans le graphe.
     *
     * @return {index} du nouvel élément si on a dû l'ajouter, sinon -1.
     */
    private int _put_in_graph (Parking newParking) {
        int i = 0;
        // Recherche pour savoir si il y a déjà un {newParking} dans la liste.
        while ( (i < parkingList.size()) && (parkingList.get(i) != newParking) ) {
            ++i;
        }
        if (i == parkingList.size()) {
            // Si il n'est pas dans la liste.
            parkingList.add(newParking);
            this._add_entry_to_graph(); // Rajoute de la place pour {i}
        }

        return i;
    }

    /* @desc Génère récursivement toutes les configurations 
     *      possible de parking.
     *
     * @param {parking_conf} : Un objet parking qui contient 
     *      la configuration d'un parking (position voiture
     *      + taille du parking).
     *
     *  @return {Index du noeud} : Retourne l'index du noeud qui a été traité.
     *      
     */
    private int _generate_parkings(Parking parkingConf) {
        int i = this._put_in_graph (parkingConf);
        if ( i < (parkingList.size() - 1) ) {
            // Si i est plus petit que la taille de "parkingList - 1"
            // ça veut dire que l'on a déjà traité ce sommet, ou qu'il est
            // occupé d'être traité.
            return i;
        }

        int j; // Sert à savoir quel noeud à été ajouté
        for ( Car specific_car : parkingConf ) {
            // TODO Trouver un moyen plus joli pour faire le backtracking.

            // On avance la voiture tant que c'est possible.
            Parking tmp_parking_conf = parkingConf.move_forward( specific_car );
            while ( tmp_parking_conf != null ) {
                j = this._generate_parkings( tmp_parking_conf );
                // Création d'une arrête entre les deux en indiquant quel
                // voiture à été bougée. 
                this.parkingGraph.get(i).set(j, specific_car.get_num() );

                tmp_parking_conf = parkingConf.move_forward( specific_car );
            }

            // On recule la voiture tant que c'est possible.
            tmp_parking_conf = parkingConf.move_backward( specific_car );
            while ( tmp_parking_conf != null ) {
                j = this._generate_parkings( tmp_parking_conf );
                // Création d'une arrête entre les deux en indiquant quel
                // voiture à été bougée. 
                this.parkingGraph.get(i).set( j, specific_car.get_num() );

                tmp_parking_conf = parkingConf.move_backward( specific_car );
            }
        }
        return i;
    }

    public static void main (String[] args) {
        if ( args.length > 1) {
            ParkingIN parsedParking = new ParkingIN(args[1]);
            baseParking = parsedParking.parseParking();

            _generate_parkings(baseParking);
        } else {
            // print usage.
        }
    }
}
