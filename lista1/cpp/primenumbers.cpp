#include "primenumbers.hpp"
#include <vector>
#include <stdexcept>

using namespace std;

bool primenumbers::is_prime(int x){
  for (int i=0; i<this->primes.size(); i++){
    if ((this->primes[i] * this->primes[i]) > x){
      break;
    }
    if (x % this->primes[i] == 0){
      return false;
    }
  }
  return true;
}

void primenumbers::create_primes(int n){
  if (n<2){
    throw invalid_argument("invalid argument");
  }
  this->primes.push_back(2);
  for (int i=3; i<=n; i++){
    if (is_prime(i)){
      this->primes.push_back(i);
    }
  }
}

primenumbers::primenumbers(int n){
  vector<int> primes;
  create_primes(n);
}

primenumbers::~primenumbers() {
  this->primes.clear();
}

int primenumbers::get_number(int m){
  if (m >= this->primes.size() || m < 0){
    throw out_of_range("out of range");
  }
  return this->primes[m];
}
