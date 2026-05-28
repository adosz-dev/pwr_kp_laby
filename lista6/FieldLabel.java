import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;

/**
 * Label opisujący parametry w menu
 */
public class FieldLabel extends Label {
  /**
   * @param text tekst wyświetlany w etykiecie
   */
  public FieldLabel(String text){
    super(text);
    styleLabel();
  }

  private void styleLabel(){
    this.setFont(Font.font("SansSerif", 14));
    this.setTextFill(Color.BLACK);
  }
}
