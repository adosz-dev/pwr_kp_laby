import java.awt.*;
import java.awt.event.*;

class WindowAdapterMain extends WindowAdapter {
  public void windowClosing(WindowEvent e){
    System.exit(0);
  }
}

class WindowAdapterSub extends WindowAdapter {
  Frame fr;
  WindowAdapterSub(Frame fr){
    this.fr = fr;
  }
  public void windowClosing(WindowEvent e){
    this.fr.dispose();
  }
}

class OkayButtonListener implements ActionListener {
  Frame fr;
  OkayButtonListener(Frame fr){
    this.fr = fr;
  }
  public void actionPerformed(ActionEvent e){
    fr.dispose();
  }
}

class ErrorMessage {
  void message(String input){
    Frame error_fr = new Frame("Trójkąt pascala - Błąd");
    error_fr.setLayout(new GridLayout(2, 1));
    Panel label_panel = new Panel(new GridLayout(2, 1));
    Label error_lab = new Label("Wystąpił błąd");
    error_lab.setFont(new Font(Font.SERIF, Font.PLAIN, 18));
    Label data_lab = new Label("Dane:" + input);
    data_lab.setFont(new Font(Font.SERIF, Font.PLAIN, 18));
    Button okay_button = new Button("OK");
    okay_button.addActionListener(new OkayButtonListener(error_fr));
    okay_button.setFont(new Font(Font.SERIF, Font.PLAIN, 18));
    error_fr.addWindowListener(new WindowAdapterSub(error_fr));
    label_panel.add(error_lab);
    label_panel.add(data_lab);
    error_fr.add(label_panel);
    error_fr.add(okay_button);
    error_fr.setMinimumSize(new Dimension(200, 200));
    error_fr.pack();
    error_fr.setVisible(true);
  }
}

class GenButtonListener implements ActionListener {
  int n;
  TextField input;
  GenButtonListener(TextField input){
    this.input = input;
  }
  public void actionPerformed(ActionEvent e) {
    Frame fr = new Frame("Trójkąt Pascala - Wygenerowany");
    try {
      n = Integer.parseInt(this.input.getText());
    }
    catch (NumberFormatException ex){
      System.out.println("to nie liczba");
      ErrorMessage m_err = new ErrorMessage();
      m_err.message(this.input.getText());
      return;
    }
    try {
      PascalsTriangle triangle = new PascalsTriangle(this.n);
      fr.setLayout(new GridLayout(n+1, 1));
      for (int i=0; i<=n; i++){
        Label lab = new Label(triangle.GetRow(i), Label.CENTER);
        lab.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 13));
        fr.add(lab);
      }
      fr.addWindowListener(new WindowAdapterSub(fr));
      fr.setMinimumSize(new Dimension(200,200));
      fr.pack();
      fr.setVisible(true);
    }
    catch (PascalsTriangle.RowNumberException ex){
      System.out.println("Błędna wartość wielkości");
      ErrorMessage m_err = new ErrorMessage();
      m_err.message(this.input.getText());
      return;
    }
  }
}

public class PascalsTriangleGUI {
  public static void main(String[] args){
    Frame fr = new Frame("Trójkąt Pascala");
    fr.setLayout(new GridLayout(3, 1));
    Label lab_gen = new Label("Wygeneruj trójkąt Pascala", Label.CENTER);
    lab_gen.setFont(new Font(Font.SERIF, Font.BOLD, 30));
    Panel len_input_panel = new Panel(new FlowLayout(FlowLayout.CENTER));
    TextField len_input = new TextField(15);
    len_input_panel.add(len_input);
    Button gen_button = new Button("Wygeneruj");
    gen_button.addActionListener(new GenButtonListener(len_input));
    gen_button.setFont(new Font(Font.SERIF, Font.PLAIN, 18));
    fr.setSize(430, 300);
    fr.addWindowListener(new WindowAdapterMain());
    fr.add(lab_gen);
    fr.add(len_input_panel);
    fr.add(gen_button);
    fr.setVisible(true);
  }
}
