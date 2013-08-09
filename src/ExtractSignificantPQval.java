import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;


public class ExtractSignificantPQval {

	public static void main(String[] args) {
		
		try {
			String outputFile = "C:\\School Notes\\RNAseqCancer\\DEseq Human Data\\DEseq Result\\sample31_noncoding_bioreps_0.05.txt";
			
			FileWriter fwriter = new FileWriter(outputFile);
			BufferedWriter out = new BufferedWriter(fwriter);
			
			
			String fileName = "C:\\School Notes\\RNAseqCancer\\DEseq Human Data\\DEseq Result\\sample31_noncoding_bioreps.txt";
			FileInputStream fstream = new FileInputStream(fileName);
			DataInputStream din = new DataInputStream(fstream);
			BufferedReader in = new BufferedReader(new InputStreamReader(din));
			in.readLine();
			while (in.ready()) {
				
				String str = in.readLine();
				
				String[] split = str.split("\t");
				if (!split[6].equals("NA")) {
					double pval = new Double(split[6]);
					double qval = new Double(split[7]);
					if (pval < 0.05 && qval < 0.05) {
						out.write(str.replaceAll("\"", "") + "\n");
					}
				}
			}
			in.close();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
