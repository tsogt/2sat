package algo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TwoSAT {
	BufferedReader br;
	FileReader fr;
	int n;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TwoSAT obj=new TwoSAT();
		SCC scc=new SCC();				
		ArrayList<String> strList=obj.readFile("2sat1");
		scc.maxVal=obj.n;
		for(int i=0;i<10;i++) {
			System.out.println(strList.get(i));
		}
/*		for(int i=1;i<=obj.n;i++) {
			scc.explored.put(i,false);
		}
		ArrayList<Integer>[] graphOrder = new ArrayList[obj.n+1];
		for(int i=0;i<=obj.n;i++) {
			graphOrder[i] = new ArrayList<Integer>();
		}

		ArrayList<Integer>[] graphOrig = scc.adjacency(strList);
		System.out.println("adj");
		ArrayList<Integer>[] graphRev = scc.adjacencyRev(strList);
		System.out.println("adjRev");
		scc.DFSLoop(graphRev);
		System.out.println("DFS Loop on Rev");
		for(int j=1;j<=obj.n;j++) {
//			graphOrder[finishTime.get(j)] = graphOrig[j];
			for(int k:graphOrig[j]) {
				graphOrder[scc.finishTime.get(j)].add(scc.finishTime.get(k));
			}			
			
			
		}
		
		
		for(int i=1;i<=obj.n;i++) {
			scc.explored.put(i,false);
		}		
		scc.DFSLoop(graphOrder);*/
		System.out.println("DFS Loop on Ordered");
		
		
		
		
	}
	public ArrayList<String> readFile(String filename) {
		try {
			fr = new FileReader("/home/stark/Documents/Workspace/java/" + filename + ".txt");
			br = new BufferedReader(fr);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {		
			String i;
			
			ArrayList<String> fileList = new ArrayList<String>();
			
			while(true) {				
				i = br.readLine();
				if (i == null)
					break;
			
				fileList.add(i);
				

			}
			br.close();
			fr.close();
						
			n=Integer.parseInt(fileList.get(0));
//			System.out.println(n);
			ArrayList<String> strList=new ArrayList<String>();
			String a,b,nega, negb;
			for(int j=0;j<n;j++) {
				a=fileList.get(j+1).split(" ")[0];
				b=fileList.get(j+1).split(" ")[1];
				if(a.charAt(0)=='-') {
					nega=a.substring(1);
				}
				else {
					nega="-"+a;
				}
				
				if(b.charAt(0)=='-') {
					negb=b.substring(1);
				}
				else {
					negb="-"+b;
				}
				strList.add(nega+" "+b);
				strList.add(negb+" "+a);
					
			}
			
			
					
			return strList;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}

}
