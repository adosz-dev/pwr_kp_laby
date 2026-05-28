import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;

/**
 * Label kategorii inputów
 */
public class CategoryLabel extends Label {
  /**
   * @param text tekst wyświetlany w etykiecie
   */
  public CategoryLabel(String text){
    super(text);
    styleLabel();
  }

  private void styleLabel(){
    this.setFont(Font.font("SansSerif", 18));
    this.setTextFill(Color.BLACK);
    this.setAlignment(Pos.CENTER);
    this.setMaxWidth(Double.MAX_VALUE);
  }
}
