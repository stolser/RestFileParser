# RestFileParser
A Spring app that exposes several endpoints: one for uploading multiple text files, another for getting results.

Business logic: uploaded files are parsed in parallel way. The Parser calculates the frequency of distinct lines across all files uploaded
along one HTTP request.

Used technologies: H2 database, Spring Data JPA, Spring MVC.
