import java.util.Iterator;
import java.util.Random;
import java.util.Vector;

import java.util.ArrayList;
import java.util.Collections;


public class StudentAI extends AI {
    
    public class Node{
        int j;
        int i;
        int MAX_DEPTH;
        Boolean MAX;
        Move Make;
        int score;
        Vector<Integer> depthScore;
        int player;
        
    }

    Node NODE;
    //how to choose next move using recurrsion
    public int FSM_chooseMove(int depth,int player, Board currBoard, boolean max){
        int k = 1; //states
        //index
        int i = 0;
        int j = -1;

        int score = 0;
        
        Vector<Vector<Move>> currentPossibleMoves = currBoard.getAllPossibleMoves(player); ;
        Iterator<Vector<Move>> temp1 = null;
        Iterator<Move> temp2 = null ;
        Move temp3 = null; 
        

        while(true){
            switch(k){
                case 1:{ 

                    temp1 = currentPossibleMoves.iterator();
                    if(temp1.hasNext()){
                        temp2 = temp1.next().iterator();
                        //System.out.println("IN 1");
                        k = 2;

                    }
                    else{
                        return 0;
                    }
                    
                    break;
                }
                case 2:{  
 
                    if(temp2.hasNext()){
                        //System.out.println("IN 2");
                        temp3 = temp2.next();
                        //System.out.println(this.NODE.temp3.get(this.NODE.temp3.size() - 1).seq.get(0));
                        if(depth == this.NODE.MAX_DEPTH){
                            j += 1;
                        }
                        k = 3;
                        break;
                    }
                    else if(temp1.hasNext()){
                    
                        temp2 = temp1.next().iterator();
                        j = -1;

                        if(depth == this.NODE.MAX_DEPTH){
                            i += 1;
                        }
                        k = 2;
                        break;
                            
                    }
                    else{
                       // System.out.println(this.NODE.temp3.get(this.NODE.temp3.size()-1).toString() + "in 3");
                       
                        

                        if(depth == this.NODE.MAX_DEPTH){// rnrn its not waiting to go here to make a move idk how its exiting but gotta check logic, its exiting and making move at 00 by default when it goes back to getmove
                            //System.out.println("i: " + this.NODE.i +" " +"j: "+ this.NODE.j);
                            if(this.NODE.i > currentPossibleMoves.size()){
                                this.NODE.i -= 1;
                            }
                            if(this.NODE.j > currentPossibleMoves.get(this.NODE.i).size()){
                                this.NODE.j -= 1;
                            }
                            this.NODE.Make = (currentPossibleMoves.get(this.NODE.i)).get(this.NODE.j);
                            return 0;   
                        }
                        else if(depth == 0){
                            return score;
                        }
                        else{
                            return this.NODE.score;
                        }
                         
                    }
                   
                    
                }
                case 3:{
                    
                    try{

                        //System.out.println(this.NODE.temp3.get(this.NODE.temp3.size()-1).toString());
                        currBoard.makeMove(temp3, player);
                        k = 4;

                    }

                    catch(Exception e){}
                    
                    if(depth == 0){
                        k = 5;
                    }
                    break;
                }
                case 4:{
                    //System.out.println("in 4");

                    if(player == 2){
                        score = FSM_chooseMove(depth - 1, 1, currBoard, !max);

                    }
                    else{
                        score = FSM_chooseMove(depth - 1, 2, currBoard, !max);
                    }
                    // if(score == 0){
                    //     k = 6;
                    // }
                    // else{
                         k = 5;
                         this.NODE.depthScore.add(score);
                    //}
                    break;
                }
                case 5:{
                    
                    if(depth == 0){
                        score = gamescore(currBoard, player);
                        k = 6;
                        break;
                    }
                
                    if(max){
                        //System.out.println("Depth: " + depth + "|| Node Score: " + this.NODE.score +" || Score: "+ score);

                        if(this.NODE.score > Collections.max(this.NODE.depthScore)){
                            if(depth == this.NODE.MAX_DEPTH){
                                //System.out.println("new i: " + i + "new j: " + j);
                                //if(j > currentPossibleMoves){}
                                this.NODE.i = i;
                                this.NODE.j = j;
                            }
                            this.NODE.score = Collections.max(this.NODE.depthScore);
                        }
                    }
                    else{
                        if(this.NODE.score < Collections.min(this.NODE.depthScore)){
                                this.NODE.score = Collections.min(this.NODE.depthScore);
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
        
        int x = curr.isWin(this.NODE.player);
        int bonusPoints = x == 1 || x == -1 ? 9999: 0;
        int temp = 0;
        Board.Saved_Move lastMove = curr.saved_move_list.get(curr.saved_move_list.size() - 1);
        bonusPoints = lastMove.made_move.isCapture ? bonusPoints + 4 : bonusPoints;
        bonusPoints = lastMove.become_king ? bonusPoints + 4 : bonusPoints;
        if(this.NODE.player == 1){ //starts at 2nd row -> y > size(row) - 4 //check if isking already, if so points are normal

            // if((curr.board.get(lastMove.made_move.seq.get(lastMove.made_move.seq.size() - 1).y)).get(lastMove.made_move.seq.get(lastMove.made_move.seq.size() - 1).x).isKing && !lastMove.become_king){
            //     if(lastMove.made_move.isCapture){
            //         bonusPoints = 3;
            //     }
            //     return (curr.blackCount - curr.whiteCount) + bonusPoints;

            // }
            // else{
            //     if(lastMove.made_move.seq.get(lastMove.made_move.seq.size() - 1).y > (curr.row - 4)){
            //         bonusPoints = (lastMove.become_king) ? 5 : 3; //if last move became king and 
                    
            //     }
            // }
            // if(lastMove.made_move.isCapture){
            //     bonusPoints += 3;
            // }
            // if(curr.blackCount <= 5){
            //     bonusPoints = lastMove.made_move.isCapture ? 3 : 0;
            // }
            temp = ((curr.blackCount - curr.whiteCount) * 3) + bonusPoints;
            
        }
        else{//starts at [size(row) - 2] -> y < 4 , row = 0 , 1 , ..... last 3 rows are more points
            
            // if((curr.board.get(lastMove.made_move.seq.get(lastMove.made_move.seq.size() - 1).y)).get(lastMove.made_move.seq.get(lastMove.made_move.seq.size() - 1).x).isKing && !lastMove.become_king){
                
            //     return (curr.whiteCount - curr.blackCount);

            // }
            // else{
            //     if(lastMove.made_move.seq.get(lastMove.made_move.seq.size() - 1).y < 4){
            //         bonusPoints = (lastMove.become_king) ? 5 : 3; //if last move became king and 
            //     }

            // }   
            // if(lastMove.made_move.isCapture){
            //     bonusPoints += 3;
            // } 
            // if(curr.whiteCount <= 5){
            //     bonusPoints = lastMove.made_move.isCapture ? 3 : 0;
            // }
                 temp = ((curr.whiteCount - curr.blackCount)* 3) + bonusPoints;
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
        if(this.board.col == 9){
            this.NODE.MAX_DEPTH = 4;
        }
        else{
            this.NODE.MAX_DEPTH = 5;
        }
        this.NODE.depthScore = new Vector<Integer>();
        this.NODE.player = player;
        //this.NODE.MAX_DEPTH = 5;
        this.NODE.MAX = true;
        FSM_chooseMove(this.NODE.MAX_DEPTH, player, this.board, this.NODE.MAX);
        this.board.makeMove(this.NODE.Make, player);
        return this.NODE.Make;
    }
}