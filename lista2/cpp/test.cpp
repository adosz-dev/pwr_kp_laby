#include "pascaltrianglerow.hpp"
#include <iostream>
#include <string>

int main (int argc, char *argv[]) {
  pascaltrianglerow* row = nullptr;
  
  if (argc < 3){
    std::cout << "Za mało argumentów!";
    return -1;
  }
  try {
    int n = std::stoi(argv[1]);
    row = new pascaltrianglerow(n);
  }
  catch (std::invalid_argument e){
    std::cout << argv[1] << " - "<< "Nieprawidłowy argument";
    return -1;
  }
  catch (pascaltrianglerow::RowNumberError e){
    std::cout << argv[1] << " - " << "Nieprawidłowy numer wiersza";
    return -1;
  }

  for (int i=2; i<argc; i++){
    try {
      int c = std::stoi(argv[i]);
      int value = row->get_value(c);
      std::cout << argv[i] << " - " << value << std::endl;
    }
    catch (std::invalid_argument e){
      std::cout << argv[i] << " - " << "Nieprawidłowa dana" << std::endl;
    }
    catch (pascaltrianglerow::OutOfRangeError e){
      std::cout << argv[i] << " - " << "liczba spoza zakresu" << std::endl;
    }
  }
  return 0;
}
