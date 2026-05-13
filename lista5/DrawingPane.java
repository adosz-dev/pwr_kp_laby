import javafx.scene.canvas.Canvas;
// import javafx.scene.canvas.GraphicsContent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.Cursor;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;


public class DrawingPane extends Pane {
  private boolean isDefining;
  private double x1;
  private double y1;
  private Tool currentTool = Tool.EDIT;
  private Shape currentShape = null;
  private Circle activeCircle;
  private Rectangle activeRectangle;
  
  private void closeShapeCreation(){
    activeCircle = null; 
    isDefining = false;
    this.currentShape = null;
    this.currentTool = Tool.EDIT;
    this.setCursor(Cursor.DEFAULT);
  }

  private void setClipDrawingPane(){
    // tworzę clip, w którym będziemy rysować
    // dzięki niemu figury nie będą wystawać poza obszar
    Rectangle clip = new Rectangle();
    // powiązuje szerokość clipu z wielkością pane'a
    clip.widthProperty().bind(this.widthProperty());
    clip.heightProperty().bind(this.heightProperty());
    // ustawiam go jako klip do tego pane'a
    this.setClip(clip);
  }
  private void createCircle(MouseEvent e){
    if (!isDefining) {
      x1 = e.getX();
      y1 = e.getY();

      activeCircle = new Circle();
      activeCircle.setCenterX(x1);
      activeCircle.setCenterY(y1);
      activeCircle.setRadius(0);
      activeCircle.setFill(Color.WHITE);
      activeCircle.setStroke(Color.BLACK);

      this.getChildren().add(activeCircle);
        
      isDefining = true;
    }
    else {
      closeShapeCreation();
    }
  }
  private void createRectangle(MouseEvent e){
    if (!isDefining) {
      x1 = e.getX();
      y1 = e.getY();

      activeRectangle = new Rectangle();
      activeRectangle.setX(x1);
      activeRectangle.setY(y1);
      activeRectangle.setWidth(0);
      activeRectangle.setHeight(0);
      activeRectangle.setFill(Color.WHITE);
      activeRectangle.setStroke(Color.BLACK);

      this.getChildren().add(activeRectangle);
      
      isDefining = true;
    }
    else {
      closeShapeCreation();
    }
  }
  private void buildDrawingPane(){
    setClipDrawingPane();

    this.setOnMouseClicked(e -> {
      if (currentTool == Tool.CREATE){
        switch (currentShape){
          case CIRCLE:
            createCircle(e);
            break;
          case RECTANGLE:
            createRectangle(e);
            break;
        }
      }
    });

    this.setOnMouseMoved(e -> {
      if (isDefining){
        double x2 = e.getX();
        double y2 = e.getY();

        if (activeCircle != null){
          // obliczamy promień - Math.hypot oblicza przeciwprostokątną
          double radius = Math.hypot(x2-x1, y2-y1);
          activeCircle.setRadius(radius);
        }
        else if (activeRectangle != null) {
          // prostokąt wymaga ustawienia lewego górnego rogu
          // oraz dodatniej szerokości i wysokości
          activeRectangle.setX(Math.min(x1, x2));
          activeRectangle.setY(Math.min(y1, y2));
          activeRectangle.setWidth(Math.abs(x2 - x1));
          activeRectangle.setHeight(Math.abs(y2 - y1));
        }
      }
    });
  }

  public DrawingPane() {
    buildDrawingPane();
  }
  public void setTool(Tool tool) {
    this.currentTool = tool;
  }
  public void setShape(Shape shape){
    this.currentShape = shape;
  }
}
