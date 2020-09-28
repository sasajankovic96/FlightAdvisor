# FlightAdvisor

For running this app Java 11 is needed.
The application listens on port 5000. If needed, the port can be changed by changing server.port value in application.properties. 
The application uses in memory database. 
To use the application a login is needed. For log in, username and password need to be provided. There are two users preconfigured. First user has username 'jackwhite', password 'Admin!123' and role 'ADMINISTRATOR'. The second user has username 'johnsmith', password 'RegUserPass!123' and role 'REGULAR_USER'. 
New users can be registered. For this to work email and email password need to be supplied in the application.properties file. After registering, the user will get an activation code on his email. To activate his account the user needs to post the code to /auth/account/verify
