import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;


public class MergeSignificantReps {

	
	public static void main(String[] args) {
		
		try {
			String outputFile = "C:\\School Notes\\RNAseqCancer\\DEseq Human Data\\DEseq Result\\Noncoding_Overlapping_Changes.txt";
			
			FileWriter fwriter = new FileWriter(outputFile);
			BufferedWriter out = new BufferedWriter(fwriter);
			
			
			HashMap map = new HashMap();
			String fileName = "C:\\School Notes\\RNAseqCancer\\DEseq Human Data\\DEseq Result\\sample31_noncoding_bioreps_0.05.txt";
			FileInputStream fstream = new FileInputStream(fileName);
			DataInputStream din = new DataInputStream(fstream);
			BufferedReader in = new BufferedReader(new InputStreamReader(din));			
			while (in.ready()) {				
				String str = in.readLine();
				String[] split = str.split("\t");
				map.put(split[0], split[0]);
			}
			in.close();
			
			HashMap final_map = new HashMap();
			fileName = "C:\\School Notes\\RNAseqCancer\\DEseq Human Data\\DEseq Result\\sample17_noncoding_bioreps_0.05.txt";
			fstream = new FileInputStream(fileName);
			din = new DataInputStream(fstream);
			in = new BufferedReader(new InputStreamReader(din));			
			while (in.ready()) {				
				String str = in.readLine();
				String[] split = str.split("\t");
				if (map.containsKey(split[0])) {
					final_map.put(split[0], split[0]);
				}
				
			}
			in.close();
			
			Iterator itr = final_map.keySet().iterator();
			while (itr.hasNext()) {
				String key = (String)itr.next();
				out.write(key + "\n");
			}
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
