import java.awt.*;

public class Score extends Rectangle{

   static int GAME_WIDTH, GAME_HEIGHT;
   int player1, player2;      //variabel untuk menampung score untuk pemain1 dan pemain2

   Score(int GAME_WIDTH, int GAME_HEIGHT){
      Score.GAME_WIDTH = GAME_WIDTH;
      Score.GAME_HEIGHT = GAME_HEIGHT;
   }

   //fungsi untuk menampilkan skor untuk kedua player
   public void draw (Graphics g){
      g.setColor(Color.white);
      g.setFont(new Font("Consolas", Font.PLAIN, 60));

      //untuk membuat garis tengah seperti net pembatas untuk area player1 dan player2
      g.drawLine(GAME_WIDTH/2, 0, GAME_WIDTH/2, GAME_HEIGHT);

      //menampilkan skor untuk kedua player
      g.drawString(String.valueOf(player1/10) + String.valueOf(player1%10), (GAME_WIDTH/2)-85, 50);
      g.drawString(String.valueOf(player2/10) + String.valueOf(player2%10), (GAME_WIDTH/2)+20, 50);
   }

}
