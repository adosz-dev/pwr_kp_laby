#include "pascaltrianglerow.hpp"
#include <stdexcept>
#include <exception>
#include <cstdlib>

void pascaltrianglerow::create_row(int n){
  this->row[0] = 1;
  this->previous_row[0] = 1;
  for (int i=1; i<n; i++){
    this->row[i] = 0;
    this->previous_row[i] = 0;
  }
  if (n == 0) return;
  this->row[1] = 1;
  if (n == 1) return;

  for (int i=1; i<=n; i++) {
    this->temp_row = this->row;
    this->row = this->previous_row;
    this->previous_row = this->temp_row;
    this->row[i] = 1;
    for (int j=1; j<i;j++){
      this->row[j] = this->previous_row[j-1] + this->previous_row[j];
    }
  }
}

pascaltrianglerow::pascaltrianglerow(int n){
  if (n < 0){
    throw pascaltrianglerow::RowNumberError();
  }
  this->row_length = n+1;
  this->row = (int*) malloc(sizeof(int)*(n+1));
  this->previous_row = (int*) malloc(sizeof(int)*(n+1));
  create_row(n);
}

pascaltrianglerow::~pascaltrianglerow(){
  free(this->row);
  free(this->previous_row);
}

int pascaltrianglerow::get_value(int i){
  if (i >= this->row_length || i < 0){
    throw pascaltrianglerow::OutOfRangeError();
  }
  else {
    return this->row[i];
  }
}
