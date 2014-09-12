package slick2d;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.util.Log;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * An extended version of the TiledMap class, with more functionality. 
 * A merge of Liam (liamzebedee) Edwards-Playne's  existing library, TiledMapPlus
 * 
 * @author liamzebedee
 */
public class TiledMapPlus extends TiledMap {
	private HashMap<String,Integer> objectGroupNameToOffset = new HashMap<String,Integer>();
	private HashMap<String,Integer> layerNameToIDMap = new HashMap<String,Integer>();
	private HashMap<String,Integer> tilesetNameToIDMap = new HashMap<String,Integer>();

	/**
	 * Create a new tile map based on a given TMX file
	 * 
	 * @param ref The location of the tile map to load
	 * @throws SlickException Indicates a failure to load the tilemap
	 */
	public TiledMapPlus(String ref) throws SlickException {
		this(ref, true);
	}

	/**
	 * Create a new tile map based on a given TMX file
	 * 
	 * @param ref The location of the tile map to load
	 * @param loadTileSets True if we want to load tilesets - including their image data
	 * @throws SlickException Indicates a failure to load the tilemap
	 */
	public TiledMapPlus(String ref, boolean loadTileSets) throws SlickException {
		super(ref, loadTileSets);
		processNameToObjectMap();
		processLayerMap();
		processTilesetMap();
	}

	/**
	 * Create a new tile map based on a given TMX file
	 * 
	 * @param ref The location of the tile map to load
	 * @param tileSetsLocation The location where we can find the tileset images and other resources
	 * @throws SlickException Indicates a failure to load the tilemap
	 */
	public TiledMapPlus(String ref, String tileSetsLocation) throws SlickException {
		super(ref, tileSetsLocation);
		processNameToObjectMap();
		processLayerMap();
		processTilesetMap();
	}

	/**
	 * Load a tile map from an arbitary input stream
	 * 
	 * @param in The input stream to load from
	 * @throws SlickException Indicates a failure to load the tilemap
	 */
	public TiledMapPlus(InputStream in) throws SlickException {
		super(in);
		processNameToObjectMap();
		processLayerMap();
		processTilesetMap();
	}

	/**
	 * Load a tile map from an arbitary input stream
	 * 
	 * @param in The input stream to load from
	 * @param tileSetsLocation The location at which we can find tileset images
	 * @throws SlickException Indicates a failure to load the tilemap
	 */
	public TiledMapPlus(InputStream in, String tileSetsLocation) throws SlickException {
		super(in, tileSetsLocation);
		processNameToObjectMap();
		processLayerMap();
		processTilesetMap();
	}

	/**
	 * Populates the objectGroupName to objectGroupOffset map and the
	 * groupObjectName to groupObjectOffset map
	 *  
	 * @author liamzebedee
	 */
	private void processNameToObjectMap(){
		for(int i = 0;i<this.getObjectGroupCount();i++){
			ObjectGroup g = this.objectGroups.get(i);
			this.objectGroupNameToOffset.put(g.name, i);
			HashMap<String,Integer> nameToObjectMap = new HashMap<String,Integer>();
			for(int ib = 0;ib<this.getObjectCount(i);ib++){
				nameToObjectMap.put(this.getObjectName(i, ib), ib);
			}
			g.setObjectNameMapping(nameToObjectMap);
		}
	}

	/**
	 * Populates the tileSet name to offset map
	 * 
	 * @author liamzebedee
	 */
	private void processLayerMap(){
		for (int l=0;l<layers.size();l++) {
			Layer layer = layers.get(l);
			this.layerNameToIDMap.put(layer.name, l);
		}
	}

	/**
	 * Populates the tileSet name to offset map
	 * 
	 * @author liamzebedee
	 */
	private void processTilesetMap(){
		for(int t = 0;t<this.getTileSetCount();t++){
			TileSet tileSet = this.getTileSet(t);
			this.tilesetNameToIDMap.put(tileSet.name, t);
		}
	}

	/**
	 * Gets a layer by its name
	 * 
	 * @author liamzebedee
	 * @param layerName The name of the layer to get
	 */
	public Layer getLayer(String layerName){
		int layerID = this.layerNameToIDMap.get(layerName);
		return this.layers.get(layerID);
	}

	/**
	 * Gets an ObjectGroup
	 *
	 * @author liamzebedee
	 * @param groupName The name of the group 
	 */
	public ObjectGroup getObjectGroup(String groupName){
		return this.objectGroups.get(
				this.objectGroupNameToOffset.get(groupName));
	}

	/**
	 * Gets all ObjectGroup's
	 * 
	 * @author liamzebedee
	 */
	public ArrayList<ObjectGroup> getObjectGroups(){
		return this.objectGroups;
	}

	/**
	 * Get all tiles from all layers that are part of a specific tileset
	 * 
	 * @author liamzebedee
	 * @param tilesetName The name of the tileset that the tiles are part of
	 */
	public ArrayList<Tile> getAllTilesFromAllLayers(String tilesetName){
		ArrayList<Tile> tiles = new ArrayList<Tile>();
		int tilesetID = this.tilesetNameToIDMap.get(tilesetName);
		for (int x = 0; x < this.getWidth(); x++) {
			for (int y = 0; y < this.getHeight(); y++) {
				for(int l = 0;l<this.getLayerCount();l++){
					Layer layer = this.layers.get(l);
					if (layer.data[x][y][0] == tilesetID) {
						Tile t = new Tile(x,y,layer.name,layer.data[x][y][2],tilesetName);	
						tiles.add(t);
					}
				}
			}
		}
		return tiles;
	}


	/**
	 * Writes the current TiledMap to a stream
	 * 
	 * @author liamzebedee
	 * @param stream The stream in which the TiledMap is to be written to
	 * @throws SlickException 
	 */
	public void write(OutputStream o) throws SlickException{
		try {
			DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
			Document doc = docBuilder.newDocument();

			Element map = doc.createElement("map");
			map.setAttribute("version","1.0");
			map.setAttribute("orientation", "orthogonal");
			map.setAttribute("tilewidth", ""+this.tileWidth);
			map.setAttribute("tileheight", ""+this.tileHeight);
			map.setAttribute("width", ""+this.width);
			map.setAttribute("height", ""+this.height);
			doc.appendChild(map);
			for(int i = 0;i<this.tileSets.size();i++){ //Loop through all tilesets
				TileSet tilesetData =  this.tileSets.get(i);
				Element tileset = doc.createElement("tileset");
				tileset.setAttribute("firstgid", ""+tilesetData.firstGID);
				tileset.setAttribute("name", tilesetData.name);
				tileset.setAttribute("tilewidth", ""+tilesetData.tileWidth);
				tileset.setAttribute("tileheight", ""+tilesetData.tileHeight);
				tileset.setAttribute("spacing", ""+tilesetData.tileSpacing);
				tileset.setAttribute("margin", ""+tilesetData.tileMargin);
				Element image = doc.createElement("image");
				String imagePath = tilesetData.imageref.replaceFirst(this.getTilesLocation()+"/", "");
				image.setAttribute("source", imagePath);
				image.setAttribute("width", ""+tilesetData.tiles.getWidth());
				image.setAttribute("height", ""+tilesetData.tiles.getHeight());
				tileset.appendChild(image);
				int tileCount = tilesetData.tiles.getHorizontalCount()*
						tilesetData.tiles.getVerticalCount();
				if(tileCount == 1){
					tileCount++;
				}
				for(int tileI = 0;tileI<tileCount;tileI++){
					Properties tileProperties = tilesetData.getProperties(tileI);
					if(tileProperties != null){
						Element tile = doc.createElement("tile");
						int tileID = tileI-tilesetData.firstGID;
						tile.setAttribute("id", ""+tileID);
						Element tileProps = doc.createElement("properties");

						Enumeration propertyEnum = tilesetData.getProperties(tileI).propertyNames();
						while (propertyEnum.hasMoreElements()) {
							String key = (String) propertyEnum.nextElement();
							Element tileProperty = doc.createElement("property");
							tileProperty.setAttribute("name", key);
							tileProperty.setAttribute("value", tileProperties.getProperty(key));
							tileProps.appendChild(tileProperty);
						}		
						tile.appendChild(tileProps);
						tileset.appendChild(tile);
					}
				}
				map.appendChild(tileset);
			}

			for(int i = 0;i<this.layers.size();i++){
				Element layer = doc.createElement("layer");
				Layer layerData = layers.get(i);
				layer.setAttribute("name", layerData.name);
				layer.setAttribute("width", ""+layerData.width);
				layer.setAttribute("height",""+layerData.height);
				Element data = doc.createElement("data");

				ByteArrayOutputStream os = new ByteArrayOutputStream();                
				for(int tileY = 0;tileY<layerData.height;tileY++){
					for(int tileX = 0;tileX<layerData.width;tileX++){
						int tileGID = layerData.data[tileX][tileY][2];
						os.write(tileGID);
						os.write(tileGID << 8);
						os.write(tileGID << 16);
						os.write(tileGID << 24);
					}
				}
				os.flush();
				String compressedData = Base64.encodeBytes(os.toByteArray(),Base64.DONT_BREAK_LINES|Base64.GZIP|Base64.ENCODE);
				data.appendChild(doc.createTextNode(compressedData));           	
				data.setAttribute("encoding", "base64");
				data.setAttribute("compression","gzip");

				layer.appendChild(data);
				map.appendChild(layer);
			}
			for(int objectGroupI = 0;objectGroupI<this.objectGroups.size();objectGroupI++){
				Element objectgroup = doc.createElement("objectgroup");
				ObjectGroup objectGroup = objectGroups.get(objectGroupI);
				objectgroup.setAttribute("color", "white"); 
				//	It doesn't appear we use a color value,
				//	but its in the format so...
				objectgroup.setAttribute("name", objectGroup.name);
				objectgroup.setAttribute("width", ""+objectGroup.width);
				objectgroup.setAttribute("height", ""+objectGroup.height);

				for(int groupObjectI = 0;groupObjectI<objectGroup.objects.size();groupObjectI++){
					Element object = doc.createElement("object");
					GroupObject groupObject = objectGroup.objects.get(groupObjectI);
					object.setAttribute("name", groupObject.name);
					object.setAttribute("type", groupObject.type);
					object.setAttribute("x", ""+groupObject.x);
					object.setAttribute("y", ""+groupObject.y);
					object.setAttribute("width", ""+groupObject.width);
					object.setAttribute("height", ""+groupObject.height);
					if(groupObject.props != null){
						Element objectProps = doc.createElement("properties");
						Enumeration propertyEnum = groupObject.props.propertyNames();
						while (propertyEnum.hasMoreElements()) {
							String key = (String) propertyEnum.nextElement();
							Element objectProperty = doc.createElement("property");
							objectProperty.setAttribute("name", key);
							objectProperty.setAttribute("value", groupObject.props.getProperty(key));
							objectProps.appendChild(objectProperty);
						}
						object.appendChild(objectProps);
					}
					objectgroup.appendChild(object);
				}

				map.appendChild(objectgroup);
			}
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(o);
			transformer.transform(source, result);

		}
		catch(Exception e){
			Log.error(e);
			throw new SlickException("Failed to write tiledmap", e);
		}
	}

	/**
	 * Gets an arrayList of all layers in the TiledMap
	 * 
	 * @author liamzebedee
	 */
	public ArrayList<Layer> getLayers(){
		return this.layers;
	}

	/**
	 * Gets an arrayList of all tilesets in the TiledMap
	 * 
	 * @author liamzebedee
	 */
	public ArrayList<TileSet> getTilesets(){
		return this.tileSets;
	}

	/**
	 * Gets the visible tile's image at the co-ordinates
	 * 
	 * @author liamzebedee
	 * @param x The x co-ordinate of the tile
	 * @param y The y co-ordinate of the tile
	 * @return The visible tile at this location
	 * @throws SlickException 
	 */
	public Image getVisibleTile(int x, int y) throws SlickException{
		Image visibleTileImage = null;
		for(int l = this.getLayerCount()-1;l > -1;l--){
			if(visibleTileImage == null){
				visibleTileImage = this.getTileImage(x, y, l);
				continue;
			}
		}
		if(visibleTileImage == null){
			throw new SlickException("Tile doesn't have a tileset!");
		}
		return visibleTileImage;
	}

	/**
	 * Gets the ID of a tileset from its name
	 * 
	 * @author liamzebedee
	 * @param tilesetName The name of the tileset to get the id of
	 */
	public int getTilesetID(String tilesetName){
		int tilesetID = this.tilesetNameToIDMap.get(tilesetName);
		return tilesetID;
	}

	/**
	 * Gets the ID of a layer from its name
	 * 
	 * @author liamzebedee
	 * @param layerName The name of the layer to get the id of
	 */
	public int getLayerID(String layerName){
		int layerID = this.layerNameToIDMap.get(layerName);
		return layerID;
	}

}
