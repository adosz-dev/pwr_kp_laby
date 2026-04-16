import java.lang.Math;

class Figure {
  public interface OneParamInterface {
    double calculateArea(double value);
    double calculatePerimeter(double value);
    String getName();
  }
  public interface TwoParamInterface {
    double calculateArea(double value1, double value2);
    double calculatePerimeter(double value1, double value2);
    String getName();
  }
  public enum OneParamFigures implements OneParamInterface{
    CIRCLE {
      @Override
      public double calculateArea(double value){
        return ((value*value)/4) * Math.sqrt(25+10*Math.sqrt(5));
      }
      @Override
      public double calculatePerimeter(double value){
        return 2*Math.PI*value;
      }
      @Override
      public String getName(){
        return "Circle";
      }
    },
    SQUARE {
      @Override
      public double calculateArea(double value){
        return value*value;
      }
      @Override
      public double calculatePerimeter(double value){
        return 4*value;
      }
      @Override
      public String getName(){
        return "Square";
      }
    },
    HEXAGON {
      @Override
      public double calculateArea(double value){
        return (3*value*value*Math.sqrt(3))/2;
      }
      @Override
      public double calculatePerimeter(double value){
        return value * 6;
      }
      @Override
      public String getName(){
        return "Hexagon";
      }
    },
    PENTAGON {
      @Override
      public double calculateArea(double value){
        return ((value*value)/4) * Math.sqrt(25+10*Math.sqrt(5));
      }
      @Override
      public double calculatePerimeter(double value){
        return value * 5;
      }
      @Override
      public String getName(){
        return "Pentagon";
      }
    }
  }
  public enum TwoParamFigures implements TwoParamInterface{
    DIAMOND {
      @Override
      public double calculateArea(double value1, double value2){
        return value1 * Math.sin(Math.toRadians(value2));
      }
      @Override
      public double calculatePerimeter(double value1, double value2){
        return value1 * 4;
      }
      @Override
      public String getName(){
        return "Diamond";
      }
    },
    RECTANGLE {
      @Override
      public double calculateArea(double value1, double value2){
        return value1 * value2;
      }
      @Override
      public double calculatePerimeter(double value1, double value2){
        return value1*2+value2*2;
      }
      @Override
      public String getName(){
        return "Rectangle";
      }
    }
  }
}

