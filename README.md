# CalcMadeEasy

A fullstack mathematics learning platform designed to make calculus and advanced mathematical topics accessible and engaging. Inspired by interactive learning methodologies, this application provides structured lessons, interactive problem-solving, and comprehensive progress tracking.

## Table of Contents

- [About the Project](#about-the-project)
- [Key Features](#key-features)
- [Tech Stack](#tech-stack)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
  - [Environment Variables](#environment-variables)
- [Project Structure](#project-structure)
- [Usage](#usage)
- [Contributing](#contributing)
- [License](#license)
- [Contact](#contact)

## About the Project

CalcMadeEasy is a comprehensive educational web application aimed at simplifying complex mathematical concepts. The platform is built with a focus on interactive learning, offering carefully crafted lessons, homework problems, and examinations. Upon completing a course, users are awarded a certificate of completion. User progress and authentication are managed securely via Google OAuth 2.0.

## Key Features

- **Interactive Lessons**: Step-by-step guided learning modules for calculus and advanced topics.
- **Dynamic Problem Solving**: Carefully designed homework problems with real-time feedback and mathematical input support.
- **Progress Tracking**: Persistent user progress tracking linked securely to Google accounts.
- **Assessments & Certification**: End-of-course examinations and automated certificate generation.
- **Responsive Design**: Optimized for both desktop and mobile learning experiences.

## Tech Stack

- **Frontend**: React, TypeScript, Vite, MathLive (for mathematical input/rendering)
- **Backend**: Java (Spring Boot)
- **Database**: Supabase, PostgreSQL
- **Authentication**: Google OAuth 2.0
- **Styling**: CSS, Tailwind CSS

## Getting Started

To get a local copy up and running, follow these steps:

### Prerequisites

Ensure you have the following installed on your local machine:
- Node.js (v18 or higher) and npm
- Java Development Kit (JDK 17 or higher)
- Maven
- [Supabase, e.g., PostgreSQL]

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/dragiev1/CalcMadeEasy.git
   cd CalcMadeEasy
   ```

2. Set up the client:
   ```bash
   cd client
   npm i
   ```

3. Set up server:
   ```bash
   cd ../server
   mvn clean install
   ```

## Environment Variables
Create a .env file in the client directory and a corresponding configuration file (e.g., application.properties or .env) in the server directory. 

Client (`client/.env`):
```
VITE_API_BASE_URL=http://localhost:8080
VITE_GOOGLE_CLIENT_ID=your_google_client_id
```

Server (`server/src/main/resources/application.properties`):

```
spring.datasource.url=jdbc:postgresql://localhost:5432/calcmadeeasy
spring.datasource.username=your_db_username
spring.datasource.password=your_db_password
google.oauth2.client-id=your_google_client_id
google.oauth2.client-secret=your_google_client_secret
```


### Project Structure
```
CalcMadeEasy/
├── client/                 # Frontend React application
│   ├── public/             # Static assets
│   ├── src/                # Source code (components, services, hooks)
│   ├── package.json        # Frontend dependencies
│   └── vite.config.ts      # Vite configuration
├── server/                 # Backend Java application
│   ├── src/                # Source code (controllers, services, models)
│   └── pom.xml             # Maven dependencies and build configuration
└── README.md               # Project documentation
```

### Usage

1. Start backend server:
```
cd server
mvn spring-boot:run
```

2. In a separate terminal, start the frontend server
```
cd client
npm run dev
```

3. Open your browser and navigate to `http://localhost:5173` (or the port specified by your Vite configuration).


### Contributing
Contributions are welcome and appreciated. If you have a suggestion that would make this platform better, please fork the repository and create a pull request.

1. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
2. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
3. Push to the Branch (`git push origin feature/AmazingFeature`)
4. Open a Pull Request

### License 
Distributed under the MIT License. See LICENSE for more information.

### Developers/Contact
Stiviyan Dragiev
GitHub: `@dragiev1`
Project Link: `https://github.com/dragiev1/CalcMadeEasy`



   
