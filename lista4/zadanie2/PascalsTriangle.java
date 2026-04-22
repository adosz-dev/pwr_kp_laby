import java.util.ArrayList;

public class PascalsTriangle {
  private ArrayList<ArrayList<Integer>> triangle = new ArrayList<>();
  private int n;

  public static class RowNumberException extends Exception {
    public RowNumberException(){
      super("Triangle's size must be non-negative");
    }
  }
  
  private void CreateTriangle(){
    ArrayList<Integer> first_row = new ArrayList<>();
    first_row.add(1);
    this.triangle.add(first_row);
    
    if (n > 0){
      ArrayList<Integer> second_row = new ArrayList<>();
      second_row.add(1);
      second_row.add(1);
      this.triangle.add(second_row);
    }
    else {
      return;
    }
    
    if (n == 1){
      return;
    }
    
    for (int i=2; i<=n; i++){
      ArrayList<Integer> row = new ArrayList<>();
      row.add(1);
      for (int j=1; j<i; j++){
        row.add(this.triangle.get(i-1).get(j-1) + this.triangle.get(i-1).get(j));
      }
      row.add(1);
      this.triangle.add(row);
    }
  }
  public PascalsTriangle(int n) throws RowNumberException{
    if (n < 0){
      throw new RowNumberException();
    }
    this.n = n;
    CreateTriangle();
  }
  public String GetRow(int i) {
    String row_label = "";
    ArrayList<Integer> row = this.triangle.get(i);
    for (int j=0; j < row.size(); j++){
      row_label = row_label + " " + Integer.toString(row.get(j));
    }
    return row_label;
  }
}
