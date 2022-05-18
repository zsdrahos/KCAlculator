
# KCALculator 
> The calory calculator app


![](https://raw.githubusercontent.com/zsdrahos/KCAlculator/main/Kép7.png)

`DOWNLOAD` : [kcalculator.apk](https://github.com/zsdrahos/KCAlculator/blob/main/kcalculator.apk)


### About
Eating is always a problem, it is not always a question of how much of what you should get into your body because after a certain amount it can be poisonous. To help with this, I have developed an app, that helps you easily keep track of calories, protein and other nutritional values, that we put into our bodies every day. The purpose of this app is to make everyday life easier, even for an athlete who needs to watch how many calories they take in a day or for an everyday person who just wants to eat more calories and maintain a healthy lifestyle. 

### Technlogy

- UI: The application user interface: activities, fragments and Intetn
- Recyclerview: the application displays a list of dishes using RecyclerView
- Network Management: the application will use an open API and will download the meal data via a REST endpoint from which the user can choose (https://calorieninjas.com/api)
- Database management: the user will save the ingested nutrients in a Room database and the profile will be saved in a shared preferences.
- Graph: an external library is used to display the total caloric intake for a given time of day (MPAndrpidChart). 



![](https://raw.githubusercontent.com/zsdrahos/KCAlculator/main/image/Kép1.png)![](https://raw.githubusercontent.com/zsdrahos/KCAlculator/main/image/Kép2.png)

Profile view, first start and second start


![](https://raw.githubusercontent.com/zsdrahos/KCAlculator/main/image/Kép3.png)![](https://raw.githubusercontent.com/zsdrahos/KCAlculator/main/image/Kép4.png)

Clicking on the + button will bring up a dialog fragment where you can record the food and select when the user ate it



![](https://raw.githubusercontent.com/zsdrahos/KCAlculator/main/image/Kép5.png)![](https://raw.githubusercontent.com/zsdrahos/KCAlculator/main/image/Kép6.png)

Graph view: if you swipe the screen to the left you can see the second graph





