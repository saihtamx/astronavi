package astro;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class navi {

	public static void main(String[] args) throws FileNotFoundException {
		File file = new File("generatedGraph.json");
		Scanner scan = new Scanner(file);
		String zeile = scan.nextLine();	
		scan.close();				
		
		int source0=0;
		int target0=0;
		int n=0;		
		String erde = "Erde";
		String bert = "b3-r7-r4nd7";
		int[][] nodes = new int[1000][20];
		double[][] edges = new double[1000][20];
		int[][] pfad = new int[1001][100];
		double[][] strecke = new double[1001][100];
		int source=0;
		int target=0;
		double cost=0;
		double weg=0;
		double minweg = 9999d;
		boolean doppel;
		
		String split[] = zeile.split(",");
				
		for(int i=0; i<split.length; i++) {			// Datenfelder füllen
			if (split[i].indexOf(erde)>0){
				source0 = n;
			}
			if (split[i].indexOf(bert)>0){
				target0 = n;
			}
			if (split[i].indexOf("source")>0){
				source = Integer.parseInt(split[i].replaceAll("[^0-9]", ""));
			}
			if (split[i].indexOf("target")>0){
				target = Integer.parseInt(split[i].replaceAll("[^0-9]", ""));
			}
			if (split[i].indexOf("cost")>0){
				cost = Double.parseDouble(split[i].replaceAll("[^0-9,.]", "")); 
				nodes[source][0] = nodes[source][0]+1; 
				nodes[target][0] = nodes[target][0]+1;
				nodes[source][nodes[source][0]] = target;
				nodes[target][nodes[target][0]] = source;
				edges[source][nodes[source][0]] = cost;
				edges[target][nodes[target][0]] = cost;		
			}			
			n++;
		}
			
		for(int i=0; i<1000; i++) {
			for (int j=0; j<100; j++) {
				pfad[i][j] = 9999;
			}
		}	
			
		pfad[source0][0]=source0;
			
		for(int etap=0; etap<=30; etap++) {				// Anzahl der Etappen
			for (source=0; source<1000; source++) {
				if(pfad[source][etap]!=9999) {						
					for(int i=1; i<=nodes[source][0]; i++) {
						target = nodes[source][i];
						cost = edges[source][i];
						doppel = false;							// Prüfung auf doppelte Sterne im Pfad
						int ti=source;
						for(int j=etap; j>=1; j--) { 
							int si=pfad[ti][j];
							if(si==target) {
								doppel=true; }
							ti=si; }
						if(!doppel) {
							weg = cost+strecke[source][etap];
							if(weg<strecke[target][etap+1] || strecke[target][etap+1]==0) {
								pfad[target][etap+1]=source;
								strecke[target][etap+1]=weg;
							}
						}
					}
				}
			}
				
			if(pfad[target0][etap]!=9999) {
				int ti=target0;
				weg = strecke[target0][etap];
				if (weg < minweg) {
					minweg = weg;
				}
				
				System.out.println("etappen = "+etap +"      Gesamtstrecke = "+weg);
				for(int i=etap; i>=0; i--) {
					int si=pfad[ti][i];
					pfad[1000][i] = ti;					
					ti=si;
				}
				System.out.print(erde+" - ");
				for(int i=1; i<=etap-1; i++) {
					System.out.print(pfad[1000][i]+" - ");		
				}
				System.out.println(bert+"\n");
			}	
		}
		System.out.println("kuerzeste Gesamtstrecke = "+ minweg);
	}
}


	


