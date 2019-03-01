import java.util.*;
import redis.clients.jedis.Jedis;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.toList;
import java.util.Arrays;
import org.apache.commons.lang3.ArrayUtils;

class Schmedis {
    public interface ILambda<R,P> {
        public R slides(P param);
    } 

    public static class Bishop implements ILambda <List<List<Integer>>,Integer> {
        public List<List<Integer>> slides(Integer start) { return Schmedis.bishopSlides(start); }
    }
    public static class Rook implements ILambda <List<List<Integer>>,Integer> {
        public List<List<Integer>> slides(Integer start) { return Schmedis.rookSlides(start); }
    }
    public static class Horse implements ILambda <List<List<Integer>>,Integer> {
        public List<List<Integer>> slides(Integer start) { return Schmedis.horseSlides(start); }
    }
    public static class Queen implements ILambda <List<List<Integer>>,Integer> {
        public List<List<Integer>> slides(Integer start) { return Schmedis.queenSlides(start); }
    }
    
    static final double inf = Double.POSITIVE_INFINITY;
    static final double ninf = Double.NEGATIVE_INFINITY; 
   
    public static void main(String[] args) {
        char [] haloBoard = {
                    'C',' ','B','Q','K','B','H','C',
                    'P','P','P','P','P','P','P','P',
                    ' ',' ','H',' ',' ',' ',' ',' ',
                    ' ',' ',' ',' ','p',' ',' ',' ',
                    ' ',' ',' ',' ',' ',' ',' ',' ',
                    'c',' ',' ','q',' ',' ',' ',' ',
                    'p','p','p','p',' ','p','p','p',
                    ' ','h','b',' ','k','b','h','c'
                    };
          System.out.println( "Bishop Halo" + genericHalo( 61, haloBoard, new Bishop() ));
          System.out.println( "Rook Halo" +  genericHalo( 40, haloBoard, new Rook() ));
          System.out.println( "Horse Halo" + genericHalo( 18, haloBoard, new Horse() ));
          System.out.println( "Queen Halo" + genericHalo( 43, haloBoard, new Queen() ));
          System.out.println( "Pawn Halo" + pawnHalo( 8, haloBoard ));
          System.out.println( "King Halo" + kingHalo( 60, haloBoard ));
          System.out.println( "Enemy Halo" + enemyHalo( 60, haloBoard ));

          Set<Integer> test1 = new HashSet<Integer>();
          test1.add(0);
          test1.add(1);
          test1.add(2);
          test1.add(8);
          test1.add(10);
          test1.add(16);
          test1.add(17);
          test1.add(18);
   
          Set<Integer> test2 = new HashSet<Integer>();
          test2.add(1);
          test2.add(2);
          test2.add(3);
          test2.add(4);
          test2.add(5);

          System.out.println(differenceJava8(test1, test2));

//        for(int i=0;i<64;i++)System.out.println(queenSlides(i));
        System.out.println("*************************************************************");
        System.out.println("*************************************************************");
//        for(int i=0;i<64;i++)System.out.println(rookSlides(i));
        System.out.println("**************************************************************");
        System.out.println("*************************************************************");
//        for(int i=0;i<64;i++)System.out.println(bishopSlides(i));
        System.out.println("*************************************************************");
        System.out.println("*************************************************************");
//        for(int i=0;i<64;i++)System.out.println(horseSlides(i));
//below is the test for en passant      
        List<char[]> history = new ArrayList<>();     
        char [] previousChessBoard = {
                    'C',' ','B','Q','K','B','H','C',
                    'P','P','P','P','P','P','P','P',
                    ' ',' ','H',' ',' ',' ',' ',' ',
                    ' ',' ',' ',' ','p',' ',' ',' ',
                    ' ',' ',' ','H',' ','C',' ',' ',
                    ' ',' ',' ',' ',' ',' ',' ',' ',
                    'p','p','p',' ',' ',' ','p','p',
                    'c','h','b',' ','k',' ','h','c'
                    };
        char [] currentChessBoard = {
                    'C',' ','B','Q','K','B','H','C',
                    'P','P','P',' ','P','P','P','P',
                    ' ',' ','H',' ',' ',' ',' ',' ',
                    ' ',' ',' ','P','p',' ',' ',' ',
                    ' ',' ',' ','H',' ','C',' ',' ',
                    ' ',' ',' ',' ',' ',' ',' ',' ',
                    'p','p','p',' ',' ',' ','p','p',
                    'c','h','b',' ','k',' ','h','c'
                    };

        
        history.add(previousChessBoard);
        history.add(currentChessBoard);
        {
            List <char[]> resultList = pawnBoards (3*8+4, history);
            for(char[] cb : resultList) 
            {
                printChessBoard(cb);
            }
        }
        {
            List <char[]> resultList = kingBoards (60, history);
            for(char[] cb : resultList) 
            {
                printChessBoard(cb);
            }
        }
        Integer[] way = {0, 4, 8};
        List<Integer>w = Arrays.asList(way);
        w.forEach(System.out::println);
        Scanner in  = new Scanner(System.in);
      
        Jedis jedis = new Jedis("0.0.0.0",6379);

        System.out.println("Lets ping the server"+jedis.ping());
        
        char [] b = {' ',' ',' ',' ',' ',' ',' ',' ',' '};      
        char [] chessBoard = {
                    'C','H','B','Q','K','B','H','C',
                    'P','P','P','P','P','P','P','P',
                    ' ',' ',' ',' ',' ',' ',' ',' ',
                    ' ',' ',' ',' ',' ',' ',' ',' ',
                    ' ',' ',' ',' ',' ',' ',' ',' ',
                    ' ',' ',' ',' ',' ',' ',' ',' ',
                    'p','p','p','p','p','p','p','p',
                    'c','h','b','q','k','b','h','c'
                    };
        
        System.out.println(boardValue(chessBoard));
        System.out.println("Choose X or O"); 
        char c=in.next(".").charAt(0);
        if(c=='x'){

            while(true) { 
                int move; 
                System.out.println("Here is the current board");
                printBoard(b);
                
                System.out.println("Please enter a number from zero to eight, for where you want to move");
                move = in.nextInt();
                in.nextLine();
                b[move] = 'x';
                printBoard(b);
                System.out.println("Press enter to see your opponents move");
                in.nextLine();
                char[] champion = null;
                Double championValue = 0.0;
                for(char[] child : children(b, false)){
                double value = minimax(child, 9, true);
                    if(champion == null || value < championValue){
                        champion = child;
                        championValue = value;
                    } 
    
                }
                    b = champion;
            }
        }else{

            while(true) { 
                
                System.out.println("Press enter to see your opponents move");
                in.nextLine();
                char[] champion = null;
                Double championValue = 0.0;
                for(char[] child : children(b, true)){
                double value = minimax(child, 9, false);
                    if(champion == null || value > championValue){
                        champion = child;
                        championValue = value;
                    } 
    
                }
                    b = champion;
                int move; 
                System.out.println("Here is the current board");
                printBoard(b);
                
                System.out.println("Please enter a number from zero to eight, for where you want to move");
                move = in.nextInt();
                in.nextLine();
                b[move] = 'o';
                printBoard(b);
            }

        }
        }

////////

        public static double minimax(char[] node,int depth,boolean maximizingPlayer) {
            Double value;
            if (depth == 0){
                return estimate(node, maximizingPlayer);
            }
            if (nodeIsATerminalNode(node)) {
                if(somebodyWon(node))
                    return(maximizingPlayer ? ninf:inf);
                else
                    return(0.0); 
            }    
            if (maximizingPlayer) {
                value = ninf; 
                for(char[] child : children(node, maximizingPlayer)) 
                    value = Math.max(value, minimax(child, depth - 1, false));
                return value;
            } else {
                value = inf; 
                for(char[] child : children(node, maximizingPlayer)) 
                    value = Math.min(value, minimax(child, depth - 1, true));
                return value; 
            
            }
        }

////////

        public static boolean tie(char[] node) {

        int count = 0;
        for(int i=0;i<node.length;i++) if (node[i] == ' ')
            count ++;
        
        return count == 0;
        }

////////

        private static int[][] waysToWin = new int[][]
            {
                {0,1,2}, 
                {3,4,5}, 
                {6,7,8}, 
                {0,3,6}, 
                {1,4,7}, 
                {2,5,8}, 
                {0,4,8}, 
                {2,4,6} 
            };
////////

        public static boolean somebodyWon(char[] node) {
            
        for(int i=0;i < waysToWin.length;i++){
            int x=waysToWin[i][0];
            int y=waysToWin[i][1];
            int z=waysToWin[i][2];
            
        if(node[x]!=' '&& node[x] == node[y] && node[y] == node[z]) return true; 
        }
        
        return false; 
        };

////////

        public static List<char[]> children(char[] node, boolean maximizingPlayer) {
            List<char[]> children = new ArrayList<>();
            for(int i=0;i<9;i++) {
               if(node[i] == ' ') {
                   char[] child = node.clone();
                   child[i] = maximizingPlayer ?'x' : 'o'; 
                   children.add(child);
                } 
                
        }
        return children;
        }
        public static boolean nodeIsATerminalNode(char[] node) {
            return tie(node) || somebodyWon(node);
        }
        public static void printBoard(char[] node){
            for(int i=0;i<3;i++) System.out.println( Character.toString(node[0+3*i]) +  Character.toString(node[1+3*i] ) +
            Character.toString(node[2+3*i]));  
            try{Thread.sleep(50);}catch(InterruptedException e){} 
        }

////////

        public static int goodLines(char[] node, char me) {
        int count = 0;
        for(int i=0;i < waysToWin.length;i++){
            int x=waysToWin[i][0];
            int y=waysToWin[i][1];
            int z=waysToWin[i][2];
            
        if((node[x]==me || node[x]==' ') && 
            (node[y]==me || node[y]==' ') && 
                (node[z]==me || node[z]==' ') &&
                    (node[x]==me || node[y]==me || node[z]==me)
          ) count++; 
        }
        
        return count; 
        };

////////

        public static double estimate(char[]node, boolean maximizingPlayer){
            int goodX = goodLines(node, 'x');
            int goodO = goodLines(node, 'o');
            double estimate = maximizingPlayer ? goodX-goodO : goodO-goodX;
            return estimate;
        
        }

////////

        public static double chessEstimate(char[]node, boolean maximizingPlayer){
            int goodX = goodLines(node, 'x');
            int goodO = goodLines(node, 'o');
            double estimate = maximizingPlayer ? goodX-goodO : goodO-goodX;
            return estimate;
        }

////////
       
        public static Double pieceValue(char piece) {
            double value = 0.0;
            if(piece == 'C') value=-5.10;
            if(piece == 'H') value=-3.20;
            if(piece == 'B') value=-3.33;
            if(piece == 'Q') value=-8.80;
            if(piece == 'P') value=-1.0;
            if(piece == 'K') value=0.0;
            if(piece == 'c') value=5.10;
            if(piece == 'h') value=3.20;
            if(piece == 'b') value=3.33;
            if(piece == 'q') value=8.80;
            if(piece == 'p') value=1.0;
            if(piece == 'k') value=0.0;
            if(piece == ' ') value=0.0;
            return value;
        }

////////

        public static Double boardValue(char[] node) {
            Character[] characterArray = ArrayUtils.toObject(node);
            List<Character> cs = Arrays.asList(characterArray);
            return cs.stream().map(x -> pieceValue(x)).mapToDouble(x -> x).sum();
        
        } 

//////// LOTS OF REPLICATED CODE BELOW, WILL REFACTOR LATER

        public static List<List<Integer>>horseSlides(int start){
            List<List<Integer>> slides = new ArrayList<List<Integer>>();
            int startingRow = start/8;
            int startingColumn = start%8;
        
            {
                int r = startingRow-2;
                int c = startingColumn+1;
                List<Integer> slide = new ArrayList<Integer>();
                slide.add(start);
                if (r>=0 && r<8 && c>=0 && c<8) slide.add(r*8+c); 
                if (slide.size()==2)slides.add(slide);  
            }
        
            {
                int r = startingRow-2;
                int c = startingColumn-1;
                List<Integer> slide = new ArrayList<Integer>();
                slide.add(start);
                if (r>=0 && r<8 && c>=0 && c<8) slide.add(r*8+c); 
                if (slide.size()==2)slides.add(slide);  
            }
        
            {
                int r = startingRow-1;
                int c = startingColumn+2;
                List<Integer> slide = new ArrayList<Integer>();
                slide.add(start);
                if (r>=0 && r<8 && c>=0 && c<8) slide.add(r*8+c); 
                if (slide.size()==2)slides.add(slide);  
            }
        
            {
                int r = startingRow-1;
                int c = startingColumn-2;
                List<Integer> slide = new ArrayList<Integer>();
                slide.add(start);
                if (r>=0 && r<8 && c>=0 && c<8) slide.add(r*8+c); 
                if (slide.size()==2)slides.add(slide);  
            }
        
            {
                int r = startingRow+2;
                int c = startingColumn+1;
                List<Integer> slide = new ArrayList<Integer>();
                slide.add(start);
                if (r>=0 && r<8 && c>=0 && c<8) slide.add(r*8+c); 
                if (slide.size()==2)slides.add(slide);  
            }
        
            {
                int r = startingRow+2;
                int c = startingColumn-1;
                List<Integer> slide = new ArrayList<Integer>();
                slide.add(start);
                if (r>=0 && r<8 && c>=0 && c<8) slide.add(r*8+c); 
                if (slide.size()==2)slides.add(slide);  
            }
        
            {
                int r = startingRow+1;
                int c = startingColumn+2;
                List<Integer> slide = new ArrayList<Integer>();
                slide.add(start);
                if (r>=0 && r<8 && c>=0 && c<8) slide.add(r*8+c); 
                if (slide.size()==2)slides.add(slide);  
            }
        
            {
                int r = startingRow+1;
                int c = startingColumn-2;
                List<Integer> slide = new ArrayList<Integer>();
                slide.add(start);
                if (r>=0 && r<8 && c>=0 && c<8) slide.add(r*8+c); 
                if (slide.size()==2)slides.add(slide);  
            }

        return slides;   
        }
         
////////

        public static List<List<Integer>>bishopSlides(int start){
            List<List<Integer>> slides = new ArrayList<List<Integer>>();
            int startingRow = start/8;
            int startingColumn = start%8;
        
            for(int i=1;i<=7;i++){
                List<Integer> slide = new ArrayList<Integer>();
                slide.add(start);
            for (int j=1;j<=i;j++){int r = startingRow + j; int c = startingColumn + j; if (r>=0 && r<8 && c>=0 && c<8) slide.add(r*8+c);}
                if (slide.size()==i+1)slides.add(slide);  
            }
        
            for(int i=1;i<=7;i++){
                List<Integer> slide = new ArrayList<Integer>();
                slide.add(start);
            for (int j=1;j<=i;j++){int r = startingRow + j; int c = startingColumn - j; if (r>=0 && r<8 && c>=0 && c<8) slide.add(r*8+c);}
                if (slide.size()==i+1)slides.add(slide);  
            }
        
            for(int i=1;i<=7;i++){
                List<Integer> slide = new ArrayList<Integer>();
                slide.add(start);
            for (int j=1;j<=i;j++){int r = startingRow - j; int c = startingColumn - j; if (r>=0 && r<8 && c>=0 && c<8) slide.add(r*8+c);}
                if (slide.size()==i+1)slides.add(slide);  
            }
        
            for(int i=1;i<=7;i++){
                List<Integer> slide = new ArrayList<Integer>();
                slide.add(start);
            for (int j=1;j<=i;j++){int r = startingRow - j; int c = startingColumn + j; if (r>=0 && r<8 && c>=0 && c<8) slide.add(r*8+c);}
                if (slide.size()==i+1)slides.add(slide);  
            }
            
        return slides;   

        }

////////

        public static List<char[]> kingBoards (int start, List<char[]> history) {
            Set<Integer> kingMoves = new HashSet<Integer>();
            char[] board = history.get(history.size()-1);
            List<char[]> boards = new ArrayList<char[]>();
            int startingRow = start/8;
            int startingColumn = start%8;
            for(int r=-1;r<=1;r++){
                for(int c=-1;c<=1;c++){
                    int row = startingRow+r;
                    int column = startingColumn+c;
                    boolean onBoard = row>=0 && row<=7 && column>=0 && column<=7;
                    boolean noMove = r==0 && c==0;
                    boolean realMove = !noMove;
                    if(onBoard&&realMove){
                        int target = row*8+column;
                        char targetThing = board[target];
                        char startThing = board[start];
                        boolean colorOfTarget = Character.isUpperCase(targetThing);
                        boolean colorOfStart = Character.isUpperCase(startThing);
                        boolean targetIsEmpty = targetThing == ' ';
                        boolean enemyAtTarget = colorOfTarget != colorOfStart;
                        if(targetIsEmpty||enemyAtTarget){
                            kingMoves.add(target);
                        }
                    } 
                }
            }
            Set<Integer> halo = enemyHalo( start, board );
            kingMoves.removeAll(halo);
            for (Integer i : kingMoves) 
            {
                char[] alteredBoard = board.clone();
                char pigeon = board[start];
                alteredBoard[start] = ' ';
                alteredBoard[i] = pigeon;
                boards.add(alteredBoard);
            }
            return boards;
        }

////////

        public static Set<Integer>genericHalo(int start, char[] board, ILambda <List<List<Integer>>,Integer> f ) {
            int startingRow = start/8;
            int startingColumn = start%8;
            Set<Integer> halo = new HashSet<Integer>();
            List<List<Integer>>slides = f.slides(start);
            for( List<Integer>slide : slides )
            {
                int indexOfFirstElement = 0;
                int indexOfLastElement = slide.size()-1;
                boolean somethingInTheWay = false;
                for(int i = indexOfFirstElement+1; i<indexOfLastElement; i++)
                {
                    int position = slide.get(i);
                    if (board[position] != ' ') {
                        somethingInTheWay = true;
                    }
                }
                if(!somethingInTheWay)
                {
                    halo.add(slide.get(indexOfLastElement));
                }
            }
            return halo; 
        }

////////

        public static Set<Integer>pawnHalo(int start, char[] board) {
            int startingRow = start/8;
            int startingColumn = start%8;
            char pP = board[start]; 
            int direction = pP=='p'? -1 : 1;
            Set<Integer> halo = new HashSet<Integer>();
            for(int eastWest = -1;eastWest<=1;eastWest+=2) {
                int targetR = startingRow+direction;
                int targetC = startingColumn+eastWest;
                if (targetC >= 0 && targetC <= 7){
                    int target = targetR*8+targetC;
                    halo.add(target);
                }
            }
            return halo;
        }

////////

        public static Set<Integer>kingHalo(int start, char[] board) {
            int startingRow = start/8;
            int startingColumn = start%8;
            Set<Integer> halo = new HashSet<Integer>();
            for(int r=-1;r<=1;r++){
                for(int c=-1;c<=1;c++){
                    int row = startingRow+r;
                    int column = startingColumn+c;
                    boolean onBoard = row>=0 && row<=7 && column>=0 && column<=7;
                    boolean noMove = r==0 && c==0;
                    boolean realMove = !noMove;
                    if(onBoard&&realMove){
                        int target = row*8+column;
                        halo.add(target);
                    }
                } 
            }
            return halo;
        }
        

////////

        public static List<char[]> pawnBoards (int start, List<char[]> history){
            char[] board = history.get(history.size()-1);
            List<char[]> boards = new ArrayList<char[]>();
            int startingRow = start/8;
            int startingColumn = start%8;
            char pP = board[start]; 
            int direction = pP=='p'? -1 : 1;
        
            boolean penultimate = startingRow+direction==0||startingRow+direction==7;
            int oneSquareAhead = (startingRow+direction)*8+startingColumn;
            int twoSquaresAhead = (startingRow+direction*2)*8+startingColumn;
            boolean squareAheadIsEmpty = board[oneSquareAhead] == ' ';
            boolean twoSquaresAheadIsEmpty = board[twoSquaresAhead] == ' ';
            boolean pawnAtOriginalPosition = pP == 'p' && startingRow==6 || pP == 'P' && startingRow==1;
            char[] whitePromotions = {'c', 'h', 'b', 'q'};
            char[] blackPromotions = {'C', 'H', 'B', 'Q'};
            char[] promotions = pP=='p' ? whitePromotions : blackPromotions;

//HANDLE TWO SQUARE PAWN PUSH        
            if (pawnAtOriginalPosition&&squareAheadIsEmpty&&twoSquaresAheadIsEmpty){
                char[] modifiedBoard = board.clone(); 
                modifiedBoard[start] = ' ';
                modifiedBoard[oneSquareAhead] = pP;
                boards.add(modifiedBoard);

            }
//HANDLE ONE SQUARE PAWN PUSH       
            
            if (squareAheadIsEmpty){
                if (penultimate){
                    for(int i=0;i<promotions.length;i++){
                        char[] modifiedBoard = board.clone();
                        modifiedBoard[start] = ' ';
                        modifiedBoard[oneSquareAhead] = promotions[i];
                        boards.add(modifiedBoard);
                    }                     
                }else{
                    char[] modifiedBoard = board.clone(); 
                    modifiedBoard[start] = ' ';
                    modifiedBoard[oneSquareAhead] = pP;
                    boards.add(modifiedBoard);
                
                }
                
            }
//PAWN MOVES DIAGONALLY
            for(int eastWest = -1;eastWest<=1;eastWest+=2) {
                int targetR = startingRow+direction;
                int targetC = startingColumn+eastWest;
                if (targetC >= 0 && targetC <= 7){
                    int target = targetR*8+targetC;
                    char targetThing = board[target];
                    if (targetThing != ' ' && Character.isUpperCase(targetThing)!=Character.isUpperCase(pP)){
                        if (penultimate){
                            for(int i=0;i<promotions.length;i++){
                                char[] modifiedBoard = board.clone();
                                modifiedBoard[start] = ' ';
                                modifiedBoard[target] = promotions[i];
                                boards.add(modifiedBoard);
                            }                     
                        }else{
                            char[] modifiedBoard = board.clone();
                            modifiedBoard[start] = ' ';
                            modifiedBoard[target] = pP;
                            boards.add(modifiedBoard);
                        }
    
                    }
                }
            }

//////// EN PASSANT BELOW

            for(int eastWest = -1;eastWest<=1;eastWest+=2) 
            {
                int guardRow = pP == 'p' ? 3 : 4;
                int enemyPawnOriginRow = guardRow + direction*2; 
                int enemyPawnColumn = startingColumn + eastWest;
                boolean previousBoardExists = history.size() >= 2;
                boolean pawnIsOnGuardRow = startingRow == guardRow; 
                boolean enemyPawnColumnIsOnTheBoard = enemyPawnColumn >= 0 && enemyPawnColumn <= 7;
                if(pawnIsOnGuardRow && previousBoardExists && enemyPawnColumnIsOnTheBoard){
                    int sneakyBastardStart = enemyPawnOriginRow*8+enemyPawnColumn; 
                    int sneakyBastardEnd = guardRow*8+enemyPawnColumn; 
                    char[] previousBoard = history.get(history.size()-2);
                    char enemyPawn = pP == 'p' ? 'P' : 'p';
                    boolean enemyPawnWasAtSneakyBastardStart = previousBoard[sneakyBastardStart]==enemyPawn;
                    boolean enemyPawnGoneNowFromSneakyBastardStart = board[sneakyBastardStart]==' ';
                    boolean enemyPawnNowAtSneakyBastardEnd = board[sneakyBastardEnd]==enemyPawn;
                    boolean enemyPawnWasNotAtSneakyBastardEndBefore = previousBoard[sneakyBastardEnd]==' ';
                    boolean enPassant = enemyPawnWasAtSneakyBastardStart 
                        && enemyPawnGoneNowFromSneakyBastardStart 
                        && enemyPawnNowAtSneakyBastardEnd 
                        && enemyPawnWasNotAtSneakyBastardEndBefore; 
                    if(enPassant){
                        int targetRow = startingRow+direction;
                        int targetColumn = enemyPawnColumn;
                        int target = targetRow*8+targetColumn;
                        char[] modifiedBoard = board.clone();
                        modifiedBoard[start] = ' ';
                        modifiedBoard[target] = pP;
                        modifiedBoard[sneakyBastardEnd] = ' ';                       
                        boards.add(modifiedBoard);
                   }
                }
            }

            return boards;
        }

////////
 
        public static List<List<Integer>>rookSlides(int start){
            List<List<Integer>> slides = new ArrayList<List<Integer>>();
            int startingRow = start/8;
            int startingColumn = start%8;
        
            for(int i=1;i<=7;i++){
                List<Integer> slide = new ArrayList<Integer>();
                slide.add(start);
            for (int j=1;j<=i;j++){int r = startingRow - j; int c = startingColumn + 0; if (r>=0 && r<8 && c>=0 && c<8) slide.add(r*8+c);}
                if (slide.size()==i+1)slides.add(slide);  
            }
        
            for(int i=1;i<=7;i++){
                List<Integer> slide = new ArrayList<Integer>();
                slide.add(start);
            for (int j=1;j<=i;j++){int r = startingRow + j; int c = startingColumn + 0; if (r>=0 && r<8 && c>=0 && c<8) slide.add(r*8+c);}
                if (slide.size()==i+1)slides.add(slide);  
            }
        
            for(int i=1;i<=7;i++){
                List<Integer> slide = new ArrayList<Integer>();
                slide.add(start);
            for (int j=1;j<=i;j++){int r = startingRow + 0; int c = startingColumn - j; if (r>=0 && r<8 && c>=0 && c<8) slide.add(r*8+c);}
                if (slide.size()==i+1)slides.add(slide);  
            }
        
            for(int i=1;i<=7;i++){
                List<Integer> slide = new ArrayList<Integer>();
                slide.add(start);
            for (int j=1;j<=i;j++){int r = startingRow + 0; int c = startingColumn + j; if (r>=0 && r<8 && c>=0 && c<8) slide.add(r*8+c);}
                if (slide.size()==i+1)slides.add(slide);  
            }
            
        return slides;   

        }
       
////////

        public static List<List<Integer>>queenSlides(int start){
            List<List<Integer>> diagonalSlides = new ArrayList<List<Integer>>();
            List<List<Integer>> horizontalSlides = new ArrayList<List<Integer>>();
            List<List<Integer>> slideList = new ArrayList<List<Integer>>();
            int startingRow = start/8;
            int startingColumn = start%8;
             
            diagonalSlides = bishopSlides(start);
            horizontalSlides = rookSlides(start);
            slideList.addAll(diagonalSlides);
            slideList.addAll(horizontalSlides);
             
            return slideList;
        }    

////////

        public static void printChessBoard(char[] node){
            for(int r=0;r<8;r++) {
                for(int c=0;c<8;c++){
                    System.out.print(Character.toString(node[r*8+c]));
                } 
                System.out.println();
            }
            try{Thread.sleep(50);}catch(InterruptedException e){} 
        }

////////

        public static <T> Set<T> differenceJava8(final Set<T> setOne, final Set<T> setTwo) {
             Set<T> result = new HashSet<T>(setOne);
             result.removeIf(setTwo::contains);
             return result;
        }
        
////////
      
       public static Set<Integer> enemyHalo (int locationOfFriendlyPigeon, char[] board) {
           Set<Integer> halo = new HashSet<Integer>();
           int friendlyPigeon = board[locationOfFriendlyPigeon];
           boolean colorOfFriendlyPigeon = Character.isUpperCase(friendlyPigeon);
           for(int i=0;i<64; i++){
               int pigeon = board[i];
               boolean colorOfPigeon = Character.isUpperCase(pigeon);
               boolean enemyPigeon = colorOfPigeon != colorOfFriendlyPigeon;
               boolean empty = pigeon == ' ';
               if( !empty && enemyPigeon )
               {
                   switch(pigeon)
                   {
                       case 'k':  
                       case 'K':  
                           halo.addAll( kingHalo( i, board ));
                           break;
                       case 'p':  
                       case 'P':
                           halo.addAll( pawnHalo( i, board ));
                           break;
                       case 'h':  
                       case 'H':
                           halo.addAll( genericHalo( i, board, new  Horse() ));
                           break;
                       case 'b':  
                       case 'B':
                           halo.addAll( genericHalo( i, board, new  Bishop() ));
                           break;
                       case 'q':  
                       case 'Q':
                           halo.addAll( genericHalo( i, board, new  Queen() ));
                           break;
                       case 'c':  
                       case 'C':
                           halo.addAll( genericHalo( i, board, new  Rook() ));
                           break;
                   }
               } 
           } 

           return halo;
       } 


}
