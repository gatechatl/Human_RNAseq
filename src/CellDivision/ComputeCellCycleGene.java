package CellDivision;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;

public class ComputeCellCycleGene {

	public static void main(String[] args) {
		
		try {
			
			String outputFile = "C:\\School Notes\\RNAseqCancer\\Analysis Result Report\\Human_Glioma\\Cell Cycle\\Comprehensive.txt";
			FileWriter fwriter = new FileWriter(outputFile);
			BufferedWriter out = new BufferedWriter(fwriter);

			
			HashMap everything = new HashMap();
			HashMap reactome = new HashMap();
			String inputFile = "C:\\School Notes\\RNAseqCancer\\Analysis Result Report\\Human_Glioma\\Cell Cycle\\Reactome.csv";
			FileInputStream fstream = new FileInputStream(inputFile);
			DataInputStream din = new DataInputStream(fstream); 
			BufferedReader in = new BufferedReader(new InputStreamReader(din));
			while (in.ready()) {
				String str = in.readLine();
				String[] split = str.split("\t");
				if (split.length >= 4) {
					if (split[3].toUpperCase().contains("CELL CYCLE") || split[3].toUpperCase().contains("CELL DIVISION")) {
						reactome.put(split[0], split[3]);
						everything.put(split[0], split[0]);
					}
				}
				
			}
			in.close();
			System.out.println(reactome.size());
			
			HashMap GO = new HashMap();
			inputFile = "C:\\School Notes\\RNAseqCancer\\Analysis Result Report\\Human_Glioma\\Cell Cycle\\GO_Ensembl.txt";
			fstream = new FileInputStream(inputFile);
			din = new DataInputStream(fstream); 
			in = new BufferedReader(new InputStreamReader(din));
			while (in.ready()) {
				String str = in.readLine();
				GO.put(str, str);
				everything.put(str, str);
			}
			in.close();
			
			HashMap KEGG = new HashMap();
			inputFile = "C:\\School Notes\\RNAseqCancer\\Analysis Result Report\\Human_Glioma\\Cell Cycle\\KEGG.txt";
			fstream = new FileInputStream(inputFile);
			din = new DataInputStream(fstream); 
			in = new BufferedReader(new InputStreamReader(din));
			while (in.ready()) {
				String str = in.readLine();
				KEGG.put(str, str);
				everything.put(str, str);
			}
			in.close();
			
			HashMap SP_PIR = new HashMap();
			inputFile = "C:\\School Notes\\RNAseqCancer\\Analysis Result Report\\Human_Glioma\\Cell Cycle\\SP_PIR.txt";
			fstream = new FileInputStream(inputFile);
			din = new DataInputStream(fstream); 
			in = new BufferedReader(new InputStreamReader(din));
			while (in.ready()) {
				String str = in.readLine();
				SP_PIR.put(str, str);
				everything.put(str, str);
			}
			in.close();
			out.write("EnsemblID\tReactome\tGO(DAVID)\tKEGG(DAVID)\tSP_PIR(DAVID)\n");
			Iterator itr = everything.keySet().iterator();
			while (itr.hasNext()) {
				String key = (String)itr.next();
				out.write(key);
				if (reactome.containsKey(key)) {
					out.write("\tyes");					
				} else {
					out.write("\tno");
				}
				
				if (GO.containsKey(key)) {
					out.write("\tyes");					
				} else {
					out.write("\tno");
				}
				
				if (KEGG.containsKey(key)) {
					out.write("\tyes");					
				} else {
					out.write("\tno");
				}
				
				if (SP_PIR.containsKey(key)) {
					out.write("\tyes");					
				} else {
					out.write("\tno");
				}
				out.write("\n");
			}
			
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
