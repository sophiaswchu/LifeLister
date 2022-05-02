# Life Lister - A Birding Application

## Sophia Chu's CPSC 210 Personal Project

For many years I have been an avid birder and as such every year I see many new bird species.
In the birding community there is something called a *"life list"*, which is a list of all 
the different species of birds a birder has seen in their lifetime. Now I have seen a lot 
of birds in my lifetime and I hope to see even more in the future... I've tried recording 
my life list on paper, on my first generation iPod touch, and even on my Notes application 
but none of these worked for me long term. I plan to create an application that allows 
birders to record their life list in a way that works! 

##User Stories 

As a user I want to be able to...
- add a bird to my life list
- view the list of birds in my life list
- know if a specific species of bird is on my life list
- view the last new species I've seen 
- update the last sighting of a bird 
- view all the birds seen in a specific time period
- view all the birds seen in a specific location
- be able to close the application using a quit option
- have my file save when I choose
- load a saved list from file when I choose

##References:
https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
https://github.students.cs.ubc.ca/CPSC210/TellerApp
https://stackoverflow.com/questions/6714045/how-to-resize-jlabel-imageicon
https://stackoverflow.com/questions/299495/how-to-add-an-image-to-a-jpanel
https://stackoverflow.com/questions/28394129/getting-the-value-from-a-jspinner
https://www.codejava.net/java-se/swing/jpanel-basic-tutorial-and-examples#SetLayout
https://stackoverflow.com/questions/23349506/linked-list-gui-tostring
https://docs.oracle.com/javase/tutorial/uiswing/examples/components/ListDemoProject/src/components/ListDemo.java
https://stackoverflow.com/questions/6578205/swing-jlabel-text-change-on-the-running-application
https://docs.oracle.com/javase/tutorial/uiswing/examples/events/ListDataEventDemoProject/src/events/ListDataEventDemo.java

##Phase 4: Task 2

Tue Nov 23 19:36:08 PST 2021
New bird, Gray Jay, added to lifeList.


Tue Nov 23 19:37:02 PST 2021
New bird, Robin, added to lifeList.


Tue Nov 23 19:37:30 PST 2021
New bird, Bald Eagle, added to lifeList.


Tue Nov 23 19:38:27 PST 2021
Sighting of Gray Jay changed from 02/04/2020, Vancouver to 11/10/2021, Burnaby.

##Phase 4: Task 3
- make Sighting implement Writeable instead of converting to and from a String inside Bird
- this would make more sense in terms of structure as all elements of model would then be 
  directly written to file rather than having to be converted to and from a String with an 
  additional method every time