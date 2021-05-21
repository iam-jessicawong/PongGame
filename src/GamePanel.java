import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class GamePanel extends JPanel implements Runnable {

   static final int GAME_WIDTH = 1000;
   static final int GAME_HEIGHT = (int)(GAME_WIDTH * (0.5555));
   static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH, GAME_HEIGHT);
   static final int BALL_DIAMETER = 20;
   static final int PADDLE_WIDTH = 25;
   static final int PADDLE_HEIGHT = 100;
   Boolean isStarted;
   String winner;

   Thread gameThread;
   Image image;
   Graphics graphics;
   Random rand;
   Paddle pad1, pad2;
   Ball ball;
   Score score;

   GamePanel(){
      newPaddles();
      newBall();
      score = new Score(GAME_WIDTH, GAME_HEIGHT);

      this.setFocusable(true);
      this.addKeyListener(new AL());
      this.setPreferredSize(SCREEN_SIZE);
      this.isStarted = true;

      gameThread = new Thread(this);
      gameThread.start();
   }

   public void newBall() {
      rand = new Random();
      ball = new Ball((GAME_WIDTH/2) - (BALL_DIAMETER/2), rand.nextInt(GAME_HEIGHT-BALL_DIAMETER), BALL_DIAMETER, BALL_DIAMETER);
   }

   public void newPaddles() {
      pad1 = new Paddle(0, (GAME_HEIGHT/2) - (PADDLE_HEIGHT/2), PADDLE_WIDTH, PADDLE_HEIGHT, 1);
      pad2 = new Paddle(GAME_WIDTH - PADDLE_WIDTH, (GAME_HEIGHT/2) - (PADDLE_HEIGHT/2), PADDLE_WIDTH, PADDLE_HEIGHT, 2);
   }

   public void paint(Graphics g) {
      image = createImage(getWidth(), getHeight());
      graphics = image.getGraphics();
      draw(graphics);
      g.drawImage(image, 0, 0, this);
   }

   public void draw(Graphics g) {
      pad1.draw(g);
      pad2.draw(g);
      ball.draw(g);
      score.draw(g);
   }

   public void move() {
      pad1.move();
      pad2.move();
      ball.move();
   }

   public void checkCollision() {
      //bounce ball from top and bottom window edges
      if(ball.y <= 0)
         ball.setYDirection(-ball.yVelocity);
      if(ball.y >= GAME_HEIGHT - BALL_DIAMETER)
         ball.setYDirection(-ball.yVelocity);

      //bounce ball from paddles
      if(ball.intersects(pad1)) {
         ball.xVelocity = Math.abs(ball.xVelocity);
         ball.xVelocity++;

         if(ball.yVelocity > 0)
            ball.yVelocity++;
         else
            ball.yVelocity--;

         ball.setXDirection(ball.xVelocity);
         ball.setYDirection(ball.yVelocity);
      }

      if(ball.intersects(pad2)) {
         ball.xVelocity = Math.abs(ball.xVelocity);
         ball.xVelocity++;

         if(ball.yVelocity > 0)
            ball.yVelocity++;
         else
            ball.yVelocity--;

         ball.setXDirection(-ball.xVelocity);
         ball.setYDirection(-ball.yVelocity);
      }

      //stop paddles at window edge
      if(pad1.y <= 0)
         pad1.y = 0;
      if(pad1.y >= (GAME_HEIGHT - PADDLE_HEIGHT))
         pad1.y = GAME_HEIGHT - PADDLE_HEIGHT;
      if(pad2.y <= 0)
         pad2.y = 0;
      if(pad2.y >= (GAME_HEIGHT - PADDLE_HEIGHT))
         pad2.y = GAME_HEIGHT - PADDLE_HEIGHT;

      //memberikan score 1 untuk pemain yang berhasil, kemudian membuat paddle dan bola baru
      //pengecekan jika pemain 2 yang berhasil mendapatkan poin jika bolanya tidak dapat ditangkap oleh paddle pemain 1 atau bolanya keluar dari area pemain 1
      if(ball.x <= 0) {
         score.player2++;
         try {
            Thread.sleep(100);
         } catch (InterruptedException e) {
            e.printStackTrace();
         }
         newPaddles();
         newBall();
      }

      //pengecekan jika pemain 1 yang berhasil mendapatkan poin jika bolanya tidak dapat ditangkap oleh paddle pemain 2 atau bolanya keluar dari area pemain 2
      if(ball.x >= GAME_WIDTH - BALL_DIAMETER) {
         score.player1++;
         try {
            Thread.sleep(100);
         } catch (InterruptedException e) {
            e.printStackTrace();
         }
         newPaddles();
         newBall();
      }

   }

   public void run() {
      long lastTime = System.nanoTime();
      double amountOfTicks = 60.0;
      double ns = 1000000000/amountOfTicks;
      double delta = 0;

      while(this.isStarted) {
         long now = System.nanoTime();
         delta += (now - lastTime)/ns;
         lastTime = now;

         if(delta >= 1) {
            move();
            checkCollision();
            repaint();
            delta--;
         }

         if (score.player1 == 10 || score.player2 == 10) {
            this.isStarted = false;
            if(score.player1 == 10) {
               winner = "Player 1";
            }
            else {
               winner = "Player 2";
            }
            popUpWinner(winner);
         }
      }
   }

   public void popUpWinner(String winner) {
      int response = JOptionPane.showConfirmDialog(this, winner + " WIN!\n Wanna play again?", "Winner", JOptionPane.YES_NO_OPTION);
      if(response == JOptionPane.NO_OPTION) {
         this.isStarted = false;
      }
      else {
         score.player2 = 0;
         score.player1 = 0;
         this.isStarted = true;
      }
   }

   public class AL extends KeyAdapter{
      public void keyPressed(KeyEvent e) {
         pad1.keyPressed(e);
         pad2.keyPressed(e);
      }

      public void keyReleased(KeyEvent e) {
         pad1.keyReleased(e);
         pad2.keyReleased(e);
      }
   }

}
