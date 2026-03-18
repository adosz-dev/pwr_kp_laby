#include <vector>

using namespace std;
  
class primenumbers{
  private:
    bool is_prime(int x);
    vector<int> primes;
    void create_primes(int n);
  public:
    primenumbers(int n);
    ~primenumbers();
    int get_number(int m);
};
