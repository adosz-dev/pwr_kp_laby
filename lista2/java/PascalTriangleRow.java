public class PascalTriangleRow {
  private int[] row;
  private int[] previous_row;
  private int[] temp_row;
  private int row_length;
  public static class RowNumberException extends Exception {
    public RowNumberException(){
      super("Row number must be positive");
    }
  }
  public static class OutOfBoundsException extends Exception {
    public OutOfBoundsException(){
      super("Number out of bounds");
    }
  }
  private void CreateRow(int n){
    this.row[0] = 1;
    this.previous_row[0] = 1;
    for (int i=1; i<=n; i++){
      this.row[i] = 0;
      this.previous_row[i] = 0;
    }
    if (n == 0){
      return;
    }
    this.row[1] = 1;
    if (n == 1){
      return;
    }
    for (int i=1; i<=n; i++){
      this.temp_row = this.row;
      this.row = this.previous_row;
      this.previous_row = this.temp_row;
      this.row[i] = 1;
      for (int j=1; j<i; j++){
        this.row[j] = this.previous_row[j-1] + this.previous_row[j];
      }
    }
  }
  public PascalTriangleRow(int n) throws RowNumberException{
    if (n < 0){
      throw new RowNumberException();
    }
    this.row_length = n + 1;
    this.row = new int[n+1];
    this.previous_row = new int[n+1];
    CreateRow(n);
  }
  public int GetValue(int i) throws OutOfBoundsException{
    if (i >= this.row_length || i<0){
      throw new OutOfBoundsException();
    }
    else {
      return this.row[i];
    }
  }
}
