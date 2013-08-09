package VennDiagram;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;

public class CreateVennDiagram {

	public static void main(String[] args) {
		
		
		try {
			HashMap protein = new HashMap();
			String fileName = "C:\\School Notes\\RNAseqCancer\\Analysis Result Report\\General_Information\\Human_Gene_Info\\Human_ncRNA.txt";
			FileInputStream fstream = new FileInputStream(fileName);
			DataInputStream din = new DataInputStream(fstream);
			BufferedReader in = new BufferedReader(new InputStreamReader(din));
			while (in.ready()) {
				String str = in.readLine();
				protein.put(str, str);
			}
			in.close();
			
			HashMap cuffdiff = new HashMap();
			fileName = "C:\\School Notes\\RNAseqCancer\\Analysis Result Report\\Human_Glioma\\Differential Expression Evaluation\\VennDiagram\\Differential Expressed Reads\\Cuffdiff_gene.diff.diff";
			fstream = new FileInputStream(fileName);
			din = new DataInputStream(fstream);
			in = new BufferedReader(new InputStreamReader(din));
			in.readLine();
			while (in.ready()) {
				String str = in.readLine();
				String[] split = str.split("\t");
				if (split[split.length - 1].equals("yes")) {
					if (protein.containsKey(split[0])) {
						cuffdiff.put(split[0], split[0]);
					}
				}
			}
			in.close();
			
			
			HashMap bayseq = new HashMap();
			fileName = "C:\\School Notes\\RNAseqCancer\\Analysis Result Report\\Human_Glioma\\Differential Expression Evaluation\\VennDiagram\\Differential Expressed Reads\\ControlGroupvsGliomaGroup_SigDE.txt";
			fstream = new FileInputStream(fileName);
			din = new DataInputStream(fstream);
			in = new BufferedReader(new InputStreamReader(din));
			in.readLine();
			while (in.ready()) {
				String str = in.readLine();
				String[] split = str.split("\t");
				split[1] = split[1].replaceAll("\"","");
				if (protein.containsKey(split[1])) {
					bayseq.put(split[1], split[1]);
				}
			}
			in.close();					
			
			HashMap deseq = new HashMap();
			fileName = "C:\\School Notes\\RNAseqCancer\\Analysis Result Report\\Human_Glioma\\Differential Expression Evaluation\\VennDiagram\\Differential Expressed Reads\\DESeqOutputControl GroupvsTest Groupsig.txt";
			fstream = new FileInputStream(fileName);
			din = new DataInputStream(fstream);
			in = new BufferedReader(new InputStreamReader(din));
			in.readLine();
			while (in.ready()) {
				String str = in.readLine();
				String[] split = str.split("\t");
				split[0] = split[0].replaceAll("\"","");
				split[7] = split[7].replaceAll("\"", "");
				if (!split[7].equals("NA")) {
					double pdj = new Double(split[7]);
					if (pdj < 0.05) {
						if (protein.containsKey(split[0])) {
							deseq.put(split[0], split[0]);
						}
					}
				}								
			}
			in.close();
			
			
			HashMap edgeR = new HashMap();
			fileName = "C:\\School Notes\\RNAseqCancer\\Analysis Result Report\\Human_Glioma\\Differential Expression Evaluation\\VennDiagram\\Differential Expressed Reads\\edgeR_Control_vs_Test.txt";
			fstream = new FileInputStream(fileName);
			din = new DataInputStream(fstream);
			in = new BufferedReader(new InputStreamReader(din));
			in.readLine();
			while (in.ready()) {
				String str = in.readLine();
				String[] split = str.split(" ");
				split[0] = split[0].replaceAll("\"","");
				split[4] = split[4].replaceAll("\"", "");
				if (!split[4].equals("")) {
					double pdj = new Double(split[4]);
					if (pdj < 0.05) {
						if (protein.containsKey(split[0])) {
							edgeR.put(split[0], split[0]);
						}
					}
				}								
			}
			in.close();
			
			int n1 = cuffdiff.size();
			int n2 = bayseq.size();
			int n3 = deseq.size();
			int n4 = edgeR.size();
			int n12 = intersect(cuffdiff, bayseq).size();
			int n13 = intersect(cuffdiff, deseq).size();
			int n14 = intersect(cuffdiff, edgeR).size();			
			int n23 = intersect(bayseq, deseq).size();
			int n24 = intersect(bayseq, edgeR).size();
			int n34 = intersect(deseq, edgeR).size();
			int n123 = intersect(cuffdiff, bayseq, deseq).size();
			int n124 = intersect(cuffdiff, bayseq, edgeR).size();
			int n134 = intersect(cuffdiff, deseq, edgeR).size();
			int n234 = intersect(bayseq, deseq, edgeR).size();
			int n1234 = intersect(cuffdiff, bayseq, deseq, edgeR).size();
			
			
			
			System.out.println();
			System.out.println(n1 + "\t" + n2 + "\t" + n3 + "\t" + n4);
			System.out.println("venn.plot <- draw.quad.venn(area1 = " + n1 + ",area2 = " + n2 + ",area3 = " + n3 + ",area4 = " + n4 + ",n12 = " + n12 + ",n13 = " + n13 + ",n14 = " + n14 + ",n23 = " + n23 + ",n24 = " + n24 + ",n34 = " + n34 + ",n123 = " + n123 + ",n124 = " + n124 + ",n134 = " + n134 + ",n234 = " + n234 + ",n1234 = " + n1234 + ",category = c('Cuffdiff', 'bayseq', 'DESeq', 'edgeR'),fill = c('orange', 'red', 'green', 'blue'),lty = 'dashed',cex = 2,cat.cex = 2,cat.col = c('orange', 'red', 'green', 'blue'));");
			
			
						
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public static HashMap intersect(HashMap map1, HashMap map2, HashMap map3, HashMap map4) {		
		HashMap map12 = intersect(map1, map2);
		HashMap map123 = intersect(map12, map3);
		return intersect(map123, map4);
	}
	public static HashMap intersect(HashMap map1, HashMap map2, HashMap map3) {		
		HashMap map12 = intersect(map1, map2);
		return intersect(map12, map3);
	}
	public static HashMap intersect(HashMap map1, HashMap map2) {
		HashMap result = new HashMap();
		Iterator itr = map1.keySet().iterator();
		while (itr.hasNext()) {
			String key = (String)itr.next();
			if (map2.containsKey(key)) {
				result.put(key,  key);			
			}
		}
		return result;
	}
	
}
