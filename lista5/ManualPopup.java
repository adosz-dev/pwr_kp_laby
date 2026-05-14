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

public class ManualPopup extends Popup {
  public ManualPopup() {
    super();
    setAutoHide(true);

    VBox content = new VBox();
    content.setMaxWidth(500);
    content.setBackground(new Background(new BackgroundFill(
            Color.WHITE, new CornerRadii(4), Insets.EMPTY)));
    content.setPadding(new Insets(24,30,24,30));
    content.setAlignment(Pos.CENTER_LEFT);
    Label title = new Label("Figures - Instrukcja");
    title.setFont(Font.font("SansSerif", FontWeight.BOLD, 22));
    Label generalLabel = new Label("Rysowanie i edytowanie figur geometrycznych");
    generalLabel.setFont(Font.font("SansSerif", FontWeight.BOLD, 18));

    Label general = new Label("Aby stworzyć figurę, wybierz jedną "
        + "z możliwych figur po prawej stronie: okrąg (circle), "
        + "prostokąt (rectangle) lub wielokąt (polygon)");
    general.setFont(Font.font("SansSerif", 14));
    general.setWrapText(true);

    Label simpleDrawingLabel = new Label("Rysowanie okręgu i prostokąta");
    simpleDrawingLabel.setFont(Font.font("SansSerif", FontWeight.BOLD, 16));
    Label simpleDrawing = new Label("Aby narysować okrąg najpierw kliknij"
        + " myszką w miejsce środka okręgu a następnie w miejsce, odległe"
        + " o promień, który chcesz uzyskać. Aby narysować prostokąt kliknij"
        + " w punkt pierwszego wierzchołka, a następnie wierzchołka przeciwnego.");
    simpleDrawing.setFont(Font.font("SansSerif", 14));
    simpleDrawing.setWrapText(true);

    Label polygonLabel = new Label("Rysowanie wielokąta");
    polygonLabel.setFont(Font.font("SansSerif", FontWeight.BOLD, 16));
    Label polygonDrawing = new Label("Aby narysować wielokąt, klikaj kolejno "
        + "w punkty wierzchołków. Ostatni wierzchołek wyznacz "
        + "podwójnym kliknięciem");
    polygonDrawing.setFont(Font.font("SansSerif", 14));
    polygonDrawing.setWrapText(true);

    Label editLabel = new Label("Edytowanie figur");
    editLabel.setFont(Font.font("SansSerif", FontWeight.BOLD, 16));
    Label editInfo = new Label("Aby przesunąć, zmieniać wielkość lub obracać"
        + " figurę należy ją najpierw zaznaczyć podwójnym kliknięciem."
        + " Naciśnij i przytrzymaj lewy przycisk myszy jednocześnie "
        + " ruszając myszką, aby zmienić położenie figury."
        + " Scroll w górę powoduje powiększenie figury, a w dół - pomniejszenie."
        + " Klawisz E obraca figurę w prawo, a Q - w lewo");
    editInfo.setFont(Font.font("SansSerif", 14));
    editInfo.setWrapText(true);

    content.getChildren().addAll(
      title,
      generalLabel, general,
      simpleDrawingLabel, simpleDrawing,
      polygonLabel, polygonDrawing,
      editLabel, editInfo
    );
    this.getContent().add(content);
  }
}
