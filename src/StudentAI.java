import java.util.Iterator;
import java.util.Random;
import java.util.Vector;

// The following part should be completed by students.
// Students can modify anything except the class name and exisiting functions and varibles.

public class StudentAI extends AI {
    public class Node{
        Board currentBoard; //current state of board
        Vector<Vector<Move>> currentPossibleMoves; //current possible moves for player's turn
        int GameScore; // game score at the end of each recurrsion: [currPlayer # of pieces ] - [Opponent # pieces] --> use absolute value of GameScore to decide which move to go with 
        int depth; // the current depth, [depth  += 1] ---> once each player has made a move in the tree and resets after each iteration of currentPossibleMoves in the root node  
        int depthMax; // max depth of recurssion 
        //Boolean MakeMove;
        int playersMove;
    }
    //how to choose next move using recurrsion 
    public Move chooseMove(Node curr){
        //Move decision;
        
        curr.currentPossibleMoves = curr.currentBoard.getAllPossibleMoves(curr.playersMove);
        Iterator<Vector<Move>> temp = curr.currentPossibleMoves.iterator();
        
        while(temp.hasNext()){// iterates tthrough all the vectors in curr.currentPossibleMoves
            Iterator<Move> tempMoves = temp.next().iterator();
            while(tempMoves.hasNext()){ // iterates through elements inside the vector that the first iterator gave
                
            } 
            curr.currentBoard = new Board(this.board); //reset to the current board after going through possible moves of one checker
        }

        
        return (curr.currentPossibleMoves.get(0)).get(0); //for now will change later
    }

    public int gamescore(Node curr){
        return 0;
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
            return ; //hard code default first move, easier to just do here instead of making a fn to be used only once
        }
        /*Vector<Vector<Move>> moves = board.getAllPossibleMoves(player);
        Random randGen = new Random();
        int index = randGen.nextInt(moves.size());
        int innerIndex = randGen.nextInt(moves.get(index).size());
        Move resMove = moves.get(index).get(innerIndex);
        board.makeMove(resMove, player);
        */
        Node Game = new Node(); 
        Game.playersMove = this.player; //tells node if youre 1st or 2nd player
        Game.depthMax = 7; //depth Max of recurrsion --> really pushing it with 7 but we can just adjust here in one location 
        Game.currentBoard = new Board(this.board); //creates the current node 
        Move temp = chooseMove(Game);
        this.board.makeMove(temp, player);
        return temp;
    }
}
