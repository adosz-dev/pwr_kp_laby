#include "figure.hpp"

#include <string>
#include <iostream>
#include <vector>
#include <exception>

using namespace std;

class WrongParametersException: public exception {
  public:
    const char* what() const noexcept override {
      return "Błędne parametry";
    }
};

class InvalidFigureException: public exception {
  public:
    const char* what() const noexcept override {
      return "Błędna figura";
    }
};

class WrongParametersNumberException: public exception {
  public:
    const char* what() const noexcept override {
      return "Zła liczba parametrów";
    }
};

class FigureData {
  private:
    string fig;
    vector<double> dimensions;
    bool dim_temp_contains(double value, const vector<double>& dim){
      for (int i=0; i<dim.size(); i++){
        if (value == dim[i]){
          return true;
        }
      }
      return false;
    }
    void save_data_one_param(Figure::OneParamEnum o, double value){
      OneParamFigures shape(o, value);
      this->area = shape.calculate_area();
      this->perimeter = shape.calculate_perimeter();
      this->name = shape.get_name();
    }
    void save_data_two_param(Figure::TwoParamEnum o, double value1, double value2){
      TwoParamFigures shape(o, value1, value2);
      this->area = shape.calculate_area();
      this->perimeter = shape.calculate_perimeter();
      this->name = shape.get_name();
    }
    void get_quadrangle(){
      int dim_len = this->dimensions.size();
      if (dim_len < 2 || dim_len > 5){
        throw WrongParametersNumberException();
      }
      vector<double> dims_temp;
      double angle = this->dimensions[dim_len-1];
      for (int i=0; i<dim_len-1; i++){
        if (!dim_temp_contains(this->dimensions[i], dims_temp)){
          dims_temp.push_back(this->dimensions[i]);
        }
      }
      if (angle == 90){
        if (dims_temp.size() < 1 || dims_temp.size() > 2){
          throw WrongParametersException();
        }
        if (dims_temp.size() == 1){
          Figure::OneParamEnum o = Figure::OneParamEnum::SQUARE;
          save_data_one_param(o, dims_temp[0]);
        }
        else {
          Figure::TwoParamEnum o = Figure::TwoParamEnum::RECTANGLE;
          save_data_two_param(o, dims_temp[0], dims_temp[1]);
        }
      }
      else {
        if (dims_temp.size() != 1){
          throw WrongParametersException();
        }
        Figure::TwoParamEnum o = Figure::TwoParamEnum::DIAMOND;
        save_data_two_param(o, dims_temp[0], angle);
      }
    }
  public:
    string name;
    double perimeter;
    double area;
    FigureData(string fig, const vector<double>& dimensions){
      this->dimensions = dimensions;
      this->fig = fig;
      get_figure_data();
    }
    void get_figure_data(){
      if (this->fig == "q"){
        get_quadrangle();
      }
      else if (this->dimensions.size() == 1){
        if (this->fig == "c"){
          Figure::OneParamEnum o = Figure::OneParamEnum::CIRCLE;
          save_data_one_param(o, this->dimensions[0]);
        }
        else if (this->fig == "p"){
          Figure::OneParamEnum o = Figure::OneParamEnum::PENTAGON;
          save_data_one_param(o, this->dimensions[0]);
        }
        else if (this->fig == "h"){
          Figure::OneParamEnum o = Figure::OneParamEnum::HEXAGON;
          save_data_one_param(o, this->dimensions[0]);
        }
        else {
          throw InvalidFigureException();
        }
      }
      else {
        throw WrongParametersNumberException();
      }
    }
};

void print_dimensions(const vector<double>& dims){
  for (int i=0; i<dims.size(); i++){
    cout << " " << dims[i];
  }
}

int main (int argc, char *argv[]) {
  if (argc < 3){
    cout << "Zła liczba argumentów";
    return -1;
  }

  string fig = argv[1];
  double num;
  vector<FigureData> shapes;
  vector<double> dimensions;

  for (int i=2; i<argc; i++){
    try{
      num = stod(argv[i]);
      dimensions.push_back(num);
    }
    catch (invalid_argument e){
      try {
        FigureData fd(fig, dimensions);
        shapes.push_back(fd);
      }
      catch (WrongParametersException ex){
        cout << fig;
        print_dimensions(dimensions);
        cout << " - Błędne wymiary" << endl;
      }
      catch (InvalidFigureException ex){
        cout << fig;
        print_dimensions(dimensions);
        cout << " - Błędna figura" << endl;
      }
      catch(WrongParametersNumberException ex){
        cout << fig;
        print_dimensions(dimensions);
        cout << " - Błędna liczba parametrów" << endl;
      }
      fig = argv[i];
      dimensions.clear();
    }
  }
  try {
    FigureData fd(fig, dimensions);
    shapes.push_back(fd);
  }
  catch (WrongParametersException ex){
    cout << fig;
    print_dimensions(dimensions);
    cout << " - Błędne wymiary" << endl;
  }
  catch (InvalidFigureException ex){
    cout << fig;
    print_dimensions(dimensions);
    cout << " - Błędna figura" << endl;
  }
  catch(WrongParametersNumberException ex){
    cout << fig;
    print_dimensions(dimensions);
    cout << " - Błędna liczba parametrów" << endl;
  }

  for (int i=0; i<shapes.size(); i++){
    FigureData shape = shapes[i];
    cout << shape.name << " perimeter:" << shape.perimeter << " area:" << shape.area << endl;
  }
  return 0;
}
