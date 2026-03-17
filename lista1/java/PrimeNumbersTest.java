public class PrimeNumbersTest {
  public static void main(String[] args){
    PrimeNumbers prime;
    try {
      final int n = Integer.parseInt(args[0]);
      if (n < 2){
        System.out.println(n + " - Nieprawidłowy zakres");
        return;
      }
      prime = new PrimeNumbers(n);
    }
    catch (NumberFormatException ex){
      System.out.println(args[0] + " - Nieprawidłowy zakres");
      return;
    }

    for (int i=1; i < args.length; i++){
      try {
        int k = Integer.parseInt(args[i]);
        int m = prime.getNumber(k);
        if (m == -1){
          System.out.println(args[i] + " - Liczba spoza zakresu");
        }
        else {
          System.out.println(args[i] + " - " +m);
        }
        
      }
      catch (NumberFormatException ex){
        System.out.println(args[i] + " - Nieprawidłowa dana");
      }
    }
  }
}
