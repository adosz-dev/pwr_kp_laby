import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.Cursor;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Polygon;
import javafx.scene.input.KeyCode;
import javafx.scene.transform.Rotate;
import java.util.List;

public class DrawingPane extends Pane {
  private boolean isDefining;
  private double x1;
  private double y1;
  
  Object editTarget = null;
  boolean isEditing = false;

  private Tool currentTool = Tool.EDIT;
  private ShapeType currentShape = null;
  private Circle activeCircle;
  private Polygon activePolygon;
  private Rectangle activeRectangle;
  
  private void closeShapeCreation(){
    activeCircle = null; 
    activeRectangle = null;
    activePolygon = null;
    isDefining = false;
    this.currentShape = null;
    this.currentTool = Tool.EDIT;
    this.setCursor(Cursor.DEFAULT);
  }
  private void stopEditing(){
    if (this.editTarget instanceof Shape){
      ((Shape) this.editTarget).setStroke(Color.BLACK);
    }
    if (this.editTarget != null){
      this.editTarget = null;
    }
    this.isEditing = false;
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

  // tworzenie wielokątów
  private void createPolygon(MouseEvent e){
    x1 = e.getX();
    y1 = e.getY();
    if (!isDefining){
      activePolygon = new Polygon();
      activePolygon.getPoints().addAll(x1,y1);
      activePolygon.setFill(Color.TRANSPARENT);
      activePolygon.setStroke(Color.BLACK);
      isDefining = true;
      this.getChildren().add(activePolygon);
    }
    else {
      if (e.getClickCount() == 2){
        activePolygon.getPoints().addAll(x1, y1);
        // usuwam objekt z pane'a, jeżeli nie jest wielokątem
        if (activePolygon.getPoints().size() < 3){
          this.getChildren().remove(activePolygon);
        }
        closeShapeCreation();
      }
      else {
        activePolygon.getPoints().addAll(x1, y1);
      }
    }
  }

  private void createCircle(MouseEvent e){
    if (!isDefining) {
      x1 = e.getX();
      y1 = e.getY();

      activeCircle = new Circle();
      activeCircle.setCenterX(x1);
      activeCircle.setCenterY(y1);
      activeCircle.setRadius(0);
      activeCircle.setFill(Color.TRANSPARENT);
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
      activeRectangle.setFill(Color.TRANSPARENT);
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
          case POLYGON:
            createPolygon(e);
            break;
        }
      }
      // zaznaczanie figury
      else if (currentTool == Tool.EDIT){
        // zaznaczamy podwójnym kliknięciem
        if (e.getClickCount() == 2){
          stopEditing();
          this.editTarget = e.getTarget();
          if (this.editTarget instanceof Shape){
            this.isEditing = true;
            this.setCursor(Cursor.MOVE);
            // javafx przenosi focus na drawingpane
            // dzięki temu możemy używać klawiszy
            this.requestFocus();
          }
          else {
            stopEditing();
          }
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
      else if (isEditing){
        if (this.editTarget == e.getTarget()){
          this.setCursor(Cursor.MOVE);
        }
        else {
          this.setCursor(Cursor.DEFAULT);
        }
      }
    });

    // przemieszczanie figury 
    this.setOnMousePressed(e -> {
      if (isEditing && editTarget == e.getTarget()){
        x1 = e.getX();
        y1 = e.getY();
      }
    });

    this.setOnMouseDragged(e -> {
      if (isEditing && editTarget == e.getTarget()){
        double deltaX = e.getX() - x1;
        double deltaY = e.getY() - y1;
        
        // sprawdzam typy i robię cast
        if (editTarget instanceof Circle c){
          double centerX = c.getCenterX();
          double centerY = c.getCenterY();
          c.setCenterX(centerX + deltaX);
          c.setCenterY(centerY + deltaY);
        }
        else if (editTarget instanceof Rectangle r){
          r.setX(r.getX() + deltaX);
          r.setY(r.getY() + deltaY);
        }
        else if(editTarget instanceof Polygon p){
          List<Double> polygonPoints = p.getPoints();
          for (int i=0; i<polygonPoints.size(); i+=2){
            polygonPoints.set(i, polygonPoints.get(i) + deltaX);
            polygonPoints.set(i+1, polygonPoints.get(i+1) + deltaY);
          }
        }
      }
      x1 = e.getX();
      y1 = e.getY();
    });

    // obracanie figur
    this.setOnKeyPressed(e -> {
      if (isEditing && editTarget instanceof Shape target){
        if (e.getCode() == KeyCode.E){
          target.setRotate(target.getRotate() + 5);
        }
        else if (e.getCode() == KeyCode.Q){
          target.setRotate(target.getRotate() -5);
        }
      }
    });
    // zmiana rozmiaru figur
    this.setOnScroll(e -> {
      if (isEditing && editTarget instanceof Shape target){
        double delta = e.getDeltaY();
        target.setScaleX(target.getScaleX() + delta*0.01);
        target.setScaleY(target.getScaleY() + delta*0.01);
      }
    });
  }

  public DrawingPane() {
    buildDrawingPane();
  }
  public void setTool(Tool tool) {
    this.currentTool = tool;
  }
  public void setShape(ShapeType shape){
    this.currentShape = shape;
  }
}
