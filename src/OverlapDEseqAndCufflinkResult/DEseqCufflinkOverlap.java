package OverlapDEseqAndCufflinkResult;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;

public class DEseqCufflinkOverlap {

	public static void main(String[] args) {
		
		
		try {
	
			HashMap map = new HashMap();
			String inputFile = args[0];
		    FileInputStream fstream = new FileInputStream(inputFile);
			DataInputStream din = new DataInputStream(fstream); 
			BufferedReader in = new BufferedReader(new InputStreamReader(din));
			while (in.ready()) {
				String str = in.readLine();
				String[] split = str.split("\t");
				map.put(str, str);
			}
			in.close();
			
			HashMap overlap_map = new HashMap();
			
			inputFile = args[1];
		    fstream = new FileInputStream(inputFile);
			din = new DataInputStream(fstream); 
			in = new BufferedReader(new InputStreamReader(din));
			while (in.ready()) {
				String str = in.readLine();
				String[] split = str.split("\t");
				String ensemblid = split[4];
				if (map.containsKey(ensemblid)) {
					overlap_map.put(ensemblid, ensemblid);
				}
			}
			in.close();
			
			String outputFile = args[2];
			FileWriter fwriter = new FileWriter(outputFile);
			BufferedWriter out = new BufferedWriter(fwriter);
			Iterator itr = overlap_map.keySet().iterator();
			while(itr.hasNext()) {
				String key = (String)itr.next();
				out.write(key + "\n");
				
			}
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
