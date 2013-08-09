package DEseq;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.HashMap;

public class ExtractProteinNonCodingDEseq {
    public static void main(String[] args) {
        try {

                HashMap ensembl = new HashMap();
                String protein = args[2];
                String ensembl_file = args[0];
                FileInputStream fstream = new FileInputStream(ensembl_file);
                DataInputStream din = new DataInputStream(fstream);
                BufferedReader in = new BufferedReader(new InputStreamReader(din));
                int count = 0;
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
                                        ensembl.put(ensemblid,  geneName + "\t" + isprotein);
                                } else {
                                        ensembl.put(ensemblid,  geneName + "\t" + isprotein);
                                }
                        } else {
                            ensembl.put(ensemblid,  geneName + "\t" + isprotein);
                        }
                        count++;
                        if (count % 1000000 == 0) {
                             System.out.println(count);
                        }
                }
                in.close();
                
                
				String outputFile = args[3];
				FileWriter fwriter = new FileWriter(outputFile);
				BufferedWriter out = new BufferedWriter(fwriter);
				
                String inputFIle = args[1];
                fstream = new FileInputStream(inputFIle);
                din = new DataInputStream(fstream);
                in = new BufferedReader(new InputStreamReader(din));
                while (in.ready()) {
                	String str = in.readLine();
                	String[] split = str.split("\t");
                	String e = split[0].replaceAll("\"", "");
                	String stuff = (String)ensembl.get(e);
                	String[] split2 = stuff.split("\t");
                	if (split2.equals(protein)) {
                		out.write(str + "\n");
                	}
                }
                in.close();
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
