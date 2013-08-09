package Protein3Way;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * Create KEGG Websites
 * @author gatech
 *
 */
public class KEGGPathway {

	
	public static void main(String[] args) {
		
		try {
			String[] title = {"Alzheimer_ENSEMBL.txt", "Glioma_ENSEMBL.txt", "Hedgehog_signaling_ENSEMBL.txt", "Long_term_potentiation_ENSEMBL.txt", "MAPK_signaling_ENSEMBL.txt", "mTOR_signaling_ENSEMBL.txt", "NOTCH_signaling_ENSEMBL.txt", "P53_signaling_ENSEMBL.txt", "Pathway_in_Cancer_ENSEMBL.txt", "TGF_signaling_ENSEMBL.txt", "Transcriptional_Misregulation_in_Cancer_ENSEMBL.txt", "WNT_signaling_ENSEMBL.txt"};
			String[] keggs = {"hsa05010", "hsa05214", "hsa04340", "hsa04720", "hsa04010", "hsa04150", "hsa04330", "hsa04115", "hsa05200", "hsa04350", "hsa05202", "hsa04310"};
			String[] title2 = {"Alzheimer_KEGG.txt", "Glioma_KEGG.txt", "Hedgehog_signaling_KEGG.txt", "Long_term_potentiation_KEGG.txt", "MAPK_signaling_KEGG.txt", "mTOR_signaling_KEGG.txt", "NOTCH_signaling_KEGG.txt", "P53_signaling_KEGG.txt", "Pathway_in_Cancer_KEGG.txt", "TGF_signaling_KEGG.txt", "Transcriptional_Misregulation_in_Cancer_KEGG.txt", "WNT_signaling_KEGG.txt"};			
			for (int i = 0; i < title.length; i++) {
				
				HashMap map = new HashMap();
				
				//String inputFile = "C:\\School Notes\\RNAseqCancer\\Analysis Result Report\\Human_Glioma\\DE All Genes\\IntersectAtLeast3\\KEGG_Pathways\\Filtered_" + title[i] + "_Output.txt";
				String inputFile = "C:\\School Notes\\RNAseqCancer\\Analysis Result Report\\Human_Glioma\\DE All Genes\\IntersectAll\\KEGG_Pathways\\Filtered_" + title[i] + "_Output.txt";
				FileInputStream fstream = new FileInputStream(inputFile);
				DataInputStream din = new DataInputStream(fstream); 
				BufferedReader in = new BufferedReader(new InputStreamReader(din));		
				while(in.ready()) {					
					String str = in.readLine();
					String[] split = str.split("\t");
					map.put(split[0], split[1]);
					
				}
				in.close();
				
				String path = "http://www.genome.jp/dbget-bin/show_pathway?" + keggs[i];
				
				inputFile = "C:\\School Notes\\RNAseqCancer\\Analysis Result Report\\Selected KEGG Pathways\\" + title2[i];
				fstream = new FileInputStream(inputFile);
				din = new DataInputStream(fstream); 
				in = new BufferedReader(new InputStreamReader(din));		
				while(in.ready()) {					
					String str = in.readLine();
					String[] split = str.split("\t");
					if (split.length > 1) {
						String geneName = split[1].split(";")[0];
						if (map.containsKey(geneName)) {
							path += "+hsa:" + split[0].trim();
						}					
					}
				}
				in.close();
				
				System.out.println(title2[i] + "\t" + path);
			}
			
			
		} catch (Exception e) {			
			e.printStackTrace();
		}		
	}
}
