package xu.gensheng.ShutTheBox;

import java.util.ArrayList;

public class Driver {

	public static void main(String[] args) {
		
		// Nine Tiles
		int NUM_TILES = 9;
		ArrayList<Tile> tiles = new ArrayList<>();
		
		for (int i=0; i<NUM_TILES; i++) {
			tiles.add(new Tile((i+1)));
		}
		
		boolean done = false;
		while (!done) {
			for (Tile t: tiles) {
				System.out.print(t);
			}
			done = true;
		}
	
	}
}