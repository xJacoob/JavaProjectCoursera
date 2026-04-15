# Interactive World Map - Java (UCSD Course Project)
This project is an interactive world map application that visualizes airport traffic and earthquake data globally. It was developed as part of the "Object Oriented Programming in Java" course by UC San Diego.

## My Contributions & Implemented Features
Based on the project documentation, I implemented and enhanced the following functionalities:

*Map Provider Integration: Switched to Microsoft Hybrid Maps to ensure stable tile loading and better visualization.
*Traffic Analysis System: Developed the calculateTraffic method to return a map with source and number of routes from each airport.
*Filtering Logic: Excluded airports with zero routes from the visualization to keep the map clean.
*Dynamic Marker Visualization: Implemented custom logic where airport markers are drawn with different colors and sizes based on their specific traffic value.

Advanced User Interactivity:
*Hover Information: Implemented mouseMoved to highlight airport information like name, country, city, and ID.
*Click Interaction: Added mouseClicked functionality where clicking an airport shows its specific routes and hides all other airports and routes.
*Route Visibility: Routes are hidden by default and only become visible when the corresponding airport is selected.
*Graphical User Interface: Created a dynamic legend (addKey) explaining the meaning of different marker sizes and colors.
*Marker Management: Implemented the unhideMarkers method to reset the view.

Technologies & Data Structures
*Language: Java
*Libraries: Unfolding Maps, Processing
*Structures: Used HashMap<String, ArrayList<Marker>> to efficiently store and access routes connected to each airport.
