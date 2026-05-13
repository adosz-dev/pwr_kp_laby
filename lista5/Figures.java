import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.util.ArrayList;
 
public class Figures extends Application {
  public ShapeType shape;
  private DrawingPane drawing_pane;

  @Override
  public void start(Stage main_stage) {
      main_stage.setTitle("new document - Figures");
      BorderPane main_layout = new BorderPane();
      main_layout.setTop(buildTopBar());
      main_layout.setLeft(buildSideBar());

      drawing_pane = new DrawingPane();
      main_layout.setCenter(drawing_pane);

      Scene scene = new Scene(main_layout, 1000, 800);
      main_stage.setScene(scene);

      main_stage.show();
  }
  private HBox buildTopBar() {
      HBox bar = new HBox(10);
      bar.setPadding(new Insets(15, 20, 15, 10));
      bar.setAlignment(Pos.CENTER_RIGHT);
      bar.setBackground(new Background(new BackgroundFill(Color.rgb(71,71,71),
              CornerRadii.EMPTY, Insets.EMPTY)));
      bar.setBorder(new Border(new BorderStroke(Color.rgb(67,67,67),
              BorderStrokeStyle.SOLID,
              CornerRadii.EMPTY,
              new BorderWidths(0,0,2,0))));

      Button save = new TopBarButton("save");
      Button load = new TopBarButton("load");


      bar.getChildren().addAll(save, load);
      return bar;
  }

  private VBox buildSideBar() {
      VBox bar = new VBox(8);
      bar.setBackground(new Background(new BackgroundFill(
              Color.rgb(71, 71, 71), CornerRadii.EMPTY, Insets.EMPTY)));
      bar.setPadding(new Insets(15, 8, 8, 8));
      bar.setPrefWidth(130);
      bar.setMinWidth(130);

      Button rectangle = new SideBarButton("Rectangle");
      rectangle.setOnAction(e -> {
        this.drawing_pane.setShape(ShapeType.RECTANGLE);
        this.drawing_pane.setTool(Tool.CREATE);
        this.drawing_pane.setCursor(Cursor.CROSSHAIR);
      });

      Button circle = new SideBarButton("Circle");
      circle.setOnAction(e -> {
        this.drawing_pane.setShape(ShapeType.CIRCLE);
        this.drawing_pane.setTool(Tool.CREATE);
        this.drawing_pane.setCursor(Cursor.CROSSHAIR);
      });

      Button polygon = new SideBarButton("Polygon");
      polygon.setOnAction(e -> {
        this.drawing_pane.setShape(ShapeType.POLYGON);
        this.drawing_pane.setTool(Tool.CREATE);
        this.drawing_pane.setCursor(Cursor.CROSSHAIR);
      });

      Region spacer = new Region();
      VBox.setVgrow(spacer, Priority.ALWAYS);

      bar.getChildren().addAll(rectangle, circle, polygon, spacer);
      return bar;
  }

  public static void main(String[] args) {
      launch(args);
  }
}

