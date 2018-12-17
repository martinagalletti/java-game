package map;

import java.awt.Graphics;

import gui.Assets;
import notmoving.NotMovings;
import tilegame.Position2D;
import tilegame.Tile;

/**
 * @author Faustine & Martina
 * 
 * The World class is the terrain of the world. 
 * It is composed of Tiles (grass or water) which might have NotMovings on it.
 * The render methods draw the tiles and their NotLivings if any.
 * 
 */

public class World {

	private int[][] worldTiles;
	private NotMovings[][] worldNotMovings = new NotMovings[Map.SIZE_MAP][Map.SIZE_MAP];

	/**
	 * Constructor. Create the tiles according to the noise map landMap.
	 * @param landMap
	 */
	public World(float[][] landMap) {
		worldTiles = new int[Map.SIZE_MAP][Map.SIZE_MAP];
		for (int x = 0; x < Map.SIZE_MAP; x++) {
			for (int y = 0; y < Map.SIZE_MAP; y++) {
				worldTiles[x][y] = Math.abs(Math.round(landMap[x][y]));
			}
		}		
	}

	/**
	 * Get the tile corresponding to the map position.  
	 * @param x, y
	 * @return Tile
	 */
	public Tile getTile(int x, int y) {
		Tile t = Tile.tiles[worldTiles[x][y]];
		if(t == null) {
			return Tile.grassTile;
		}
		return t;
	}

	/**
	 * Get the tile corresponding to the map position.  
	 * @param pos
	 * @return Tile
	 */
	public Tile getTile(Position2D pos) {
		Tile t = Tile.tiles[worldTiles[pos.getX()][pos.getY()]];
		if(t == null) {
			return Tile.grassTile;
		}
		return t;
	}

	/**
	 * Getter.
	 * @return worldTiles
	 */
	public int[][] getWorldTiles() {
		return worldTiles;
	}

	/**
	 * Get the NotMoving corresponding to the map position.  
	 * @param pos
	 * @return NotMovings
	 */
	public NotMovings getWorldNotMovings(Position2D pos) {
		return worldNotMovings[pos.getX()][pos.getY()];
	}

	/**
	 * Set the NotMoving in the worldNotMovings matrix..  
	 * @param notMovings
	 */
	public void setWorldNotMovings(NotMovings notMovings) {
		worldNotMovings[notMovings.getNotMovingsPosition().getX()][notMovings.getNotMovingsPosition().getY()] = notMovings;
	}

	/**
	 * Set the worldNotMovings matrix to null at position pos.  
	 * @param pos
	 */
	public void setWorldNotMovingsNull(Position2D pos) {
		worldNotMovings[pos.getX()][pos.getY()] = null;
	}

	/**
	 * Draw all tiles.
	 * @deprecated Now only visible tiles are drawn.    
	 * @param g
	 */
	public void render(Graphics g) {
		for (int x = 0; x < Map.SIZE_MAP; x++) {
			for (int y = 0; y < Map.SIZE_MAP; y++) {
				//System.out.println(x + ", " + y);
				getTile(x, y).render(g, x, y);
			}
		}
	}

	/**
	 * Fill the world with gray tiles.   
	 * @param g
	 */
	public void renderGrayTiles(Graphics g) {
		for (int x = 0; x < Map.SIZE_MAP; x++) {
			for (int y = 0; y < Map.SIZE_MAP; y++) {
				getTile(x, y).renderGrayTile(g, y, x);
			}
		}
	}

	/**
	 * Draw the tile corresponding the the position x, y.   
	 * @param g, x, y
	 */
	public void renderOneTile(Graphics g, int x, int y) {
		if (0 <= x && x < Map.SIZE_MAP && 0 <= y && y < Map.SIZE_MAP) {
			getTile(x, y).render(g, y, x);
			if (worldNotMovings[x][y] != null) {
				g.drawImage(Assets.getImageFromString(worldNotMovings[x][y].getName()), x*Map.PIXEL_SCALE, y*Map.PIXEL_SCALE, null);
			}
		}
	}
}
