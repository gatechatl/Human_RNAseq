package DifferentiallyExpressedGenes;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.HashMap;

public class CreateCufflinkncRNAandProteinSigDiff {

	
	public static void main(String[] args) {
		
		try {
			//String outputFile = "C:\\School Notes\\RNAseqCancer\\Human RNAseq Final Report\\Significant Differential Expressed Genes\\ncRNA\\Cufflink\\ncRNA_Cufflink_MergedResult.output";
			String outputFile = "C:\\School Notes\\RNAseqCancer\\Human RNAseq Final Report\\Significant Differential Expressed Genes\\ncRNA\\NoIsoformCufflink\\ncRNA_NoIsoformCufflink_MergedResult.output";
			//String outputFile = "C:\\School Notes\\RNAseqCancer\\Human RNAseq Final Report\\Significant Differential Expressed Genes\\Protein\\NoIsoformCufflink\\Protein_NoIsoformCufflink_MergedResult.output";
			FileWriter fwriter = new FileWriter(outputFile);
			BufferedWriter out = new BufferedWriter(fwriter);
			
			HashMap protein = new HashMap();
			String inputFile = "C:\\School Notes\\RNAseqCancer\\NoIsoform_CufflinkResult\\NoIsoform_CufflinkMergedResult.output";
		    FileInputStream fstream = new FileInputStream(inputFile);
			DataInputStream din = new DataInputStream(fstream); 
			BufferedReader in = new BufferedReader(new InputStreamReader(din));
			while (in.ready()) {
				String str = in.readLine();
				String[] split = str.split("\t");
				if (split[5].equals("true")) {
					out.write(str + "\n");
					
				}
			}
			in.close();									
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
