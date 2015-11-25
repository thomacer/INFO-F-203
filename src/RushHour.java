import java.util.ArrayList;
import java.util.LinkedList;

public class RushHour {
    // private Parking baseParking;
    private ArrayList<ArrayList<Integer>> parkingGraph = new ArrayList<>(new ArrayList<>(0));
    // Chaques configuration de parking vont être stockée ici.
    private ArrayList<Parking> parkingList = new ArrayList<Parking>(0);

    // TODO Mettre les opération sur les matrices dans une classe ou quelque
    // chose comme ça pour une lecture plus facile du code.
    /* @desc Ajoute une entrée (pour le sommet) dans le graph.
     */
    private void _add_entry_to_graph () {
        // Ajout à chaque colonne d'une nouvelle entrée.
        for ( int i = 0; i < parkingGraph.size(); ++i ) {
            parkingGraph.get(i).add(0);
        }

        // Création de la nouvelle colonne
        int newSize = (parkingGraph.size() + 1);
        ArrayList<Integer> newColumn = new ArrayList<>(newSize);
        for ( int i = 0; i < newColumn.size(); ++i ) {
            newColumn.set(i, 0);
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

    /* @desc Parcour en breadth first du graph pour trouver le chemin le plus
     *      court pour accêder à la sortie.
     *
     * @param {baseParking} : Configuration du parking au départ.
     *
     * @return {Parking[]} La trajet à travers les différentes configurations
     *      de parking pour arriver à la sortie.
     */
    private Parking[] find_shortest_path (int baseParking) {
        boolean[] nodeMark = new boolean[this.parkingList.size()];

        // Va stocker l'antécédent à chaques noeuds traité.
        // À chaque "case" de ce tableau il va être stocké
        // l'index de son antécédent.
        int[] nodePath = new int[this.parkingList.size()];
        nodePath[0] = 0;

        // Va stocker la distance par apport à la base.
        int[] countNode = new int[this.parkingList.size()];
        countNode[0] = 0;

        LinkedList<Integer> queue = new LinkedList<>(); // Stock les noeuds à traiter.
        int currentNode = baseParking;
        int newNode;

        while ( !(this.parkingList.get(currentNode).is_won()) ) {
            for (int i = 0; i < this.parkingList.size(); ++i) {
                if ( this.parkingGraph.get(currentNode).get(i) > 0 && !(nodeMark[i]) ) {
                    // Si le noeud est accessible et n'a pas encore été traité.
                    queue.addFirst(i);
                }
            }
            
            if ( queue.size() > 0 ) {
                newNode = queue.pop();
            } else {
                // Si on est arrivé au dernier noeud et qu'on a toujours 
                // pas trouvé de chemin gagnant.
                return null;
            }
            nodePath[newNode] = currentNode; // On met d'où vient le nouveau noeud.W
            countNode[newNode] = (countNode[currentNode] + 1); // Incrémente le compteur.
            currentNode = newNode;
        }
        // Si l'on sort normalement de la boucle, on a trouvé un chemin.
        // Il faut désormait créer une liste avec tout les chemins emprunté
        int resultSize = countNode[currentNode];
        Parking[] result = new Parking[resultSize];
        for (int i = resultSize; i >= 0; --i) {
            result[i] = this.parkingList.get(currentNode);
            currentNode = nodePath[currentNode];
        }
        return result;
    }

    RushHour() {
    }

    public static void main (String[] args) {
        if ( args.length > 0) {
            RushHour main = new RushHour();

            ParkingIN parsedParking = new ParkingIN(args[0]);
            Parking baseParking = parsedParking.parse_input_file();
            System.out.println(baseParking);
            main._generate_parkings(baseParking);
        } else {
            System.out.println("Veuillez passer le fichier en argument.");
        }
    }
}
