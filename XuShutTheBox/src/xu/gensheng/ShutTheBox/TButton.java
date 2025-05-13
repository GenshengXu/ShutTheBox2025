package xu.gensheng.ShutTheBox;
import javafx.scene.control.Button;

public class TButton extends Button {
	
	private int value;

	TButton (int value) {
			super(String.valueOf(value));
			this.value = value;
			setStyle("-fx-background-color: #0000ff;");
			setStyle("-fx-background-color: #ff0000");
	}
	public TButton() {
		
	}
	public int getValue() {
		return value;
	}
	public int getState() {
		return getState();
	}
	
}

