package main;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

public class Ex3 {
	
	
	
	public static void main(String[] args) throws CycleFoundException{
	
	Graf G=new Graf();
	
	
	Collection<Vertex> myGraph;

	// Read the graph 
	try {
	    myGraph = G.readGraph(args[0]);
	} catch (IOException | FileFormatException e) { // No file 
	    System.out.println("Error:" + e.getMessage());
	    return;
	} catch (ArrayIndexOutOfBoundsException e) { //No filename was given, print help message
	    System.out.println("This program prints the topological order of a given graph");
	    System.out.println("");
	    System.out.println("Usage: Ex2 filename");
	    return;
	}

	// Make an array of the nodes in the graph
	Vertex[] myGraphArray = myGraph.toArray(new Vertex[0]);

	
	G.addEgdes(myGraphArray); // Bygger upp grafen
	G.printGraph(myGraph); // Skriver ut incidenslistan för grafen
	System.out.println("");
	G.dijkstra(myGraphArray[1], myGraphArray[2]); // Utför dijkstras på (Startnoden, Slutnoden) 
    }
	
	
}


class Vertex{
	
    protected String name;                 // Name of the node
    protected int indegree;                // Indegree
    protected LinkedList<Vertex> Adj;      // Linked list of adjacent nodes
    protected int topNum;                  // Order in topological sort
    protected int dist;						
    protected boolean known;				
    protected Vertex path;

    public Vertex(String name){
	this.name=name;
	this.indegree=0;
	this.Adj=new LinkedList<Vertex>();
	this.topNum=-1;
	this.dist = Integer.MAX_VALUE;
	this.known = false;
	this.path=null;
	}
    
    // Add an adjacent node
    public void connectTo(Vertex a){
	a.indegree++;        // Increment the indegree of node a
	Adj.add(a);          // Add it to the list of adjacent nodes
    }

}


class Graf {

    /* Read in a graph from a file.
     */
    public static Collection<Vertex> readGraph(String fileName) throws IOException, FileFormatException {

	// Hash map for the nodes in the input graph
    	HashMap<String,Vertex> input = new HashMap<String,Vertex>();
    	String data=null;
    	BufferedReader r = new BufferedReader(new FileReader(fileName));
	 
    	while ((data = r.readLine()) != null) {
			input.put(data, new Vertex(data) );
		}
	
    	r.close();  // Close the reader
	
    	return input.values(); //Return the vertices as a Collection
    }

    

    // Print the graph as an incident list
    public void printGraph (Collection<Vertex> someGraph) {
	/* You implement this */
    	Vertex[] someGraphArray = someGraph.toArray(new Vertex[0]); //skapar en array av grafen
    	
    	System.out.println("Incidenslista:");
    	
    	for(int i=0;i<someGraphArray.length;i++) {	//printar alla noders namn
    		System.out.print(someGraphArray[i].name);
    		
    		for(int j=0;j<someGraphArray[i].Adj.size();j++) {	//kollar alla b�gar f�r varje nod och printar noderna som de pekar p�
    			System.out.print(" ----> " + someGraphArray[i].Adj.get(j).name);
    		}
    		System.out.println("");
    	}
    }
    public void addEgdes(Vertex[] inputArray) {
    	
    	int numDifferentLetters = 0;
		
    	for(int i=0;i<inputArray.length;i++) {	//För varje ord
    		
    		for(int j=0;j<inputArray.length;j++) { //Kolla alla andra ord
				numDifferentLetters = 0;
    				
    			for(int k=1;k<inputArray[j].name.length();k++) { //Kolla varje bokstav
    			
    				if(inputArray[i].name.charAt(k) != inputArray[j].name.charAt(k)) { //ifall en bokstav är olika	
    					numDifferentLetters++;	 //antalet olika bokst�ver �kar
    				}
    			}
    			if(numDifferentLetters == 1) { 	//och ifall det finns en exakt en olika bokstav   				
    				inputArray[i].Adj.add(inputArray[j]);	//Sätt en båge mellan
    				
    			}
    		}
    	}		
	}
    public void dijkstra (Vertex start, Vertex end) {
  	 
        LinkedList<Vertex> queue = new LinkedList<Vertex>();	//denna dijksrtas algoritm anv�nder inte sig av dist. p.g.a. all distans = 1
        														//och kortaste vägen blir automatiskt den första vägen som hittas
        														//gjord av Simon Kjällberg och Kevin Koljonen
        start.known=true;	//markerar den första noden till known
        queue.add(start);	//och sätter till den i queuen
 
        while(queue.size() != 0) {
            start = queue.poll();	//Vi tar bort toppen av queuen och sätter det till startnoden
            if(start == end) {		//och kollar att ifall vi redan hittat slutet
            	break;
            }

            Iterator<Vertex> i = start.Adj.listIterator();	//Vi går genom alla närliggande noder till start
            while(i.hasNext()) {
                Vertex n = i.next();						
                
                if(!n.known) {								//ifall noden inte kollats tidigare markeras den true och läggs till i queuen
                    n.known = true;							
                    queue.add(n);
                    n.path = start;							//dessutom sätts path att vara den tidigare noden
                }
            }
        }
        if(end.path == null) {	//ifall ingen väg finns mellan start och end skrivs det ut
        	System.out.println("NO CONNECTION BETWEEN "+ start.name +" AND "+ end.name);
        }
    	
    	printPath(end);
    	
    }

    public void printPath(Vertex end) {
    	if(end.path != null) {
    		printPath(end.path);
    		System.out.println(" To ");
    	}
    	System.out.println(end.name);
    }
}

@SuppressWarnings("serial")
class FileFormatException extends Exception { //Input file has the wrong format
    public FileFormatException(String message) {
	super(message);
    }
    
}

@SuppressWarnings("serial")
class CycleFoundException extends Exception {
    public CycleFoundException(String message) {
    }
}