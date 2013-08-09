package Protein3Way;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;

public class SignificantExpressed {

	
	public static void main(String[] args) {
						
		try {
			HashMap de = new HashMap();
			HashMap map = new HashMap();
			
			//String inputFile = "C:\\School Notes\\Glioma\\Glioma_DE_Output_Files\\coding_differential_expression.txt";
			String inputFile = "C:\\School Notes\\Glioma\\Glioma_DE_Output_Files\\noncoding_differential_expression.txt";
			FileInputStream fstream = new FileInputStream(inputFile);
			DataInputStream din = new DataInputStream(fstream); 
			BufferedReader in = new BufferedReader(new InputStreamReader(din));		
			String titlez = "GeneID\t" + in.readLine();
			while (in.ready()) {
				String str = in.readLine();
				String[] split = str.split("\t");
				map.put(split[0], str);

				int count = 0;
				if (split[3].equals("true")) {
					count ++;
				}
				if (split[6].equals("true")) {
					count ++;
				}
				if (split[9].equals("true")) {
					count ++;
				}
				if (split[12].equals("true")) {
					count ++;
				}

				if (count >= 3) {
					System.out.println(split[0]);
				}
			}
			in.close();
			System.exit(0);
			
			String[] title = {"Alzheimer_ENSEMBL.txt", "Glioma_ENSEMBL.txt", "Hedgehog_signaling_ENSEMBL.txt", "Long_term_potentiation_ENSEMBL.txt", "MAPK_signaling_ENSEMBL.txt", "mTOR_signaling_ENSEMBL.txt", "NOTCH_signaling_ENSEMBL.txt", "P53_signaling_ENSEMBL.txt", "Pathway_in_Cancer_ENSEMBL.txt", "TGF_signaling_ENSEMBL.txt", "Transcriptional_Misregulation_in_Cancer_ENSEMBL.txt", "WNT_signaling_ENSEMBL.txt"};
			String[] title2 = {"Alzheimer_KEGG_hsa05010.txt", "Glioma_KEGG_hsa05214.txt", "Hedgehog_signaling_KEGG_hsa04340.txt", "Long_term_potentiation_KEGG_hsa04720.txt", "MAPK_signaling_KEGG_hsa04010.txt", "mTOR_signaling_KEGG_hsa04150.txt", "NOTCH_signaling_KEGG_hsa04330.txt", "P53_signaling_KEGG_hsa04115.txt", "Pathway_in_Cancer_KEGG_hsa05200.txt", "TGF_signaling_KEGG_hsa04350.txt", "Transcriptional_Misregulation_in_Cancer_KEGG_hsa05202.txt", "WNT_signaling_KEGG_hsa04310.txt"};
			for (int i = 0; i < title.length; i++) {
				String[] split9 = title2[i].replaceAll("\\.txt", "").split("_");
				String hsa = split9[split9.length - 1];
				
								
				HashMap KEGG = new HashMap();
				String kegg_file = "C:\\School Notes\\Glioma\\KEGGID\\" + title[i];
			    fstream = new FileInputStream(kegg_file);
				din = new DataInputStream(fstream); 
				in = new BufferedReader(new InputStreamReader(din));
				while (in.ready()) {
					String str = in.readLine();
					String[] split = str.split("\t");
					KEGG.put(split[1], split[0]);
				}
				in.close();

				HashMap KEGG2 = new HashMap();
				String kegg_file2 = "C:\\School Notes\\Glioma\\KEGGID\\" + title2[i];
			    fstream = new FileInputStream(kegg_file2);
				din = new DataInputStream(fstream); 
				in = new BufferedReader(new InputStreamReader(din));
				while (in.ready()) {
					String str = in.readLine();
					String[] split = str.split("\t");
					KEGG2.put(split[1].split(";")[0], split[0]);
				}
				in.close();
			
				
				String outputFile = "C:\\School Notes\\Glioma\\Final Summary\\" + title[i] + "_Output.txt";
				FileWriter fwriter = new FileWriter(outputFile);
				BufferedWriter out = new BufferedWriter(fwriter);
				out.write(titlez + "\n");
				Iterator itr = map.keySet().iterator();
				while (itr.hasNext()) {
					String key = (String)itr.next();
					if (KEGG.containsKey(key)) {
						String strin = (String)map.get(key);
						String tag = (String)KEGG.get(key);
						out.write(tag + "\t" + strin + "\n");
					}
				}
				out.close();
				
				String extra = "";
				String outputFile2 = "C:\\School Notes\\Glioma\\Final Summary\\Filtered_" + title[i] + "_Output.txt";
				FileWriter fwriter2 = new FileWriter(outputFile2);
				BufferedWriter out2 = new BufferedWriter(fwriter2);
				out2.write("GeneID\tEnsembleID" + "\n");
				itr = map.keySet().iterator();
				while (itr.hasNext()) {
					String key = (String)itr.next();
					if (KEGG.containsKey(key)) {
						String strin = (String)map.get(key);
						String tag = (String)KEGG.get(key);
						String[] split = strin.split("\t");		
						int count = 0;
						if (split[3].equals("true")) {
							count ++;
						}
						if (split[6].equals("true")) {
							count ++;
						}
						if (split[9].equals("true")) {
							count ++;
						}
						if (split[12].equals("true")) {
							count ++;
						}
						//if (split[3].equals("true") && split[6].equals("true") && split[9].equals("true")) {
						if (count >= 4) {
							String hsaTag = "hsa:" + KEGG2.get(tag);
							extra += "+" + hsaTag.trim();
							out2.write(tag + "\t" + key + "\n");							
						}
														
					}
				}
				out2.close();
				
				//System.out.println(title[i] + "\t" + "http://www.genome.jp/dbget-bin/show_pathway?" + hsa + extra);
			}
								
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
