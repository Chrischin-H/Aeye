import java.util.Random;
import java.util.Vector;

// The following part should be completed by students.
// Students can modify anything except the class name and exisiting functions and varibles.

public class StudentAI extends AI {
    public class Node{
        Board currentBoard; //current state of board
        Vector<Vector<Move>> currentPossibleMoves; //current possible moves for player's turn
        int GameScore; // game score: [currPlayer # of pieces ] - [Opponent # pieces]
        int depth; // the depth limit of recurrsion
        Boolean MakeMove; //
        int playersMove;
    }
    //how to choose next move using recurrsion 
    public Move chooseMove(Node curr){
        //curr.currentBoard = new Board(this.board); ---> this line will be used in another fn right before it calls this one

        
        return (curr.currentPossibleMoves.get(0)).get(0); //for now will change later
    }



    public StudentAI(int col, int row, int k) throws InvalidParameterError {
        super(col, row, k);

        this.board = new Board(col, row, k);
        this.board.initializeGame();
        this.player = 2;
    }

    public Move GetMove(Move move) throws InvalidMoveError {
        if (!move.seq.isEmpty())
            board.makeMove(move, (player == 1) ? 2 : 1);// updates opponents turn/move and update current game state
        else
            player = 1;
        Vector<Vector<Move>> moves = board.getAllPossibleMoves(player);
        Random randGen = new Random();
        int index = randGen.nextInt(moves.size());
        int innerIndex = randGen.nextInt(moves.get(index).size());
        Move resMove = moves.get(index).get(innerIndex);
        board.makeMove(resMove, player);
        return resMove;
    }
}
