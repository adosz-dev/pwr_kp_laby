import java.util.ArrayList;

class FigureData {
  public static class WrongParametersNumberException extends Exception{
    public WrongParametersNumberException(){
      super("Zła liczba parametrów");
    }
  }
  public static class WrongParametersException extends Exception {
    public WrongParametersException(){
      super("Błędne parametry");
    }
  }
  public static class InvalidFigureException extends Exception{
    public InvalidFigureException(){
      super("Błędna figura");
    }
  }
  private FigureInterface getQuadrangle()
    throws WrongParametersNumberException,
           WrongParametersException{
    if (this.dimensions.size() < 2 || this.dimensions.size() > 5){
      throw new WrongParametersNumberException();
    }
    ArrayList<Double> dims_temp = new ArrayList<Double>();
    double angle = this.dimensions.get(this.dimensions.size()-1);
    for (int i=0; i<this.dimensions.size() - 1; i++){
      if (!dims_temp.contains(this.dimensions.get(i))){
        dims_temp.add(this.dimensions.get(i));
      }
    }
    if (angle == 90){
      if (dims_temp.size() < 1 || dims_temp.size() > 2){
        throw new WrongParametersException();
      }
      if (dims_temp.size() == 1){
        Square s = new Square(this.dimensions.get(0));
        return s;
      }
      else {
        Rectangle r = new Rectangle(dims_temp.get(0), dims_temp.get(1));
        return r;
      }
    }
    else {
      if (dims_temp.size() != 1){
        throw new WrongParametersException();
      }
      Diamond d = new Diamond(dims_temp.get(0), angle);
      return d;
    }
  }
  String fig;
  ArrayList<Double> dimensions;
  public FigureInterface getFigureData() 
      throws WrongParametersNumberException,
          InvalidFigureException,
          WrongParametersException{
    if (this.fig.equals("q")){
      return getQuadrangle();
    }
    else if (this.dimensions.size() == 1) {
      if (this.fig.equals("c")) {
        Circle c = new Circle(this.dimensions.get(0));
        return c;
      }
      else if (this.fig.equals("p")) {
        Pentagon p = new Pentagon(this.dimensions.get(0));
        return p;
      }
      else if (this.fig.equals("h")) {
        Hexagon h = new Hexagon(this.dimensions.get(0));
        return h;
      }
      else {
        throw new InvalidFigureException();
      }
    }
    else {
      throw new WrongParametersNumberException();
    }
  }
  public FigureData(String fig, ArrayList<Double> dimensions){
    this.fig = fig;
    this.dimensions = dimensions;
  }
}

public class FigureTest{
  static public void main(String[] args){
    if (args.length < 2){
      System.out.println("Zła liczba argumentów");
      return;
    }
    String fig = args[0];
    double num;
    ArrayList<FigureInterface> shapes = new ArrayList<FigureInterface>();
    ArrayList<Double> dimensions = new ArrayList<Double>();

    for (int i=1; i<args.length; i++){
      try {
        num = Double.parseDouble(args[i]);
        dimensions.add(num);
      }
      catch (NumberFormatException e){
        FigureData fd = new FigureData(fig, dimensions);
        try {
          shapes.add(fd.getFigureData());
        }
        catch (FigureData.WrongParametersNumberException ex){
          System.out.println(fig + " " + dimensions + " - Błędne parametry");
        }
        catch (FigureData.InvalidFigureException ex){
          System.out.println(fig + " " + dimensions + " - Błędna figura");
        }
        catch(FigureData.WrongParametersException ex){
          System.out.println(fig + " " + dimensions + " - Błędne parametry");
        }
        fig = args[i];
        dimensions = new ArrayList<Double>();
      }
    }
    FigureData fd = new FigureData(fig, dimensions);
    try {
      shapes.add(fd.getFigureData());
    }
    catch (FigureData.WrongParametersNumberException ex){
      System.out.println(fig + " " + dimensions + " - Błędne parametry");
    }
    catch (FigureData.InvalidFigureException ex){
      System.out.println(fig + " " + dimensions + " - Błędna figura");
    }
    catch(FigureData.WrongParametersException ex){
      System.out.println(fig + " " + dimensions + " - Błędne parametry");
    }

    for (int i=0; i<shapes.size(); i++){
      FigureInterface shape = shapes.get(i);
      System.out.println(shape.getName() + " perimeter:" + shape.getPerimeter() + " area:" + shape.getArea());
    }
  }
}
