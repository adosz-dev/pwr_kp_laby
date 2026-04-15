#include "figure.hpp"
#include <string>
#include <cmath>

#define PI 3.14159

Figure::Figure(){
  this->value = 0;
}

Figure::Figure(double value){
  this->value=value;
}

Quadrangle::Quadrangle(double side1, double side2, double side3, double side4, double angle){
  this->side1 = side1;
  this->side2 = side2;
  this->side3 = side3;
  this->side4 = side4;
  this->angle = angle;
}
double Quadrangle::get_perimeter(){
  return this->side1+this->side2+this->side3+this->side4;
}
double Quadrangle::get_area() {
  return this->side1*this->side2*std::sin(this->angle*(PI/180));
}

Square::Square(double side): Quadrangle(side, side, side, side, 90){}
std::string Square::get_name(){
  return "Square";
}
Rectangle::Rectangle(double side1, double side2): Quadrangle(side1, side2, side1, side2, 90){}
std::string Rectangle::get_name(){
  return "Rectangle";
}
Diamond::Diamond(double side, double angle): Quadrangle(side, side, side, side, angle){}
std::string Diamond::get_name(){
  return "Diamond";
}

Pentagon::Pentagon(double side) : Figure(side){}
double Pentagon::get_perimeter() {
  return this->value * 5;
}
double Pentagon::get_area() {
  return (this->value*this->value)/4 * std::sqrt(25+10*std::sqrt(5));
}
std::string Pentagon::get_name() {
  return "Pentagon";
}

Hexagon::Hexagon(double side) : Figure(side){}
double Hexagon::get_area() {
  return (3*this->value*this->value*std::sqrt(3))/2;
}
double Hexagon::get_perimeter() {
  return this->value * 6;
}
std::string Hexagon::get_name(){
  return "Hexagon";
}

Circle::Circle(double radius) : Figure(radius){}
double Circle::get_perimeter() {
  return 2*PI*this->value;
}
double Circle::get_area() {
  return PI*this->value*this->value;
}
std::string Circle::get_name() {
  return "Circle";
}
