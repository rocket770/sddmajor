# sddmajor

This was a project done for my Software Major in year 12. It is a programming written in Java using the Greenfoot IDE where the computer will generate a random maze, that is then solved and used for a set of 'species' to simulate genetic evoluation. The user is given menus and settings allowing for complete customization of evolution paramters, maze size, a customer maze editor, where files can be saved and imported later on with a custom file parser.
The code is not perfect, and there are a lot of flaws and even some sections that are just poorly coded. I started this project 2 years ago and was fairly new to java. 


Key/Cool Features
- Multithreaded ray caster 
  - Ray Caster from RC Cookie Library 
- Custom File saving/ GUI editding for files
- Custom Menu system with my own implementation of buttons, sliders, overlays and More!
  - The sliders can be dragged or buttons can be pressed for inputs (even in settings menu)
- Genetic Evolution Algorithm where learning can be simulated and visualized over 'generations'
- A Depth First Search maze Generator
- In-game settings that can be changed in real time
- A* Maze Solving Alogorithm 
  - Path can be toggled on and off wiht in-game settings
- User Manual :)

This demonstrates everything we learnt in the course in OOP java besides interfaces becuase I found using pure abstraction was easier for me at the time. 


All Questions about the project should be answered inside the User Manual which can be opened by pressing the 'Help' button in the menu screen.


**<h>IMPORTANT</h>**

Please note the Greenfoot IDE is not at all threadsafe, and my implementation is quite janky because of that. Do not use the IDE's 'act' or 'reset' buttons, use my own exit buttons within the 'world'. 
Ensure you are in the menu screen when greenfoot is closed. For some reason I didnt know that daemon threads existed when I coded this lol.

This requires greenfoot to be downloaded https://www.greenfoot.org/download), once installed simply doulbe click the project.greenfoot file. 
Before you press 'Enter World', press the 'Settings' button in the menu and then the 'Set Recc' button to use the better evolution settings, the computer wont really learn with the default ones. 
**Especially the Speed setting, having it at 20 will make it extremely slow, just set it to 100 :)**
- The menu settings are very important and if its laggy I suggest lowering the population
If you import one of the pre-saved maps, you may need to alt+tab into the file exploer pop up, this is an issue with greenfoot. 

Once again, some of the code (Especially the buttons if I remember) is a little spaghetti, I wasn't super experinced in conventions at time. 
If you have any questions, just email me @ Nicholas.Surmon@student.uts.edu.au ; I have more complex projects that I have taken on recently that are privated on my github that I would like to submit, but they're not exactly OOP technically. 

