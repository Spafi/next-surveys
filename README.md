<!-- PROJECT LOGO -->
<br />
<p align="center">
  <a href="https://github.com/Spafi/online-surveys/">

[comment]: <> (    <img src="images/logo.png" alt="Logo" width="80" height="80">)
  </a>

<h3 align="center">Next Surveys</h3>

  <p align="center">
    This project is an online survey platform, where users can register and create or respond to surveys.
    Backend written in Java with Spring Framework and PostgreSQL for database.
    Frontend written in Javascript with React framework and styled with TailwindCSS framework.
</p>



<!-- TABLE OF CONTENTS -->
<details open="open">
  <summary><h2 style="display: inline-block">Table of Contents</h2></summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>

  </ol>
</details>



<!-- ABOUT THE PROJECT -->

## About The Project

This project is my first full stack solo project. The scope of it was to better understand React and refresh my Java & Spring knowledge. 
I also found the Tailwind CSS framework that I wanted to try and thought a site might be a good idea to learn it.
At the moment of writing this README, the project needs refactoring on the frontend part to stick with the DRY principle & to implement correctly react hooks. <br/>
It was a fun journey, and I learned a lot during its development.

### Built With

#### Backend: <img src="https://img.icons8.com/color/48/000000/java-coffee-cup-logo.png" alt="Java" height="40"/> Java +  <img src="https://img.icons8.com/color/48/000000/spring-logo.png" alt="Spring" height="30"/> Spring Framework + <img src="https://img.icons8.com/color/48/000000/postgreesql.png" alt="PostgreSQL" height="30" />  PostrgreSQL

#### Frontend: <img src="https://img.icons8.com/nolan/64/html-5.png" alt="HTML5" height="30" /> HTML5 + <img src="https://img.icons8.com/color/48/000000/javascript.png" alt="JavaScript" height="30" /> Javascript + <img src="https://img.icons8.com/color/48/000000/react-native.png" height="30"/> React + <img src="https://seeklogo.com/images/T/tailwind-css-logo-5AD4175897-seeklogo.com.png" alt="TailwindCSS" height="30" > TailwindCSS

<!-- GETTING STARTED -->

## Getting Started

To get a local copy up and running follow these simple steps.

### Prerequisites


* npm
  ```sh
  npm install npm@latest -g
  ```

* Java 11
  
  https://www.java.com/en/download/


* PostgreSQL

  https://www.postgresql.org/download/


### Installation
#### Frontend:
1. Clone the repo
   ```sh
   git clone https://github.com/Spafi/online-surveys.git
   ```
2. Install NPM packages
   ```sh
   npm install
   ```
3. Run the project
   ```
   npm start
   ```
#### Backend:
1. Create the PostgreSQL database
2. Replace the connection properties with your database credentials in ```src/main/resources/application.properties```
3. The backend uses Gmail SMTP for sending registration emails to users, so you'll need an Application Password for your account. You can see [here](https://support.google.com/mail/answer/185833?hl=en-GB#zippy=%2Cwhy-you-may-need-an-app-password) how to create one. <br>
After you create one, put it in ```src/.env``` at ```GMAIL_APPLICATION_PASSWORD```

[comment]: <> (<!-- USAGE EXAMPLES -->)

[comment]: <> (## Usage)


<!-- ROADMAP -->

## Roadmap

1. Refactor the frontend to stick to the DRY principle.
2. Refactor the frontend to keep the state of components only in the main component or implement Context API.
3. Redesign the UI using the mobile-first approach.
4. Add more features. 



<!-- CONTACT -->

## Contact

[![Twitter: Spafi](https://img.shields.io/twitter/follow/CristianSpafiu?style=social)](https://twitter.com/CristianSpafiu)
[![Linkedin: Spaf](https://img.shields.io/badge/-spaf-blue?style=flat-square&logo=Linkedin&logoColor=white&link=https://www.linkedin.com/in/spaf/)](https://www.linkedin.com/in/spaf/)
[![GitHub Spafi](https://img.shields.io/github/followers/spafi?label=follow&style=social)](https://github.com/Spafi)




<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->

[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555

[linkedin-url]: https://linkedin.com/in/Spafi
