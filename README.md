# csvToTadDelimited

I wrote this Java program while working for Jahnel Group. 

My assignment was to write an Object-Oriented program that reads in a file with comma-delimited list of student names, student IDs, and student schools. The program should parse the list to produce and save a separate file for each school name. Each school file should contain a tab-delimited list of students that belong to that school. 

Additional requirements: 
Write this code in a simple text editor and compile and run it via the command line. Comments are not permitted for this exercise. The program should handle any number of schools. 

My example of a CSV file for this program. 
```
3,John Watson,Harvard
4,Jessica Johns,Yale
6,Joseph Loop,Yale
7,Matt Berry,Yale
1,Dave Skrinik,Suny
2,Hope Skrinik,Suny
9,Mark Malone,Suny
9,Mark Malone,Suny
8,Mark Malone,Suny
5,John Watson,Siena
```
Notice that Mark Malone repeats 3 times. Yet only 2 times Mark Malone has the same ID of 9. Records with same name and ID should be ignored. Records with same names but different IDs should not be ignored since a school may contain students with identical names, so long as they have a unique IDs.

The resulting output school files and their students should be the following. 
```
Harvard
3	John Watson

Yale
6	Joseph Loop
7	Matt Berry
4	Jessica Johns

Suny
2	Hope Skrinik
1	Dave Skrinik
8	Mark Malone
9	Mark Malone

Siena
5	John Watson
```
