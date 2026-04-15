import java.lang.Math;

interface FigureInterface {
  public double getPerimeter();
  public double getArea();
  public String getName();
}

abstract class Figure implements FigureInterface {
  double value;

  public Figure(){}

  public Figure(double value){
    this.value = value;
  }
  public String getName(){
    return this.getClass().getSimpleName();
  }
}

class Pentagon extends Figure {
  public Pentagon(double side){
    super(side);
  }

  @Override
  public double getArea(){
    return ((super.value*super.value)/4) * Math.sqrt(25+10*Math.sqrt(5));
  }

  @Override
  public double getPerimeter(){
    return super.value * 5;
  }
}

class Hexagon extends Figure {
  public Hexagon(double side){
    super(side);
  }

  @Override
  public double getArea(){
    return (3*super.value*super.value*Math.sqrt(3))/2;
  }

  @Override
  public double getPerimeter(){
    return super.value * 6;
  }
}

class Circle extends Figure {
  public Circle(double radius){
    super(radius);
  }

  @Override
  public double getPerimeter(){
    return 2*Math.PI*super.value;
  }

  @Override
  public double getArea(){
    return Math.PI*super.value*super.value;
  }
}

abstract class Quadrangle extends Figure{
  double side1, side2, side3, side4, angle;
  public Quadrangle(double side1, double side2, double side3, double side4, double angle){
    this.side1 = side1;
    this.side2 = side2;
    this.side3 = side3;
    this.side4 = side4;
    this.angle = angle;
  }
  
  @Override
  public double getPerimeter(){
    return this.side1 + this.side2 + this.side3 + this.side4;
  }

  @Override
  public double getArea(){
    return this.side1 * this.side2 * Math.sin(Math.toRadians(this.angle));
  }
}

class Square extends Quadrangle {
  public Square(double side){
    super(side, side, side, side, 90);
  }
}

class Rectangle extends Quadrangle {
  public Rectangle(double side1, double side2){
    super(side1, side2, side1, side2, 90);
  }
}

class Diamond extends Quadrangle {
  public Diamond(double side, double angle){
    super(side, side, side, side, angle);
  }
}

