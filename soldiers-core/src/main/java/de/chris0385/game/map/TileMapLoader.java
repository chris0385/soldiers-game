package de.chris0385.game.map;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;

import tiled.core.Map;
import tiled.core.MapLayer;
import tiled.core.MapObject;
import tiled.core.ObjectGroup;
import tiled.core.Tile;
import tiled.core.TileLayer;
import tiled.io.TMXMapReader;

public class TileMapLoader {
	
	private static final String TILE_LAYER_NAME = "tiles";

	public static void main(String[] args) throws Exception {
		new TileMapLoader().load("src/main/resources/map/map.tmx");
	}

	private Map map;

	private void load(String string) throws Exception {
		// TODO Auto-generated method stub
		TMXMapReader r = new TMXMapReader();
		map = r.readMap(string);
		
		System.out.println("getTileSets"+map.getTileSets().size());
		TileLayer tileLayer = getTileLayer();
		int w = tileLayer.getWidth();
		int h = tileLayer.getHeight();
		
		TileMap tileMap = new TileMap(w, h);
		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				Tile tile = tileLayer.getTileAt(x, y);
				if(tile==null) {
					System.err.println(x+","+y);
					continue;
				}
				Properties props = tile.getProperties();
				if(!props.isEmpty()) {
//					System.out.println(props);
				}
			}
		}
		
		for (MapLayer layer : map.getLayers()) {
			if (layer instanceof ObjectGroup) {
				processObjects((ObjectGroup) layer);
			}
		}
		
	}

	private void processObjects(ObjectGroup layer) {
		// TODO Auto-generated method stub
		for (Iterator<MapObject> it = layer.getObjects(); it.hasNext();) {
			MapObject obj = it.next();
			double pixelX = obj.getX();
			double pixelY = obj.getY();
			System.out.println(obj + " n=" + obj.getName()+" t="+obj.getTile().getProperties());
		}
	}

	private TileLayer getTileLayer() {
		System.out.println(map.getLayers().size());
		for (MapLayer layer : map.getLayers()) {
			System.out.println(layer.getClass());
			if (TILE_LAYER_NAME.equals(layer.getName())) {
				return (TileLayer)layer;
			}
		}
		throw new IllegalStateException();
	}
}
