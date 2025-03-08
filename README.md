## AEFI Data Analysis
- This project analyzes Adverse Events Following Immunization (AEFI) data for different vaccination products. 
- The data is read from a CSV file and various analyses are performed to answer specific questions.

### Project Structure
- [MyApp.scala](src/main/scala/MyApp.scala): Main application code.
- [aefi.csv](src/main/resources/aefi.csv): CSV file containing AEFI data, retrieved from [MoH-Malaysia](https://github.com/MoH-Malaysia/covid19-public)

### Analysis Performed 
- calculates the total doses for each vaccine product and identifies the most commonly used vaccine.
- calculates the total and average occurrences of headaches for each vaccine product.
- calculates the total occurrences of vomiting after the first injection for each vaccine product and identifies the one with the highest occurrence.  

### Sample Output
```
PRG2103 Assignment 2
-------------------------

Question 1: Which vaccination product is the most commonly used by Malaysian?
Total doses for each vaccine product:
---------------------------------------------
Vaccination Product   |   Total Occurrence
---------------------------------------------
astrazeneca           |   388394
pfizer                |   429277
sinopharm             |   74
sinovac               |   191420
Ans: The most commonly used vaccine product is pfizer with a total of 429277 doses.


Question 2: What are the average occurrences of headache for each type of vaccination product in the provided data?
Total and Average Occurrence of Headache for each type of vaccination product:
---------------------------------------------------------------------------------------
Vaccination Product   |   Total Occurrence   |   Average Occurrence by No. of Records
---------------------------------------------------------------------------------------
astrazeneca           |   236211             |   592.0075
pfizer                |   148250             |   316.7735
sinopharm             |   36                 |   0.1644
sinovac               |   80419              |   184.8713


Question 3: Which vaccination type has the highest occurrence of vomiting after the first injection in the provided data?
Total vomiting occurrences after first injection by vaccination type:
---------------------------------------------
Vaccination Product   |   Total Occurrence
---------------------------------------------
astrazeneca           |   52429
pfizer                |   36152
sinopharm             |   6
sinovac               |   20600
Ans: The vaccination type with the highest occurrence of vomiting after the first injection is astrazeneca with 52429 occurrences.
```
