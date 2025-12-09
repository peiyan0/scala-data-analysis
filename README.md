# 💉 COVID-19 Vaccine AEFI Data Analysis

> A data analysis project that processes Adverse Events Following Immunization (AEFI) data to derive key insights on vaccination products, focusing on occurrences of side effects in Malaysia.

<p align="center">
  <img src="https://img.shields.io/badge/Domain-Public%20Health%20Data-red?style=for-the-badge" alt="Domain: Public Health Data" />
  <img src="https://img.shields.io/badge/Source-MoH--Malaysia-informational?style=for-the-badge" alt="Source: MoH-Malaysia" />
</p>

## 📊 Key Analysis Performed

This project processes raw AEFI data to answer critical public health questions:

1.  **Usage Frequency**: Calculates the total doses administered for each vaccine to identify the **most commonly used vaccine product**.
2.  **Headache Occurrence**: Determines the **total and average occurrences of headaches** for every vaccine type.
3.  **First-Dose Reactions**: Identifies the vaccine with the **highest occurrence of vomiting** specifically after the first injection.

## 💻 Tech Stack

| Category | Technologies |
| :--- | :--- |
| **Language** | ![Scala](https://img.shields.io/badge/Scala-DC322F?style=for-the-badge&logo=scala&logoColor=white) |
| **Data Source** | `aefi.csv` (Retrieved from [MoH-Malaysia/covid19-public](https://github.com/MoH-Malaysia/covid19-public)) |
| **Environment** | Java Development Kit (JDK) |
| **Tooling** | Sbt (Scala Build Tool) |

## 📁 Project Structure

The project is structured simply for clarity and focus on the analysis logic:
```
. ├── src/ 
│ └── main/ 
│ ├── resources/ 
│ │ └── aefi.csv # The raw AEFI dataset 
│ └── scala/ 
│ └── MyApp.scala # ➡️ Main application logic 
└── build.sbt
```

## 🔍 Sample Output

Below is a snapshot of the insights generated from the analysis:
```
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
