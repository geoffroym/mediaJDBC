# Media Library Project

This project is a media library where we store and manage information about different books, games, and movies.

## Features
- Store information about books, games, and movies.
- Normalize the game platform table for better organization, as one game can have multiple platforms.
- Read data from a text file and add it to the database.
- User can log in by adding their name to the database.
- Users can:
  - View all information.
  - Search by name (e.g., author, director, developer).
  - Add and remove media from the database.

## Project Structure

### FileReader
The `FileReader` class reads data from a text file and processes it. This file contains the initial data for books, games, and movies. The data is then added to the database.

### MediaService
The `MediaService` class handles all MySQL data source methods. It is responsible for adding the data read from the text file to the database and provides methods for interacting with the database.

## Database Normalization
To organize the data effectively, the game platform information was normalized. This means the platform data is stored in a separate table, allowing a game to have multiple platforms associated with it.

## User Interaction
Once the data is read and added to the database, users can interact with the program. They can log in by adding their name to the database. After logging in, users can:
- View all information.
- Search by name (e.g., author, director, developer).
- Add new media to the database.
- Remove existing media from the database.

## Getting Started

### What I've used
- Java
- MySQL
- IntelliJ IDEA

### Installation
1. Clone the repository:
   ```sh
   git clone https://github.com/your-username/your-repository.git

