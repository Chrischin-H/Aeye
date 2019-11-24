import java.util.Iterator;
import java.util.Random;
import java.util.Vector;

import java.util.ArrayList;


public class StudentAI extends AI {
    
    public class Node{
        int j;
        int i;
        int MAX_DEPTH;
        Boolean MAX;
        Move Make;
        int score;
        Vector<Iterator<Vector<Move>>> temp1;
        Vector<Iterator<Move>> temp2; 
        Vector<Move> temp3; 
        
    }

    Node NODE;
    //how to choose next move using recurrsion
    public int FSM_chooseMove(int depth,int player, Board currBoard, boolean max){
        int k = 1; //states
        //index
        int i = -1;
        int j = -1;

        int score = 0;
        
        Vector<Vector<Move>> currentPossibleMoves = currBoard.getAllPossibleMoves(player); ;

        

        while(true){
            switch(k){
                case 1:{ 

                    this.NODE.temp1.add(currentPossibleMoves.iterator());
                    if(this.NODE.temp1.get(this.NODE.temp1.size() - 1).hasNext()){
                        this.NODE.temp2.add(this.NODE.temp1.get(this.NODE.temp1.size() - 1).next().iterator());
                        System.out.println("IN 1");
                        if(depth == this.NODE.MAX_DEPTH){
                            i += 1;
                        }

                    }
                    k = 2;
                    break;
                }
                case 2:{  
 
                    if(this.NODE.temp2.get(this.NODE.temp2.size() - 1).hasNext()){
                        System.out.println("IN 2");
                        this.NODE.temp3.add((this.NODE.temp2.get(this.NODE.temp2.size() - 1).next()));
                        System.out.println(this.NODE.temp3.get(this.NODE.temp3.size() - 1).seq.get(0));
                        if(depth == this.NODE.MAX_DEPTH){
                            j += 1;
                        }
                        k = 3;
                        
                    }
                    else if(this.NODE.temp1.get(this.NODE.temp1.size() - 1).hasNext()){
                        this.NODE.temp2.remove(this.NODE.temp2.size() - 1);
                        this.NODE.temp2.add(this.NODE.temp1.get(this.NODE.temp1.size() - 1).next().iterator());

                        if(depth == this.NODE.MAX_DEPTH){
                            i += 1;
                        }
                        k = 2;
                        break;
                            
                    }
                    else{
                        if(depth == this.NODE.MAX_DEPTH){
                            this.NODE.Make = (currentPossibleMoves.get(this.NODE.i)).get(this.NODE.j);
                            
                        }
                        return score; 
                    }
                   
                    break;
                }
                case 3:{
                    
                    try{

                        System.out.println(this.NODE.temp3.get(this.NODE.temp3.size()-1));
                        currBoard.makeMove(this.NODE.temp3.get(this.NODE.temp3.size() - 1), player);
                        k = 4;

                    }

                    catch(Exception e){}
                    
                    if(depth == 0){
                        k = 5;
                    }
                    break;
                }
                case 4:{
                    System.out.println("in 4");

                    if(player == 2){
                        player = 1;
                    }
                    else{
                        player = 2;
                    }
                    
                    score = FSM_chooseMove(depth - 1, player, currBoard, !max);
                    
                    this.NODE.temp1.remove(this.NODE.temp1.size() - 1);
                    this.NODE.temp2.remove(this.NODE.temp2.size() - 1);
                    this.NODE.temp3.remove(this.NODE.temp3.size() - 1);

                    k = 5;
                    
                    break;
                }
                case 5:{
                    
                    if(depth == 0){
                        score = gamescore(currBoard, player);
                    }
                
                    if(max){
                        if(this.NODE.score < score){
                            if(depth == this.NODE.MAX_DEPTH){
                                this.NODE.i = i;
                                this.NODE.j = j;
                            }
                            this.NODE.score = score;
                        }
                    }
                    else{
                        if(this.NODE.score > score){
                                this.NODE.score = score;
                        }
                    }
                    k = 6;
                    break;
                }
                case 6:{
                    currBoard.Undo();

                    k = 2;
                    break;
                }
            }
        }
    }

    
    public int gamescore(Board curr, int player){
        int bonusPoints = 0;
        int temp = 0;
        Board.Saved_Move lastMove = curr.saved_move_list.get(curr.saved_move_list.size() - 1);
        if(player == 1){ //starts at 2nd row -> y > size(row) - 4 //check if isking already, if so points are normal

            if((curr.board.get(lastMove.made_move.seq.get(lastMove.made_move.seq.size() - 1).y)).get(lastMove.made_move.seq.get(lastMove.made_move.seq.size() - 1).x).isKing && !lastMove.become_king){
                
                return (curr.blackCount - curr.whiteCount);

            }
            else{
                if(lastMove.made_move.seq.get(lastMove.made_move.seq.size() - 1).y > (curr.row - 4)){
                    bonusPoints = (lastMove.become_king) ? 5 : 3; //if last move became king and 
                    
                }
            }
            temp = (curr.blackCount - curr.whiteCount) + bonusPoints;
            
        }
        else{//starts at [size(row) - 2] -> y < 4 , row = 0 , 1 , ..... last 3 rows are more points
            
            if((curr.board.get(lastMove.made_move.seq.get(lastMove.made_move.seq.size() - 1).y)).get(lastMove.made_move.seq.get(lastMove.made_move.seq.size() - 1).x).isKing && !lastMove.become_king){
                
                return (curr.whiteCount - curr.blackCount);

            }
            else{
                if(lastMove.made_move.seq.get(lastMove.made_move.seq.size() - 1).y < 4){
                    bonusPoints = (lastMove.become_king) ? 5 : 3; //if last move became king and 
                }

            }    
                temp = (curr.whiteCount - curr.blackCount) + bonusPoints;
        }
 //  Vector<Saved_Move> saved_move_list = new Vector<Saved_Move>(); check size() -1 to see if last move became king / made a move with a position close to becoming king
 //use condition statement to see which side of board is of interest  


        return temp;
    }

    public StudentAI(int col, int row, int k) throws InvalidParameterError {
        super(col, row, k);

        this.board = new Board(col, row, k);
        this.board.initializeGame();
        this.player = 2;
        this.NODE = new Node();
    }

    public Move GetMove(Move move) throws InvalidMoveError {
        if (!move.seq.isEmpty()){
            board.makeMove(move, (player == 1) ? 2 : 1);// updates opponents turn/move and update current game state

        }
        else{


            player = 1; // should only come here if its first move of game, therfore default first move http://www.quadibloc.com/other/bo1211.htm

            //given professor's random move
            Vector<Vector<Move>> moves = board.getAllPossibleMoves(player);
            Random randGen = new Random();
            int index = randGen.nextInt(moves.size());
            int innerIndex = randGen.nextInt(moves.get(index).size());
            Move resMove = moves.get(index).get(innerIndex);
            board.makeMove(resMove, player);
            return resMove;
        }

        this.NODE.temp1 = new Vector<Iterator<Vector<Move>>>();
        this.NODE.temp2 = new Vector<Iterator<Move>>();
        this.NODE.temp3 = new Vector<Move>();

        this.NODE.MAX_DEPTH = 4;
        this.NODE.MAX = true;
        FSM_chooseMove(this.NODE.MAX_DEPTH, player, this.board, this.NODE.MAX);
        this.board.makeMove(this.NODE.Make, player);
        return this.NODE.Make;
    }
}