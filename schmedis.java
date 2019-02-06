import java.util.*;
import redis.clients.jedis.Jedis;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.toList;

class Schmedis {
    static final double inf = Double.POSITIVE_INFINITY;
    static final double ninf = Double.NEGATIVE_INFINITY; 
   
        public static void main(String[] args) {
        Integer[] way = {0, 4, 8};
        List<Integer>w = Arrays.asList(way);
        w.forEach(System.out::println);
        Scanner in  = new Scanner(System.in);
      
        Jedis jedis = new Jedis("0.0.0.0",6379);

        System.out.println("Lets ping the server"+jedis.ping());
        
        char [] b = {' ',' ',' ',' ',' ',' ',' ',' ',' '};      
//      char [] b = {
//                  'C','H','B','Q','K','B','H','C',
//                  'P','P','P','P','P','P','P','P',
//                  ' ',' ',' ',' ',' ',' ',' ',' ',
//                  ' ',' ',' ',' ',' ',' ',' ',' ',
//                  ' ',' ',' ',' ',' ',' ',' ',' ',
//                  ' ',' ',' ',' ',' ',' ',' ',' ',
//                  'p','p','p','p','p','p','p','p',
//                  'c','h','b','q','k','b','h','c'
//                  };
// horse 3.20, bishop 3.33, castle 5.10, queen 8.80 
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
            for(int i=0;i<3;i++) System.out.println( Character.toString(node[0+3*i]) +  Character.toString(node[1+3*i]            ) + Character.toString(node[2+3*i]));  
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
       
       public static double pieceValue(char piece) {
           Double value = 0.0;
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

        public static double boardValue(char[] node) {
            return Arrays.asList(node).stream().map(x -> pieceValue(x)).mapToDouble(x -> x).sum();
        
        } 

}
