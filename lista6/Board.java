import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.layout.Pane;
import java.util.Random;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;


public class Board extends GridPane{
  private static final Random RAND = new Random();
  private final Color[][] colors;
  private final ColorPane[][] panes;
  private final Boolean[][] isActive;
  private final int n, m, k;
  private final double p;

  class ColorPane extends Pane {
    public int row, col;
    public ColorPane(int row, int col){
      super();
      this.row = row;
      this.col = col;
    }
  }

  class CellThread extends Thread {
    private int row, col;
    public CellThread(int row, int col){
      this.row = row;
      this.col = col;
      // zamyka program, mimo że wątek nie zakończył działania
      this.setDaemon(true);
    }

    private void setColor(Color c){
      // blokujemy this funkcji zewnetrznej Board
      synchronized(Board.this){
        System.out.println("Start: " + Thread.currentThread().getId());
        colors[row][col] = c;
        panes[row][col].setBackground(new Background(
            new BackgroundFill(colors[row][col], CornerRadii.EMPTY, Insets.EMPTY)
        ));
        System.out.println("End: " + Thread.currentThread().getId());
      }
    }
    
    private void getColor(int i, int j, int[] sum){
      Color c = colors[i][j];
      sum[0] += (int) Math.round(c.getRed()*255);
      sum[1] += (int) Math.round(c.getGreen()*255);
      sum[2] += (int) Math.round(c.getBlue()*255);
    }
    private void setNeighboringColor(){
      synchronized(Board.this){
        int[] sum = {0, 0, 0};
        int divisor = 0;

        if (row > 0 && isActive[row-1][col]){
          getColor(row-1, col, sum);
          divisor ++;
        }
        if (row < n-1 && isActive[row+1][col]){
          getColor(row+1, col, sum);
          divisor ++;
        }
        if (col > 0 && isActive[row][col-1]){
          getColor(row, col-1, sum);
          divisor ++;
        }
        if (col < m-1 && isActive[row][col+1]){
          getColor(row, col+1, sum);
          divisor ++;
        }
        if (divisor == 0){
          return;
        }
        for (int i=0; i<3; i++){
          sum[i] = Math.round(sum[i]/divisor);
        }
        setColor(Color.rgb(sum[0], sum[1], sum[2]));
      }
    }

    @Override
    public void run() {
      while(isActive[row][col]){
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
        colors[i][j] = getRandomColor();
      }
    }
  }
  
  private void setConstraints(){
    // ustalanie wielkości komórek po równo dla każdej
    for (int i=0; i<n; i++){
      ColumnConstraints colSize = new ColumnConstraints();
      colSize.setPercentWidth(100.0 / n);
      this.getColumnConstraints().add(colSize);
    }
    
    for (int i=0; i<m; i++){
      RowConstraints rowSize = new RowConstraints();
      rowSize.setPercentHeight(100.0 / m);
      this.getRowConstraints().add(rowSize);
    }
  }

  private void runThreads(){
    for (int i=0; i<n; i++){
      for (int j=0; j<m; j++){
        new CellThread(i, j).start();
      }
    }
  }

  private void setPanes(){
    for (int i=0; i<n; i++){
      for (int j=0; j<m; j++){
        ColorPane cellPane = new ColorPane(i, j);
        cellPane.setBackground(new Background(
              new BackgroundFill(colors[i][j], CornerRadii.EMPTY, Insets.EMPTY)
              ));
        
        // rozszerzanie pane'ów do 100% możliwego miejsca
        cellPane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        
        // musi być final dla lamba funkcji
        final int iFinal = i;
        final int jFinal = j;
        cellPane.setOnMouseClicked(event -> {
          boolean currentlyActive = isActive[iFinal][jFinal];
          isActive[iFinal][jFinal] = !isActive[iFinal][jFinal];
          if (!currentlyActive){
            new CellThread(iFinal, jFinal).start();
          }
        });

        this.add(cellPane, i, j);
        panes[i][j] = cellPane;
      }
    }
  }

  private void setActive(){
    for (int i=0; i<n; i++){
      for (int j=0; j<m; j++){
        isActive[i][j] = true;
      }
    }
  }

  public void setInactive(){
    for (int i=0; i<n; i++){
      for (int j=0; j<m; j++){
        isActive[i][j] = false;
      }
    }
  }

  public Board(int n, int m, int k, double p){
    this.n = n;
    this.m = m;
    this.k = k;
    this.p = p;

    colors = new Color[n][m];
    panes = new ColorPane[n][m];
    isActive = new Boolean[n][m];
    setRandomColors();
    setConstraints();
    setPanes();
    setActive();
    runThreads();
  }
}
