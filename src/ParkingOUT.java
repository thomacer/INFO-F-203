import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;

public class ParkingOUT {
    static private BufferedWriter creating_file(String filename){
        BufferedWriter bw = null;
        try{
            File file = new File(filename);
            file.createNewFile();
            FileWriter fw = new FileWriter(file);
            bw = new BufferedWriter(fw);
        } catch(IOException exc){
            System.out.print("Cannot create file!");
        }
        return bw;
    }

    /* @desc Imprime dans la console les déplacements fait par les voitures pour
     *      arriver à la solution.
     *
     * @param {resultPath} : Liste des parkings qui sont utiles pour la réponse.
     */
    static public void printToConsole(Parking[] resultPath){
        for (int carNum = 0; carNum < resultPath[0].get_number_of_car(); ++carNum) {
            // Pour printer comme dans l'énoncé il faut faire une voiture à la fois
            // et rechercher dans les solutions quand est-ce que celle-ci est bougé.

            // Cas de base.
            if (carNum==0){
                System.out.println("\nDéplacement éffectués par la voiture G:");
            }
            else{
                System.out.println("\nDéplacement éffectués par la voiture V" + carNum + ":");
            }
            System.out.println("1. " + resultPath[0].get(carNum));
            int count = 2;
            for (int j = 1; j < resultPath.length; ++j) {
                if (resultPath[j].get_moved_car().get_num() == carNum) {
                    System.out.println(count + ". " + resultPath[j].get_moved_car());
                    ++count;
                }
                
            }
        }
    }
    static public String print_move(Parking[] path) {
        String content = "";
        int precNum = -1;
        for (int i = 1; i < path.length; ++i){
            Car movedCar = path[i].get_moved_car();
            int newCarNum = movedCar.get_num();
            if (newCarNum != precNum) {
                if (newCarNum==0){
                    content += "\nDéplacements voiture G:\n";
                }
                else{
                    content += "\nDéplacements voiture V" + newCarNum + ":\n";
                }
                content +=  movedCar.posPreviousToString() + movedCar.posToString();
            } else {
                content += " -> " + movedCar.posToString();
            }
            precNum = newCarNum;
        }

        return content;
    }

    static public void print_init(Parking resultPath){
        String content;
        content = "Situation initiale :\n";
        content += resultPath;
        for (int i=0;i<resultPath.get_number_of_car();++i){
            if (i==0){
                content+="Emplacement de la voiture G:\n";
            }
            else{
            content+="Emplacement de la voiture V"+i+":\n";
            }
            content +=resultPath.get(i)+"\n";
        }
        BufferedWriter file=creating_file("Cas_de_base.txt");
        try{
            file.write(content);
            file.close();
        } catch(IOException e){
            System.out.print("Cannot close file!");
        }
    }

    static public void print_win(Parking[] resultPath){
        ParkingOUT.printToConsole(resultPath);
        String content;

        content = "Solution finale :\n";
        content += resultPath[resultPath.length - 1].toString();
        content += "Une façon de sortir du Parking en " + (resultPath.length - 1)
            + " mouvement a été trouvée.\n\n";
        // (resultPath.length - 1) est utilisé car le cas de base n'est pas considéré
        // comme un mouvement.
        content += print_move(resultPath);

        BufferedWriter file=creating_file("Solution.txt");
        try{
            file.write(content);
            file.close();
        } catch(IOException e){
            System.out.print("Cannot close file!");
        }
    }

    static private void print_to_console_fail (Parking lastParking) {
        System.out.println("Il n'y a pas moyen de sortir du parking");
        System.out.print("car la voiture ");

        // ---- Detection de la voiture blocante:

        int baseYpos = lastParking.get(0).get_y_pos().get(0);
        // Comme la voiture de base est toujours horizontale on doit 
        // juste savoir en quel ligne la voiture de base est.

        int i = 1;
        while (i < lastParking.get_number_of_car()) {
            int newYpos1 = lastParking.get(i).get_y_pos().get(0);
            int newYpos2 = lastParking.get(i).get_y_pos().get(1);
            if (baseYpos == newYpos1 || baseYpos == newYpos2) {
                // Vérification qu'une voiture est sur notre axe
                System.out.print(lastParking.get(i).get_num());
            }
            ++i;
        }
        System.out.println(" nous empêche de sortir.");
    }

    static public void print_no_result (Parking[] failPath) {
        print_to_console_fail(failPath[failPath.length - 1]);

        String content = "Situation finale\n";

        content += failPath[failPath.length - 1].toString();

        content += "Il n'y a pas moyen de sortir du parking.\n";

        content += print_move(failPath);

        BufferedWriter file = creating_file("Solution.txt");
        try{
            file.write(content);
            file.close();
        } catch(IOException e){
            System.out.print("Cannot close file!");
        }

    }
}
