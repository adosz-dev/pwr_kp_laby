import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;


/**
 * Klasa przycisku używanego w pasku bocznym
 * Rozszerza klasę Button z JavaFX
 */
public class SideBarButton extends Button{
  public SideBarButton(String text){

    super(text);
    this.setFont(Font.font("SansSerif", FontWeight.BOLD, 18));
    this.setCursor(Cursor.HAND);
    this.setMaxWidth(Double.MAX_VALUE);
    this.setPrefHeight(45);
    this.setTextFill(Color.WHITE);
    this.setBackground(new Background(new BackgroundFill(
            Color.GRAY, new CornerRadii(4), Insets.EMPTY)));

    this.setOnMouseEntered(e -> this.setBackground(new Background(new BackgroundFill(
            Color.rgb(120, 120, 120), new CornerRadii(4), Insets.EMPTY))));
    this.setOnMouseExited(e -> this.setBackground(new Background(new BackgroundFill(
            Color.GRAY, new CornerRadii(4), Insets.EMPTY))));

  }
}
