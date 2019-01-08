package computc;

import java.io.FileInputStream;
import java.util.Properties;

public class Utility {
	
	private static Utility singleton;

	private int moveNorth;
	private int moveSouth;
	private int moveWest;
	private int moveEast;
	private int openMap;
	private int swordAttack;
	private int arrowAttack;
	private int runKey;
	
	private Utility() {
	    try{
	    	//Load ini file
	        Properties p = new Properties();
	        p.load(new FileInputStream("controls.ini"));
	        //Each key of the file has a string for a key to press. Map to them
			this.moveNorth = Utility.convertChar(p.getProperty("up"));
			this.moveSouth = Utility.convertChar(p.getProperty("down"));
			this.moveWest = Utility.convertChar(p.getProperty("left"));
			this.moveEast = Utility.convertChar(p.getProperty("right"));
			this.swordAttack = Utility.convertChar(p.getProperty("slash"));
			this.arrowAttack = Utility.convertChar(p.getProperty("arrow"));
			this.openMap = Utility.convertChar(p.getProperty("map"));
	    }
	    catch (Exception e) {
	      System.out.println(e);
	    }
	}
	
	public static int getKey(String key) {
		if(singleton == null) {
			singleton = new Utility();
		}
		return singleton._getKey(key);
	}
	
	private int _getKey(String key) {
		switch(key.toLowerCase()) {
		case "up": return this.moveNorth;
		case "down": return this.moveSouth;
		case "left": return this.moveWest;
		case "right": return this.moveEast;
		case "slash": return this.swordAttack;
		case "arrow": return this.arrowAttack;
		case "map": return this.openMap;
		case "run": return this.runKey;
		default: return 28;
		}
	}
	
	public static int convertChar(String key) {
		switch(key.toUpperCase()) {
			case "KEY_1": return 2;
			case "KEY_2": return 3;
			case "KEY_3": return 4;
			case "KEY_4": return 5;
			case "KEY_5": return 6;
			case "KEY_6": return 7;
			case "KEY_7": return 8;
			case "KEY_8": return 9;
			case "KEY_9": return 10;
			case "KEY_0": return 11;
			case "KEY_MINUS": return 12;
			case "KEY_EQUALS": return 13;
			case "KEY_BACK": return 14;
			case "KEY_TAB": return 15;
			case "KEY_Q": return 16;
			case "KEY_W": return 17;
			case "KEY_E": return 18;
			case "KEY_R": return 19;
			case "KEY_T": return 20;
			case "KEY_Y": return 21;
			case "KEY_U": return 22;
			case "KEY_I": return 23;
			case "KEY_O": return 24;
			case "KEY_P": return 25;
			case "KEY_LBRACKET": return 26;
			case "KEY_RBRACKET": return 27;
			case "KEY_RETURN": return 28;
			case "KEY_ENTER": return 28;
			case "KEY_LCONTROL": return 29;
			case "KEY_A": return 30;
			case "KEY_S": return 31;
			case "KEY_D": return 32;
			case "KEY_F": return 33;
			case "KEY_G": return 34;
			case "KEY_H": return 35;
			case "KEY_J": return 36;
			case "KEY_K": return 37;
			case "KEY_L": return 38;
			case "KEY_SEMICOLON": return 39;
			case "KEY_APOSTROPHE": return 40;
			case "KEY_GRAVE": return 41;
			case "KEY_LSHIFT": return 42;
			case "KEY_BACKSLASH": return 43;
			case "KEY_Z": return 44;
			case "KEY_X": return 45;
			case "KEY_C": return 46;
			case "KEY_V": return 47;
			case "KEY_B": return 48;
			case "KEY_N": return 49;
			case "KEY_M": return 50;
			case "KEY_COMMA": return 51;
			case "KEY_PERIOD": return 52;
			case "KEY_SLASH": return 53;
			case "KEY_RSHIFT": return 54;
			case "KEY_MULTIPLY": return 55;
			case "KEY_LMENU": return 56;
			case "KEY_SPACE": return 57;
			case "KEY_CAPITAL": return 58;
			case "KEY_F1": return 59;
			case "KEY_F2": return 60;
			case "KEY_F3": return 61;
			case "KEY_F4": return 62;
			case "KEY_F5": return 63;
			case "KEY_F6": return 64;
			case "KEY_F7": return 65;
			case "KEY_F8": return 66;
			case "KEY_F9": return 67;
			case "KEY_F10": return 68;
			case "KEY_NUMLOCK": return 69;
			case "KEY_SCROLL": return 70;
			case "KEY_NUMPAD7": return 71;
			case "KEY_NUMPAD8": return 72;
			case "KEY_NUMPAD9": return 73;
			case "KEY_SUBTRACT": return 74;
			case "KEY_NUMPAD4": return 75;
			case "KEY_NUMPAD5": return 76;
			case "KEY_NUMPAD6": return 77;
			case "KEY_ADD": return 78;
			case "KEY_NUMPAD1": return 79;
			case "KEY_NUMPAD2": return 80;
			case "KEY_NUMPAD3": return 81;
			case "KEY_NUMPAD0": return 82;
			case "KEY_DECIMAL": return 83;
			case "KEY_F11": return 87;
			case "KEY_F12": return 88;
			case "KEY_F13": return 100;
			case "KEY_F14": return 101;
			case "KEY_F15": return 102;
			case "KEY_KANA": return 112;
			case "KEY_CONVERT": return 121;
			case "KEY_NOCONVERT": return 123;
			case "KEY_YEN": return 125;
			case "KEY_NUMPADEQUALS": return 141;
			case "KEY_CIRCUMFLEX": return 144;
			case "KEY_AT": return 145;
			case "KEY_COLON": return 146;
			case "KEY_UNDERLINE": return 147;
			case "KEY_KANJI": return 148;
			case "KEY_STOP": return 149;
			case "KEY_AX": return 150;
			case "KEY_UNLABELED": return 151;
			case "KEY_NUMPADENTER": return 156;
			case "KEY_RCONTROL": return 157;
			case "KEY_NUMPADCOMMA": return 179;
			case "KEY_DIVIDE": return 181;
			case "KEY_SYSRQ": return 183;
			case "KEY_RMENU": return 184;
			case "KEY_PAUSE": return 197;
			case "KEY_HOME": return 199;
			case "KEY_UP": return 200;
			case "KEY_PRIOR": return 201;
			case "KEY_LEFT": return 203;
			case "KEY_RIGHT": return 205;
			case "KEY_END": return 207;
			case "KEY_DOWN": return 208;
			case "KEY_NEXT": return 209;
			case "KEY_INSERT": return 210;
			case "KEY_DELETE": return 211;
			case "KEY_LWIN": return 219;
			case "KEY_RWIN": return 220;
			case "KEY_APPS": return 221;
			case "KEY_POWER": return 222;
			case "KEY_SLEEP": return 223;
			case "KEY_LALT": return 56;
			case "KEY_RALT": return 184;
			default: throw new RuntimeException("Input Key not found");
		}
	}
}
