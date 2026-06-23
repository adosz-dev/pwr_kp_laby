#include <iostream>
#include "tree.hpp"

int main(int argc, char* argv[]) {
  // tworzenie drzewa
  Tree<int> tree;
  // dodawanie elementów
  tree.insert(4);
  tree.insert(2);
  tree.insert(6);
  tree.insert(1);
  tree.insert(3);
  tree.insert(5);
  tree.insert(7);
  std::cout << "Istnieje 5?: " << tree.search(5) << std::endl;
  std::cout << "Istnieje 22?: " << tree.search(22) << std::endl;
  // usuwam 5
  tree.remove(5);
  std::cout << "Istnieje 5?: " << tree.search(5) << std::endl;
  tree.insert(5);
  tree.draw();
  return 0;
}
