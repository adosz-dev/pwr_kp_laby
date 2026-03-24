#include <exception>
#include <stdexcept>

class pascaltrianglerow{
  private:
    int *row;
    int *previous_row;
    int *temp_row;
    int row_length;
    void create_row(int n);
  public:
    class OutOfRangeError : public std::out_of_range{
      public:
        OutOfRangeError() : std::out_of_range("Index out of range") {}
    };
    class RowNumberError : public std::logic_error{
      public:
        RowNumberError() : std::logic_error("Row number must be positive") {}
    };
    int get_value(int i);
    pascaltrianglerow(int n);
    ~pascaltrianglerow();
};
