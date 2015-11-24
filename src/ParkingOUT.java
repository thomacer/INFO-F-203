import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;

public class ParkingOUT {
	private String _filename;
	private int _xSize;
	private int _ySize;
	
	ParkingOUT(String filename,int xSize,int ySize){
		this._filename=("Solution_"+filename);
		this._xSize=xSize;
		this._ySize=ySize;
	}
	
	public BufferedWriter creating_file(){
		BufferedWriter bw=null;
		try{
		File file=new File(this._filename);
		file.createNewFile();
		FileWriter fw = new FileWriter(file);
		bw=new BufferedWriter(fw);
		}
		catch(IOException exc){
		System.out.print("Cannot create file!");
		}
		return bw;
	}
	
	public void printing(){
		String content;
		BufferedWriter file=this.creating_file();
		try{
			content=this.title();
			System.out.print(content);
			file.write(content);
			content=this.tableau();		
			System.out.print(content);
			
			
			
			
			file.write(content);
			file.close();
		}
		catch(IOException e){
			System.out.print("Cannot close file!");
		}
	}
	public String title(){
		String res="Solution finale :\n";
		return res;
	}
	public String tableau(){
		String res="";
		res+=this.make_line("+---",this._xSize);
		res+="+\n";
		int taille=res.length();
		//System.out.print(taille);
		for (int j=1;j<=this._ySize*2-1;++j){
			if(j%2==1){
				res+="|";
				res+=this.make_line(" ", taille-3);		///fonction qui va checker si qqch sur la ligne ou bien si exit gauche ou droite
				res+="|\n";	
			}
			else if(j%2==0){
				res+="+";
				res+=this.make_line("   +", this._xSize);
				res+="\n";
				}	
		}
		res+=this.make_line("+---",this._xSize);
		res+="+\n";
		return res;
	}
	public String make_line(String c,int fin){
		String chaine="";
		for (int i=0;i<fin;++i){
			chaine+=c;
		}
		return chaine;
	}
	
	public static void main(String args[]){
		ParkingOUT fichier_out=new ParkingOUT("test.txt",5,5);
		fichier_out.printing();
		
	}
}
