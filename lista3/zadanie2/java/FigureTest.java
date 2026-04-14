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
  String fig;
  ArrayList<Double> dimensions;

  String name;
  double area;
  double perimeter;

  private void saveDataOneParam(Figure.OneParamFigures shape){
    this.name = shape.getName();
    this.area = shape.calculateArea(this.dimensions.get(0));
    this.perimeter = shape.calculatePerimeter(this.dimensions.get(0));
  }

  private void saveDataTwoParam(Figure.TwoParamFigures shape, double value1, double value2){
    this.name = shape.getName();
    this.area = shape.calculateArea(value1, value2);
    this.perimeter = shape.calculatePerimeter(value1, value2);
  }

  private void getQuadrangle()
    throws WrongParametersNumberException,
           WrongParametersException{
    if (dimensions.size() < 2 || dimensions.size() > 5){
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
        Figure.OneParamFigures s = Figure.OneParamFigures.SQUARE;
        saveDataOneParam(s);
      }
      else {
        Figure.TwoParamFigures c = Figure.TwoParamFigures.RECTANGLE;
        saveDataTwoParam(c, dims_temp.get(0), dims_temp.get(1));
      }
    }
    else {
      if (dims_temp.size() != 1){
        throw new WrongParametersException();
      }
      Figure.TwoParamFigures d = Figure.TwoParamFigures.DIAMOND;
      saveDataTwoParam(d, dims_temp.get(0), dims_temp.get(1));
    }
  }
  private void getFigureData() 
      throws WrongParametersNumberException,
          InvalidFigureException,
          WrongParametersException{
    if (this.fig.equals("q")){
      try {
        getQuadrangle();
      }
      catch (WrongParametersException ex){
        throw new WrongParametersException();
      }
      catch (WrongParametersNumberException ex){
        throw new WrongParametersNumberException();
      }
    }
    else 
      if (this.dimensions.size() == 1) {
      if (this.fig.equals("c")) {
        Figure.OneParamFigures c = Figure.OneParamFigures.CIRCLE;
        saveDataOneParam(c);
      }
      else if (this.fig.equals("p")) {
        Figure.OneParamFigures p = Figure.OneParamFigures.PENTAGON;
        saveDataOneParam(p);
      }
      else if (this.fig.equals("h")) {
        Figure.OneParamFigures h = Figure.OneParamFigures.HEXAGON;
        saveDataOneParam(h);
      }
      else {
        throw new InvalidFigureException();
      }
    }
    else {
      throw new WrongParametersNumberException();
    }
  }
  public FigureData(String fig, ArrayList<Double> dimensions) 
      throws WrongParametersNumberException,
           WrongParametersException,
           InvalidFigureException{
    this.fig = fig;
    this.dimensions = dimensions;
    try {
      getFigureData();
    }
    catch (WrongParametersException ex){
      throw new WrongParametersException();
    }
    catch (WrongParametersNumberException ex){
      throw new WrongParametersNumberException();
    }
    catch (InvalidFigureException ex){
      throw new InvalidFigureException();
    }
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
    ArrayList<FigureData> shapes = new ArrayList<FigureData>();
    ArrayList<Double> dimensions = new ArrayList<Double>();

    for (int i=1; i<args.length; i++){
      try {
        num = Double.parseDouble(args[i]);
        dimensions.add(num);
      }
      catch (NumberFormatException e){
        try {
          FigureData fd = new FigureData(fig, dimensions);
          shapes.add(fd);
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
    try {
      FigureData fd = new FigureData(fig, dimensions);
      shapes.add(fd);
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
      FigureData shape = shapes.get(i);
      System.out.println(shape.name + " perimeter:" + shape.perimeter + " area:" + shape.area);
    }
  }
}
