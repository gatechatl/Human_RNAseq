package DifferentiallyExpressedGenes;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;

public class MergeLogFoldPerPathway {

	public static void main(String[] args) {
		
		try {
			String[] title = {"Alzheimer_ENSEMBL.txt", "Ezh2_ENSEMBL.txt", "Glioma_ENSEMBL.txt", "Hedgehog_signaling_ENSEMBL.txt", "Long_term_potentiation_ENSEMBL.txt", "MAPK_signaling_ENSEMBL.txt", "mTOR_signaling_ENSEMBL.txt", "NOTCH_signaling_ENSEMBL.txt", "P53_signaling_ENSEMBL.txt", "Pathway_in_Cancer_ENSEMBL.txt", "TGF_signaling_ENSEMBL.txt", "Transcriptional_Misregulation_in_Cancer_ENSEMBL.txt", "WNT_signaling_ENSEMBL.txt"};
		
			String outputFile = "C:\\School Notes\\RNAseqCancer\\Human RNAseq Final Report\\Protein Logfold Changes in KEGG Pathway\\NoIsoform_Cufflinke_DEseq_HeatMap_LogFold.output";
			FileWriter fwriter = new FileWriter(outputFile);
			BufferedWriter out = new BufferedWriter(fwriter);
			
			HashMap cufflink17 = new HashMap();
			
			HashMap total = new HashMap();
			for (int i = 0; i < title.length; i++) {
				String inputFile = "C:\\School Notes\\RNAseqCancer\\Human RNAseq Final Report\\Protein Logfold Changes in KEGG Pathway\\NoIsoform_CufflinkResult\\Cufflink_Logfold_17_" + title[i] + ".output";
			    FileInputStream fstream = new FileInputStream(inputFile);
				DataInputStream din = new DataInputStream(fstream); 
				BufferedReader in = new BufferedReader(new InputStreamReader(din));
				while (in.ready()) {
					String str = in.readLine();
					String[] split = str.split("\t");
					cufflink17.put(split[0], split[2]);
					total.put(split[0], split[0]);
				} 
				in.close();
			}
			
			HashMap cufflink31 = new HashMap();
			for (int i = 0; i < title.length; i++) {
				String inputFile = "C:\\School Notes\\RNAseqCancer\\Human RNAseq Final Report\\Protein Logfold Changes in KEGG Pathway\\NoIsoform_CufflinkResult\\Cufflink_Logfold_31_" + title[i]+ ".output";
			    FileInputStream fstream = new FileInputStream(inputFile);
				DataInputStream din = new DataInputStream(fstream); 
				BufferedReader in = new BufferedReader(new InputStreamReader(din));
				while (in.ready()) {
					String str = in.readLine();
					String[] split = str.split("\t");
					cufflink31.put(split[0], split[2]);
					total.put(split[0], split[0]);
				} 
				in.close();
			}
			

			HashMap DEseq17 = new HashMap();
			for (int i = 0; i < title.length; i++) {
				String inputFile = "C:\\School Notes\\RNAseqCancer\\Human RNAseq Final Report\\Protein Logfold Changes in KEGG Pathway\\DEseqResult\\DEseq_Logfold_17_" + title[i] + ".output";
			    FileInputStream fstream = new FileInputStream(inputFile);
				DataInputStream din = new DataInputStream(fstream); 
				BufferedReader in = new BufferedReader(new InputStreamReader(din));
				while (in.ready()) {
					String str = in.readLine();
					String[] split = str.split("\t");
					DEseq17.put(split[0], split[2]);
					total.put(split[0], split[0]);
				} 
				in.close();
			}
			
			HashMap DEseq31 = new HashMap();
			for (int i = 0; i < title.length; i++) {
				String inputFile = "C:\\School Notes\\RNAseqCancer\\Human RNAseq Final Report\\Protein Logfold Changes in KEGG Pathway\\DEseqResult\\DEseq_Logfold_31_" + title[i] + ".output";
			    FileInputStream fstream = new FileInputStream(inputFile);
				DataInputStream din = new DataInputStream(fstream); 
				BufferedReader in = new BufferedReader(new InputStreamReader(din));
				while (in.ready()) {
					String str = in.readLine();
					String[] split = str.split("\t");
					DEseq31.put(split[0], split[2]);
					total.put(split[0], split[0]);
				} 
				in.close();
			}
			
			System.out.println(DEseq31.size());
			System.out.println(DEseq17.size());
			System.out.println(cufflink31.size());
			System.out.println(cufflink17.size());
			System.out.println(total.size());
			
			out.write("GeneID" + "\t" + "DEseq17" + "\t" + "DEseq31" + "\t" + "Cufflink17" + "\t" + "Cufflink31\n");
			Iterator itr = total.keySet().iterator();
			while (itr.hasNext()) {
				String key = (String)itr.next();
				out.write(key + "\t" + DEseq17.get(key) + "\t" + DEseq31.get(key) + "\t" + cufflink17.get(key) + "\t" + cufflink31.get(key) + "\n");
			}
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

