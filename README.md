# RestFileParser
A Spring app that exposes several endpoints: one for uploading multiple text files, another for getting results.

Business logic: uploaded files are parsed in parallel way using the Executor framework. The Parser calculates the frequency of distinct lines across all files uploaded along one HTTP request.

Used technologies: IDEA, Java8, H2 database, Spring Data JPA, Spring MVC.
To run the build: choose three goals: clean, package, tomcat7:run and click button 'Run Maven Build'.
