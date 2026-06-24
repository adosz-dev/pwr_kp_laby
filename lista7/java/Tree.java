import java.util.ArrayList;
import java.io.PrintWriter;
import java.util.List;

/**
 * Drzewo Binarne Przeszukiwań
 *
 * @param <T> typ przechowywanych wartości
 */
public class Tree<T extends Comparable<T>> {
  private TreeElem<T> root;
  private int height = 0;

  /**
   * Node drzewa
   *
   * @param <T> typ przechowywanej wartości
   */
  private static class TreeElem<T> {
    T element;
    TreeElem<T> left;
    TreeElem<T> right;

    /**
     * Tworzy węzeł z podaną wartością
     * @param element wartość węzła
     */
    TreeElem(T element) {
        this.element = element;
    }
  }

  /**
   * Tworzy puste drzewo
   */
  public Tree() {
    this.root = null;
  }

  /**
   * Tworzy drzewo z jednym elementem jako korzeniem
   * @param element wartość korzenia
   */
  public Tree(T element) {
    this.root = new TreeElem<>(element);
  }

  /**
   * Rekurencyjnie wstawia element do poddrzewa
   * @param element wstawiany element
   * @param node korzeń poddrzewa
   * @return korzeń poddrzewa po wstawieniu
   */
  private TreeElem<T> insertHelper(T element, TreeElem<T> node) {
    if (node == null) { 
      return new TreeElem<>(element);
    }
    int cmp = element.compareTo(node.element);
    if (cmp < 0){
      node.left = insertHelper(element, node.left);
    }
    else if (cmp > 0) {
      node.right = insertHelper(element, node.right);
    }
    return node;
  }

  /**
   * Wstawia element do drzewa
   * @param element wstawiany element
   */
  public void insert(T element) {
    this.root = insertHelper(element, this.root);
  }

  /**
   * Rekurencyjnie przeszukuje poddrzewo
   * @param element szukany element
   * @param node korzeń poddrzewa
   * @return true jeśli element istnieje, false w przeciwnym razie
   */
  private boolean searchHelper(T element, TreeElem<T> node) {
    if (node == null) {
      return false;
    }
    int cmp = element.compareTo(node.element);
    if (cmp == 0) {
      return true;
    }
    if (cmp < 0) {
      return searchHelper(element, node.left);
    }
    return searchHelper(element, node.right);
  }

  /**
   * Sprawdza czy element istnieje w drzewie
   * @param element szukany element
   * @return true jeśli znaleziono, false w przeciwnym razie
   */
  public boolean search(T element) {
    return searchHelper(element, this.root);
  }

  /**
   * Znajduje węzeł z minimalną wartością w poddrzewie
   * @param node korzeń poddrzewa
   * @return węzeł z minimalną wartością
   */
  private TreeElem<T> findMin(TreeElem<T> node) {
    while (node.left != null) {
      node = node.left;
    }
    return node;
  }

  /**
   * Rekurencyjnie usuwa element z poddrzewa
   * @param element usuwany element
   * @param node korzeń poddrzewa
   * @return korzeń poddrzewa po usunięciu
   */
  private TreeElem<T> removeHelper(T element, TreeElem<T> node) {
    if (node == null) {
      return null;
    }
    int cmp = element.compareTo(node.element);
    if (cmp > 0) {
      node.right = removeHelper(element, node.right);
    } 
    else if (cmp < 0) {
      node.left = removeHelper(element, node.left);
    } 
    else {
      if (node.left == null && node.right == null){
        return null;
      }
      if (node.left == null){
        return node.right;
      }
      if (node.right == null){ 
        return node.left;
      }
      TreeElem<T> s = findMin(node.right);
      node.element = s.element;
      node.right = removeHelper(s.element, node.right);
    }
    return node;
  }

  /**
   * Usuwa element z drzewa
   * @param element usuwany element
   */
  public void remove(T element) {
    this.root = removeHelper(element, this.root);
  }

  /**
   * Rekurencyjnie wyznacza wysokość drzewa
   * @param curr głębokość bieżącego węzła
   * @param node bieżący węzeł
   */
  private void getHeightHelper(int curr, TreeElem<T> node) {
    if (node == null) {
      if (curr > this.height) {
        this.height = curr;
      }
      return;
    }
    curr++;
    getHeightHelper(curr, node.left);
    getHeightHelper(curr, node.right);
  }

  /**
   * Oblicza wysokość drzewa
   * @return liczba poziomów (0 dla pustego drzewa)
   */
  public int getHeight() {
    this.height = 0;
    getHeightHelper(0, this.root);
    return this.height;
  }

  /**
   * Wypisuje drzewo poziomami
   * Puste węzły rysowane jako gwiazdki
   * @param pw strumień wyjściowy
   */
  public void draw(PrintWriter pw) {
    int h = getHeight();
    if (h == 0) return;

    List<TreeElem<T>> level = new ArrayList<>();
    level.add(root);

    for (int d = 0; d < h; d++) {
      int indent = (int) Math.pow(2, h - 1 - d) - 1;
      int spacing = (int) Math.pow(2, h - d) - 1;

      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < indent; i++) sb.append(' ');

      List<TreeElem<T>> next = new ArrayList<>();
      for (int i = 0; i < level.size(); i++) {
        if (i > 0) {
          for (int s = 0; s < spacing; s++) sb.append(' ');
        }
        TreeElem<T> node = level.get(i);
        if (node != null) {
          sb.append(node.element);
          next.add(node.left);
          next.add(node.right);
        } else {
          sb.append('*');
          next.add(null);
          next.add(null);
        }
      }
      pw.println(sb);
      level = next;
    }
  }
}
