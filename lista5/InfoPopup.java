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

public class InfoPopup extends Popup {
  public InfoPopup() {
    super();
    setAutoHide(true);

    VBox content = new VBox();
    content.setBackground(new Background(new BackgroundFill(
            Color.WHITE, new CornerRadii(4), Insets.EMPTY)));
    content.setPadding(new Insets(24,30,24,30));
    content.setAlignment(Pos.CENTER_LEFT);
    Label title = new Label("Figures");
    title.setFont(Font.font("SansSerif", FontWeight.BOLD, 22));
    Label purpose = new Label("Rysowanie i edytowanie figur geometrycznych");
    purpose.setFont(Font.font("SansSerif", 18));
    Label name = new Label("Adam Latos-Ważny");
    name.setFont(Font.font("SansSerif", 18));

    content.getChildren().addAll(title, purpose, name);
    this.getContent().add(content);
  }
}
