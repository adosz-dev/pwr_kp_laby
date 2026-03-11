import java.lang.Math;

public class zadanie3 {
  public static int div(int n){
    int m = 0;
    n = Math.abs(n);
    for (int i=1; i<n; i++){
      if (n % i == 0){
        m = i;
      } 
    }
    return m;
  }
  public static void main(String[] args){
    for (int i=0; i<args.length; i++){
      try {
        int n = Integer.parseInt(args[i]);
        System.out.println(args[i] + " — największy dzielnik: " + div(n));
      }
      catch (NumberFormatException ex) {
        System.out.println(args[i] + " nie jest liczbą całkowitą");
      }
    }
  }
}
