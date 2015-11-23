import java.util.Scanner;
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
		_filename=file;
	}
	
	public void parse_input_file(){
		BufferedReader file_reader=null;
		try{
			file_reader=new BufferedReader(new FileReader(this._filename));
			this.parse_parking(file_reader);
			this.parse_element(file_reader);
			this.parse_emplaceme(file_reader);
			file_reader=new BufferedReader(new FileReader(this._filename));		//reouvre le fichier
			this.parse_exit(file_reader);
		}
		catch( FileNotFoundException exception){
			System.out.print("File not found!Try again");
		}
	}
	
	public void parse_parking(BufferedReader file){
		try{
			String line;
			line=file.readLine();
			this._xSize=this.get_next_int(line, line.indexOf(":"));
			this._ySize=this.get_next_int(line, line.indexOf("s"));
			
		}catch(IOException ioe){
			System.out.print("Erreur --"+ioe.toString());
		}
	}
	public void parse_element(BufferedReader file){
		try{
			String line;
			line=file.readLine();
			for (int i=0;i<=this._ySize*2+1;++i){
				line=file.readLine();
			}
			this._nbreGoal=this.get_next_int(line, line.indexOf(":"));
			line=file.readLine();
			this._nbreVoiture=this.get_next_int(line, line.indexOf(":"));
			
			
		}catch(IOException ioe){
			System.out.print("Erreur --"+ioe.toString());
		}
	}
	public void parse_emplaceme(BufferedReader file){
		try{
			String line;
			line=file.readLine(); 		
			for (int i=0;i<this._nbreGoal+this._nbreVoiture;++i){
				line=file.readLine();	//chaque ligne
				int x=this.get_next_int(line, line.indexOf("("));//premier x coord
				int y=this.get_next_int(line, line.indexOf(","));//premier y coord
				//System.out.print(x);			//premier x coord
				//System.out.print(y);			//premier y coord
				int xt=this.get_next_int(line, line.lastIndexOf("(")); 	//deuximee x coord
				int yt=this.get_next_int(line, line.lastIndexOf(","));	//deuxiem y coord
				//System.out.print(" ");
				//System.out.print(xt);	//deuximee x coord
				//System.out.print(yt);	//deuxiem y coord
				//System.out.print(" ");
				
			}
			
		}catch(IOException ioe){
			System.out.print("Erreur --"+ioe.toString());
		}
	}
	public void parse_exit(BufferedReader file){
		try{
			this._exit=new int[4];			// position 0,1 premiere coorde position 2,3 deuxieme coordonne
			String line;
			line=file.readLine();			//on lit les deux ligne qui ne nous servent a rien
			line=file.readLine();
			int j=1;						//conteur pour savoir le nombre de ligne qu'on saute
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
					System.out.print("fin");
					System.out.print("("+this._exit[0]+","+this._exit[1]+")"+", "+"("+this._exit[2]+","+this._exit[3]+")");
					}
				}
			}
		catch(IOException ioe){
			System.out.print("Erreur --"+ioe.toString());
		}
	}
	private int get_next_int(String line, int i) {
		boolean cycle=true;
        while (i < line.length() && cycle)
        {
        	if (Character.isDigit(line.charAt(i))){
        		cycle=false;
        	}
        	else{
        		++i;
        	}
        }
        int j = i;
        ++j;
        cycle=true;
        while (j < line.length() && cycle)
        {
        	if (!Character.isDigit(line.charAt(j))){
        		cycle=false;
        	}
        	else{
        		++j;
        	}
        }
        String res="";
        for(int k=i;k<j;++k){
        	res+=line.charAt(k);
        }
        int result = Integer.parseInt(res);
        return result;
    }
	public static void main(String args[]){
		Scanner scan=new Scanner(System.in);
		System.out.print("Enter file name :");
		String file=scan.nextLine();
		ParkingIN test=new ParkingIN(file);
		test.parse_input_file();
		
		
		
		
	}
	
}
