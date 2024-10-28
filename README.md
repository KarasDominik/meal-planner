## How to run locally:
1. Clone the repository:
   ```bash
   git clone git@github.com:KarasDominik/meal-planner.git
2. Navigate to the project directory:
   ```bash
   cd meal-planner
3. Run the application:
   ```bash
   ./mvnw spring-boot:run
4. The application will be running on port 8080

## How to use
To fetch some recipes, send GET request to http://localhost:8080/api/v1/meal.

Additionally, to make sure your favourite ingredient is included, you can add it to the url with `name` parameter: http://localhost:8080/api/v1/meal?name=chicken.


Having ids of chosen meals, send POST request onto the following url to generate weekly pdf: http://localhost:8080/api/v1/weekly-pdf.

Exemplary request body is as follows:

```json
{
    "monday": 52772,
    "tuesday": 53013,
    "wednesday": 52977,
    "thursday": 52978,
    "friday": 53026,
    "saturday": 52948,
    "sunday": 52971
}
```
Each field is optional.

## Technologies Used

Spring boot, iTextPDF, Apache PDFBox, JUnit, Mockito, RestAssured

