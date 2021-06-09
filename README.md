# taskmaster

Is an android app created for creating, monitoring and organizing user tasks

## Documentation

### May 16 :

- Created a new directory and repo to hold the app, named taskmaster.
- Created three pages with very basic layout - homepage, add a task, all tasks.
- The homepage has two buttons, one which allows the user to go to the Add Task view, and the other that allows the user to go to the All Tasks view.
- Added a task page that allows users to type in a title and a body for their task.

## app views

![main](https://github.com/anassawalha95/taskmaster/blob/main/screenshots/mainLab26.png)

![add](https://github.com/anassawalha95/taskmaster/blob/main/screenshots/addtaskLab26.png)

![all](https://github.com/anassawalha95/taskmaster/blob/main/screenshots/alltasksLab26.png)

### May 17 :

- Created a Task Detail Page
- Created Settings page that allows users to enter their username and hit save.
- The main page was modified to contain three different buttons with hardcoded task titles. When a user taps one of the titles, it goes to the Task Detail page, and the title at the top of the page matches the task title that was tapped on the previous page.
- The homepage contains a button to visit the Settings page. Once the user has entered their username

## app views

![main](https://github.com/anassawalha95/taskmaster/blob/main/screenshots/mainLab27.png)

![setting](https://github.com/anassawalha95/taskmaster/blob/mainForResubmission/screenshots/setting.png)

![details](https://github.com/anassawalha95/taskmaster/blob/main/screenshots/task-detailsLab27.png)


### May 18 :

- Created a TaskModel class. Each task has a title, body and a state.
- Refactored home page of app to display a RecyclerView to display Task data using a styled fragment.
- Created a ViewAdapter class that displays data from a list of Tasks.
- Data that displays on home page includes title and task body, and is from hardcoded Task data.
- User is able to touch a task in the RecyclerView, and is directed over to a detail page, where the title and the body is rendered correctly (for that task).

## app views


![main](https://github.com/anassawalha95/taskmaster/blob/main/screenshots/mainLab28.png)

![task-details](https://github.com/anassawalha95/taskmaster/blob/main/screenshots/task-detailsLab28.png)



### May 19 :

- Modified Add Task form to save the data entered in as a Task in the local SQLite database.
- Refactored homepage RecyclerView to display all Task entities in database.
- Description and title of a tapped task is displayed on the detail page along with the status

## app views


![main](https://github.com/anassawalha95/taskmaster/blob/main/screenshots/mainLab29.png)

![task-details](https://github.com/anassawalha95/taskmaster/blob/main/screenshots/task-detailsLab29.png)

### May 24 :

- Add Espresso tests 

### May 30 :

- Added the functionality to use AWS Amplify 
- build and connected to the DynamoDB using graphQL 


### May 31 :

- Added the functionality to use AWS coginto for login signup 


![login](https://github.com/anassawalha95/taskmaster/blob/mainForResubmission/screenshots/login.PNG)

![notifications](https://github.com/anassawalha95/taskmaster/blob/mainForResubmission/screenshots/signup.jpg)

### June 8 :

- Added the functionality to use AWS Storage for uploading, viewing and downloading files

 ![task details img](https://github.com/anassawalha95/taskmaster/blob/mainForResubmission/screenshots/task-detailsLab37.png)

 ![task details img](https://github.com/anassawalha95/taskmaster/blob/mainForResubmission/screenshots/task-detailsLab37_1.png)
 
 
 ![task details img](https://github.com/anassawalha95/taskmaster/blob/mainForResubmission/screenshots/task-detailsLab37_2.png)
 
 
### June 8  after daylight:

- Added the functionality to use firebase notifications

 ![notifications](https://github.com/anassawalha95/taskmaster/blob/mainForResubmission/screenshots/notificationLab38.png)


### June 9  :

- Added the functionality to Intent filter dirctly upload an image file to the taks file
- 
![intent filter1](https://github.com/anassawalha95/taskmaster/blob/mainForResubmission/screenshots/intent-filterLab41_0.png)

![intent filter2](https://github.com/anassawalha95/taskmaster/blob/mainForResubmission/screenshots/intent-filterLab41_2.png)

![intent filter3](https://github.com/anassawalha95/taskmaster/blob/mainForResubmission/screenshots/intent-filterLab41_1.png)
