import java.util.Scanner;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ParkingIN {
    static private int get_next_int(String line, int i) {
        while (i < line.length() && !Character.isDigit(line.charAt(i))) {
            ++i;
        }

        int j = i;
        String res="";
        while (j < line.length() && Character.isDigit(line.charAt(j))) {
        	res+=line.charAt(j);
            ++j;
        }

        int result = Integer.parseInt(res);

        return result;
    }

    /* @desc Ajoute les poisitions des voitures dans le parking, 
     *      à partir du fichier en input
     */
    static private void parse_emplacement(BufferedReader file, int carNumber, Parking parkingRef){
        try{
            String line;
            line=file.readLine();	//emplacement
            ArrayList<Integer> xPos;
            ArrayList<Integer> yPos;
            for (int i=0; i < carNumber; ++i){
                line=file.readLine(); //chaque ligne

                xPos = new ArrayList<Integer>(0);
                yPos = new ArrayList<Integer>(0);

                yPos.add( get_next_int(line, line.indexOf("(")) ); //premier x coord
                xPos.add( get_next_int(line, line.indexOf(",")) ); //premier y coord
                yPos.add( get_next_int(line, line.lastIndexOf("(")) ); //deuximee x coord
                xPos.add( get_next_int(line, line.lastIndexOf(",")) ); //deuxiem y coord
                parkingRef.add_car(xPos, yPos);			//ajout des position
            }
            
        }catch(IOException ioe){
            System.out.print("Erreur --"+ioe.toString());
        }
    }

    /* @desc Détecte le nombre de voiture Goal et normale dans le fichier en input.
     */
    static private int[] parse_element(BufferedReader file){
        int[] nbreVoiture = new int[2];
        try{
            String line;
            line = file.readLine(); //emplacement:
            line = file.readLine(); // noombre de voiture GOAL 
            nbreVoiture[0] = get_next_int(line, line.indexOf(":"));
            line = file.readLine(); // voiture 
            nbreVoiture[1] = get_next_int(line, line.indexOf(":"));
        }catch(IOException ioe){
            System.out.print("Erreur --"+ioe.toString());
        }
        return nbreVoiture;
    }

    static private int[] parse_exit(BufferedReader file, int xSize, int ySize){
        int[] exit = new int[2];			// position 0,1 premiere coorde position 2,3 deuxieme coordonne
        try{
            String line;
            line = file.readLine();		//premiere ligne +---+
            int j = 1;					//conteur pour savoir le nombre de ligne qu'on saute
            for (int i=1; (i <= ySize * 2 - 1); ++i){
                line = file.readLine();
                if (i % 2 == 0){
                    //pas les ligne qui contien +---+
                    line = file.readLine();
                    ++i;
                    ++j;
                }
                if (line.charAt(0) == ' '){ //en debut de ligne
                    exit[0]=0;  //(0,0) (1,0)
                    exit[1]=i-j;
                }
                else if(line.lastIndexOf('|') == 0) { //en fin de ligne
                    exit[0] = xSize - 1; //(3,2) (4,2)
                    exit[1]=i-j;
                }
            }
            line=file.readLine();				//derniere ligne du tableau
        }
        catch(IOException ioe){
            System.out.print("Erreur --"+ioe.toString());
        }
        return exit;
    }

    /* @desc Détecte la taille du parking dans le fichier en input.
     */
    static private int[] parse_parking(BufferedReader file){
        int[] size = new int[2];
        try{
            String line;
            line=file.readLine();
            size[0] = get_next_int(line, line.indexOf(":"));
            size[1] = get_next_int(line, line.indexOf("s"));
        }catch(IOException ioe){
            System.out.print("Erreur --"+ioe.toString()); // TODO Throw au lieu d'un simple message d'erreur.
        }
        return size;
    }

    static public Parking parse_input_file(String filename) {
        BufferedReader file_reader;
        Parking baseParking = null;
        String content;
        try{
            file_reader=new BufferedReader(new FileReader(filename));
            int[] size = parse_parking(file_reader);
            System.out.println("Le parking a une dimension " + size[0] + " fois " + size[1] + "\n");

            int[] exit = parse_exit(file_reader, size[0], size[1]);

            baseParking = new Parking(size[0], size[1], exit);

            int[] numOfCar = parse_element(file_reader);
            System.out.println("Il contient " + numOfCar[0] + " voiture Goal et " 
                    + numOfCar[1] + " autres voitures.");

            parse_emplacement(file_reader, numOfCar[0] + numOfCar[1], baseParking);

            for (Car toPrint : baseParking) {
                int carNumber = toPrint.get_num() ;
                String carNumberString = "";
                if (carNumber == 0) {
                    carNumberString = "Goal";       
                } else {
                    carNumberString = Integer.toString(carNumber);
                }
                System.out.println("La voiture " + carNumberString 
                        + " se trouve en position: " + toPrint);
            }
            file_reader.close();
        }
        catch(FileNotFoundException exception){
            System.out.print("File not found!Try again");
        }
        catch(IOException file){
            System.out.print("File cannot be closed");
        }
        return baseParking;
    }
}
