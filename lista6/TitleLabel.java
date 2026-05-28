import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.FontPosture;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;

public class TitleLabel extends Label {
  public TitleLabel(String text){
    super(text);
    styleLabel();
  }
  private void styleLabel(){
    this.setFont(Font.font("SansSerif", FontWeight.BOLD, FontPosture.ITALIC, 24));
    this.setTextFill(Color.BLACK);
    this.setAlignment(Pos.CENTER);
    this.setMaxWidth(Double.MAX_VALUE);
  }
}
