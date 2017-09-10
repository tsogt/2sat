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

public class SCCtest2 {
	FileReader fr;
	BufferedReader br;
	static int t=0;
	static String s=null;
	Map<String,String> vMapFinishedTime=new HashMap<String,String>();

	Set<String> v=new HashSet<String>();
	static int maxVal, minVal;
	HashMap<String,Boolean> explored = new HashMap<String, Boolean>();
	HashMap<String,String> leader = new HashMap<String, String>();
	HashMap<String,ArrayList<String>> leaderA = new HashMap<String, ArrayList<String>>();
	static HashMap<Integer,Integer> leaders = new HashMap<Integer, Integer>();
	static HashMap<String,String> finishTime = new HashMap<String, String>();
	public static void main(String[] args) {
		SCCtest2 obj = new SCCtest2();
//		obj.run("input_beaunus_9_20");
//		obj.run("2sat1");
//		obj.run("2sat2");
//		obj.run("2sat3");
		obj.run("2sat4");
		obj.run("2sat5");
		obj.run("2sat6");
	}
	public void run(String filename) {
		// TODO Auto-generated method stub
		SCCtest2 obj = new SCCtest2();
		MergeSort sort=new MergeSort();
		
		ArrayList<String> arr = obj.readFile2(filename);
		
/*		for(String e:arr) {
			System.out.println(e);
		}*/
		
//		System.out.println("Read File");
//		System.out.println(minVal+":"+maxVal);
		

		Map<String, ArrayList<String>> graphOrig = obj.adjacency(arr);
//		System.out.println("adj:"+graphOrig.size());
		Map<String, ArrayList<String>> graphRev = obj.adjacencyRev(arr);
//		System.out.println("adjRev:"+graphRev.size());
		
		String vArr[]=Arrays.copyOf(obj.v.toArray(),obj.v.toArray().length,String[].class);
//		System.out.println("varr:"+vArr.length);
		
		String vArrSorted[]=sort.sort(vArr,vArr.length);
////		vArr=sort.sort(vArr,vArr.length);								
//		int len=vArrSorted.length;
//		System.out.println("vArrSprted:"+len);
		for(Map.Entry<String, ArrayList<String>> e:graphRev.entrySet()) {
			obj.explored.put(e.getKey(),false);
			for(String e1:e.getValue()) {
				obj.explored.put(e1,false);	
			}
			
		}
//		System.out.println("explored:"+obj.explored.size());
		
		obj.DFSLoop(graphRev,vArrSorted);
//		System.out.println("start:"+obj.finishTime.size());
		
		for(Map.Entry<String, String> e:obj.finishTime.entrySet()) {
			obj.vMapFinishedTime.put(e.getValue(),e.getKey());
//			System.out.println(e.getValue()+":"+e.getKey());
		}
//		System.out.println("end");

		Map<String, ArrayList<String>> graphOrder = new HashMap<String, ArrayList<String>>();
/*		for(int i=0;i<=maxVal;i++) {
			graphOrder[i] = new ArrayList<Integer>();
		}*/
		
//		System.out.println("DFS Loop on Rev");
		
/*		for(Map.Entry<String, String> e:obj.finishTime.entrySet()) {
			System.out.println(e.getKey()+":"+e.getValue());
		}*/
		
//		System.out.println(obj.finishTime.get("8"));
		
		for(Map.Entry<String, ArrayList<String>> e:graphOrig.entrySet()) {

			for(String k:e.getValue()) {				
//				if(e.getKey().equals(obj.finishTime.get(e.getKey()))) {
				if(graphOrder.containsKey(obj.finishTime.get(e.getKey()))) {
					ArrayList<String> tmpList=graphOrder.get(obj.finishTime.get(e.getKey()));
//					System.out.println(k+":"+obj.finishTime.get(k));
					tmpList.add(obj.finishTime.get(k));
					
					graphOrder.put(obj.finishTime.get(e.getKey()), tmpList);	
				}
				else {
					ArrayList<String> tmpList=new ArrayList<String>();
					tmpList.add(obj.finishTime.get(k));
					graphOrder.put(obj.finishTime.get(e.getKey()), tmpList);	
				}
				
			}			
				
		}
//		Insertint remainings
//		System.out.println("graphOrder:"+graphOrder.size());
		for(Map.Entry<String, ArrayList<String>> e:graphOrig.entrySet()) {
			if(!graphOrder.containsKey(obj.finishTime.get(e.getKey()))) {
				ArrayList<String> tmpList=new ArrayList<String>();
				graphOrder.put(obj.finishTime.get(e.getKey()), tmpList);	
				
			}
			
			
		}
		
		String[] vTimeSorted=new String[obj.finishTime.size()];
		int cntTime=0;
		for(Map.Entry<String, String> e:obj.finishTime.entrySet()) {
			vTimeSorted[cntTime++]=e.getValue();
		}
		vTimeSorted=sort.sort(vTimeSorted, vTimeSorted.length);
//		System.out.println(vTimeSorted[1]);
//		System.out.println("finishTime:"+obj.finishTime.size());
//		System.out.println("graphOrder:"+graphOrder.size());
		obj.explored.clear();
		for(Map.Entry<String, ArrayList<String>> e:graphOrder.entrySet()) {
			obj.explored.put(e.getKey(),false);
			for(String e1:e.getValue()) {
				obj.explored.put(e1,false);	
			}
			
		}

//		System.out.println("explored:"+obj.explored.size());
		obj.leader.clear();
		obj.DFSLoop(graphOrder,vTimeSorted);
//		System.out.println("DFS Loop on Ordered");




//		Print SCCs
		
		
		for(Map.Entry<String, String> e:obj.leader.entrySet()) {
			if(obj.leaderA.containsKey(e.getValue())) {
				obj.leaderA.get(e.getValue()).add(e.getKey());
			}
			else {
				ArrayList<String> tmpList=new ArrayList<String>();
				tmpList.add(e.getKey());
				obj.leaderA.put(e.getValue(), tmpList);
			}
				
		}

		
/*		int	maxSCC = 0;
		String maxKey = null;
		for(int k=0;k<10;k++) {
			maxSCC = 0;
			for(Map.Entry<String, ArrayList<String>> e:obj.leaderA.entrySet()) {
				if(maxSCC<e.getValue().size()) {
					maxKey=e.getKey();
					maxSCC=e.getValue().size();							
				}
			}
			System.out.println(maxKey + ": " + maxSCC);			
			obj.leaderA.remove(maxKey);
			
		}		*/

		int satisfy=1;
		for(Map.Entry<String, ArrayList<String>> e:obj.leaderA.entrySet()) {
			if(e.getValue().size()>1) {
//				System.out.println("hi");
				for(String en1:e.getValue()) {
					for(String en2:e.getValue()) {
//						System.out.println("start");
//						System.out.println(en1+":"+obj.vMapFinishedTime.get(en1));
//						System.out.println(en2+":"+obj.vMapFinishedTime.get(en2));

						if(!en1.equals(en2)) {
//							System.out.println("start");
//							System.out.println(en1+":"+obj.vMapFinishedTime.get(en1));
//							System.out.println(en2+":"+obj.vMapFinishedTime.get(en2));
							if(obj.vMapFinishedTime.get(en1).charAt(0)=='-') {							
								if(obj.vMapFinishedTime.get(en1).substring(1).equals(obj.vMapFinishedTime.get(en2))) {
									satisfy=0;								
									break;
								}
							}
//							System.out.println("start");
//							System.out.println(en1+":"+obj.vMap.get(en1));
//							System.out.println(en2+":"+obj.vMap.get(en2));
							
						}

								
					}	
					if(satisfy==0)
						break;
				}
				
			}
			if(satisfy==0)
				break;			
		}
		
		System.out.print(satisfy);

		

	}
	public void DFSLoop(Map<String, ArrayList<String>> Graph,String[] vArrSorted) {
		
		t=0;
		
		for(int i=0;i<vArrSorted.length;i++) {
//			System.out.println("first");
			if(explored.get(vArrSorted[i])==false) {
				s=vArrSorted[i];
				DFS(Graph,vArrSorted[i]);
			}
		}
	}
	public void DFS(Map<String, ArrayList<String>> Graph, String node) {
		explored.put(node,true);
		leader.put(node, s);
		
//		int sizeG = Graph.get(node).size();
		
		for(String e:Graph.get(node)) {
			if(explored.get(e)==false) {
				DFS(Graph,e);
			}
		}
		t++;
		finishTime.put(node, Integer.toString(t));
			
		
//		vMapFinishedTime.put(t, node);
		
	}
	public Map<String, ArrayList<String>> adjacencyRev(ArrayList<String> fileList) {
		
		int len = fileList.size();		

		
		Map<String, ArrayList<String>> edges=new HashMap<String, ArrayList<String>>();
			
		
		for(int j=0;j<len;j++) {
			ArrayList<String> tmpList;	
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
		for(int j=0;j<len;j++) {
			ArrayList<String> tmpList;
			if(!edges.containsKey(fileList.get(j).split(" ")[0])) {
				tmpList=new ArrayList<String>();				
				edges.put(fileList.get(j).split(" ")[0], tmpList);
			}

				
		}
		
		return edges;

	}

	public Map<String, ArrayList<String>> adjacency(ArrayList<String> fileList) {
			
		int len = fileList.size();		
		
		Map<String, ArrayList<String>> edges=new HashMap<String, ArrayList<String>>();
							
		for(int j=0;j<len;j++) {
			ArrayList<String> tmpList;
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
		for(int j=0;j<len;j++) {
			ArrayList<String> tmpList;
			if(!edges.containsKey(fileList.get(j).split(" ")[1])) {
				tmpList=new ArrayList<String>();				
				edges.put(fileList.get(j).split(" ")[1], tmpList);
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

	
	public ArrayList<String> readFile2(String filename) {
		try {
			fr = new FileReader("/home/stark/Documents/Workspace/java/" + filename + ".txt");
//			fr = new FileReader("/home/stark/Documents/Workspace/java/stanford-algs-master/testCases/course4/assignment4TwoSat/" + filename + ".txt");
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
			
			for(int j=1;j<fileList.size();j++) {
				String a,b,nega,negb;
				
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
				
			}

			
			maxVal=0;
			minVal=Integer.MAX_VALUE;
			for(int j=1;j<fileList.size();j++) {
				if(maxVal<Integer.parseInt(fileList.get(j).split(" ")[0]))
					maxVal=Integer.parseInt(fileList.get(j).split(" ")[0]);
				if(maxVal<Integer.parseInt(fileList.get(j).split(" ")[1]))
					maxVal=Integer.parseInt(fileList.get(j).split(" ")[1]);
				if(minVal>Integer.parseInt(fileList.get(j).split(" ")[0]))
					minVal=Integer.parseInt(fileList.get(j).split(" ")[0]);
				if(minVal>Integer.parseInt(fileList.get(j).split(" ")[1]))
					minVal=Integer.parseInt(fileList.get(j).split(" ")[1]);
				v.add(fileList.get(j).split(" ")[0]);
				v.add(fileList.get(j).split(" ")[1]);

			}
					
			return strList;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}

}
