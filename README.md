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

Technologies used are Java, J-Commander, Google Protocol Buffers, GRPC, and JAVA Multicore API. Programmed using VIM, on Byobu. In this Chess game black represents negative, and white represents positive. Minimax recursively searches the possible history game tree of each move, and asks the opposing player to rate each of the moves,  then chooses which is best for us. The opposing player will always do his best to make moves that are bad for us. so the scores he gives us reflect that.

The game tree for chess is vast, and there are too many possibilities. Therefor a limited search of the game tree is the best we can do. And a heuristic is used once a certain depth is reached in the tree. The way this program decides is by employing a heuristic that counts the pieces combined value that is returned, white looking for the highest possible return value, and black looking for the lowest possible return value.

 Later in this project, I transitioned from Mini-Max to the Alph-Beta Pruning algorithm. If the algorithm sees something that is pretty good, and then later sees something that worse on a different branch of the tree, he is halted and doesn’t continue to search that path in the tree, he just knows that the prior branch is much more acceptable. It prunes the branch without really searching it anymore. Its like looking down an alley and seeing something dangerous, and knowing there’s another alley that has cupcakes In it

Alpha Beta provides faster processing of the number of moves in a typical game, which is generally 30. 

To improve performance I investigated multi-core programming.  My laptop has 12 cores. It's capable of clock speeds up to 4.8GHz. So I dived into using Multi-Core threading, exploring different branches with different cores. At this point each possible move was sent to a separate core for processing, increasing my overall processing speed greatly. I  implemented Google Protocol Buffers with the eventual goal of distributed processing to something like AWS cloud services, to hit the next level in this app. I choose the Google protocol Buffers because they have an API that works everywhere (IOS, Java, etc.) They are not Language or platform-specific. I was going to use GRPC, but then I was hired to West Marine.

Phew, now you understand what lives in this repository.

```html
At this point, you may want to try this app out for yourself. After cloning this repository, install the dependencies and use the command:

$ cd work/functionalChess/chess-app

$ mvn package

$ mvn -q exec:java -Dexec.mainClass="com.coderz.app.App" -Dexec.args="-mode play" 
```


You may notice this repository is a combination of two. The reasoning behind this merge is two-fold: search through this project in its entirety with ease, and to retain the git history. Git history is quite valuable. 

Below you will find the git commands I used to merge two repositories, sometimes including small descriptions. These commands may be useful to those of you who use Git regularly, or with increasing frequency. 

`Clone && use cloned repository for merge, as an added safety measure.`

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

Thanks for looking. If you enjoyed this project drop me a line, or star this repository to help me stay motivated. 


![IBM's Deep Blue](https://2.bp.blogspot.com/-uN4Z0M3iaIw/WnAYl5B1zhI/AAAAAAAAPJc/A5vSZxSPEi4ifjpVZn6JGGiE5FaXfZjTACLcBGAs/s1600/Kasparov_vs_Deep_Blue%2B%25283%2529.jpg)


