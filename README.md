# FlipkartAutomation-Manual1
The Automation test steps as follows:
1. Launch Flipkart Web Application
2. Clicking ESC using Robot function to close the login pop up 
3. Enter Search key "iPhone" and click the search button
4. Set the price range from the filter dropdown to Rs.50,000 max 
5. Click on "Low to high" to sort by price
6. Read iphone description which includes title, price and its ratings 
7. Check if price is less than Rs. 40,000 than add it to the list
8. Repeat step 6 and 7 for the phones listed in the current page
9. Check if "Next" button is available and than click "Next" to navigate next Page
10. Repeat steps 6 to 9 until Next is available
11. Write the list data to the CSV file.

A CSV file is created in the root folder of the project.

