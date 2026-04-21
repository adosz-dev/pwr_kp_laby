import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.io.InputStreamReader;
import java.io.BufferedReader;

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
    Frame error_fr = new Frame("Rząd Trójkąta Pascala - Błąd");
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
  TextField input_elements_numbers;
  TextField input_row_number;
  int length;
  GenButtonListener(TextField input_row_number, TextField input_elements_numbers){
    this.input_row_number = input_row_number;
    this.input_elements_numbers = input_elements_numbers;
  }
  public void actionPerformed(ActionEvent e) {
    String row_number = this.input_row_number.getText();
    String elements_numbers = this.input_elements_numbers.getText();
    try{
      this.length = Integer.parseInt(row_number);
    }
    catch (NumberFormatException ex){
      ErrorMessage m_err = new ErrorMessage();
      m_err.message(row_number + " " + elements_numbers);
      return;
    }
    try {
      ArrayList<String> command = new ArrayList<>();
      command.add("./pascaltrianglerow");
      command.add(row_number);
      String[] elements = elements_numbers.split("\\s+");
      for (int i=0; i<elements.length; i++){
        command.add(elements[i]);
      }
      ProcessBuilder pb = new ProcessBuilder(command);
      Process process = pb.start();
      BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
      ArrayList<String> lines = new ArrayList<>();
      String line;
      while ((line = reader.readLine()) != null) {
        lines.add(line);
      }
      System.out.println(lines.size());
      if (process.waitFor() != 0){
        ErrorMessage m_err = new ErrorMessage();
        m_err.message(row_number + " " + elements_numbers);
        return;
      }

      Frame fr = new Frame("Elementy rzędu " + row_number);
      fr.setLayout(new GridLayout(lines.size()+1, 1));

      Label main_lab = new Label("Dla " + row_number + " rzędu:");
      fr.add(main_lab);

      for (int i=0; i<lines.size(); i++){
        Label lab = new Label(lines.get(i));
        lab.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 13));
        fr.add(lab);
      }

      main_lab.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
      fr.addWindowListener(new WindowAdapterSub(fr));
      fr.pack();
      fr.setVisible(true);
    }
    catch (Exception ex) {
      ErrorMessage m_err = new ErrorMessage();
      m_err.message(row_number + " " + elements_numbers);
      return;
    }
  }
}

public class PascalsTriangleRow {
  public static void main(String[] args){
    Frame fr = new Frame("Rząd Trójkąta Pascala");
    fr.setLayout(new GridLayout(4, 1));

    // main label
    // --------------------
    Label lab_gen = new Label("Rząd Trójkąta Pascala", Label.CENTER);
    lab_gen.setFont(new Font(Font.SERIF, Font.BOLD, 30));
    // --------------------
    fr.add(lab_gen);

    // len_panel container
    // ---------------------
    Panel len_panel = new Panel(new GridLayout(2,1));
    Label len_label = new Label("Numer wiersza", Label.CENTER);
    len_label.setFont(new Font(Font.SERIF, Font.BOLD, 20));
    len_panel.add(len_label);
    Panel len_input_panel = new Panel(new FlowLayout(FlowLayout.CENTER));
    TextField len_input = new TextField(15);
    len_input_panel.add(len_input);
    len_panel.add(len_input_panel);
    // ----------------------
    fr.add(len_panel);

    // elements_panel container
    // ---------------------
    Panel elements_panel = new Panel(new GridLayout(2,1));
    Label elements_label = new Label("Numery elementu", Label.CENTER);
    elements_label.setFont(new Font(Font.SERIF, Font.BOLD, 20));
    elements_panel.add(elements_label);
    Panel elements_input_panel = new Panel(new FlowLayout(FlowLayout.CENTER));
    TextField elements_input = new TextField(15);
    elements_input_panel.add(elements_input);
    elements_panel.add(elements_input_panel);
    // ----------------------
    fr.add(elements_panel);

    // button
    // ----------------------
    Button gen_button = new Button("Wygeneruj");
    gen_button.addActionListener(new GenButtonListener(len_input, elements_input));
    gen_button.setFont(new Font(Font.SERIF, Font.PLAIN, 18));
    // ---------------------
    fr.add(gen_button);

    fr.setSize(430, 300);
    fr.addWindowListener(new WindowAdapterMain());
    fr.setVisible(true);
  }
}
