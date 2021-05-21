import java.awt.*;
import javax.swing.*;

public class Frame extends JFrame {
   GamePanel gp;

   Frame(){
      gp = new GamePanel();
      this.add(gp);
      this.setTitle("Pong");
      this.setResizable(false);
      this.setBackground(Color.green);
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      this.pack();
      this.setVisible(true);
      this.setLocationRelativeTo(null);
   }
}
