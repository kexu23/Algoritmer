
public class DisjSets{
	
	
	private int [] s;

/* Initiera en disjunkt mängd */
	public DisjSets(int numElements){ 
		s = new int[numElements];
		for ( int i = 0; i < s.length; i++)
			s[i] = -1;
	}
	/* Antar att rot1 och rot2 är rötter */
	public void union(int rot1, int rot2)	{
		
		if ( s[rot2] <= s[rot1] ){/* rot2 är större eller lika */
			s[rot2] += s[rot1]; /* Addera storekarna */
			s[rot1] = rot2; /* rot2 blir ny rot */
	 }
	 else /* rot1 är det större trädet */
	 { s[rot1] += s[rot2];
	 s[rot2] = rot1; /* rot1 blir ny rot */
	 }
	}
	public int find(int x){
		if( s[x] < 0 ) /* x är en rot, returnera den */
			return x;
		else return find( s[x] ); /* annars gå ett steg uppåt */
	}
	
}
