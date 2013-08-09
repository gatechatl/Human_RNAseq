package VennDiagram;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;

public class CreateProteinNcRNAList {

	public static void main(String[] args) {
		
		
		try {
			
			HashMap map = new HashMap();
			String fileName = "C:\\School Notes\\RNAseqCancer\\Analysis Result Report\\General_Information\\Human_Gene_Info\\Human_nonCoding.bed";
			FileInputStream fstream = new FileInputStream(fileName);
			DataInputStream din = new DataInputStream(fstream);
			BufferedReader in = new BufferedReader(new InputStreamReader(din));			
			while (in.ready()) {
				String str = in.readLine();
				String[] split = str.split("\t");
				split[3] = split[3].replaceAll("\"", "");
				map.put(split[3], split[3]);
				
			}
			in.close();
			
			String outputFile = "C:\\School Notes\\RNAseqCancer\\Analysis Result Report\\General_Information\\Human_Gene_Info\\Human_nonCoding_list.txt";
			FileWriter fwriter = new FileWriter(outputFile);
			BufferedWriter out = new BufferedWriter(fwriter);
			Iterator itr = map.keySet().iterator();
			while (itr.hasNext()) {
				String key = (String)itr.next();
				out.write(key + "\n");
				
			}
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
