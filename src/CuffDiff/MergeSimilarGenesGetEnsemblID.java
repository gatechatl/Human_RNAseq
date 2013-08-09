package CuffDiff;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;

public class MergeSimilarGenesGetEnsemblID {

	public static void main(String[] args) {
		try {
	
			HashMap ensembl = new HashMap();
			String ensembl_file = args[2];
		    FileInputStream fstream = new FileInputStream(ensembl_file);
			DataInputStream din = new DataInputStream(fstream); 
			BufferedReader in = new BufferedReader(new InputStreamReader(din));
			while (in.ready()) {
				String str = in.readLine();
				String[] split = str.split("\t");
				String meta = split[8];
				String geneName = grabMeta(meta, "gene_name");
				String ensemblid = grabMeta(meta, "gene_id");
				boolean isprotein = false;
				if (split[1].equals("protein_coding")) {
					isprotein = true;
				}
				if (ensembl.containsKey(geneName)) {
					String stuff = (String)ensembl.get(geneName);
					String[] split2 = stuff.split("\t");
					if (split2[1].equals("true")) {
						isprotein = true;
						ensembl.put(geneName,  ensemblid + "\t" + isprotein);
					} else {
						ensembl.put(geneName,  ensemblid + "\t" + isprotein);
					}
				} else {
					ensembl.put(geneName,  ensemblid + "\t" + isprotein);
				}
			}
			in.close();
			HashMap genes = new HashMap();
									
			String inputFile = args[0]; /*file + "/gene_exp.diff";*/
		    fstream = new FileInputStream(inputFile);
			din = new DataInputStream(fstream); 
			in = new BufferedReader(new InputStreamReader(din));
			while (in.ready()) {
				String str = in.readLine();
				String[] split = str.split("\t");
				String key = split[1] + "\t" + split[2] + "\t" + split[3];
				genes.put(key, key);
			}
			in.close();

			HashMap overlap_genes = new HashMap();
			inputFile = args[1];
			fstream = new FileInputStream(inputFile);
			din = new DataInputStream(fstream); 
			in = new BufferedReader(new InputStreamReader(din));
			while (in.ready()) {
				String str = in.readLine();
				String[] split = str.split("\t");
				String key = split[1] + "\t" + split[2] + "\t" + split[3];
				if (genes.containsKey(key)) {
					overlap_genes.put(key, key);
				}
				
			}
			in.close();
			String outputFile = args[3];
			FileWriter fwriter = new FileWriter(outputFile);
			BufferedWriter out = new BufferedWriter(fwriter);
			Iterator itr = overlap_genes.keySet().iterator();
			while (itr.hasNext()) {
				String key = (String)itr.next();
				out.write(key + "\t" + ensembl.get(key) + "\n");
			}
			out.close();
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
