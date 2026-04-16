#include <string>

class Figure {
  public:
    virtual double calculate_area() = 0;
    virtual double calculate_perimeter() = 0;
    virtual std::string get_name() = 0;
    enum class OneParamEnum {
      CIRCLE,
      SQUARE,
      HEXAGON,
      PENTAGON
    };
    enum class TwoParamEnum {
      DIAMOND,
      RECTANGLE
    };
    static double calculate_area_one_param(OneParamEnum fig, double value);
    static double calculate_perimeter_one_param(OneParamEnum fig, double value);
    static std::string get_name_one_param(OneParamEnum fig);
    
    static double calculate_area_two_param(TwoParamEnum fig, double value1, double value2);
    static double calculate_perimeter_two_param(TwoParamEnum fig, double value1, double value2);
    static std::string get_name_two_param(TwoParamEnum fig);
};

class OneParamFigures: public Figure {
  private:
    OneParamEnum fig;
    double value;
  public:
    OneParamFigures(OneParamEnum fig, double value);
    double calculate_area() override;
    double calculate_perimeter() override;
    std::string get_name() override;
};
class TwoParamFigures: public Figure {
  private:
    TwoParamEnum fig;
    double value1;
    double value2;
  public:
    TwoParamFigures(TwoParamEnum fig, double value1, double value2);
    double calculate_area() override;
    double calculate_perimeter() override;
    std::string get_name() override;
};
