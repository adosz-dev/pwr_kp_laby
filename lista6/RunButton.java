import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;

/**
 * Przycisk uruchamiający symulację
 */
public class RunButton extends Button {
  /**
   * @param text etykieta przycisku
   */
  public RunButton(String text){
    super(text);
    styleButton();
  }

  private void styleButton(){
    this.setFont(Font.font("SansSerif", 18));
    this.setTextFill(Color.BLACK);
    this.setAlignment(Pos.CENTER);
  }
}
