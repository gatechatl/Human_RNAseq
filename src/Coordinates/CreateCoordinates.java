package Coordinates;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;

public class CreateCoordinates {

	
	public static void main(String[] args) {		
		try {			
			
			HashMap map_min = new HashMap();
			HashMap map_max = new HashMap();
			HashMap map_chr = new HashMap();
			
			HashMap query = new HashMap();
			
			//String outputFile = "C:\\School Notes\\RNAseqCancer\\Analysis Result Report\\Human_Glioma\\Supplementary\\Coordinates\\Human_Other.txt";
			String outputFile = "C:\\School Notes\\RNAseqCancer\\Analysis Result Report\\Human_Glioma\\Significant Differential Expressed Genes\\Coordinates\\IntersectAll\\Protein_Coord_DE_Across_All.txt";
			FileWriter fwriter = new FileWriter(outputFile);
			BufferedWriter out = new BufferedWriter(fwriter);

			FileInputStream fstream = new FileInputStream("C:\\School Notes\\RNAseqCancer\\Analysis Result Report\\Human_Glioma\\DE All Genes\\IntersectAll\\Protein_Differentially_Expressed_Across_All.txt");
			DataInputStream din = new DataInputStream(fstream); 
			BufferedReader in = new BufferedReader(new InputStreamReader(din));
			while(in.ready()) {
				String str = in.readLine();
				query.put(str, str);
			}
			in.close();
			//fstream = new FileInputStream("C:\\School Notes\\RNAseqCancer\\Analysis Result Report\\Human_Glioma\\Supplementary\\Coordinates\\Human_ncRNA.bed");
			fstream = new FileInputStream("C:\\School Notes\\RNAseqCancer\\Analysis Result Report\\Human_Glioma\\Supplementary\\Coordinates\\Human_Coding.bed");
			din = new DataInputStream(fstream); 
			in = new BufferedReader(new InputStreamReader(din));
			while (in.ready()) {
				String str = in.readLine();
				String[] split = str.split("\t");
				split[3] = split[3].replaceAll("\"", "");
				map_chr.put(split[3], split[0]);
				if (map_min.containsKey(split[3])) {
					double min = new Double(split[1]); 
					double prev_min = (Double)map_min.get(split[3]);
					if (prev_min > min) {
						map_min.put(split[3], min);
					}
				} else {
					double min = new Double(split[1]);
					map_min.put(split[3], min);
				}
				
				if (map_max.containsKey(split[3])) {
					double max = new Double(split[1]); 
					double prev_max = (Double)map_max.get(split[3]);
					if (prev_max < max) {
						map_max.put(split[3], max);
					}
				} else {
					double max = new Double(split[1]);
					map_max.put(split[3], max);
				}
				
			}
			in.close();
			
			Iterator itr = map_max.keySet().iterator();
			while (itr.hasNext()) {
				String key = (String)itr.next();
				if (query.containsKey(key)) {
					out.write(key + "\t" + map_chr.get(key) + "\t" + new Double((Double)map_min.get(key)).intValue() + "\t" + (new Double((Double)map_max.get(key))).intValue() + "\n");
				}
			}
			out.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
