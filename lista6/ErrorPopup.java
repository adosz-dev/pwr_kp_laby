import javafx.stage.Popup;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

/**
 * Klasa popupu w przypadku błędu przy generowaniu planszy
 */
public class ErrorPopup extends Popup {
  public ErrorPopup(String errorInfo) {
    super();

    setAutoHide(true);

    VBox content = new VBox();
    content.setBackground(new Background(new BackgroundFill(
            Color.GRAY, new CornerRadii(4), Insets.EMPTY)));
    content.setPadding(new Insets(24,30,24,30));
    content.setAlignment(Pos.CENTER_LEFT);
    Label title = new Label("Wystąpił błąd!");
    title.setFont(Font.font("SansSerif", FontWeight.BOLD, 22));
    title.setTextFill(Color.RED);

    Label errorMsg = new Label(errorInfo);
    errorMsg.setFont(Font.font("SansSerif", 18));
    errorMsg.setTextFill(Color.WHITE);
    errorMsg.setWrapText(true);

    content.getChildren().addAll(title, errorMsg);
    this.getContent().add(content);
  }
}
