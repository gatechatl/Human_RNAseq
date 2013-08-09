package CuffDiff;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;

public class ExtractCuffDiffGeneExpression {

	public static void main(String[] args) {
		
		try {
			String outputFile = args[1];
			HashMap geneDiff = new HashMap();
									
			String inputFile = args[0]; /*file + "/gene_exp.diff";*/
		    FileInputStream fstream = new FileInputStream(inputFile);
			DataInputStream din = new DataInputStream(fstream); 
			BufferedReader in = new BufferedReader(new InputStreamReader(din));
			while (in.ready()) {
				String str = in.readLine();
				String[] split = str.split("\t");
				if (split[13].equals("yes")) {
					String key = split[1] + "\t" + split[2] + "\t" + split[3];
					if (geneDiff.containsKey(key)) {
						String list = (String)geneDiff.get(key);
						System.out.println(str);
						/*geneDiff.put(key, str);*/
					} else {
						geneDiff.put(key, str);
					}
				}
			}
			in.close();
		
			FileWriter fwriter = new FileWriter(outputFile);
			BufferedWriter out = new BufferedWriter(fwriter);
			Iterator itr = geneDiff.keySet().iterator();
			while (itr.hasNext()) {
				String key = (String)itr.next();

				out.write(geneDiff.get(key) + "\n");
			}
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
