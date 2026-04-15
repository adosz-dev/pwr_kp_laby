#include <string>

class Figure {
  public:
    double value = 0;
    virtual double get_perimeter() = 0;
    virtual double get_area() = 0;
    virtual std::string get_name() = 0;
    Figure();
    Figure(double value);
};

class Quadrangle: public Figure {
  public:
    double side1, side2, side3, side4, angle;
    Quadrangle(double side1, double side2, double side3, double side4, double angle);
    double get_perimeter() override;
    double get_area() override;
};

class Square: public Quadrangle {
  public:
    Square(double side);
    std::string get_name() override;
};

class Rectangle: public Quadrangle {
  public:
    Rectangle(double side1, double side2);
    std::string get_name() override;
};

class Diamond: public Quadrangle {
  public:
    Diamond(double side, double angle);
    std::string get_name() override;
};

class Pentagon: public Figure {
  public:
    Pentagon(double side);
    double get_area() override;
    double get_perimeter() override;
    std::string get_name() override;
};

class Hexagon: public Figure {
  public:
    Hexagon(double side);
    double get_area() override;
    double get_perimeter() override;
    std::string get_name() override;
};

class Circle: public Figure {
  public:
    Circle(double radius);
    double get_area() override;
    double get_perimeter() override;
    std::string get_name() override;
};


