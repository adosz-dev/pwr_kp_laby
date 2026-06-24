import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class TreeGui extends Application {
  private static final String HOST = "localhost";
  private static final int PORT = 8000;

  private Socket socket;
  private BufferedReader in;
  private PrintWriter out;
  private final Object lock = new Object();

  @Override
  public void start(Stage stage) {
    showTypeSelection(stage);
  }

  private void showTypeSelection(Stage stage) {
    Label title = new Label("BST Server Client");
    title.setFont(Font.font(null, FontWeight.BOLD, 22));

    ToggleGroup group = new ToggleGroup();
    RadioButton rbInteger = new RadioButton("Integer");
    RadioButton rbDouble = new RadioButton("Double");
    RadioButton rbString = new RadioButton("String");
    rbInteger.setToggleGroup(group);
    rbDouble.setToggleGroup(group);
    rbString.setToggleGroup(group);
    rbInteger.setSelected(true);

    VBox radioBox = new VBox(10, rbInteger, rbDouble, rbString);
    radioBox.setAlignment(Pos.CENTER_LEFT);

    Button runBtn = new Button("Połącz");
    runBtn.setFont(Font.font(14));
    runBtn.setPadding(new Insets(6, 20, 6, 20));

    Label statusLabel = new Label();
    statusLabel.setTextFill(Color.RED);

    runBtn.setOnAction(e -> {
      RadioButton selected = (RadioButton) group.getSelectedToggle();
      String type = selected.getText();
      try {
        socket = new Socket(HOST, PORT);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
        out.println("type " + type);
        showMainScreen(stage, type);
      } catch (IOException ex) {
        statusLabel.setText("Błąd połączenia: " + ex.getMessage());
      }
    });

    VBox root = new VBox(20, title, radioBox, runBtn, statusLabel);
    root.setAlignment(Pos.CENTER);
    root.setPadding(new Insets(50));

    stage.setTitle("TreeGui");
    stage.setScene(new Scene(root, 320, 280));
    stage.show();
  }

  private void showMainScreen(Stage stage, String type) {
    TextField inputField = new TextField();
    inputField.setPromptText("wartość");
    inputField.setPrefWidth(160);

    Button insertBtn = new Button("Insert");
    Button removeBtn = new Button("Remove");
    Button searchBtn = new Button("Search");
    Button drawBtn = new Button("Draw");

    Label resultLabel = new Label(" ");
    resultLabel.setFont(Font.font(13));

    HBox controls = new HBox(8, inputField, insertBtn, removeBtn, searchBtn, drawBtn);
    controls.setAlignment(Pos.CENTER_LEFT);
    controls.setPadding(new Insets(10));

    VBox treeArea = new VBox();

    insertBtn.setOnAction(e -> {
      String val = inputField.getText().trim();
      if (val.isEmpty()) return;
      runOp(() -> {
        sendCmd("insert " + val);
        readLine(); // OK
        requestDraw(treeArea);
        Platform.runLater(() -> resultLabel.setText("Wstawiono: " + val));
      });
    });

    removeBtn.setOnAction(e -> {
      String val = inputField.getText().trim();
      if (val.isEmpty()) return;
      runOp(() -> {
        sendCmd("remove " + val);
        readLine(); // OK
        requestDraw(treeArea);
        Platform.runLater(() -> resultLabel.setText("Usunięto: " + val));
      });
    });

    searchBtn.setOnAction(e -> {
      String val = inputField.getText().trim();
      if (val.isEmpty()) return;
      runOp(() -> {
        sendCmd("search " + val);
        String resp = readLine();
        Platform.runLater(() -> {
          if ("true".equals(resp)){
            resultLabel.setText("Znaleziono "+ val);
          }
          else {
            resultLabel.setText("Nie znaleziono "+ val);
          }
        });
      });
    });

    drawBtn.setOnAction(e -> runOp(() -> requestDraw(treeArea)));

    VBox root = new VBox(0, controls, resultLabel, treeArea);
    root.setPadding(new Insets(5));

    stage.setTitle("Tree Client — " + type);
    stage.setScene(new Scene(root, 650, 500));
  }

  private void runOp(Runnable op) {
    new Thread(() -> {
      synchronized (lock) {
        try {
          op.run();
        } catch (Exception ex) {
          System.out.println("Błąd "+ ex.getMessage());
        }
      }
    }).start();
  }

  private void sendCmd(String cmd) {
    out.println(cmd);
  }

  private String readLine() {
    try {
      return in.readLine();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private void requestDraw(VBox treeArea) {
    sendCmd("draw");
    String line;
    int height = 0;
    if ((line = readLine()) != null && "DRAW_METADATA".equals(line)) {
      if ((line = readLine()) != null) {
        try {
          height = Integer.parseInt(line);
        } catch (NumberFormatException e) {
          return;
        }
      }
    }
    if (height == 0) return;

    List<String[]> levels = new ArrayList<>();
    boolean reading = false;
    while ((line = readLine()) != null) {
      if ("DRAW_START".equals(line)) { reading = true; continue; }
      if ("DRAW_END".equals(line)) break;
      if (reading) levels.add(line.trim().split("\\s+"));
    }

    final int h = height;
    final double nodeD = 44;
    final double minGap = 8;
    Platform.runLater(() -> {
      treeArea.getChildren().clear();
      for (int i=0; i<levels.size(); i++) {
        String[] level = levels.get(i);
        double spacing = (nodeD + minGap) * Math.pow(2, h - 1 - i) - nodeD;
        HBox row = new HBox(Math.max(spacing, minGap));
        row.setAlignment(Pos.CENTER);
        row.setPadding(new Insets(6, 0, 6, 0));
        for (String val : level) {
          if ("*".equals(val)) {
            Circle phantom = new Circle(22);
            phantom.setFill(Color.TRANSPARENT);
            phantom.setStroke(Color.TRANSPARENT);
            row.getChildren().add(phantom);
          } else {
            Circle c = new Circle(22);
            c.setFill(Color.LIGHTSTEELBLUE);
            c.setStroke(Color.STEELBLUE);
            c.setStrokeWidth(2);
            Text t = new Text(val);
            t.setFont(Font.font(12));
            StackPane sp = new StackPane(c, t);
            row.getChildren().add(sp);
          }
        }
        treeArea.getChildren().add(row);
      }
    });
  }

  @Override
  public void stop() throws Exception {
    if (socket != null && !socket.isClosed()) {
      out.println("quit");
      socket.close();
    }
  }

  public static void main(String[] args) {
    launch(args);
  }
}
