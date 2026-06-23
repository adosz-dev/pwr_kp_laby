#pragma once
#include <cmath>
#include <iostream>
#include <vector>

template <typename T>
class TreeElem;

template <typename T>
class Tree {
 private:
  TreeElem<T>* root;
  TreeElem<T>* insert_helper(const T& element, TreeElem<T>* node);
  TreeElem<T>* find_min(TreeElem<T>* node);
  bool search_helper(const T& element, TreeElem<T>* node);
  TreeElem<T>* remove_helper(const T& elem, TreeElem<T>* node);
  int height;
  void get_height_helper(int curr, TreeElem<T>* node);

 public:
  Tree();
  Tree(T element);
  ~Tree();
  void insert(T elem);
  bool search(const T& elem);
  void remove(const T& elem);
  int get_height();
  void draw();
};

template <typename T>
class TreeElem {
 private:
  T element;
  TreeElem<T>* left;
  TreeElem<T>* right;

 public:
  TreeElem(T element);
  ~TreeElem();
  friend class Tree<T>;
};

template <typename T>
TreeElem<T>::TreeElem(T element) {
  this->element = element;
  this->left = nullptr;
  this->right = nullptr;
}

template <typename T>
TreeElem<T>::~TreeElem() {
  if (this->left != nullptr) {
    delete this->left;
  }
  if (this->right != nullptr) {
    delete this->right;
  }
}

template <typename T>
Tree<T>::Tree() {
  this->root = nullptr;
  this->height = 0;
}

template <typename T>
Tree<T>::Tree(T element) {
  this->root = new TreeElem<T>(element);
  this->height = 0;
}

template <typename T>
void Tree<T>::get_height_helper(int curr, TreeElem<T>* node) {
  if (node == nullptr) {
    if (curr > this->height) {
      this->height = curr;
    }
    return;
  }
  curr++;
  get_height_helper(curr, node->left);
  get_height_helper(curr, node->right);
}

template <typename T>
int Tree<T>::get_height() {
  this->height = 0;
  get_height_helper(0, this->root);
  return this->height;
}

template <typename T>
void Tree<T>::draw() {
  int h = get_height();
  if (h == 0) return;

  std::vector<TreeElem<T>*> level = {root};
  for (int d = 0; d < h; d++) {
    int indent = pow(2, h - 1 - d) - 1;
    int spacing = pow(2, h - d) - 1;

    for (int i = 0; i < indent; i++) std::cout << ' ';
    std::vector<TreeElem<T>*> next;
    for (size_t i = 0; i < level.size(); i++) {
      if (i > 0) {
        for (int s = 0; s < spacing; s++) {
          std::cout << ' ';
        }
      }
      TreeElem<T>* node = level[i];
      if (node != nullptr) {
        std::cout << node->element;
        next.push_back(node->left);
        next.push_back(node->right);
      } else {
        std::cout << ' ';
        next.push_back(nullptr);
        next.push_back(nullptr);
      }
    }
    std::cout << '\n';
    level = next;
  }
}

template <typename T>
Tree<T>::~Tree() {
  if (this->root != nullptr) {
    delete this->root;
  }
}

template <typename T>
TreeElem<T>* Tree<T>::insert_helper(const T& element, TreeElem<T>* node) {
  if (node == nullptr) {
    return new TreeElem<T>(element);
  }
  if (element < node->element) {
    node->left = insert_helper(element, node->left);
  } else if (element > node->element) {
    node->right = insert_helper(element, node->right);
  }
  return node;
}

template <typename T>
void Tree<T>::insert(T element) {
  this->root = insert_helper(element, this->root);
}

template <typename T>
bool Tree<T>::search_helper(const T& element, TreeElem<T>* node) {
  if (node == nullptr) return false;
  if (node->element == element) return true;
  if (element < node->element) {
    return search_helper(element, node->left);
  } else {
    return search_helper(element, node->right);
  }
}

template <typename T>
bool Tree<T>::search(const T& element) {
  return search_helper(element, this->root);
}

template <typename T>
TreeElem<T>* Tree<T>::find_min(TreeElem<T>* node) {
  while (node->left != nullptr) {
    node = node->left;
  }
  return node;
}

template <typename T>
TreeElem<T>* Tree<T>::remove_helper(const T& element, TreeElem<T>* node) {
  if (node == nullptr) return nullptr;
  if (element > node->element) {
    node->right = remove_helper(element, node->right);
  } else if (element < node->element) {
    node->left = remove_helper(element, node->left);
  } else {
    if (node->left == nullptr && node->right == nullptr) {
      delete node;
      return nullptr;
    } else if (node->left == nullptr) {
      TreeElem<T>* tmp = node->right;
      node->right = nullptr;
      delete node;
      return tmp;
    } else if (node->right == nullptr) {
      TreeElem<T>* tmp = node->left;
      node->left = nullptr;
      delete node;
      return tmp;
    } else {
      TreeElem<T>* s = find_min(node->right);
      node->element = s->element;
      node->right = remove_helper(s->element, node->right);
    }
  }
  return node;
}

template <typename T>
void Tree<T>::remove(const T& element) {
  this->root = remove_helper(element, this->root);
}
