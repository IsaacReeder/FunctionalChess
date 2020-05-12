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


## This project started as an interactive Tic Tac Toe game using the MiniMax algorithm of John Von Neumann's Game Theory. This project slowly transformed into an interactive chess game, and now uses the Alpha-Beta Algorithm for increased efficiency. 

[![AI Chess ](http://img.youtube.com/vi/JPfVU8zGflQ/0.jpg)](http://www.youtube.com/watch?v=JPfVU8zGflQ "Video Title")

Technologies used: Java, J-Commander, Google Protocol Buffers, GRPC, and JAVA Multicore API. Programmed using VIM, on Byobu. In this Chess game black represents negative, and white represents positive. Minimax recursively searches the possible history game tree of each move and asks my opposing player to rate each of the moves, then chooses which is best for me. The opposing player (AI) will always do its best to make moves that are bad for me. The scores it gives me will reflect that.

The game tree for chess is vast, and there are too many possibilities. Therefor a limited search of the game tree is the optimum outcome. A heuristic is used once a specific depth is reached in the game tree. This heuristic counts the pieces combined value being returned, white expecting the highest possible return value, and black the lowest possible return value.

 Modification from Mini-Max to the predominant Alph-Beta Pruning algorithm happened roughly 7 weeks into this project. Alph-Beta algorithm will first identify what is currently an apposite find. If later Alph-Beta perceives something worse on a different branch of the tree, it immediately discontinues search of the path in the tree that contained the inapt finding. It knows that the prior branch is much more acceptable. Alph-Beta prunes the subjacent branch promptly. 

In short, Alpha Beta provides faster processing of the number of moves in a typical game as compared to Mini-Max. Ordinarily, the number of moves in a chess game is 30. 

An investigation into multi-core programming was spurred by a sudden realization; this program had become something substantial. My laptop has 12 cores and is capable of clock speeds of 4.8GHz. The goal quickly became: I must use Multi-Core threading, to explore different branches synchronously using different cores. 

Upon Succesful implementation each viable move was sent to a separate core for processing, increasing my overall processing speed markedly. I implemented Google Protocol Buffers with the eventual goal of distributed processing employing AWS cloud services. Google Protocol Buffers has an API that works everywhere (IOS, Java, etc.) Proto-Buffs are not Language or platform-specific. I considered GRPC, but as work ramped up this avocation, AI Chess fell to the wayside.

phew! After reading that description, I'de wager you're ready to play chess. 

```html
$ cd work/functionalChess/chess-app

$ mvn package

$ mvn -q exec:java -Dexec.mainClass="com.coderz.app.App" -Dexec.args="-mode play" 
```


You may notice this repository is a combination of two. The reasoning behind this merge is two-fold: the ability to search through this project in its entirety with ease and to retain the git history. Git history is quite valuable. 

Below you will find the git commands I used to merge two repositories, sometimes including small descriptions. 


```html 
- git remote add IntChess <RemoteName> <RemoteURL>      

- git fetch <RemoteName>       

- check to see whether remote is available with git remote -v       

- git stash

- create a new branch with git checkout -b <NewBranchName> <Branch/master>     

- git branch

- ls

- git checkout master (to see the repo you’re merging into)

- ls 

- git checkout <NewBranchName>

- ls 

- git status

- mkdir <DirectoryName>

- Git mv -v <source> <DirectoryName>       
 - move all the files into a subdirectory so there aren't any conflicts with names  

- git commit -m “Moved all files into a subfolder interactiveChess” 

- git checkout master  
 - (the repo you are merging to)

- ls

- git checkout <NewBranchName>

- git status

- ls (you see just one subfolder)

- git checkout master

- git merge <NewBranchName> --allow-unrelated-histories  

- git commit -m ”Merged with repository”

- git push 

- cleanup everything with: git remote rm <RemoteName>    

- git branch -d <NewBranchName>    

- git log --graph --full-history --all --color --date=short --pretty=format:"%x1b[31m%h%x09%x1b[32m%d%x1b[0m%x20%ad %s"  
 - (above is a fun graphical git log, enjoy.)

- check to see whether everything is correct with: git remote -v     git branch -a -v     git status   

```

"Chess is infinite: There are 400 different positions after each player makes one move apiece. There are 72,084 positions after two moves apiece. There are 9+ million positions after three moves apiece. There are 288+ billion different possible positions after four moves apiece. There are more 40-move games on Level-1 than the number of electrons in our universe. There are more game-trees of Chess than the number of galaxies (100+ billion), and more openings, defences, gambits, etc. than the number of quarks in our universe!" --Chesmayne

Thanks for looking. If you enjoyed this project drop me a line, or star this repository to help me stay motivated. 



![IBM's Deep Blue](https://2.bp.blogspot.com/-uN4Z0M3iaIw/WnAYl5B1zhI/AAAAAAAAPJc/A5vSZxSPEi4ifjpVZn6JGGiE5FaXfZjTACLcBGAs/s1600/Kasparov_vs_Deep_Blue%2B%25283%2529.jpg)


