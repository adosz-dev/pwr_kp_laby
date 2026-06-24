import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Function;

/**
 * Serwer BST obsługujący wielu klientów jednocześnie
 */
public class TreeServer {
  /**
   * Uruchamia serwer na porcie 8000
   * @param args argumenty wiersza poleceń (nieużywane)
   */
  public static void main(String[] args) {
    int port = 8000;

    try {
      ServerSocket serverSocket = new ServerSocket(port);
      System.out.println("Serwer nasłuchuje na porcie " + port);

      while (true) {
        Socket clientSocket = serverSocket.accept();
        System.out.println("Nowy klient: " + clientSocket.getInetAddress());

        new Thread(() -> {
          handleClient(clientSocket);
        }).start();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Obsługuje połączenie z jednym klientem
   * @param socket socket połączonego klienta
   */
  private static void handleClient(Socket socket) {
    try {
      BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

      String message = in.readLine();
      String[] parts = message.trim().split("\\s+");
      if (!parts[0].equals("type")) {
        socket.close();
        System.out.println("Nie otrzymano typu");
        return;
      }

      switch (parts[1]) {
        case "Integer":
          // Objekt::funkcja - referencja do funkcji
          handleTree(new Tree<Integer>(), in, out, Integer::parseInt, socket);
          break;
        case "String":
          handleTree(new Tree<String>(), in, out, s -> s, socket);
          break;
        case "Double":
          handleTree(new Tree<Double>(), in, out, Double::parseDouble, socket); 
          break;
        default:
          socket.close();
          System.out.println("Otrzymano błędny typ");
          return;
      }

      socket.close();
    } catch (IOException e) {
      System.err.println("Błąd: " + e.getMessage());
    }
  }

  /**
   * Przetwarza polecenia BST od klienta
   * @param tree drzewo do operacji
   * @param in strumień wejściowy od klienta
   * @param out strumień wyjściowy do klienta
   * @param parse funkcja parsowania wartości z ciągu znaków
   * @param socket socket klienta
   */
  private static <T extends Comparable<T>> void handleTree(
    Tree<T> tree,
    BufferedReader in,
    PrintWriter out,
    Function<String, T> parse,
    Socket socket
  ) throws IOException {
    String message;
    while ((message = in.readLine()) != null) {
      System.out.println("Otrzymano: " + message);
      String[] parts = message.trim().split("\\s+");
      switch (parts[0]) {
        case "insert": tree.insert(parse.apply(parts[1])); out.println("OK"); break;
        case "remove": tree.remove(parse.apply(parts[1])); out.println("OK"); break;
        case "search": out.println(tree.search(parse.apply(parts[1]))); break;
        case "draw":
          // wysyłam wysokość do obliczania odstępów
          out.println("DRAW_METADATA");
          out.println(tree.getHeight());
          out.println("DRAW_START");
          tree.draw(out);
          out.println("DRAW_END");
          break;
        case "quit": socket.close(); return;
      }
    }
  }
}
