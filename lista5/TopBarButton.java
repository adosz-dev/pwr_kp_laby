import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;

public class TopBarButton extends Button {
  public TopBarButton(String text){
    super(text);
    this.setFont(Font.font("SansSerif", 15));
    this.setCursor(Cursor.HAND);
  }
}
