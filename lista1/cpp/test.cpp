#include <iostream>
#include <vector>
#include <string>
#include "primenumbers.hpp"

using namespace std;

int main (int argc, char *argv[]) {
  if (argc < 2){
    cout << "Proszę podać argument"<< endl;
    return -1;
  }
  primenumbers* primes = nullptr;

  try {
    int n = stoi(argv[1]);
    primes = new primenumbers(n);
  }
  catch (const invalid_argument ex){
    cout << argv[1] << " - nieprawidłowy zakres" << endl;
    return -1;
  }
  for (int i=2; i<argc; i++){
    try{
      int k = stoi(argv[i]);
      int m = primes -> get_number(k);
      cout << argv[i] << " - " << m << endl;
    }
    catch (const invalid_argument ex){
      cout << argv[i] << " - " << "nieprawidłowa dana" << endl;
    }
    catch (const out_of_range ex){
      cout << argv[i] << " - " << "liczba spoza zakresu" << endl;
    }
  }

  
  return 0;
}
