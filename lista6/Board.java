import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;
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


public class Board extends BorderPane {
  private static final Random RAND = new Random();
  private final GridPane gridPane = new GridPane();
  private volatile ArrayList<ArrayList<Color>> colors;
  private volatile ArrayList<ArrayList<ColorPane>> panes;
  private volatile ArrayList<ArrayList<Boolean>> isActive;
  private volatile int n, m;  // n = kolumny, m = rzędy
  private final int k;
  private final double p;

  class ColorPane extends Pane {
    public int row, col;
    public ColorPane(int col, int row){
      super();
      this.row = row;
      this.col = col;
    }
  }

  class CellThread extends Thread {
    private int row, col;
    public CellThread(int col, int row){
      this.row = row;
      this.col = col;
      // zamyka program, mimo że wątek nie zakończył działania
      this.setDaemon(true);
    }

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

    private void getColor(int col, int row, int[] sum){
      Color c = colors.get(col).get(row);
      sum[0] += (int) Math.round(c.getRed()*255);
      sum[1] += (int) Math.round(c.getGreen()*255);
      sum[2] += (int) Math.round(c.getBlue()*255);
    }

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

  private Color getRandomColor(){
    return Color.rgb(RAND.nextInt(256), RAND.nextInt(256), RAND.nextInt(256));
  }

  private void setRandomColors(){
    for (int i=0; i<n; i++){
      for (int j=0; j<m; j++){
        colors.get(i).add(getRandomColor());
      }
    }
  }

  private void setConstraints(){
    // ustalanie wielkości komórek po równo dla każdej
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

  private void runThreads(){
    for (int i=0; i<n; i++){
      for (int j=0; j<m; j++){
        new CellThread(i, j).start();
      }
    }
  }

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

  private void setPanes(){
    for (int i=0; i<n; i++){
      for (int j=0; j<m; j++){
        ColorPane cellPane = new ColorPane(i, j);
        setPane(cellPane, i, j);
      }
    }
  }

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

  private void addButtons(){
    Button newRowButton = new Button("+ Dodaj rząd");
    newRowButton.setOnMouseClicked(event -> {
      addNewRow();
    });

    Button newColButton = new Button("+ Dodaj kolumnę");
    newColButton.setOnMouseClicked(event -> {
      addNewCol();
    });

    this.setBottom(newRowButton);
    this.setRight(newColButton);
  }

  private void setActive(){
    for (int i=0; i<n; i++){
      for (int j=0; j<m; j++){
        isActive.get(i).add(true);
      }
    }
  }

  public void setInactive(){
    for (int i=0; i<n; i++){
      for (int j=0; j<m; j++){
        isActive.get(i).set(j, false);
      }
    }
  }

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
