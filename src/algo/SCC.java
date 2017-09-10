package algo;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SCC {
	FileReader fr;
	BufferedReader br;
	static int t=0;
	static String s;
	int maxVal,minVal;
	Set<String> v=new HashSet<String>();
	Map<String,ArrayList<String>> e=new HashMap<String, ArrayList<String>>();
	int n;
	HashMap<String,Boolean> explored = new HashMap<String, Boolean>();
	HashMap<String,String> leader = new HashMap<String, String>();
	static HashMap<Integer,Integer> leaders = new HashMap<Integer, Integer>();
	static HashMap<String,Integer> finishTime = new HashMap<String, Integer>();
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SCC obj = new SCC();
		MergeSort sort=new MergeSort();
		ArrayList<String> arrList = obj.readFile("scctest");
		
		System.out.println("Read File");
//		String vArr[]=Arrays.copyOf(obj.v.toArray(),obj.v.toArray().length,String[].class); 
		
//		String vArrSorted[]=sort.sort(vArr,vArr.length);
//		vArr=sort.sort(vArr,vArr.length);								
		
		for(int i=obj.minVal;i<obj.maxVal+1;i++) {
			obj.explored.put(Integer.toString(i),false);
		}
		
/*		for(int i=0;i<vArr.length;i++) {
			graphOrder[i] = new ArrayList<Integer>();
		}*/

		Map<String,ArrayList<String>> graphOrig = obj.adjacency(arrList);
		System.out.println("adj");
		Map<String,ArrayList<String>> graphRev = obj.adjacencyRev(arrList);
		System.out.println("adjRev");
		
	/*	obj.DFSLoop(graphRev,vArrSorted);
		
		System.out.println("DFS Loop on Rev");
		
		Map<String,ArrayList<Integer>> graphOrder = new HashMap<String,ArrayList<Integer>>();
		ArrayList<String> tmpList;
		for(int j=0;j<vArr.length;j++) {
//			graphOrder[finishTime.get(j)] = graphOrig[j];
			for(String k:graphOrig.get(vArr[j])) {
//				graphOrder[finishTime.get(j)].add(finishTime.get(k));
				graphOrder.get(finishTime.get(j)).add(finishTime.get(k));
				
			}			
			
			
		}
		
		
		for(int i=1;i<vArr.length;i++) {
			obj.explored.put(vArr[i],false);
		}		
		obj.DFSLoop(graphOrder);
		System.out.println("DFS Loop on Ordered");*/
		

	}
	public void DFSLoop(Map<String,ArrayList<String>> Graph, String vArrSorted[]) {
		
		t=0;
		int n=vArrSorted.length;
		for(int i=n-1;i>=0;i--) {
			if(explored.get(vArrSorted[i])==false) {
				s=vArrSorted[i];
				DFS(Graph, vArrSorted[i]);
			}
		}
	}
	public void DFS(Map<String,ArrayList<String>> Graph, String node) {
		explored.put(node,true);
		leader.put(node, s);
		int sizeG = Graph.get(node).size();
		int ii;
		for(ii=0;ii<sizeG;ii++) {
			if(explored.get(Graph.get(node).get(ii))==false) {
				DFS(Graph,Graph.get(node).get(ii));
			}
		}
		t++;
		finishTime.put(node, t);
		
	}
	public Map<String, ArrayList<String>> adjacencyRev(ArrayList<String> fileList) {
		
		int len = fileList.size();
		int i,j;
		
//		ArrayList[] adjMat= new ArrayList[maxVal+1];
		Map<String, ArrayList<String>> edges=new HashMap<String, ArrayList<String>>();
			
		ArrayList<String> tmpList;
		for(j=0;j<len;j++) {
			
			if(edges.containsKey(fileList.get(j).split(" ")[1])) {
				tmpList=edges.get(fileList.get(j).split(" ")[1]);
				tmpList.add(fileList.get(j).split(" ")[0]);
				edges.put(fileList.get(j).split(" ")[1], tmpList);
			}
			else {
				tmpList=new ArrayList<String>();
				tmpList.add(fileList.get(j).split(" ")[0]);
				edges.put(fileList.get(j).split(" ")[1], tmpList);
			}
				
		}
		return edges;
	}

	public Map<String, ArrayList<String>> adjacency(ArrayList<String> fileList) {
			
		int len = fileList.size();
		
		
		Map<String, ArrayList<String>> edges=new HashMap<String, ArrayList<String>>();
		ArrayList<String> tmpList;					
		for(int j=0;j<len;j++) {
			if(edges.containsKey(fileList.get(j).split(" ")[0])) {
				tmpList=edges.get(fileList.get(j).split(" ")[0]);
				tmpList.add(fileList.get(j).split(" ")[1]);
				edges.put(fileList.get(j).split(" ")[0], tmpList);
			}
			else {
				tmpList=new ArrayList<String>();
				tmpList.add(fileList.get(j).split(" ")[1]);
				edges.put(fileList.get(j).split(" ")[0], tmpList);
			}
		}
		return edges;
	}
	
	public int findMax(int[][] matStr) {
		int len = Array.getLength(matStr);
		int i,j,maxVal = 0;
		for(i=0;i<len;i++) {
			for(j=0;j<2;j++) {
				if(maxVal < matStr[i][j]) {
					maxVal = matStr[i][j];
				}
			}
		}
		return maxVal;
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
			ArrayList<String> strList=new ArrayList<String>();
//			n=Integer.parseInt(fileList.get(0));
			String a,b,nega,negb;
			
/*			int k=0;
			for(int j=1;j<fileList.size();j++) {
				a=fileList.get(j).split(" ")[0];
				b=fileList.get(j).split(" ")[1];
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
				v.add(a);
				v.add(b);
			}*/
						
			maxVal=0;
			minVal=Integer.MAX_VALUE;
			for(int j=0;j<fileList.size();j++) {
				if(maxVal<Integer.parseInt(fileList.get(j).split(" ")[0]))
					maxVal=Integer.parseInt(fileList.get(j).split(" ")[0]);
				if(maxVal<Integer.parseInt(fileList.get(j).split(" ")[1]))
					maxVal=Integer.parseInt(fileList.get(j).split(" ")[1]);
				if(minVal>Integer.parseInt(fileList.get(j).split(" ")[0]))
					minVal=Integer.parseInt(fileList.get(j).split(" ")[0]);
				if(minVal>Integer.parseInt(fileList.get(j).split(" ")[1]))
					minVal=Integer.parseInt(fileList.get(j).split(" ")[1]);														
			}

			return fileList;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}

}
