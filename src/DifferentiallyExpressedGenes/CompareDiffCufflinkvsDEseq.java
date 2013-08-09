package DifferentiallyExpressedGenes;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.HashMap;

public class CompareDiffCufflinkvsDEseq {

	
	public static void main(String[] args) {
		
		try {
			
			HashMap protein = new HashMap();
			//String inputFile = "C:\\School Notes\\RNAseqCancer\\NoIsoform_CufflinkResult\\NoIsoform_CufflinkMergedResult.output";
			String inputFile = "C:\\School Notes\\RNAseqCancer\\Human RNAseq Final Report\\Significant Differential Expressed Genes\\ncRNA\\NoIsoformCufflink\\ncRNA_NoIsoformCufflink_MergedResult.output";
		    FileInputStream fstream = new FileInputStream(inputFile);
			DataInputStream din = new DataInputStream(fstream); 
			BufferedReader in = new BufferedReader(new InputStreamReader(din));
			while (in.ready()) {
				String str = in.readLine();
				String[] split = str.split("\t");
				// true for protein false for ncRNA
				if (split[5].equals("false")) {
					String key = split[4] + "\t" + split[3] + "\t" + split[2];
					protein.put(split[4], key);
				}
			}
			in.close();
			System.out.println(protein.size());
			String outputFile = "C:\\School Notes\\RNAseqCancer\\Human RNAseq Final Report\\Significant Differential Expressed Genes\\ncRNA\\NoIsoformCufflinkDEseqOverlap\\NoIsoformCufflink_DEseq_Overlap_SigDiff.output";
			FileWriter fwriter = new FileWriter(outputFile);
			BufferedWriter out = new BufferedWriter(fwriter);
			
			
			//inputFile = "C:\\School Notes\\RNAseqCancer\\DEseq Human Data\\DEseq Result\\DEseq_Overlapping_Changes.txt";
			inputFile = "C:\\School Notes\\RNAseqCancer\\DEseq Human Data\\DEseq Result\\Noncoding_Overlapping_Changes.txt";
			fstream = new FileInputStream(inputFile);
			din = new DataInputStream(fstream); 
			in = new BufferedReader(new InputStreamReader(din));
			while (in.ready()) {
				String str = in.readLine();			
				if (protein.containsKey(str)) {
					out.write(protein.get(str) + "\n");
				}
			}
			in.close();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
