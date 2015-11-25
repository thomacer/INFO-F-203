import java.util.Scanner;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ParkingIN {

	private String _filename;
	private int _xSize;
	private int _ySize;
	private int _nbreGoal;
	private int _nbreVoiture;
	private int[] _exit;
	
	ParkingIN(String file){			//constructor
		_filename=file;								//a appler dans main principal
	}
	
	public Parking parse_input_file(){				//a apepler poir que tout
		BufferedReader file_reader;
        Parking baseParking = null;
		try{
			file_reader=new BufferedReader(new FileReader(this._filename));
			this.parse_parking(file_reader);
			this.parse_exit(file_reader);
			this.parse_element(file_reader);
			baseParking=new Parking(this._xSize,this._ySize,this._exit);
			this.parse_emplaceme(file_reader, baseParking);
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
	
	public void parse_parking(BufferedReader file){
		try{
			String line;
			line=file.readLine();
			this._xSize=this.get_next_int(line, line.indexOf(":"));
			this._ySize=this.get_next_int(line, line.indexOf("s"));
		}catch(IOException ioe){
			System.out.print("Erreur --"+ioe.toString()); // TODO Throw au lieu d'un simple message d'erreur.
		}
	}
	public void parse_element(BufferedReader file){
		try{
			String line;
			line=file.readLine();	//emplacement:
			line=file.readLine();	//voiture GOAL
			this._nbreGoal=this.get_next_int(line, line.indexOf(":"));
			line=file.readLine();	//voiture 
			this._nbreVoiture=this.get_next_int(line, line.indexOf(":"));
			
			
		}catch(IOException ioe){
			System.out.print("Erreur --"+ioe.toString());
		}
	}
	public void parse_emplaceme(BufferedReader file, Parking parkingRef){
		try{
			String line;
			line=file.readLine();	//emplacement
            ArrayList<Integer> xPos;
            ArrayList<Integer> yPos;
			for (int i=0;i<this._nbreGoal+this._nbreVoiture;++i){
				line=file.readLine(); //chaque ligne

                xPos = new ArrayList<Integer>(0);
                yPos = new ArrayList<Integer>(0);

				yPos.add( this.get_next_int(line, line.indexOf("(")) ); //premier x coord
				xPos.add( this.get_next_int(line, line.indexOf(",")) ); //premier y coord
				yPos.add( this.get_next_int(line, line.lastIndexOf("(")) ); //deuximee x coord
				xPos.add( this.get_next_int(line, line.lastIndexOf(",")) ); //deuxiem y coord
			    parkingRef.add_car(xPos, yPos);			//ajout des position
			}
			
		}catch(IOException ioe){
			System.out.print("Erreur --"+ioe.toString());
		}
	}
	public void parse_exit(BufferedReader file){
		try{
			this._exit=new int[4];			// position 0,1 premiere coorde position 2,3 deuxieme coordonne
			String line;
			line=file.readLine();		//premiere ligne +---+
			int j=1;					//conteur pour savoir le nombre de ligne qu'on saute
			for (int i=1;(i<=this._ySize*2-1);++i){
				line=file.readLine();
				if (i%2==0){					//pas les ligne qui contien +---+
					line=file.readLine();
					++i;
					++j;
				}
				if (line.indexOf(" ")==0){					//en debut de ligne
					this._exit[0]=0;							//(0,0) (1,0)
					this._exit[1]=i-j;
					this._exit[2]=1;							//(0,2) (1,2)
					this._exit[3]=i-j;
					System.out.print("debut");
					System.out.print("("+this._exit[0]+","+this._exit[1]+")"+", "+"("+this._exit[2]+","+this._exit[3]+")");
					}
				else if(line.lastIndexOf("|")!=line.length()-1) {		//en fin de ligne
					this._exit[0]=this._xSize-2;							//(3,2) (4,2)
					this._exit[1]=i-j;
					this._exit[2]=this._xSize-1;							//(1,0)
					this._exit[3]=i-j;
					// System.out.print("fin");
					// System.out.print("("+this._exit[0]+","+this._exit[1]+")"+", "+"("+this._exit[2]+","+this._exit[3]+")");
					}
				}
				line=file.readLine();				//derniere ligne du tableau
				//System.out.print(line);

			}
		catch(IOException ioe){
			System.out.print("Erreur --"+ioe.toString());
		}
	}

    private int get_next_int(String line, int i) {
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

	public static void main(String args[]){
		//Scanner scan=new Scanner(System.in);
		//System.out.print("Enter file name :");
		String file="../test/test1.txt";
		ParkingIN test=new ParkingIN(file);
		test.parse_input_file();
	}
}
