package xu.gensheng.ShutTheBox;

public class Tile {
	private int value;
	private boolean isDown;
	private boolean selected;
	
	
	public Tile(int v) {
		value = v;
		isDown = false;
		selected = false;
	}
	
	public void shut() {
		selected = false;
		isDown = true;
	}
	public void reset() {

		isDown = false;
	}
	
	public boolean isDown() {
		return isDown;
	}
	
	@Override
	public String toString() {
		String state = "";
		if (isDown) {
			state = "D";
		}
		else {
			state = "U";
		}
		return "" + value + ":" + state;
	}

	public void select() {
		selected = true;
	}
	
	public void deselected() {
		selected = false;
	}

	public boolean isSelected() {
		return selected;
	}

	

	public int getValue() {
		return value;
	}
	
}
