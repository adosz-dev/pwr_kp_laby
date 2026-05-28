import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;

public class FieldLabel extends Label {
  public FieldLabel(String text){
    super(text);
    styleLabel();
  }
  private void styleLabel(){
    this.setFont(Font.font("SansSerif", 14));
    this.setTextFill(Color.BLACK);
  }
}
