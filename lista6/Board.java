import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.layout.Pane;
import java.util.Random;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;
import java.util.ArrayList;


/**
 * Plansza symulacji, w której każda komórka jest Panem i każdy z nich
 * jest obsługiwany przez inny wątek. Wątek zmienia jej kolor na losowy, lub
 * na podstawie sąsiednich
 */
public class Board extends BorderPane {
  private static final Random RAND = new Random();
  private final GridPane gridPane = new GridPane();
  private volatile ArrayList<ArrayList<Color>> colors;
  private volatile ArrayList<ArrayList<ColorPane>> panes;
  private volatile ArrayList<ArrayList<Boolean>> isActive;
  private volatile int n, m;  // n = kolumny, m = rzędy
  private final int k;
  private final double p;

  /**
   * Komórka planszy przechowująca swoje współrzędne w siatce
   */
  class ColorPane extends Pane {
    public int row, col;

    /**
     * @param col indeks kolumny komórki
     * @param row indeks wiersza komórki
     */
    public ColorPane(int col, int row){
      super();
      this.row = row;
      this.col = col;
    }
  }

  /**
   * Wątek odpowiedzialny za aktualizację koloru jednej komórki
   */
  class CellThread extends Thread {
    private int row, col;

    /**
     * @param col indeks kolumny obsługiwanej komórki
     * @param row indeks wiersza obsługiwanej komórki
     */
    public CellThread(int col, int row){
      this.row = row;
      this.col = col;
      // zamyka program, mimo że wątek nie zakończył działania
      this.setDaemon(true);
    }

    /**
     * Ustawianie koloru w tablicy kolorów komórek i tło komórki
     * @param c kolor komórki
     */
    private void setColor(Color c){
      // blokujemy this funkcji zewnetrznej Board
      synchronized(Board.this){
        System.out.println("Start: " + Thread.currentThread().threadId());
        colors.get(col).set(row, c);
        panes.get(col).get(row).setBackground(new Background(
            new BackgroundFill(colors.get(col).get(row), CornerRadii.EMPTY, Insets.EMPTY)
        ));
        System.out.println("End: " + Thread.currentThread().threadId());
      }
    }

    /**
     * Dodawanie wartości RGB do tablicy sumy tych kolorów, w celu
     * obliczeniu średniego koloru wśród sąsiadów
     * @param col indeks kolumny komórki
     * @param row indeks wiersza komórki
     * @param sum tablica, do której sumujemy wartości z RGB
     */
    private void getColor(int col, int row, int[] sum){
      Color c = colors.get(col).get(row);
      sum[0] += (int) Math.round(c.getRed()*255);
      sum[1] += (int) Math.round(c.getGreen()*255);
      sum[2] += (int) Math.round(c.getBlue()*255);
    }

    /**
     * Ustawianie koloru komórki na średnią kolorów aktywnych sąsiadów.
     * Jeśli żaden sąsiad nie jest aktywny, kolor pozostaje bez zmian.
     */
    private void setNeighboringColor(){
      synchronized(Board.this){
        int[] sum = {0, 0, 0};
        int divisor = 0;

        if (row > 0 && isActive.get(col).get(row-1)){
          getColor(col, row-1, sum);
          divisor++;
        }
        if (row < m-1 && isActive.get(col).get(row+1)){
          getColor(col, row+1, sum);
          divisor++;
        }
        if (col > 0 && isActive.get(col-1).get(row)){
          getColor(col-1, row, sum);
          divisor++;
        }
        if (col < n-1 && isActive.get(col+1).get(row)){
          getColor(col+1, row, sum);
          divisor++;
        }
        if (divisor == 0){
          return;
        }
        for (int i=0; i<3; i++){
          sum[i] = sum[i]/divisor;
        }
        setColor(Color.rgb(sum[0], sum[1], sum[2]));
      }
    }

    /**
     * Co losowy czas (oparty na k) z prawdopodobieństwem p
     * ustawia losowy kolor, a w przeciwnym razie przyjmuje kolor sąsiadów.
     * Działa dopóki komórka jest aktywna.
     */
    @Override
    public void run() {
      while(isActive.get(col).get(row)){
        long delay = (long) (k*(0.5 + RAND.nextDouble()));
        try {
          Thread.sleep(delay);
        }
        catch (Exception e){
          System.out.println(e);
        }
        if (RAND.nextDouble() < p){
          Color c = getRandomColor();
          this.setColor(c);
        }
        else {
          setNeighboringColor();
        }
      }
    }
  }

  /**
   * @return losowy kolor RGB
   */
  private Color getRandomColor(){
    return Color.rgb(RAND.nextInt(256), RAND.nextInt(256), RAND.nextInt(256));
  }

  /**
   * Wypełnianie tablicę colors losowymi kolorami
   */
  private void setRandomColors(){
    for (int i=0; i<n; i++){
      for (int j=0; j<m; j++){
        colors.get(i).add(getRandomColor());
      }
    }
  }

  /**
   * Ustalanie wielkości komórek po równo dla każdej
   */
  private void setConstraints(){
    for (int j=0; j<n; j++){
      ColumnConstraints colSize = new ColumnConstraints();
      colSize.setPercentWidth(100.0 / n);
      this.gridPane.getColumnConstraints().add(colSize);
    }

    for (int i=0; i<m; i++){
      RowConstraints rowSize = new RowConstraints();
      rowSize.setPercentHeight(100.0 / m);
      this.gridPane.getRowConstraints().add(rowSize);
    }
  }

  /**
   * Uruchamia wątek {@link CellThread} dla każdej komórki planszy.
   */
  private void runThreads(){
    for (int i=0; i<n; i++){
      for (int j=0; j<m; j++){
        new CellThread(i, j).start();
      }
    }
  }

  /**
   * Konfiguruje pojedynczy panel komórki: ustawia tło, rozmiar oraz obsługę kliknięcia
   * (przełączanie aktywności komórki) i dodaje go do siatki.
   * @param cellPane panel komórki do skonfigurowania
   * @param col      indeks kolumny docelowej w siatce
   * @param row      indeks wiersza docelowego w siatce
   */
  private void setPane(ColorPane cellPane, final int col, final int row){
    cellPane.setBackground(new Background(
          new BackgroundFill(colors.get(col).get(row), CornerRadii.EMPTY, Insets.EMPTY)
          ));

    // rozszerzanie pane'ów do 100% możliwego miejsca
    cellPane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

    cellPane.setOnMouseClicked(event -> {
      boolean currentlyActive = isActive.get(col).get(row);
      isActive.get(col).set(row, !isActive.get(col).get(row));
      if (!currentlyActive){
        new CellThread(col, row).start();
      }
    });
    this.gridPane.add(cellPane, col, row);
    panes.get(col).add(cellPane);
  }

  /**
   * Tworzy i konfiguruje panele dla wszystkich komórek planszy.
   */
  private void setPanes(){
    for (int i=0; i<n; i++){
      for (int j=0; j<m; j++){
        ColorPane cellPane = new ColorPane(i, j);
        setPane(cellPane, i, j);
      }
    }
  }

  /**
   * Dodaje nową kolumnę do planszy: aktualizuje ograniczenia siatki, tworzy struktury danych
   * dla nowych komórek i uruchamia dla nich wątki.
   */
  private void addNewCol(){
    for (int i=0; i<gridPane.getColumnConstraints().size(); i++){
      ColumnConstraints colSize = new ColumnConstraints();
      colSize.setPercentWidth(100.0 / n+1);
      gridPane.getColumnConstraints().set(i, colSize);
    }
    ColumnConstraints newColSize = new ColumnConstraints();
    newColSize.setPercentWidth(100.0 / n+1);
    gridPane.getColumnConstraints().add(newColSize);

    ArrayList<Color> newColors = new ArrayList<>();
    ArrayList<ColorPane> newPanes = new ArrayList<>();
    ArrayList<Boolean> newActive = new ArrayList<>();
    for (int i=0; i<m; i++){
      newColors.add(getRandomColor());
      newActive.add(true);
    }
    colors.add(newColors);
    panes.add(newPanes);
    isActive.add(newActive);

    for (int i=0; i<m; i++){
      ColorPane cellPane = new ColorPane(n, i);
      setPane(cellPane, n, i);
      new CellThread(n, i).start();
    }
    n++;
  }

  /**
   * Dodaje nowy wiersz do planszy: aktualizuje ograniczenia siatki, tworzy struktury danych
   * dla nowych komórek i uruchamia dla nich wątki.
   */
  private void addNewRow(){
    for (int i=0; i<gridPane.getRowConstraints().size(); i++){
      RowConstraints rowSize = new RowConstraints();
      rowSize.setPercentHeight(100.0 / m+1);
      gridPane.getRowConstraints().set(i, rowSize);
    }
    RowConstraints newRowSize = new RowConstraints();
    newRowSize.setPercentHeight(100.0 / m+1);
    gridPane.getRowConstraints().add(newRowSize);

    for (int i=0; i<n; i++){
      colors.get(i).add(getRandomColor());
      isActive.get(i).add(true);
    }
    for (int i=0; i<n; i++){
      ColorPane cellPane = new ColorPane(i, m);
      setPane(cellPane, i, m);
      new CellThread(i, m).start();
    }
    m ++;
  }

  /**
   * Tworzenie i dodawanie przycisków umożliwiających dynamiczne dodawanie wierszy i kolumn
   */
  private void addButtons(){
    Button newRowButton = new Button("+ Dodaj rząd");
    newRowButton.setOnMouseClicked(event -> addNewRow());

    Button newColButton = new Button("+ Dodaj kolumnę");
    newColButton.setOnMouseClicked(event -> addNewCol());

    HBox newRowButtonContainer = new HBox(newRowButton);
    newRowButtonContainer.setAlignment(Pos.CENTER);
    newRowButtonContainer.setPadding(new Insets(8));

    VBox newColButtonContainer = new VBox(newColButton);
    newColButtonContainer.setAlignment(Pos.CENTER);
    newColButtonContainer.setPadding(new Insets(8));

    this.setBottom(newRowButtonContainer);
    this.setRight(newColButtonContainer);
  }

  /**
   * Oznaczanie wszystkich komórek jako aktywne
   */
  private void setActive(){
    for (int i=0; i<n; i++){
      for (int j=0; j<m; j++){
        isActive.get(i).add(true);
      }
    }
  }

  /**
   * Oznaczanie wszystkich komórek jako nieaktywne. Powoduje to zakończenie ich wątków.
   */
  public void setInactive(){
    for (int i=0; i<n; i++){
      for (int j=0; j<m; j++){
        isActive.get(i).set(j, false);
      }
    }
  }

  /**
   * Inicjalizowanie listy colors, isActive i panes
   */
  private void initArrayLists(){
    for (int i=0; i<n; i++){
      ArrayList<ColorPane> rowPanes = new ArrayList<>();
      ArrayList<Boolean> rowActive = new ArrayList<>();
      ArrayList<Color> rowColors = new ArrayList<>();
      colors.add(rowColors);
      panes.add(rowPanes);
      isActive.add(rowActive);
    }
  }

  /**
   * Tworzenie planszy
   * @param n liczba kolumn
   * @param m liczba wierszy
   * @param k bazowy czas (ms) wyznaczający opóźnienie między aktualizacjami komórki
   * @param p prawdopodobieństwo, że wątek komórki wybierze losowy kolor (zamiast koloru sąsiadów)
   */
  public Board(int n, int m, int k, double p){
    this.n = n;
    this.m = m;
    this.k = k;
    this.p = p;

    colors = new ArrayList<>();
    panes = new ArrayList<>();
    isActive = new ArrayList<>();
    initArrayLists();

    this.setCenter(this.gridPane);

    addButtons();
    setRandomColors();
    setConstraints();
    setPanes();
    setActive();
    runThreads();
  }
}
