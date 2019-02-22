import java.util.*;
import redis.clients.jedis.Jedis;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.toList;
import java.util.Arrays;
import org.apache.commons.lang3.ArrayUtils;

class Schmedis {
    static final double inf = Double.POSITIVE_INFINITY;
    static final double ninf = Double.NEGATIVE_INFINITY; 
   
        public static void main(String[] args) {
//        for(int i=0;i<64;i++)System.out.println(queenSlides(i));
        System.out.println("*************************************************************");
        System.out.println("*************************************************************");
        for(int i=0;i<64;i++)System.out.println(rookSlides(i));
        System.out.println("**************************************************************");
        System.out.println("*************************************************************");
        for(int i=0;i<64;i++)System.out.println(bishopSlides(i));
        System.out.println("*************************************************************");
        System.out.println("*************************************************************");
        for(int i=0;i<64;i++)System.out.println(horseSlides(i));
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
  
        public static List<char[]>pawnBoards(int start, List<char[]> history){
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

            for(int eastWest = -1;eastWest<=1;eastWest+=2) 
            {
                int gaurdRow = pP == 'p' ? 3 : 4;
                int enemyPawnOriginRow = gaurdRow + direction*2; 
                int enemyPawnColumn = startingColumn + eastWest;
                boolean pawnIsOnGaurdRow = startingRow == gaurdRow; 
                if(pawnIsOnGuardRow && history.size() >= 2 && enemyPawnColumn >= 0 && enemyPawnColumn <= 7){
                    sneakyBastardStart = enemyPawnOriginRow*8+enemyPawnColumn; 
                    sneakyBastardEnd = gaurdRow*8+enemyPawnColumn; 
                    char[] previousBoard = history.get(history.size()-2);
                    char enemyPawn = pP == 'p' ? P : p;
                    if(boolean enemyPawnWasAtSneakyBastardStart = previousBoard[sneakyBastardStart]==enemyPawn &&
                    boolean enemyPawnGoneNowFromSneakyBastardStart = board[sneakyBastardStart]==' ' &&
                    boolean enemyPawnNowAtSneakyBastardEnd = board[sneakyBastardStart]==enemyPawn &&
                    boolean enemyPawnWasNotNextToMeBefore = previousBoard[sneakyBastardEnd]==' '){
                        modifiedBoard[start] = ' ';
                        targetRow = startRow+direction;
                        targetColumn = enemyPawnColumn;
                        int target = targetRow*8+targetColumn;
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

    
}
