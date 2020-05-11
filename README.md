# FunctionalChess


        
        
                                                       =||=   
                                       o   |\ ,'`. /||\ ,'`. /|    o     
               _   _   _   |\__      /\^/\ | `'`'`' || `'`'`' |  /\^/\   |\__     _   _   _ 
              | |_| |_| | /   o\__  |  /  ) \      /  \      /  |  /  ) /   o\__ | |_| |_| |
               \       / |    ___=' | /  /   |    |    |    |   | /  / |    ___=' \       / 
                |     |  |    \      Y  /    |    |    |    |    Y  /  |    \      |     |
                |     |   \    \     |  |    |    |    |    |    |  |   \    \     |     |  
                |     |    >    \    |  |    |    |    |    |    |  |    >    \    |     |  
               /       \  /      \  /    \  /      \  /      \  /    \  /      \  /       \ 
              |_________||________||______||________||________||______||________||_________|
                  __         __       __       __        __       __       __         __   
                 (  )       (  )     (  )     (  )      (  )     (  )     (  )       (  )  
                  ><         ><       ><       ><        ><       ><       ><         ><   
                 |  |       |  |     |  |     |  |      |  |     |  |     |  |       |  |  
                /    \     /    \   /    \   /    \    /    \   /    \   /    \     /    \ 
               |______|   |______| |______| |______|  |______| |______| |______|   |______|
                    _       _        _                     _                     
                   / \     (_)      (_)                   (_) _   _              
                  |  | _/_  /        /  / _       ,        /.' ).' )             
                  | /  /      ,     /  / / )_/ / / )      /   /   /  _       _   
                .-|/--'|     / )   (__/,(_/ (_/\/ (__    /   /   /  / )_/ /_/_)  
               (__/\_   \_  /_/    __/'                 /   /   (__(_/ (_/ (__  () () ()


This project started as an interactive Tic Tac Toe game using the MiniMax algorithm of John Von Neumann's Game Theory. This project slowly transformed into an interactive chess game, and now uses the Alpha-Beta Algorithm for increased efficiency. 

[![AI Chess ](http://img.youtube.com/vi/JPfVU8zGflQ/0.jpg)](http://www.youtube.com/watch?v=JPfVU8zGflQ "Video Title")

Technologies used are Java, J-Commander, Google Protocol Buffers, GRPC, and JAVA Multicore API. Programmed using VIM, on Byobu. In this Chess game black represents negative, and white represents positive. Minimax recursively searches the possible history game tree of each move, and asks the opposing player to rate each of the moves,  then chooses which is best for us. The opposing player will always do his best to make moves that are bad for us. so the scores he gives us reflect that.

The game tree for chess is vast, and there are too many possibilities. Therefor a limited search of the game tree is the best we can do. And a heuristic is used once a certain depth is reached in the tree. The way this program decides is by employing a heuristic that counts the pieces combined value that is returned, white looking for the highest possible return value, and black looking for the lowest possible return value.

 Later in this project, I transitioned from Mini-Max to the Alph-Beta Pruning algorithm. If the algorithm sees something that is pretty good, and then later sees something that worse on a different branch of the tree, he is halted and doesn’t continue to search that path in the tree, he just knows that the prior branch is much more acceptable. It prunes the branch without really searching it anymore. Its like looking down an alley and seeing something dangerous, and knowing there’s another alley that has cupcakes In it

Alpha Beta provides faster processing of the number of moves in a typical game, which is generally 30. 

To improve performance I investigated multi-core programming.  My laptop has 12 cores. It's capable of clock speeds up to 4.8GHz. So I dived into using Multi-Core threading, exploring different branches with different cores. At this point each possible move was sent to a separate core for processing, increasing my overall processing speed greatly. I  implemented Google Protocol Buffers with the eventual goal of distributed processing to something like AWS cloud services, to hit the next level in this app. I choose the Google protocol Buffers because they have an API that works everywhere (IOS, Java, etc.) They are not Language or platform-specific. I was going to use GRPC, but then I got the job at West Marine.


