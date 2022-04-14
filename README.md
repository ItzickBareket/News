# News
An android project demonstrating MVVM architecture.

The repository class handles fetching data from 3 sources : local Json file, RoomDB, and external website.

The app has 3 Screens :

1) Main screen : shows list of news categories to choose from.

2) Articles screen : shows minimum information about each article the was retrieved from news API.

* If an article is marked as favorite, it is added to Room DB.

3) Favorites screen : shows all current articles marked as favorites.

All data is managed via repository class, communication with ui is done via ViewModels.
