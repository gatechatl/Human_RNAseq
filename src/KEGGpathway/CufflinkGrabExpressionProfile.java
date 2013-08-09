package KEGGpathway;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.HashMap;

public class CufflinkGrabExpressionProfile {

	public static void main(String[] args) {
		
		try {
			String[] samples = {"17", "31"};
			for (int k = 0; k < samples.length; k++) {
				String[] title = {"Alzheimer_ENSEMBL.txt", "Ezh2_ENSEMBL.txt", "Glioma_ENSEMBL.txt", "Hedgehog_signaling_ENSEMBL.txt", "Long_term_potentiation_ENSEMBL.txt", "MAPK_signaling_ENSEMBL.txt", "mTOR_signaling_ENSEMBL.txt", "NOTCH_signaling_ENSEMBL.txt", "P53_signaling_ENSEMBL.txt", "Pathway_in_Cancer_ENSEMBL.txt", "TGF_signaling_ENSEMBL.txt", "Transcriptional_Misregulation_in_Cancer_ENSEMBL.txt", "WNT_signaling_ENSEMBL.txt"};
				for (int i = 0; i < title.length; i++) {
					HashMap KEGG = new HashMap();
					String kegg_file = "C:\\School Notes\\RNAseqCancer\\KEGGID\\" + title[i];
				    FileInputStream fstream = new FileInputStream(kegg_file);
					DataInputStream din = new DataInputStream(fstream); 
					BufferedReader in = new BufferedReader(new InputStreamReader(din));
					while (in.ready()) {
						String str = in.readLine();
						String[] split = str.split("\t");
						KEGG.put(split[0], split[1]);
					}
					in.close();
					String outputFile = "C:\\School Notes\\RNAseqCancer\\NoIsoform_CufflinkResult\\Cufflink_Logfold_" + samples[k] + "_" + title[i] + ".output";
					FileWriter fwriter = new FileWriter(outputFile);
					BufferedWriter out = new BufferedWriter(fwriter);
					
					
					String inputFile = "C:\\School Notes\\RNAseqCancer\\NoIsoform_CufflinkResult\\diff_out_" + samples[k] + "_1634_gene_exp.diff";
				    fstream = new FileInputStream(inputFile);
					din = new DataInputStream(fstream); 
					in = new BufferedReader(new InputStreamReader(din));				
					while (in.ready()) {
						String str = in.readLine();
						String[] split = str.split("\t");
						String geneIDs = split[2];
						String[] split2 = geneIDs.split(",");
						for (int j = 0; j < split2.length; j++) {							
							if (KEGG.containsKey(split2[j])) {
								double logfold = new Double(split[9]) * -1;
								out.write(split2[j] + "\t" + KEGG.get(split2[j]) + "\t" + logfold + "\n");
							}
						}
					}
					in.close();
					out.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
