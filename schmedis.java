import java.util.*;
import redis.clients.jedis.Jedis;
class Schmedis {
    static final double inf = Double.POSITIVE_INFINITY;
    static final double ninf = Double.NEGATIVE_INFINITY; 
   
        public static void main(String[] args) {
        
        Scanner in  = new Scanner(System.in);
      
        Jedis jedis = new Jedis("0.0.0.0",6379);

        System.out.println("Lets ping the server"+jedis.ping());
        
        char [] b = {' ',' ',' ',' ',' ',' ',' ',' ',' '};
        while(true) { 
            int move; 
            System.out.println("Here is the current board");
            printBoard(b);
            
            System.out.println("Please enter a number from zero to eight, for where you want to move");
            move = in.nextInt();
            in.nextLine();
            b[move] = 'x';
            System.out.println("Press enter to see your opponents move");
            in.nextLine();
            char[] champion = null;
            Double championValue = 0.0;
                for(char[] child : children(b, false)){
                double value = minimax(child, 1000, true);
                    if(champion == null || value < championValue){
                        champion = child;
                        championValue = value;
                    } 
    
                }
                b = champion;
        }
}
////////
      public static double minimax(char[] node,int depth,boolean maximizingPlayer) {
      Double value;
        if (depth == 0 || nodeIsATerminalNode(node)) {
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
        public static boolean somebodyWon(char[] node) {

       if(node[0]!=' '&& node[0] == node[1] && node[1] == node[2]) return true; 
       if(node[3]!=' '&& node[3] == node[4] && node[4] == node[5]) return true; 
       if(node[6]!=' '&& node[6] == node[7] && node[7] == node[8]) return true; 
       if(node[0]!=' '&& node[0] == node[3] && node[3] == node[6]) return true; 
       if(node[1]!=' '&& node[1] == node[4] && node[4] == node[7]) return true; 
       if(node[2]!=' '&& node[2] == node[5] && node[5] == node[8]) return true; 
       if(node[0]!=' '&& node[0] == node[4] && node[4] == node[8]) return true; 
       if(node[2]!=' '&& node[2] == node[4] && node[4] == node[6]) return true; 
       return false; 
       }

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
}
