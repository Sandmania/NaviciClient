NaviciClient
===============

A client utility for communicating with navici server (such as http://jyvaskyla.matkahuolto.info).
This is something that I originally wrote just for my own use and for fun, so in its current state its kinda badly documented and tested. I'm hoping to fix that soon.

- Android friendly
- Does automatic coordinate conversion from KKJ to WGS84 in order to ease use with Google Maps (and therefore depends on CoordinateUtils https://github.com/Sandmania/CoordinateUtils)

Usage
-----

- Get city specific client instance

```java
NaviciClient naviciClient = NaviciClient.getInstance(City.JYVASKYLA);
```

- Do location requests for start and end points of route

```java
JsonLocationResponse fromLocationResponse = naviciClient.sendRequest("Helokantie 1");
JsonLocationResponse toLocationResponse = naviciClient.sendRequest("Nevakatu 1");
```

- Do a route request with the searched locations

```java
// Extract location from the response
Location fromLocation = fromLocationResponse.getData().getLocations().get(0);
fromLocation.setOrder(0); // Tells the server that this location is the starting point of the route TODO replace magic number with an enum

// Extract location from the response
Location toLocation = toLocationResponse.getData().getLocations().get(0);
fromLocation.setOrder(1); // Tells the server that this location is the end point of the route TODO replace magic number with an enum

// Generate route request
Date date = Calendar.getInstance().getTime();
String time = "0845"; // Format HHmm, needs leading zero
String timeDirection = "forward"; // Other possible value is "backward". Use forward for departure time, backward for arrival time TODO make the api more selfdocumenting, create an enum or something for this
AjaxRequestObject routeRequest = naviciClient.generateRouteRequest(date, time, fromLocation, toLocation, timeDirection);
NaviciResponse response = naviciClient.sendRequest(routeRequest);
```

- Do what you want with the data

```java
// All routes in the response
List<Route> foundRoutes = response.getAjaxResponseObject().getRouteResponse().getGuiObjects().getMtrxml().getRoutes();

Route route = foundRoutes.get(0);

for(Path path : route.getPath()) {
	if(path instanceof Point) { // start and end points of the route
		Point point = (Point) path;
		if(point.getUid().equals("start")) {
			// This is the starting point of the route. The coordinates should match Helokantie 1
		} else if(point.getUid().equals("stop")) {
			// This is the end point of the route. The coordinates should match Nevakatu 1
		}
	} else if(path instanceof Walk) { // Walking bits of the route
		Walk walk = (Walk) path;
		walk.getLength().getTime(); // For how long do we need to walk
		walk.getLength().getDist(); // The distance we need to walk
		walk.getPathPoints(); // Returns every point on the path. Connect the points and you get the route you need to travel.
	} else if(path instanceof Line) { // Bus line
		Line line = (Line) path;
		line.getLength().getTime(); // For how long do we need to ride the bus
		line.getCode(); // Bus number
		line.getPathPoints(); // Returns every point on the path. Connect the points and you get the route you need to travel.
	}
}

```


Notes
-----
Navici seems to be in some sort of transition from XML to Json when it comes to communication. The location requests are now done with Json, but route requests are still done with XML.
NaviciClient provides both the Json and XML versions for location requesting, but currently the Json one should be used.

TODO
----
- Tests
- Proper documentation
