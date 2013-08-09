import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;

public class GliomaExtractReadsAndFPKM {

	public static void main(String[] args) {
		
		try {
			HashMap codingSet = getDifferentialExpressedGene("C:\\School Notes\\Glioma\\Glioma_DE_Output_Files\\Human_Coding.txt");
			HashMap noncodingSet = getDifferentialExpressedGene("C:\\School Notes\\Glioma\\Glioma_DE_Output_Files\\Human_ncRNA.txt");
		
			HashMap[] cufflinkDiff = getCufflinkDiff();
			HashMap[] DEseqDiff = getDEseqDiff();
			HashMap[] EdgeDiff = getEdgeRDiff();
			HashMap[] BayseqDiff = getBaySeqDiff();

			System.out.println(codingSet.size());
			System.out.println(noncodingSet.size());
			System.out.println(cufflinkDiff.length);
			System.out.println(DEseqDiff.length);
			System.out.println(EdgeDiff.length);
			System.out.println(BayseqDiff.length);
			
			String outputNonCoding = "C:\\School Notes\\Glioma\\Glioma_DE_Output_Files\\noncoding_differential_expression.txt";
			String outputCoding = "C:\\School Notes\\Glioma\\Glioma_DE_Output_Files\\coding_differential_expression.txt";

			String outputNonCodingComprehensiveFoldChange = "C:\\School Notes\\Glioma\\Glioma_DE_Output_Files\\noncoding_differential_expression_comprehensive_foldchange.txt";
			String outputCodingComprehensiveFoldChange = "C:\\School Notes\\Glioma\\Glioma_DE_Output_Files\\coding_differential_expression_comprehensive_foldchange.txt";

			String outputNonCodingQValue = "C:\\School Notes\\Glioma\\Glioma_DE_Output_Files\\noncoding_differential_expression_qvalue.txt";
			String outputCodingQValue = "C:\\School Notes\\Glioma\\Glioma_DE_Output_Files\\coding_differential_expression_qvalue.txt";

			String outputNonCodingFoldChange = "C:\\School Notes\\Glioma\\Glioma_DE_Output_Files\\noncoding_differential_expression_foldchange.txt";
			String outputCodingFoldChange = "C:\\School Notes\\Glioma\\Glioma_DE_Output_Files\\coding_differential_expression_foldchange.txt";

			
			HashMap[] cufflinkOutput = getCufflinkOutput();
			HashMap[] DESeqOutput = getDEseqOutput();
			HashMap[] DESeqQValue = getDEseqData();
			HashMap[] EdgeRQValue = getEdgeRData();
			HashMap[] bayseqQValue = getBaySeqData();			
			
			writeTrueFalse(outputNonCoding, noncodingSet, cufflinkDiff, DEseqDiff, EdgeDiff, BayseqDiff);
			writeTrueFalse(outputCoding, codingSet, cufflinkDiff, DEseqDiff, EdgeDiff, BayseqDiff);
			
			writeQValues(outputNonCodingQValue, noncodingSet, cufflinkOutput, DESeqQValue, EdgeRQValue, bayseqQValue);
			writeQValues(outputCodingQValue, codingSet, cufflinkOutput, DESeqQValue, EdgeRQValue, bayseqQValue);
			
			writeFoldChange(outputNonCodingFoldChange, noncodingSet, cufflinkOutput, DESeqOutput);
			writeFoldChange(outputCodingFoldChange, codingSet, cufflinkOutput, DESeqOutput);
						
			writeTrueFalseComprehensive(outputCodingComprehensiveFoldChange, codingSet, cufflinkDiff, DEseqDiff, EdgeDiff, BayseqDiff, cufflinkOutput);
			writeTrueFalseComprehensive(outputNonCodingComprehensiveFoldChange, noncodingSet, cufflinkDiff, DEseqDiff, EdgeDiff, BayseqDiff, cufflinkOutput);

			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void writeTrueFalseComprehensive(String outputNonCoding, HashMap noncodingSet, HashMap[] cufflinkDiff, HashMap[] DEseqDiff, HashMap[] EdgeDiff, HashMap[] BayseqDiff, HashMap[] values) {
		
		try {
			
		    FileWriter fwriter_ncRNA = new FileWriter(outputNonCoding);
		    BufferedWriter out_ncRNA  = new BufferedWriter(fwriter_ncRNA);
			out_ncRNA.write("GeneID: " + "\t" + "GeneName" + "\t" + "Cuffdiff_Qvalue" + "\t" + "EdgeR_Qvalue" + "\t" + "DESeq_Qvalue" + "\t" + "bayseqQvalue" + "\t" + "Coordinate" + "\t" + "Treatment" + "\t" + "Control" + "\t" + "FoldChange" + "\n");
			
			int trues = 0;
			Iterator itr = noncodingSet.keySet().iterator();
			while (itr.hasNext()) {
				String geneId = (String)itr.next();
				
				trues = 0;
				
				if (cufflinkDiff[cufflinkDiff.length - 1].containsKey(geneId)) {
				
					 trues++;
				} else {
				
				}
											
				if (DEseqDiff[DEseqDiff.length - 1].containsKey(geneId)) {
				
					 trues++;
				} else {
				
				}
										
				if (EdgeDiff[EdgeDiff.length - 1].containsKey(geneId)) {
				
					 trues++;
				} else {
				
				}
											
				if (BayseqDiff[BayseqDiff.length - 1].containsKey(geneId)) {
				
					 trues++;
				} else {
				
				}
				if (trues == 4) {
					
					String stuff = (String)values[values.length - 1].get(geneId);
					String[] split = stuff.split("\t");
					String cufflinkQvalue = (String)cufflinkDiff[cufflinkDiff.length - 1].get(geneId);
					String edgeRQvalue= (String)EdgeDiff[EdgeDiff.length - 1].get(geneId);
					String DESeqQvalue = (String)DEseqDiff[DEseqDiff.length - 1].get(geneId);
					String bayseqQvalue = (String)BayseqDiff[BayseqDiff.length - 1].get(geneId);
					out_ncRNA.write(geneId + "\t" + split[2] + "\t" + cufflinkQvalue + "\t" + edgeRQvalue + "\t" + DESeqQvalue + "\t" + bayseqQvalue + "\t" + split[3] + "\t" + split[7] + "\t" + split[8] + "\t" + -(new Double(split[9])) + "\n");
				}
			}
			out_ncRNA.close();
		} catch (Exception e) {
			
		}
	}

	public static void writeFoldChange(String outputNonCoding, HashMap noncodingSet, HashMap[] cufflinkdata, HashMap[] DEseqdata) {
		
		try {
			
		    FileWriter fwriter_ncRNA = new FileWriter(outputNonCoding);
		    BufferedWriter out_ncRNA  = new BufferedWriter(fwriter_ncRNA);
		    
		    
		    out_ncRNA.write("GeneID: " + "\t" + "GeneName" + "\t" + "Coordinate" + "\t" + "CufflinkTreatment" + "\t" + "CufflinkControl" + "\t" + "CufflinkFoldChange" + "\t" + "DESeqTreatment" + "\t" + "DESeqControl" + "\t" + "DESeqFoldChange" + "\n");
			
		    
			int trues = 0;
			Iterator itr = noncodingSet.keySet().iterator();
			while (itr.hasNext()) {
				String geneId = (String)itr.next();
				
				trues = 0;
				
				if (cufflinkdata[cufflinkdata.length - 1].containsKey(geneId) && DEseqdata[DEseqdata.length - 1].containsKey(geneId)) {
					String stuff = (String)cufflinkdata[cufflinkdata.length - 1].get(geneId);
					String[] cufflinkdata_split = stuff.split("\t");
					
					stuff = (String)DEseqdata[DEseqdata.length - 1].get(geneId);
					String[] deseqdata_split = stuff.split("\t");
					out_ncRNA.write(geneId + "\t" + cufflinkdata_split[2] + "\t" + cufflinkdata_split[3] + "\t" + cufflinkdata_split[7] + "\t" + cufflinkdata_split[8] + "\t" + -(new Double(cufflinkdata_split[9])) + "\t" + deseqdata_split[3] + "\t" + deseqdata_split[2] + "\t" + deseqdata_split[5] + "\n");
					
				} else {
					out_ncRNA.write(geneId + "\t" + "NA" + "\t" + "NA" + "\t" + "NA" + "\t" + "NA" + "\t" + "NA" + "\t" + "NA" + "\t" + "NA" + "\t" + "NA" + "\n");
					
				}
			}
			out_ncRNA.close();
		} catch (Exception e) {
			//e.printStackTrace();
		}
	}
	
	public static void writeQValues(String outputNonCoding, HashMap noncodingSet, HashMap[] cufflinkdata, HashMap[] DEseqdata, HashMap[] Edgedata, HashMap[] Bayseqdata) {
		
		try {
			
		    FileWriter fwriter_ncRNA = new FileWriter(outputNonCoding);
		    BufferedWriter out_ncRNA  = new BufferedWriter(fwriter_ncRNA);
			out_ncRNA.write("GeneID: " + "\t" + "cufflink17_control" + "\t" + "cufflink31_control" + "\t" + "cufflink1731_control" + "\t" + "DEseq17_control" + "\t" + "DEseq31_control" + "\t" + "DEseq1731_control" +
					"\t" + "EdgeR17_control" + "\t" + "EdgeR31_control" + "\t" + "EdgeR1731_control" + "\t" + "Bayseq17_control" + "\t" + "Bayeseq31_control" + "\t" + "Bayseq1731_control" +
					"\n");
			
			int trues = 0;
			Iterator itr = noncodingSet.keySet().iterator();
			while (itr.hasNext()) {
				String geneId = (String)itr.next();
				out_ncRNA.write(geneId);
				trues = 0;
																				
				for (int i = 0; i < cufflinkdata.length; i++) {
					 if (cufflinkdata[i].containsKey(geneId)) {
						 String cufflinkQvalue = (String)cufflinkdata[i].get(geneId);
						 String[] split = cufflinkQvalue.split("\t");
						 
						 out_ncRNA.write("\t" + split[split.length - 2]);
						 
					 } else {
						 out_ncRNA.write("\tNA");
					 }
				}
				
				for (int i = 0; i < DEseqdata.length; i++) {
					 if (DEseqdata[i].containsKey(geneId)) {
						 String edgeRQvalue= (String)DEseqdata[i].get(geneId);
						 out_ncRNA.write("\t" + edgeRQvalue);
						 
					 } else {
						 out_ncRNA.write("\tNA");
					 }
				}
				
				for (int i = 0; i < Edgedata.length; i++) {
					 if (Edgedata[i].containsKey(geneId)) {
						 String DESeqQvalue = (String)Edgedata[i].get(geneId);
						 out_ncRNA.write("\t" + DESeqQvalue);
						 
					 } else {
						 out_ncRNA.write("\tNA");
					 }
				}
				
				for (int i = 0; i < Bayseqdata.length; i++) {
					 if (Bayseqdata[i].containsKey(geneId)) {
						 String bayseqQvalue = (String)Bayseqdata[i].get(geneId);
						 out_ncRNA.write("\t" + bayseqQvalue);
						 
					 } else {
						 out_ncRNA.write("\tNA");
					 }
				}
				out_ncRNA.write("\n");
			}
			out_ncRNA.close();
		} catch (Exception e) {
			
		}
	}
	
	public static void writeTrueFalse(String outputNonCoding, HashMap noncodingSet, HashMap[] cufflinkDiff, HashMap[] DEseqDiff, HashMap[] EdgeDiff, HashMap[] BayseqDiff) {
		
		try {
			
		    FileWriter fwriter_ncRNA = new FileWriter(outputNonCoding);
		    BufferedWriter out_ncRNA  = new BufferedWriter(fwriter_ncRNA);
			out_ncRNA.write("GeneID: " + "\t" + "cufflink17_control" + "\t" + "cufflink31_control" + "\t" + "cufflink1731_control" + "\t" + "DEseq17_control" + "\t" + "DEseq31_control" + "\t" + "DEseq1731_control" +
					"\t" + "EdgeR17_control" + "\t" + "EdgeR31_control" + "\t" + "EdgeR1731_control" + "\t" + "Bayseq17_control" + "\t" + "Bayeseq31_control" + "\t" + "Bayseq1731_control" + "\t"
					+ "Number Of Trues\n");
			
			int trues = 0;
			Iterator itr = noncodingSet.keySet().iterator();
			while (itr.hasNext()) {
				String geneId = (String)itr.next();
				out_ncRNA.write(geneId);
				trues = 0;
				
				
				
				
				
				for (int i = 0; i < cufflinkDiff.length; i++) {
					 if (cufflinkDiff[i].containsKey(geneId)) {
						 String cufflinkQvalue = (String)cufflinkDiff[i].get(geneId);
						 out_ncRNA.write("\ttrue");
						 trues++;
					 } else {
						 out_ncRNA.write("\tfalse");
					 }
				}
				
				for (int i = 0; i < DEseqDiff.length; i++) {
					 if (DEseqDiff[i].containsKey(geneId)) {
						 String edgeRQvalue= (String)EdgeDiff[i].get(geneId);
						 out_ncRNA.write("\ttrue");
						 trues++;
					 } else {
						 out_ncRNA.write("\tfalse");
					 }
				}
				
				for (int i = 0; i < EdgeDiff.length; i++) {
					 if (EdgeDiff[i].containsKey(geneId)) {
						 String DESeqQvalue = (String)DEseqDiff[i].get(geneId);
						 out_ncRNA.write("\ttrue");
						 trues++;
					 } else {
						 out_ncRNA.write("\tfalse");
					 }
				}
				
				for (int i = 0; i < BayseqDiff.length; i++) {
					 if (BayseqDiff[i].containsKey(geneId)) {
						 String bayseqQvalue = (String)BayseqDiff[i].get(geneId);
						 out_ncRNA.write("\ttrue");
						 trues++;
					 } else {
						 out_ncRNA.write("\tfalse");
					 }
				}
				out_ncRNA.write("\t" + trues + "\n");
			}
			out_ncRNA.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static HashMap getDifferentialExpressedGene(String fileName) {
		HashMap map = new HashMap();
		try {
			FileInputStream fstream = new FileInputStream(fileName);
			DataInputStream din = new DataInputStream(fstream); 
			BufferedReader in = new BufferedReader(new InputStreamReader(din));
			while (in.ready()) {
				String str = in.readLine();
				String[] split = str.split("\t");
				map.put(split[0].replaceAll("\"", ""), split[0].replaceAll("\"", ""));
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	public static HashMap[] getCufflinkOutput() {
		HashMap[] map = null;
		try {
			String[] files = {"diff_out_17_1634_humanGTF_NoJunction_gene_exp.diff", "diff_out_31_1634_humanGTF_NoJunction_gene_exp.diff", "diff_out_1731_1634_humanGTF_NoJunction_gene_exp.diff"};
			map = new HashMap[files.length];
			String fileName = "C:\\School Notes\\Glioma\\Glioma_DE_Output_Files\\";
			int i = 0;
			for (String file: files) {
				
				/*if (i == 0) {
					map[i] = new HashMap();
					FileInputStream fstream = new FileInputStream(fileName + file);
					DataInputStream din = new DataInputStream(fstream); 
					BufferedReader in = new BufferedReader(new InputStreamReader(din));
					String stuff = in.readLine();
					int num = stuff.split("\t").length - 1;
					while (in.ready()) {
						String str = in.readLine();
						String[] split = str.split("\t");					
						String geneName = split[0].replaceAll("\"", "");	
						map[i].put(geneName, split[9]);
					}
					in.close();
				}
				map[i + 1] = new HashMap();*/
				map[i] = new HashMap();
								
				FileInputStream fstream = new FileInputStream(fileName + file);
				DataInputStream din = new DataInputStream(fstream); 
				BufferedReader in = new BufferedReader(new InputStreamReader(din));
				String stuff = in.readLine();
				int num = stuff.split("\t").length - 1;
				while (in.ready()) {
					String str = in.readLine();
					String[] split = str.split("\t");					
					String geneName = split[0].replaceAll("\"", "");	
					//map[i].put(geneName, split[split.length - 2]);
					map[i].put(geneName, str);
				}
				in.close();
				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	public static HashMap[] getCufflinkDiff() {
		HashMap[] map = null;
		try {
			String[] files = {"diff_out_17_1634_humanGTF_NoJunction_gene_exp.diff", "diff_out_31_1634_humanGTF_NoJunction_gene_exp.diff", "diff_out_1731_1634_HumanGTF_NoJunction_gene_exp.diff"};
			map = new HashMap[files.length];
			String fileName = "C:\\School Notes\\Glioma\\Glioma_DE_Output_Files\\";
			int i = 0;
			for (String file: files) {
				map[i] = new HashMap();
								
				FileInputStream fstream = new FileInputStream(fileName + file);
				DataInputStream din = new DataInputStream(fstream); 
				BufferedReader in = new BufferedReader(new InputStreamReader(din));
				String stuff = in.readLine();
				int num = stuff.split("\t").length - 1;
				while (in.ready()) {
					String str = in.readLine();
					String[] split = str.split("\t");					
					String geneName = split[0].replaceAll("\"", "");	
					if (split[num].equals("yes")) {
						map[i].put(geneName, split[num - 1]);
					}
				}
				in.close();
				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	public static HashMap[] getEdgeRData() {
		HashMap[] map = new HashMap[3];
		try {
			String[] files = {"EdgeRtag_Control_vs_AYAD17.txt", "EdgeRtag_Control_vs_AYAD31.txt", "EdgeRtag_Control_vs_Glioma.txt"};  
			String fileName = "C:\\School Notes\\Glioma\\Glioma_DE_Output_Files\\";
			int i = 0;
			for (String file: files) {
				map[i] = new HashMap();
								
				FileInputStream fstream = new FileInputStream(fileName + file);
				DataInputStream din = new DataInputStream(fstream); 
				BufferedReader in = new BufferedReader(new InputStreamReader(din));
				String stuff = in.readLine();
				int num = stuff.split(" ").length - 1;
				while (in.ready()) {
					String str = in.readLine();
					String[] split = str.split(" ");
					
					String geneName = split[0].replaceAll("\"", "");								
					map[i].put(geneName, split[split.length - 1]);
					
				}
				in.close();
				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}	
	
	public static HashMap[] getEdgeRDiff() {
		HashMap[] map = new HashMap[3];
		try {
			String[] files = {"EdgeRtag_Control_vs_AYAD17.txt", "EdgeRtag_Control_vs_AYAD31.txt", "EdgeRtag_Control_vs_Glioma.txt"};  
			String fileName = "C:\\School Notes\\Glioma\\Glioma_DE_Output_Files\\";
			int i = 0;
			for (String file: files) {
				map[i] = new HashMap();
								
				FileInputStream fstream = new FileInputStream(fileName + file);
				DataInputStream din = new DataInputStream(fstream); 
				BufferedReader in = new BufferedReader(new InputStreamReader(din));
				String stuff = in.readLine();
				int num = stuff.split(" ").length - 1;
				while (in.ready()) {
					String str = in.readLine();
					String[] split = str.split(" ");					
					String geneName = split[0].replaceAll("\"", "");
					//System.out.println(fileName + file + "\t" + str);
					double fdr = new Double(split[num]);
					if (fdr < 0.05) {										
						map[i].put(geneName, split[1]);
					}
				}
				in.close();
				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	public static HashMap[] getDEseqDiff() {
		HashMap[] map = new HashMap[3];
		try {
			String[] files = {"DESeqOutputControlGroupvsAYAD17.txt", "DESeqOutputControlGroupvsAYAD31.txt", "DESeqOutputControlGroupvsGliomaGroup.txt"};  
			String fileName = "C:\\School Notes\\Glioma\\Glioma_DE_Output_Files\\";
			int i = 0;
			for (String file: files) {
				map[i] = new HashMap();
								
				FileInputStream fstream = new FileInputStream(fileName + file);
				DataInputStream din = new DataInputStream(fstream); 
				BufferedReader in = new BufferedReader(new InputStreamReader(din));
				String stuff = in.readLine();
				int num = stuff.split("\t").length - 1;
				while (in.ready()) {
					String str = in.readLine();
					String[] split = str.split("\t");
					
					String geneName = split[0].replaceAll("\"", "");
					//System.out.println(fileName + file + "\t" + str);
					if (!split[num].equals("NA")) {
					double fdr = new Double(split[num]);
						if (fdr < 0.05) {
											
							map[i].put(geneName, split[num]);
						}
					}
				}
				in.close();
				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	public static HashMap[] getDEseqData() {
		HashMap[] map = new HashMap[3];
		try {
			String[] files = {"DESeqOutputControlGroupvsAYAD17.txt", "DESeqOutputControlGroupvsAYAD31.txt", "DESeqOutputControlGroupvsGliomaGroup.txt"};  
			String fileName = "C:\\School Notes\\Glioma\\Glioma_DE_Output_Files\\";
			int i = 0;
			for (String file: files) {
				map[i] = new HashMap();
								
				FileInputStream fstream = new FileInputStream(fileName + file);
				DataInputStream din = new DataInputStream(fstream); 
				BufferedReader in = new BufferedReader(new InputStreamReader(din));
				String stuff = in.readLine();
				int num = stuff.split("\t").length - 1;
				while (in.ready()) {
					String str = in.readLine();
					String[] split = str.split("\t");					
					String geneName = split[0].replaceAll("\"", "");
					//System.out.println(fileName + file + "\t" + str);
															
					map[i].put(geneName, split[split.length - 1]);						
				
				}
				in.close();
				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	
	public static HashMap[] getDEseqOutput() {
		HashMap[] map = new HashMap[3];
		try {
			String[] files = {"DESeqOutputControlGroupvsAYAD17.txt", "DESeqOutputControlGroupvsAYAD31.txt", "DESeqOutputControlGroupvsGliomaGroup.txt"};  
			String fileName = "C:\\School Notes\\Glioma\\Glioma_DE_Output_Files\\";
			int i = 0;
			for (String file: files) {
				map[i] = new HashMap();
								
				FileInputStream fstream = new FileInputStream(fileName + file);
				DataInputStream din = new DataInputStream(fstream); 
				BufferedReader in = new BufferedReader(new InputStreamReader(din));
				String stuff = in.readLine();
				int num = stuff.split("\t").length - 1;
				while (in.ready()) {
					String str = in.readLine();
					String[] split = str.split("\t");					
					String geneName = split[0].replaceAll("\"", "");
					//System.out.println(fileName + file + "\t" + str);
															
					map[i].put(geneName, str);						
				
				}
				in.close();
				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	public static HashMap[] getBaySeqDiff() {
		HashMap[] map = new HashMap[3];
		
		try {
			String[] files = {"bayseq_ControlGroupvsAYAD17_AllDE.txt", "baySeq_ControlGroupvsAYAD31_AllDE.txt", "baySeq_ControlGroupvsGliomaGroup_AllDE.txt"};  
			String fileName = "C:\\School Notes\\Glioma\\Glioma_DE_Output_Files\\";
			int i = 0;
			for (String file: files) {
				map[i] = new HashMap();
				
				
				FileInputStream fstream = new FileInputStream(fileName + file);
				DataInputStream din = new DataInputStream(fstream); 
				BufferedReader in = new BufferedReader(new InputStreamReader(din));
				String stuff = in.readLine();
				int num = stuff.split("\t").length - 1;
				while (in.ready()) {
					String str = in.readLine();
					String[] split = str.split("\t");
					
					String geneName = split[1].replaceAll("\"", "");
					//System.out.println(fileName + file + "\t" + str);
					double fdr = new Double(split[num]);
					if (fdr < 0.05) {																		
						map[i].put(geneName, split[num]);
					}
				}
				in.close();
				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	public static HashMap[] getBaySeqData() {
		HashMap[] map = new HashMap[3];
		
		try {
			String[] files = {"bayseq_ControlGroupvsAYAD17_AllDE.txt", "baySeq_ControlGroupvsAYAD31_AllDE.txt", "baySeq_ControlGroupvsGliomaGroup_AllDE.txt"};  
			String fileName = "C:\\School Notes\\Glioma\\Glioma_DE_Output_Files\\";
			int i = 0;
			for (String file: files) {
				map[i] = new HashMap();
				
				
				FileInputStream fstream = new FileInputStream(fileName + file);
				DataInputStream din = new DataInputStream(fstream); 
				BufferedReader in = new BufferedReader(new InputStreamReader(din));
				String stuff = in.readLine();
				int num = stuff.split("\t").length - 1;
				while (in.ready()) {
					String str = in.readLine();
					String[] split = str.split("\t");
					
					String geneName = split[1].replaceAll("\"", "");
					//System.out.println(fileName + file + "\t" + str);
					double fdr = new Double(split[num]);
																							
					map[i].put(geneName, split[num]);
					
				}
				in.close();
				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
}
