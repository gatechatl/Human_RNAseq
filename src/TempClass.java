import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.HashMap;


public class TempClass {

	public static void main(String[] args) {
		
		try {
			
			String outputFile = "C:\\School Notes\\RNAseqCancer\\Analysis Result Report\\Human_Glioma\\DE All Genes\\IntersectAtLeast3\\Protein_Differentially_Expressed_AtLeastThree_GeneName.txt";
			FileWriter fwriter = new FileWriter(outputFile);
			BufferedWriter out = new BufferedWriter(fwriter);
			
			HashMap map = new HashMap();
			String inputFile = "C:\\School Notes\\RNAseqCancer\\Analysis Result Report\\Human_Glioma\\Supplementary\\Human_Protein_Coordinates.txt";
			FileInputStream fstream = new FileInputStream(inputFile);
			DataInputStream din = new DataInputStream(fstream); 
			BufferedReader in = new BufferedReader(new InputStreamReader(din));		
			while (in.ready()) {
				String str = in.readLine();
				String[] split = str.split("\t");
				map.put(split[0], split[1]);				
			}
			in.close();
			
			inputFile = "C:\\School Notes\\RNAseqCancer\\Analysis Result Report\\Human_Glioma\\DE All Genes\\IntersectAtLeast3\\Protein_Differentially_Expressed_AtLeastThree.txt";
			fstream = new FileInputStream(inputFile);
			din = new DataInputStream(fstream); 
			in = new BufferedReader(new InputStreamReader(din));		
			while (in.ready()) {
				String str = in.readLine();				
				String geneName = (String)map.get(str.trim());				
				out.write(geneName + "\n");
			}
			in.close();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
