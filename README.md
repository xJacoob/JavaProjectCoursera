# Interactive World Map - Java (UCSD Course Project)

[cite_start]This project is an interactive world map application that visualizes airport traffic and earthquake data globally[cite: 15]. [cite_start]It was developed as part of the "Object Oriented Programming in Java" course by UC San Diego[cite: 1].

## My Contributions & Implemented Features

Based on the project documentation, I implemented and enhanced the following functionalities:

* [cite_start]**Map Provider Integration**: Switched to Microsoft Hybrid Maps to ensure stable tile loading and better visualization[cite: 16, 19].
* [cite_start]**Traffic Analysis System**: Developed the `calculateTraffic` method to return a map with source and number of routes from each airport[cite: 21].
* [cite_start]**Filtering Logic**: Excluded airports with zero routes from the visualization to keep the map clean[cite: 20].
* [cite_start]**Dynamic Marker Visualization**: Implemented custom logic where airport markers are drawn with different colors and sizes based on their specific traffic value[cite: 17, 22].
* **Advanced User Interactivity**:
    * [cite_start]**Hover Information**: Implemented `mouseMoved` to highlight airport information like name, country, city, and ID[cite: 18, 24].
    * [cite_start]**Click Interaction**: Added `mouseClicked` functionality where clicking an airport shows its specific routes and hides all other airports and routes[cite: 18, 25].
    * [cite_start]**Route Visibility**: Routes are hidden by default and only become visible when the corresponding airport is selected[cite: 26].
* [cite_start]**Graphical User Interface**: Created a dynamic legend (`addKey`) explaining the meaning of different marker sizes and colors[cite: 23].
* [cite_start]**Marker Management**: Implemented the `unhideMarkers` method to reset the view[cite: 27].

## Technologies & Data Structures
* [cite_start]**Language**: Java [cite: 6]
* [cite_start]**Libraries**: Unfolding Maps, Processing [cite: 1, 8]
* [cite_start]**Structures**: Used `HashMap<String, ArrayList<Marker>>` to efficiently store and access routes connected to each airport.
