import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;

public class Parking {
    // ATTRIBUTES
    private int[2] exit;
    private int[[]] parking;

    // METHODS
    private int get_next_int(String line, int i) {
        while (i < line.length()
                && line[i] < '0'
                && line[i] > '9') 
        {
            i++;
        }
        // À ce point i représente le moment où 
        // les entiers commencent.
        int j = i;
        while (j < line.length 
                && line[j] >= '0'
                && line[j] <= '9')
        {
            // Tant que l'index pointe sur un entier.
            ++j;
        }

        int result = Integer.parseInt(line[i:j]);

        return result;
    }

    /* @desc Parse l'aspect du parking.
     *  Dans cette partie, va être interprété:
     *      Parking: 5 fois 5
     *               ^      ^
     *               |      |
     *               x      y
     *
     *      +---+---+---+---+---+
     *      |                   |
     *      + ...
     *
     * @param file Opened file.
     *
     * @return Une valeur qui nous laisse savoir 
     *      si l'exécution s'est bien passé ou non.
     */
    private void parse_parking (BufferedReader file) {
        String line = file.readLine(); 
        int i = 0;
        int x_size = get_next_int(line, i);
        int y_size = get_next_int(line, i);
       
        // Avoir la taille dans une des premières ligne.
        delimiters = "+---" * x_size;
        // endOfLine = "+" + "---+" * size;
        separator_1 = "+" + "   +" * size;
        separator_2 = "|" + "    " * (size - 1) + "   |";
        // Deuxième type de séparateur qui doit comporter une sortie.
        while ( delimiters != file.readLine() )
            // imiter le in de python.

        int i = 0
        line = file.readLine();
        while ( i < y_size ) {
            line.readLine();
            if ( line != separator_2 ) {
                // set there is the exit.                
            }
        }
        // Le fichier se met à la fin de la représentation du parking

        // line should be 
    }

    /* @desc Parse l'aspect du parking.
     *  Ex:
     *      Elements du Parking:
     *          voiture Goal: <Nombre de voitures Goal>
     *          Autres voitures: <Nmbre de voitures autres>
     *
     *  --------------------------
     *
     * @param file Opened file.
     *
     * @return Une valeur qui nous laisse savoir 
     *      si l'exécution s'est bien passé ou non.
     */
    private void parse_elements (BufferedReader file) {
    }

    /* @desc Parse l'aspect du parking.
     *
     * @param file Opened file.
     *
     * @return Une valeur qui nous laisse savoir 
     *      si l'exécution s'est bien passé ou non.
     */
    private void parse_position (BufferedReader file) {
        
    }

    public void parse_input_file (String file_path) {
        /* 1) Open file
         * 2) Parse parking
         * 3) Parse elements
         * 4) Parse position
         */
        BufferedReader file_reader = new BufferedReader(new FileReader(path));
        parse_parking(file_reader);
        parse_elements(file_reader);
        parse_position(file_reader);
    }

    public void print_output_file () {
    }
}
