package Coordinates;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

public class ExtractCoordinates {

	public static void main(String[] args) {
		
		try {
			
			HashMap map = new HashMap();
			//FileInputStream fstream = new FileInputStream("C:\\School Notes\\RNAseqCancer\\Final_DE_AnalysisPipeline\\Final Report Results\\Mouse_RNAseq_Cancer_Significant DE Expression\\Protein_GeneList\\Significant_protein_Comprehensive_Overlap_GeneList.txt");
			//FileInputStream fstream = new FileInputStream("C:\\School Notes\\RNAseqCancer\\Final_DE_AnalysisPipeline\\Final Report Results\\Mouse_RNAseq_Cancer_Significant DE Expression\\ncRNA_GeneList\\Significant_ncRNA_Comprehensive_Overlap_GeneList.txt");
			FileInputStream fstream = new FileInputStream("C:\\School Notes\\Glioma\\Report\\DE Summary\\Protein\\coding_differential_expression_comprehensive_foldchange.txt");
			DataInputStream din = new DataInputStream(fstream); 
			BufferedReader in = new BufferedReader(new InputStreamReader(din));
			while (in.ready()) {
				String str = in.readLine();
				String[] split = str.split("\t");
				map.put(split[0], str);
			}
			in.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String grabMeta(String text, String id) {
		String returnval = "";
		if (text.contains(id)) {
			String val = text.split(id)[1].split(";")[0].trim();
			val = val.replaceAll("\"", "");
			val.trim();
			returnval = val;				
		}
		return returnval;
	}
}
