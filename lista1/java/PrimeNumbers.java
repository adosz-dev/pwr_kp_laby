import java.util.ArrayList;

public class PrimeNumbers {
  private ArrayList<Integer> primes;
  public PrimeNumbers(int n){
    this.primes = new ArrayList<>();
    this.primes.add(2);
    for (int i=3; i <= n; i++){
      if (isPrime(i)){
        this.primes.add(i);
      }
    }
  }
  private boolean isPrime(int x){
    for (int i=0; i < this.primes.size(); i++){
      if (this.primes.get(i) * this.primes.get(i) > x){
        break;
      }
      if (x % this.primes.get(i) == 0){
        return false;
      }
    }
    return true;
  }
  public int getNumber(int m){
    if (m >= this.primes.size() || m < 0){
      return -1;
    }
    return this.primes.get(m);
  }
}
