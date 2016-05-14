package com.Application;
import java.util.Random;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
class Turn {
	
    enum NextMove {

        O, X
    }
    NextMove next;
}
public class Main extends Application {

    Label cell1, cell2, cell3,
    cell4, cell5, cell6,
    cell7, cell8, cell9;
Label[] cells;
    @Override
    public void start(Stage stage) {
    	BorderPane root=new BorderPane();
    	 GridPane grid = new GridPane();
    	    Board board = new Board();
    	    Turn boardTurn = new Turn();

         cell1 = new Label();
         cell2 = new Label();
         cell3 = new Label();
         cell4 = new Label();
         cell5 = new Label();
         cell6 = new Label();
         cell7 = new Label();
         cell8 = new Label();
         cell9 = new Label();

         cells = new Label[]{cell1, cell2, cell3,
             cell4, cell5, cell6,
             cell7, cell8, cell9};
         
         for (Label cell : cells) {
             cell.setMinSize(128, 128);
             boolean isUsed = false;
             cell.setUserData(isUsed);
         }
         grid.addRow(0, cell1, cell2, cell3);
         grid.addRow(1, cell4, cell5, cell6);
         grid.addRow(2, cell7, cell8, cell9);
         grid.setAlignment(Pos.CENTER);
         grid.setMaxSize(800, 800);
         grid.setStyle("-fx-border: 2px solid; -fx-border-color: black;");
         grid.setGridLinesVisible(true);
         boardTurn.next = Turn.NextMove.O;
         Image OPic = new Image(getClass().getResourceAsStream("/O.png"));
         Image XPic = new Image(getClass().getResourceAsStream("/X.png"));
         root.setCenter(grid);
         HBox h=new HBox();
         Button close=new Button("Close Game");
         Button combt=new Button("Computer First!");
         close.setPrefSize(100, 20);
         combt.setPrefSize(100, 20);
         Label l=new Label();
         h.getChildren().addAll(close,combt,l);
         root.setBottom(h);
         Scene scene = new Scene(root, 600,600);
         stage.getIcons().add(new Image(Main.class.getResourceAsStream("/icon.png")));
         stage.setScene(scene);
         stage.setTitle("Tic Tac Toe");
         stage.show();
      
        	for (Label cell : cells) {
             cell.setOnMouseClicked(event -> {
                     if (((boolean) cell.getUserData()) == false) {
                         cell.setGraphic(new ImageView(XPic));
                         
                         int index = -1;
                         for (int i = 0; i < cells.length; ++i) {
                             if (cell == cells[i]) {
                                 index = i;
                             }
                         }
                         board.placeAMove(new Point(index / 3, index % 3), 2);
                       
                         System.out.println("Placed a move at: (" + index / 3 + ", " + index % 3 + ")");
                         boolean mark = true;
                         int next = board.returnNextMove();

                         if (next != -1) {   //If the game isn't finished yet!   
                             int indexCell = next;

                             cells[indexCell].setGraphic(new ImageView(OPic));
                             cells[indexCell].setUserData(mark); //Used!
                             board.placeAMove(new Point(indexCell / 3, indexCell % 3), 1);
                             cell.setUserData(mark);
                         }
                         if(board.hasOWon())
                         {
                         	l.setText("Congrats. You WON");

                         }
                         if(board.hasXWon())
                         {
                        	l.setText("Computer WON");
                         }
                         if(board.availablePoints.isEmpty())
                         {
                        	 l.setText("Game OVER!");
                         }
                 }});
        	}
        	//computer play first 
        	combt.setOnMouseClicked((event) -> { 
        		int index = new Random().nextInt(9);
            cells[index].setGraphic(new ImageView(OPic));
            cells[index].setUserData(new Boolean(true));
            board.placeAMove(new Point(index / 3, index % 3), 1);
            boardTurn.next = Turn.NextMove.X;
           

        });
        	close.setOnAction(e->
        	{
        		stage.close();
        	});
      
    }
}