import java.awt.*;
import java.util.*;

public class Ball extends Rectangle{

   Random rand;
   int xVelocity, yVelocity;
   int initialSpeed = 5;

   Ball(int x, int y, int width, int height){
      super(x,y,width,height);
      rand = new Random();
      int randomXDirection = rand.nextInt(2);
      if(randomXDirection == 0)
         randomXDirection--;
      setXDirection(randomXDirection*initialSpeed);

      int randomYDirection = rand.nextInt(2);
      if(randomYDirection == 0)
         randomYDirection--;
      setYDirection(randomYDirection*initialSpeed);
   }

   public void setXDirection(int randomXDirection){
      xVelocity = randomXDirection;
   }

   public void setYDirection(int randomYDirection){
      yVelocity = randomYDirection;
   }

   public void move(){
      x += xVelocity;
      y += yVelocity;
   }

   public void draw(Graphics g){
      g.setColor(Color.white);
      g.fillOval(x,y,height,width);
   }

}
