public class Test {
  public static void main(String[] args){
    if (args.length < 3){
      System.out.println("Za mało argumentów!");
      return;
    }
    PascalTriangleRow row;
    try {
      final int n = Integer.parseInt(args[0]);
      row = new PascalTriangleRow(n);
    }
    catch (NumberFormatException e){
      System.out.println(args[0] + " - Nieprawidłowy argument");
      return;
    }
    catch (PascalTriangleRow.RowNumberException e){
      System.out.println(args[0] + " - Nieprawidłowy numer wiersza");
      return;
    }
    for (int i=1; i<args.length; i++){
      try {
        int c = Integer.parseInt(args[i]);
        int value = row.GetValue(c);
        System.out.println(args[i] + " - " + value);
      }
      catch (PascalTriangleRow.OutOfBoundsException e){
        System.out.println(args[i] + " - liczba spoza zakresu");
      }
      catch (NumberFormatException e){
        System.out.println(args[i] + " - Nieprawidłowa dana");
      }
    }
  }
}
