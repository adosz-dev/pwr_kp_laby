import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.Box;
import javax.swing.BoxLayout;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Component;
import java.awt.Cursor;


public class Figures {
  Figures() {
    JFrame main_frame = new JFrame("new document - Figures");
    main_frame.setLayout(new BorderLayout());
    main_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    main_frame.setSize(1000, 800);
    main_frame.setVisible(true);

    main_frame.add(BuildTopBar(), BorderLayout.NORTH);
    main_frame.add(BuildSideBar(), BorderLayout.WEST);
  }

  public static void main(String[] args){
    new Figures();
  }
  private JPanel BuildTopBar(){
    JPanel bar = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 15));
    JButton save = new JButton("save");
    save.setFont(new Font("SansSerif", Font.PLAIN, 15));
    save.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    JButton load = new JButton("load");
    load.setFont(new Font("SansSerif", Font.PLAIN, 15));
    load.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    bar.add(save);
    bar.add(load);
    return bar;
  }
  private JPanel BuildSideBar(){
    JPanel bar = new JPanel();
    bar.setBackground(new Color(71,71, 71));
    bar.setLayout(new BoxLayout(bar, BoxLayout.Y_AXIS));
    bar.setMaximumSize(new Dimension(130, 100));
    
    bar.add(Box.createRigidArea(new Dimension(0, 15)));
    bar.add(SideBarButton("Rectangle"));
    bar.add(Box.createRigidArea(new Dimension(0, 8)));
    bar.add(SideBarButton("Circle"));
    bar.add(Box.createRigidArea(new Dimension(0, 8)));
    bar.add(SideBarButton("Polygon"));
    bar.add(Box.createVerticalGlue());
    return bar;
  }
  private JButton SideBarButton(String text){
    JButton btn = new JButton(text);
    btn.setFont(new Font("SansSerif", Font.BOLD, 18));
    btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
    btn.setAlignmentX(Component.CENTER_ALIGNMENT);
    btn.setBackground(Color.GRAY);
    btn.setForeground(Color.WHITE);
    return btn;
  }
}
