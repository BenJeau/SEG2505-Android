# SEG2505-Android

Cette application Android est pour le cours SEG2505 et vise à être utile pour les fournisseurs de services pour qu'ils puissent être facilement accessibles de nos téléphones mobiles.

Nous utilisons [Trello](https://trello.com/b/3PInGFvG/seg-android-app) pour garder une trace de notre processus de développement et assigner des tâches aux membres de notre équipe.

Ce répertoire contient le code nécessaire pour l'ouvrir dans Android Studio. Nous utilisons la version 3.2.1 de Android Studio.

Nous utilisons UmpleOnline pour notre UML du code. Voici le lien pour notre premier livrable: http://cruise.eecs.uottawa.ca/umpleonline/umple.php?text=class%20Person%0A%7B%0A%20%20username%3B%0A%20%20password%3B%0A%7D%0A%0Aclass%20Administrator%0A%7B%0A%20%20isA%20Person%3B%0A%20%201%20--%2010..*%20Service%3B%0Apublic%20String%20createService()%7B%0A%0A%7D%0Apublic%20String%20removeService()%7B%0A%0A%7D%0A%7D%0A%0Aclass%20Provider%0A%7B%0A%20%201..*%20--%201..*%20Service%3B%0A%20%201%20-%3E%201..*%20Availability%3B%0A%20%20isA%20Person%3B%0Apublic%20String%20createService()%7B%0A%0A%7D%0Apublic%20String%20removeService()%7B%0A%0A%7D%0A%7D%0A%0Aclass%20Owner%0A%7B%0A%20%20isA%20Person%3B%0A%20%201%20--%200..*%20Service%3B%0Apublic%20String%20research()%7B%0A%0A%7D%0Apublic%20String%20appointment()%7B%0A%0A%7D%0A%7D%0A%0Aclass%20Availability%0A%7B%0A%20%20TimeStamp%20time%3B%0A%20%20day%3B%0A%7D%0A%0Aclass%20Service%0A%7B%0A%20%20serviceType%3B%0A%20%20Double%20hourlyRate%3B%0A%7D%0A%0A%2F%2F%24%3F%5BEnd_of_model%5D%24%3F%0A%0Aclass%20Person%0A%7B%0A%20%20position%20136%2012%20138%2095%3B%0A%7D%0A%0Aclass%20Administrator%0A%7B%0A%20%20position%2013%20151%20202%2097%3B%0A%20%20position.association%20Administrator__Service%20193%2C97%201%2C0%3B%0A%7D%0A%0Aclass%20Provider%0A%7B%0A%20%20position%20421%20152%20192%20115%3B%0A%20%20position.association%20Provider__Service%2030%2C115%20132%2C0%3B%0A%20%20position.association%20Availability__Provider%20115%2C115%2066%2C0%3B%0A%7D%0A%0Aclass%20Availability%0A%7B%0A%20%20position%20458%20315%20141%2097%3B%0A%7D%0A%0Aclass%20Service%0A%7B%0A%20%20position%20198%20324%20152%2079%3B%0A%7D%0A%0Aclass%20Owner%0A%7B%0A%20%20position%20226%20151%20187%2095%3B%0A%20%20position.association%20Owner__Service%2071%2C95%2052%2C0%3B%0A%7D. 
Faire sûr de peser le M pour voir les méthodes sur Umple après avoir clické le lien SVP. Vous pouvez aussi visionner le diagramme UML dans le fichier pdf nommé SEG2505_Android_UML.

Pour accéder à notre répertoire [github](https://github.com/BenJeau/SEG2505-Android), utiliser le lien donner.

Le fichier APK peut se retrouver à la racine de ce répertoire dans le fichier nommé *Droids_debug.apk*. Il y a un bug dans le fichier APK, il n'est pas capable de prendre le role de l'utilisateur de firebase, mais ce problème n'est pas présent lorsque tu installe l'application directement par Android Studio.

Membres de l'équipe:

|Github Username | Nom  | Numéro Étudiant |
| --- | --- | --- |
|BenJeau              | Benoit Jeaurond        | 300015520|
|amous123           | Alexander Moussa      | 300018112|
|dngen049           | diedrick ngendahayo | 300008344|
|vergenieh            |Vergenie Howayek    |300008321|
|amprodz          | Aymen Mhamdi          |300034572|

Nous avons connecté circleci à notre projet:
![alt text](https://github.com/BenJeau/SEG2505-Android/blob/master/circleCiConnected.png)
