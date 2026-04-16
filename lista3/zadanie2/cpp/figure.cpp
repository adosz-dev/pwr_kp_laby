#include "figure.hpp"
#include <string>
#include <cmath>

#define PI 3.14159

double Figure::calculate_area_one_param(OneParamEnum fig, double value){
  switch (fig){
    case OneParamEnum::CIRCLE: return PI*value*value;
    case OneParamEnum::SQUARE: return value*value;
    case OneParamEnum::HEXAGON: 
      return (3*value*value*std::sqrt(3))/2;
    case OneParamEnum::PENTAGON:
      return (value*value)/4 * std::sqrt(25+10*std::sqrt(5));
    default: return 0;
  }
}

double Figure::calculate_perimeter_one_param(OneParamEnum fig, double value){
  switch (fig){
    case OneParamEnum::CIRCLE: return 2*PI*value;
    case OneParamEnum::SQUARE: return value * 4;
    case OneParamEnum::HEXAGON: return value * 6;
    case OneParamEnum::PENTAGON: return value * 5;
    default: return 0;
  }
}

std::string Figure::get_name_one_param(OneParamEnum fig){
  switch (fig){
    case OneParamEnum::CIRCLE: return "Circle";
    case OneParamEnum::SQUARE: return "Square";
    case OneParamEnum::HEXAGON: return "Hexagon";
    case OneParamEnum::PENTAGON: return "Pentagon";
    default: return "unknown";
  }
}

double Figure::calculate_area_two_param(TwoParamEnum fig, double value1, double value2){
  switch (fig){
    case TwoParamEnum::DIAMOND:
      return value1*value1*std::sin(value2*(PI/180));
    case TwoParamEnum::RECTANGLE: return value1*value2;
    default: return 0;
  }
}

double Figure::calculate_perimeter_two_param(TwoParamEnum fig, double value1, double value2){
  switch (fig){
    case TwoParamEnum::DIAMOND: return value1 * 4;
    case TwoParamEnum::RECTANGLE: return 2*value1 + 2*value2;
    default: return 0;
  }
}

std::string Figure::get_name_two_param(TwoParamEnum fig){
  switch (fig){
    case TwoParamEnum::DIAMOND: return "Diamond";
    case TwoParamEnum::RECTANGLE: return "Rectangle";
    default: return "unknown";
  }
}

OneParamFigures::OneParamFigures(OneParamEnum fig, double value){
  this->fig = fig;
  this->value = value;
}

double OneParamFigures::calculate_area(){
  return calculate_area_one_param(this->fig, this->value);
}

double OneParamFigures::calculate_perimeter(){
  return calculate_perimeter_one_param(this->fig, this->value);
}

std::string OneParamFigures::get_name(){
  return get_name_one_param(this->fig);
}

TwoParamFigures::TwoParamFigures(TwoParamEnum fig, double value1, double value2){
  this->fig = fig;
  this->value1 = value1;
  this->value2 = value2;
}

double TwoParamFigures::calculate_area(){
  return calculate_area_two_param(this->fig, this->value1, this->value2);
}

double TwoParamFigures::calculate_perimeter(){
  return calculate_perimeter_two_param(this->fig, this->value1, this->value2);
}

std::string TwoParamFigures::get_name(){
  return get_name_two_param(this->fig);
}
