package de.chris0385.tools;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import tiled.core.Map;
import tiled.core.MapLayer;
import tiled.core.Tile;
import tiled.core.TileLayer;
import tiled.core.TileSet;
import tiled.io.TMXMapReader;
import tiled.io.TMXMapWriter;

public class TileSymmetryGeneratorTool {

	private String source;
	private String tmp;

	public TileSymmetryGeneratorTool(String source) {
		this.source = source;
		this.tmp = new File(new File(source).getParentFile(), "symmetry-maker.tmp").getAbsolutePath();
	}

	public static void main(String[] args) throws Exception {
		new TileSymmetryGeneratorTool("/home/christophe/workspaces/workspace/soldiers-game/Tiles/symmetric.tmx").run();
	}

	private void run() throws Exception {
		TMXMapReader r = new TMXMapReader();
		Map map = r.readMap(source);
		System.out.println(map);
		System.out.println(map.getHeight());
		// System.out.println(map.getTileSets());
		List<MapLayer> layers = map.getLayers();
		for (MapLayer layer : layers) {
			if (layer instanceof TileLayer) {
				new TileLayerSymmetries((TileLayer) layer).processLayer();
			}
		}
		overwrite(map);

		File sourceF = new File(source);
		long lm = sourceF.lastModified();
		while (true) {
			if (lm == sourceF.lastModified()) {
				Thread.sleep(10);
				continue;
			}
			Thread.sleep(100);
			System.out.println("Update symmetry");
			Map newMap = r.readMap(source);
			doSymmetriesForDiff(map, newMap);
			map = newMap;
//			Thread.sleep(5000);
			overwrite(map);
//			sourceF.setLastModified(System.currentTimeMillis());
			lm = sourceF.lastModified();
			System.out.println("done");
		}

	}

	private void doSymmetriesForDiff(Map map, Map newMap) {
		List<MapLayer> layers = map.getLayers();
		List<MapLayer> newLayers = newMap.getLayers();
		if(newMap.getTileSets().isEmpty()) {
			newMap.addTileset(map.getTileSets().get(0));
		}
		for (int i = 0; i < layers.size(); i++) {
			MapLayer layer = layers.get(i);
			MapLayer newLayer = newLayers.get(i);
			if (!layer.getName().equals(newLayer.getName())) {
				throw new RuntimeException();
			}
			new TileLayerSymmetries((TileLayer) newLayer).doSymmetriesForDiff((TileLayer) layer);
		}
	}

	private void overwrite(Map map) throws Exception {
//		{
//			Field declaredField = TMXMapWriter.class.getDeclaredField("ENCODE_LAYER_DATA");
//			System.out.println(declaredField);
//			setToFalse(declaredField);
//			setToFalse(TMXMapWriter.class.getDeclaredField("COMPRESS_LAYER_DATA"));
//		}
		TMXMapWriter w = new TMXMapWriter();
		if (map.getTileSets().size() > 0) {
			// Workaround some bug
			TileSet tileSet = map.getTileSets().get(0);
			tileSet.setBaseDir(".");
			tileSet.setSource(new File(tileSet.getSource()).getName());
		}
//		w.settings.layerCompressionMethod= null;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		w.writeMap(map, out);
		File file = new File(tmp);
		try (FileOutputStream o = new FileOutputStream(file)) {
			o.write(out.toByteArray());
		}
		file.renameTo(new File(source));
	}

//	private void setToFalse(Field declaredField) throws NoSuchFieldException, IllegalAccessException {
//		declaredField.setAccessible(true);
//		Field modifiersField = Field.class.getDeclaredField("modifiers");
//		modifiersField.setAccessible(true);
//		modifiersField.setInt(declaredField, declaredField.getModifiers() & ~Modifier.FINAL);
//		declaredField.set(null, false);
//	}

	enum SymmetryMode {
		TWO, FOUR, EIGHT;
	}
	private class TileLayerSymmetries {
		

		private TileLayer layer;
		private Set<Object> updated = new HashSet<>();
		private SymmetryMode symmetryMode = SymmetryMode.EIGHT; // TODO: param

		public TileLayerSymmetries(TileLayer layer) {
			this.layer = layer;
		}

		private void doSymmetriesForDiff(TileLayer olayer) {
			for (int x = 0; x < olayer.getWidth(); x++) {
				for (int y = 0; y < olayer.getHeight(); y++) {
					if (wasUpdated(x, y)) {
						System.out.println("Skip " + x + "," + y);
						continue;
					}
					Tile to = olayer.getTileAt(x, y);
					Tile tn = layer.getTileAt(x, y);
					if (((tn == null) != (to == null)) || (tn != null && tn.getId() != to.getId())) {
						doSymetries(x, y);
						System.out.println("Update " + x + "," + y);
					}
				}
			}
		}

		private boolean wasUpdated(int x, int y) {
			return updated.contains(key(x, y));
		}

		private void processLayer() {
			for (int x = 0; x < layer.getWidth(); x++) {
				for (int y = 0; y < layer.getHeight(); y++) {
					if (isRef(x, y)) {
						doSymetries(x, y);
					}
				}
			}
		}

		private void doSymetries(int x, int y) {
			float cx = (layer.getWidth() - 1) / 2f;
			float cy = (layer.getHeight() - 1) / 2f;
			Tile tile = layer.getTileAt(x, y);
			int x2 = Math.round(cx - (x - cx));
			int y2 = Math.round(cy - (y - cy));
			switch (symmetryMode) {
			case EIGHT:
				setTileAt(tile, y, x);
				setTileAt(tile, y2, x);
				setTileAt(tile, y, x2);
				setTileAt(tile, y2, x2);
			case FOUR:
				setTileAt(tile, x2, y);
				setTileAt(tile, x2, y2);
			case TWO:
				setTileAt(tile, x, y2);
			}
		}

		private void setTileAt(Tile tile, int x, int y) {
			updated.add(key(x, y));
			layer.setTileAt(x, y, tile);
		}

		private Object key(int x, int y) {
			return x * 1000 + y;
		}

		private boolean isRef(int x, int y) {
			float cx = (layer.getWidth() - 1) / 2f;
			float cy = (layer.getHeight() - 1) / 2f;
			boolean r = true;
			switch (symmetryMode) {
			case EIGHT:
				r &= x >= y;
			case FOUR:
				r &= x < cx;
			case TWO:
				r &= y < cy;
			}
			return r;
		}
	}
}
