import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.Node;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class SaveLoad {
  public static void save(List<Node> shapes, File file) throws IOException {
    // StringBuilder pozwala na budowanie Stringów
    // bez ponownego ich tworzenia za każdym
    // razem kiedy je edytujemy
    StringBuilder sb = new StringBuilder();
    for (var shape : shapes) {
      if (shape instanceof Circle c) {
        sb.append("circle")
          .append(",").append(c.getCenterX())
          .append(",").append(c.getCenterY())
          .append(",").append(c.getRadius())
          .append(",").append(c.getFill())
          .append(",").append(c.getRotate())
          // biorę jedną skalę, bo druga jest taka sama
          .append(",").append(c.getScaleX())
          .append("\n");
        }
      else if (shape instanceof Rectangle r) {
        sb.append("rectangle")
          .append(",").append(r.getX())
          .append(",").append(r.getY())
          .append(",").append(r.getWidth())
          .append(",").append(r.getHeight())
          .append(",").append(r.getFill())
          .append(",").append(r.getRotate())
          .append(",").append(r.getScaleX())
          .append("\n");
        } 
      else if (shape instanceof Polygon p) {
        sb.append("polygon");
        for (double pt : p.getPoints()) {
            sb.append(",").append(pt);
        }
        sb.append(";").append(p.getFill())
          .append(";").append(p.getRotate())
          .append(";").append(p.getScaleX())
          .append("\n");
      }
    }

    Files.writeString(file.toPath(), sb.toString());
}

public static List<Shape> load(File file) throws IOException {
    List<Shape> shapes = new ArrayList<>();

    for (String line : Files.readAllLines(file.toPath())) {
        if (line.isBlank()) continue;

        if (line.startsWith("circle")) {
            String[] p = line.split(",");
            Circle c = new Circle(
                Double.parseDouble(p[1]),
                Double.parseDouble(p[2]),
                Double.parseDouble(p[3])
            );
            c.setFill(Color.web(p[4]));
            c.setRotate(Double.parseDouble(p[5]));
            c.setScaleX(Double.parseDouble(p[6]));
            c.setScaleY(Double.parseDouble(p[6]));
            c.setStroke(Color.BLACK);
            shapes.add(c);

        } else if (line.startsWith("rectangle")) {
            String[] p = line.split(",");
            Rectangle r = new Rectangle(
                Double.parseDouble(p[1]),
                Double.parseDouble(p[2]),
                Double.parseDouble(p[3]),
                Double.parseDouble(p[4])
            );
            r.setFill(Color.web(p[5]));
            r.setRotate(Double.parseDouble(p[6]));
            r.setScaleX(Double.parseDouble(p[7]));
            r.setScaleY(Double.parseDouble(p[7]));
            r.setStroke(Color.BLACK);
            shapes.add(r);

        } else if (line.startsWith("polygon")) {
            // polygon używa ";" do oddzielenia punktów od reszty
            String[] parts = line.split(";");
            String[] coords = parts[0].split(",");
            Polygon polygon = new Polygon();
            for (int i = 1; i < coords.length; i++) {
                polygon.getPoints().add(Double.parseDouble(coords[i]));
            }
            polygon.setFill(Color.web(parts[1]));
            polygon.setRotate(Double.parseDouble(parts[2]));
            polygon.setScaleX(Double.parseDouble(parts[3]));
            polygon.setScaleY(Double.parseDouble(parts[3]));
            polygon.setStroke(Color.BLACK);
            shapes.add(polygon);
        }
    }

    return shapes;
  }
}
