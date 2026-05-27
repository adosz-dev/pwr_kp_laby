import javafx.application.Application;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
* Program pokazujący planszę ze zmieniającymi kolorami
* @author Adam Latos-Ważny
*/
public class Simulation extends Application {
  @Override
  public void start(Stage mainStage){
    TitleLabel appName = new TitleLabel("Color Simulation");

    Label sizesLabel = new Label("Rozmiary tabeli:");

    Label nLabel = new Label("n:");
    TextField nInput = new TextField();
    Label mLabel = new Label("m:");
    TextField mInput = new TextField();

    Label other = new Label("Inne parametry:");
    Label kLabel = new Label("Szybkość działania");
    TextField kInput = new TextField();
    Label pLabel = new Label("Prawdopodobieństwo zmiany koloru");
    TextField pInput = new TextField();
    
    Button showButton = new Button("Start");

    showButton.setOnAction(e -> {
      try {
        int n = Integer.parseInt(nInput.getText());
        int m = Integer.parseInt(mInput.getText());
        int k = Integer.parseInt(kInput.getText());
        double p = Double.parseDouble(pInput.getText());
        Board board = new Board(n, m, k, p);
        Stage boardStage = new Stage();
        boardStage.setScene(new Scene(board, 600, 600));
        boardStage.setTitle("Plansza (n x m)");
        boardStage.setOnCloseRequest(event -> {
          board.setInactive();
        });
        boardStage.show();
      }
      catch (NumberFormatException ex){
        Popup errorPopup = new ErrorPopup("Nieprawidłowy format liczby");
        errorPopup.show(mainStage);
      }
    });

    VBox mainLayout = new VBox();
    mainLayout.getChildren().addAll(
        appName,
        new Separator(),
        sizesLabel,
        nLabel,
        nInput,
        mLabel,
        mInput,
        new Separator(),
        other,
        kLabel,
        kInput,
        pLabel,
        pInput,
        showButton
    );

    Scene mainScene = new Scene(mainLayout, 500, 400);
    mainStage.setTitle("Color Simulation");
    mainStage.setScene(mainScene);
    mainStage.show();

  }
  public static void main(String[] args){
    launch(args);
  }
}
