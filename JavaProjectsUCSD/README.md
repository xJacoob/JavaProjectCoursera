# Interactive World Map - Java (UCSD Course Project)

[cite_start]This project is an interactive world map application that visualizes airport traffic and earthquake data globally. [cite_start]It was developed as part of the "Object Oriented Programming in Java" course by UC San Diego.

## My Contributions & Implemented Features

Based on the project documentation, I implemented and enhanced the following functionalities:

* [cite_start]**Map Provider Integration**: Replaced the default map provider with Microsoft Hybrid Maps to ensure stable tile loading and better visualization.
* [cite_start]**Traffic Analysis System**: Developed the `calculateTraffic` method using `HashMap<Integer, Integer>` to count routes for each airport and filter out those with zero traffic.
* [cite_start]**Dynamic Marker Visualization**: Implemented custom logic in `AirportMarker.java` to scale and color markers (Green/Yellow/Red) based on the specific traffic volume of each location.
* **Advanced User Interactivity**:
    * [cite_start]**Hover Information**: Implemented `showTitle` to display detailed data (Country, City, Name, Code, Traffic) when hovering over an airport.
    * [cite_start]**Click Filtering**: Added logic to show only routes connected to a clicked airport while hiding all other irrelevant markers.
* [cite_start]**Graphical User Interface**: Created a dynamic legend (`addKey`) and implemented a sorting algorithm (`sortAirports`) to display the Top 10 most crowded airports in the sidebar.

## Technologies & Data Structures
* [cite_start]**Language**: Java 
* [cite_start]**Libraries**: Unfolding Maps, Processing 
* [cite_start]**Structures**: HashMap, ArrayList, List, ShapeFeature
