import java.util.Iterator;
import java.util.Random;
import java.util.Vector;

// The following part should be completed by students.
// Students can modify anything except the class name and exisiting functions and varibles.




//during the recursion when predicting the move of the opponent,
//our ai will predict that the opponent will choose a move that makes them be the ones winning

//our ai will try and choose the move that will put us in the lead 7 turns later(so it might bait the opponent and stuff idk)
//but if you have a better idea let me know idk I just thought this would be really good and not so hard to do

public class StudentAI extends AI {
    public class Node{
       // Board currentBoard; //current state of board
        //Vector<Vector<Move>> currentPossibleMoves; //current possible moves for player's turn
        int GameScore; // game score at the end of each recurrsion: [currPlayer # of pieces ] - [Opponent # pieces] --> use absolute value of GameScore to decide which move to go with 
        //int depth; // the current depth, [depth  += 1] ---> once each player has made a move in the tree and resets after each iteration of currentPossibleMoves in the root node  
        int depthMax; // max depth of recurssion
        int i; //keeps track of which 
        int j; 
        Boolean MakeMove;
        //int currplayersMove; // keeps track of whos turn 
        int trueplayersmove; // Aeye true player#
    }
    //how to choose next move using recurrsion 
    public Move chooseMove(Node curr, int depth, Board currBoard, int currplayersMove){
        //Move decision;

        System.out.println("move?");


        Vector<Vector<Move>> currentPossibleMoves = currBoard.getAllPossibleMoves(currplayersMove); //gets all moves of current player's
        Iterator<Vector<Move>> temp = currentPossibleMoves.iterator();
        
        while(temp.hasNext()){// iterates through all the vectors in curr.currentPossibleMoves
            var tmI = temp.next();
            Iterator<Move> tempMoves = tmI.iterator();
            
            while(tempMoves.hasNext()){ // iterates through elements inside the vector that the first iterator gave
                var tmJ = tempMoves.next();
                if(curr.trueplayersmove == currplayersMove){//our moves
                    
                    try{

                        Board tempB = new Board(currBoard);
                        tempB.makeMove(tmJ,currplayersMove);
                        if(currplayersMove == 2){

                            chooseMove(curr,depth,tempB,1);
                        }
                        else{
                            chooseMove(curr,depth,tempB,2);
                        }


                    }
                    catch(Exception e){}
                }
                else{
                    try{

                        Board tempB = new Board(currBoard);
                        tempB.makeMove(tmJ,currplayersMove);
                       
                        if(depth != curr.depthMax){
                            if(currplayersMove == 2){

                                chooseMove(curr,depth + 1,tempB,1);
                            }
                            else{
                                chooseMove(curr,depth + 1,tempB,2);
                            }
                        }
                        else{
                            int tempGS = gamescore(tempB, curr.trueplayersmove);
                            if(tempGS > curr.GameScore){
                                curr.GameScore = tempGS;
                                curr.MakeMove = true;
                            }
                        }


                    }
                    catch(Exception e){}
                    
                }

                if(curr.MakeMove && depth == 0){
                    
                    curr.i = currentPossibleMoves.indexOf(tmI);
                    curr.j = currentPossibleMoves.get(curr.i).indexOf(tmJ);
                    curr.MakeMove = false;
                }

            }
            
        }

        
        return (currentPossibleMoves.get(curr.i)).get(curr.j);
    }

    public int gamescore(Board curr, int player){
        int temp = 0;
        if(player == 1){
            temp = curr.blackCount - curr.whiteCount;
        }
        else{
            temp = curr.whiteCount - curr.blackCount;
        }
        
        return temp;
    }

    public StudentAI(int col, int row, int k) throws InvalidParameterError {
        super(col, row, k);

        this.board = new Board(col, row, k);
        this.board.initializeGame();
        this.player = 2;
    }

    public Move GetMove(Move move) throws InvalidMoveError {
        if (!move.seq.isEmpty()){
            board.makeMove(move, (player == 1) ? 2 : 1);// updates opponents turn/move and update current game state

        }
        else{
            player = 1; // should only come here if its first move of game, therfore default first move http://www.quadibloc.com/other/bo1211.htm

            int col;  //initial col
            int row;  //initial row
            int newcol; //new col
            int newrow; //new row
            boolean even = true; //see if number of row occupied is even or odd
            if((board.p % 2) != 0)
            {
                even = false;
            }

            if(even) //if even, we move one in the middle to next row and col -1
            {
                col = board.col/2;
                row = board.p-1;
                newcol = board.col/2 + -1;
                newrow = board.p;
            }
            else // if odd, we move one in the middle to next row and col +1
            {
                col = board.col/2 -1 ;
                row = board.p-1;
                newcol = board.col/2;
                newrow = board.p;
            }

            Move firstmove = new Move("("+row+","+col +")"+"-"+"("+newrow+","+newcol +")"); // make first move

            return firstmove; //hard code default first move, easier to just do here instead of making a fn to be used only once
            /*
            bascically since very front checkers are placed at odd columns if p(row occupied) is odd
            and even columns  if p is even. So we have to figure out whether p is odd or even
             and choose which one is in the middle and make first move accordingly.
             */
        }

        /*Vector<Vector<Move>> moves = board.getAllPossibleMoves(player);
        Random randGen = new Random();
        int index = randGen.nextInt(moves.size());
        int innerIndex = randGen.nextInt(moves.get(index).size());
        Move resMove = moves.get(index).get(innerIndex);
        board.makeMove(resMove, player);
        */
        
        Node Game = new Node(); 
         //tells node if youre 1st or 2nd player
        Game.trueplayersmove = this.player;
        Game.i = 0;
        Game.j = 0;
        Game.GameScore = gamescore(this.board, this.player);
        Game.MakeMove = false;
        Game.depthMax = 7; //depth Max of recurrsion --> really pushing it with 7 but we can just adjust here in one location 
        //Game.currentBoard = new Board(this.board); //creates the current node 
        Move temp = chooseMove(Game, 0, new Board(this.board), this.player);
        this.board.makeMove(temp, player);
        return temp;
    }
}
