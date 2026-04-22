import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.HPos;

// class WindowAdapterMain extends WindowAdapter {
//   public void windowClosing(WindowEvent e){
//     System.exit(0);
//   }
// }
//
// class WindowAdapterSub extends WindowAdapter {
//   Frame fr;
//   WindowAdapterSub(Frame fr){
//     this.fr = fr;
//   }
//   public void windowClosing(WindowEvent e){
//     this.fr.dispose();
//   }
// }
//
// class OkayButtonListener implements ActionListener {
//   Frame fr;
//   OkayButtonListener(Frame fr){
//     this.fr = fr;
//   }
//   public void actionPerformed(ActionEvent e){
//     fr.dispose();
//   }
// }
//
class ErrorMessage {
  String input;
  private void CreateErrorMessageWindow(){
    Stage error_stage = new Stage();
    error_stage.setTitle("Trójkąt Pascala - Błąd");
    GridPane grid = new GridPane();
    grid.setVgap(40);
    grid.setHgap(10);
    grid.setAlignment(Pos.CENTER);
    grid.setPadding(new Insets(10, 10, 10, 10));
    Label lab = new Label("Wystąpił błąd");
    lab.setFont(Font.font("Arial", FontWeight.BOLD, 30));
    Label lab_data = new Label(this.input);
    lab_data.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
    Button okay_button = new Button("OK");
    GridPane.setHalignment(okay_button, HPos.CENTER);
    okay_button.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
    okay_button.setOnAction(new EventHandler<ActionEvent>(){
      @Override
      public void handle(ActionEvent event){
        error_stage.close();
      }
    });
    grid.add(lab, 1, 0);
    grid.add(lab_data, 1, 1);
    grid.add(okay_button, 1, 2);
    error_stage.setScene(new Scene(grid, 300, 200));
    error_stage.show();
  }
  ErrorMessage(String input){
    this.input = input;
    CreateErrorMessageWindow();
  }
}
//
// class GenButtonListener implements ActionListener {
//   int n;
//   TextField input;
//   GenButtonListener(TextField input){
//     this.input = input;
//   }
//   public void actionPerformed(ActionEvent e) {
//     Frame fr = new Frame("Trójkąt Pascala - Wygenerowany");
//     try {
//       n = Integer.parseInt(this.input.getText());
//     }
//     catch (NumberFormatException ex){
//       System.out.println("to nie liczba");
//       ErrorMessage m_err = new ErrorMessage();
//       m_err.message(this.input.getText());
//       return;
//     }
//     try {
//       PascalsTriangle triangle = new PascalsTriangle(this.n);
//       fr.setLayout(new GridLayout(n+1, 1));
//       for (int i=0; i<=n; i++){
//         Label lab = new Label(triangle.GetRow(i), Label.CENTER);
//         lab.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 13));
//         fr.add(lab);
//       }
//       fr.addWindowListener(new WindowAdapterSub(fr));
//       fr.setMinimumSize(new Dimension(200,200));
//       fr.pack();
//       fr.setVisible(true);
//     }
//     catch (PascalsTriangle.RowNumberException ex){
//       System.out.println("Błędna wartość wielkości");
//       ErrorMessage m_err = new ErrorMessage();
//       m_err.message(this.input.getText());
//       return;
//     }
//   }
// }
class TriangleWindow {
  String input;
  int n;
  private void CreateWindow() {
    Stage triangle_stage = new Stage();
    triangle_stage.setTitle("Trójkąt Pascala - wygenerowany");
    try {
      this.n = Integer.parseInt(this.input);
    }
    catch (NumberFormatException ex){
      System.out.println("to nie liczba");
      ErrorMessage m_err = new ErrorMessage(input);
      return;
    }
    try {
      PascalsTriangle triangle = new PascalsTriangle(n);
      GridPane grid = new GridPane();
      for (int i=0; i<=n; i++){
        Label lab = new Label(triangle.GetRow(i));
        lab.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
        GridPane.setHalignment(lab, HPos.CENTER);
        grid.add(lab, 1, i);
      }
      grid.setAlignment(Pos.CENTER);
      grid.setPadding(new Insets(10, 10, 10, 10));
      triangle_stage.setScene(new Scene(grid));
      triangle_stage.setMinWidth(200);
      triangle_stage.setMinHeight(200);
      triangle_stage.sizeToScene();
      triangle_stage.show();
    }
    catch (PascalsTriangle.RowNumberException ex){
      System.out.println("Błędna wartość wielkości");
      ErrorMessage m_err = new ErrorMessage(input);
      return;
    }
  }
  TriangleWindow(String input){
    this.input = input;
    CreateWindow();
  }
}

public class PascalsTriangleGUI extends Application{
  public static void main(String[] args){
    launch(args);
  }
  @Override
  public void start(Stage primaryStage){
    primaryStage.setTitle("Trójkąt Pascala");
    GridPane grid = new GridPane();
    grid.setAlignment(Pos.CENTER);
    grid.setVgap(40);
    grid.setHgap(10);
    grid.setPadding(new Insets(10, 10, 10, 10));
    Label lab_gen = new Label("Wygeneruj trójkąt Pascala");
    lab_gen.setFont(Font.font("Arial", FontWeight.BOLD, 30));
    TextField size_input = new TextField();
    Button gen_button = new Button("Wygeneruj");
    gen_button.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
    primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>(){
      @Override
      public void handle(WindowEvent event){
        System.exit(0);
      }
    });
    gen_button.setOnAction(new EventHandler<ActionEvent>(){
      @Override
      public void handle(ActionEvent event){
        TriangleWindow window = new TriangleWindow(size_input.getText());
      }
    });
    GridPane.setHalignment(gen_button, HPos.CENTER);
    grid.add(lab_gen, 1, 0);
    grid.add(size_input, 1, 1);
    grid.add(gen_button, 1, 2);
    primaryStage.setScene(new Scene(grid, 500, 400));
    primaryStage.show();
  }
}
