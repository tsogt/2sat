package algo;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SCCtest {
	FileReader fr;
	BufferedReader br;
	static int t=0;
	static int s=0;
	int n;
	Map<Integer,String> vMap=new HashMap<Integer,String>();
	Map<String,Integer> vMapRev=new HashMap<String,Integer>();
	Map<Integer,Integer> vMapFinishedTime=new HashMap<Integer,Integer>();
//	int ii,sizeG;
	
	HashMap<Integer,Boolean> explored = new HashMap<Integer, Boolean>();
	HashMap<Integer,Integer> leader = new HashMap<Integer, Integer>();
	HashMap<Integer,ArrayList<Integer>> leaderA = new HashMap<Integer, ArrayList<Integer>>();
	static HashMap<Integer,Integer> leaders = new HashMap<Integer, Integer>();
	static HashMap<Integer,Integer> finishTime = new HashMap<Integer, Integer>();
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SCCtest obj = new SCCtest();
		ArrayList<String> arr = obj.readFile("2sat1");
		
		System.out.println("Read File");
		
/*		for(int i=0;i<arr.size();i++) {
			System.out.println(arr.get(i));
		}*/

		int i,j;
		for(i=1;i<=obj.n;i++) {
			obj.explored.put(i,false);
		}
		ArrayList<Integer>[] graphOrder = new ArrayList[obj.n+1];
		for(i=0;i<=obj.n;i++) {
			graphOrder[i] = new ArrayList<Integer>();
		}

		ArrayList<Integer>[] graphOrig = obj.adjacency(arr);
		System.out.println("adj");
		ArrayList<Integer>[] graphRev = obj.adjacencyRev(arr);
		System.out.println("adjRev");
		
		obj.DFSLoop(graphRev);
		
		System.out.println("DFS Loop on Rev");
		for(j=1;j<=obj.n;j++) {
//			graphOrder[finishTime.get(j)] = graphOrig[j];
			for(int k:graphOrig[j]) {
				graphOrder[finishTime.get(j)].add(finishTime.get(k));
			}			
			
			
		}
		
		
		for(i=1;i<=obj.n;i++) {
			obj.explored.put(i,false);
		}		
		obj.DFSLoop(graphOrder);
		System.out.println("DFS Loop on Ordered");
		
		
		for(Map.Entry<Integer, Integer> e:obj.finishTime.entrySet()) {
			obj.vMapFinishedTime.put(e.getValue(),e.getKey());
		}
		
		
//Count SCC size`

/*		int tmp=0, leadNode;
//		HashMap<Integer,Integer> leaders = new HashMap<Integer, Integer>();
		for(i=1;i<=obj.n;i++) {
			leadNode = obj.leader.get(i);
			if(leaders.containsKey(leadNode)) {
				tmp = leaders.get(leadNode);
				tmp++;
				leaders.put(leadNode, tmp);
			}
			else {
				leaders.put(leadNode, 1);
			}			
		}*/

		for(Map.Entry<Integer, Integer> e:obj.leader.entrySet()) {
			if(obj.leaderA.containsKey(e.getValue())) {
				obj.leaderA.get(e.getValue()).add(e.getKey());
			}
			else {
				ArrayList<Integer> tmpList=new ArrayList<Integer>();
				tmpList.add(e.getKey());
				obj.leaderA.put(e.getValue(), tmpList);
			}
				
		}
		
		
/*		int	maxSCC = 0,maxKey = 0;
		maxSCC = 0;maxKey = 0;
		for(Map.Entry<Integer, ArrayList<Integer>> e:obj.leaderA.entrySet()) {
			if(maxSCC<e.getValue().size()) {
				maxKey=e.getKey();
				maxSCC=e.getValue().size();							
			}
		}
		System.out.println(obj.vMap.get(obj.vMapFinishedTime.get(maxKey)) + ": " + maxSCC);	
		int z=1;
		for(Integer e:obj.leaderA.get(maxKey)) {
			System.out.println(z+++":"+obj.vMap.get(obj.vMapFinishedTime.get(e)));	
		}*/
		
		

		
//		check 2SAT satisfiable
		boolean satisfy=true;
		for(Map.Entry<Integer, ArrayList<Integer>> e:obj.leaderA.entrySet()) {
			if(e.getValue().size()>1) {
//				System.out.println("hi");
				for(Integer en1:e.getValue()) {
					for(Integer en2:e.getValue()) {
						if(en1!=en2) {
//							System.out.println("start");
//							System.out.println(en1+":"+obj.vMapFinishedTime.get(en1)+":"+obj.vMap.get(obj.vMapFinishedTime.get(en1)));
//							System.out.println(en2+":"+obj.vMapFinishedTime.get(en2)+":"+obj.vMap.get(obj.vMapFinishedTime.get(en2)));
							if(obj.vMap.get(obj.vMapFinishedTime.get(en1)).charAt(0)=='-') {							
								if(obj.vMap.get(obj.vMapFinishedTime.get(en1)).substring(1).equals(obj.vMap.get(obj.vMapFinishedTime.get(en2)))) {
									satisfy=false;								
									break;
								}
							}
	/*						System.out.println("start");
							System.out.println(en1+":"+obj.vMap.get(en1));
							System.out.println(en2+":"+obj.vMap.get(en2));
							if(obj.vMap.get(en1).charAt(0)=='-') {							
								if(obj.vMap.get(en1).substring(1).equals(obj.vMap.get(en2))) {
									satisfy=false;								
									break;
								}
							}*/
							
						}

								
					}	
					if(satisfy==false)
						break;
				}
				
			}
			if(satisfy==false)
				break;			
		}
		
		System.out.println(satisfy);
		
//		print SCC size
/*		int	maxSCC = 0,maxKey = 0;
		for(int k=0;k<5;k++) {
			maxSCC = 0;maxKey = 0;
			for(Map.Entry<Integer, ArrayList<Integer>> e:obj.leaderA.entrySet()) {
				if(maxSCC<e.getValue().size()) {
					maxKey=e.getKey();
					maxSCC=e.getValue().size();							
				}
			}
			System.out.println(maxKey + ": " + maxSCC);			
			obj.leaderA.remove(maxKey);
			
		}		*/
		
/*		for(int k=0;k<5;k++) {
			maxSCC = 0;maxKey = 0;
			for(Map.Entry n:leaders.entrySet()) {
//				System.out.println(m.getKey() + ": " + m.getValue());
				if(maxSCC < (int)n.getValue()) {
					maxSCC = (int)n.getValue();
					maxKey = (int)n.getKey();
				}
			}
			
			System.out.println(maxKey + ": " + maxSCC);
			leaders.remove(maxKey);			
		}*/
		



	}
	public void DFSLoop(ArrayList<Integer>[] Graph) {
		int i;
		t=0;
		for(i=n;i>0;i--) {
			if(explored.get(i)==false) {
				s=i;
				DFS(Graph, i);
			}
		}
	}
	public void DFS(ArrayList<Integer>[] Graph, int node) {
		explored.put(node,true);
		leader.put(node, s);

		
		int sizeG = Graph[node].size();
		int ii;
		for(ii=0;ii<sizeG;ii++) {
			if(explored.get(Graph[node].get(ii))==false) {
				DFS(Graph,Graph[node].get(ii));
			}
		}
		t++;
		finishTime.put(node, t);
//		vMapFinishedTime.put(t,node);
//		vMap.put(t, vMap.get(node));
		
		
	}
	public ArrayList<Integer>[] adjacencyRev(ArrayList<String> fileList) {
		
		int len = fileList.size();
		int i,j;

		
		ArrayList[] adjMat= new ArrayList[n+1];
			
		for(j=0;j<=n;j++) {
			adjMat[j] = new ArrayList<Integer>();
		}
		String[] col = new String[2];
		for(j=0;j<len;j++) {
			col = fileList.get(j).split(" ");
			adjMat[Integer.parseInt(col[1])].add(Integer.parseInt(col[0]));
		}

		return adjMat;
	}

	public ArrayList<Integer>[] adjacency(ArrayList<String> fileList) {
			
		int len = fileList.size();
		int i,j;		
		ArrayList<Integer>[] adjMat= new ArrayList[n+1];			
		for(j=0;j<=n;j++) {
			adjMat[j] = new ArrayList<Integer>();
		}
		System.out.println("declared adjMat");
		
		for(j=0;j<len;j++) {
			adjMat[Integer.parseInt(fileList.get(j).split(" ")[0])].add(Integer.parseInt(fileList.get(j).split(" ")[1]));
		}
		
		return adjMat;
	}
	
	
	public ArrayList<String> readFile(String filename) {
		try {
//			fr = new FileReader("/home/stark/Documents/Workspace/java/stanford-algs-master/testCases/course4/assignment4TwoSat/" + filename + ".txt");
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
			
			
			int k=1;
			int tmp;
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
				
				if(!vMap.containsValue(a)) {
					
					tmp=k++;
					vMap.put(tmp, a);
					vMapRev.put(a, tmp);
					
				}
					
				if(!vMap.containsValue(b)) {
					
					tmp=k++;
					vMap.put(tmp, b);
					vMapRev.put(b, tmp);
				}
				if(!vMap.containsValue(nega)) {
					
					tmp=k++;
					vMap.put(tmp, nega);
					vMapRev.put(nega, tmp);
				}				
				if(!vMap.containsValue(negb)) {
					
					tmp=k++;
					vMap.put(tmp, negb);
					vMapRev.put(negb, tmp);
				}

					

			
				strList.add(vMapRev.get(nega)+" "+vMapRev.get(b));
				strList.add(vMapRev.get(negb)+" "+vMapRev.get(a));
				
//				strList.add(vMapRev.get(a)+" "+vMapRev.get(b));
			}
/*			n=0;
			for(Map.Entry<Integer, String> e:vMap.entrySet()) {
				if(n<e.getKey())
					n=e.getKey();
			}*/
			n=--k;
			System.out.println(n);

				
			return strList;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}

}
