# Informational Telegram Bot (Portfolio & Cheatsheet)

<p align="center">
  <img src="https://img.shields.io/badge/status-in%20development-blueviolet.svg" alt="Status">
  <img src="https://img.shields.io/badge/Java-17+-orange.svg" alt="Java">
  <img src="https://img.shields.io/badge/Spring_Boot-3.x-brightgreen.svg" alt="Spring Boot">
  <img src="https://img.shields.io/badge/Telegram_Bot_API-6.x-blue.svg" alt="Telegram API">
  <img src="https://img.shields.io/badge/License-MIT-lightgrey.svg" alt="License">
</p>

This is a multi-functional Telegram bot developed with Java and Spring Boot. It serves two main purposes:
1.  **Personal Portfolio**: Showcases development skills, including API integration, interactive menu creation, and code structuring.
2.  **Pocket Cheatsheet**: Provides quick access to syntax, commands, and concepts related to web development and cybersecurity.

**Please note that this bot is currently under active development. More features and content will be added over time.**

---

## ✨ Features

-   **Interactive Menus**: Easy navigation using inline keyboard buttons.
-   **Portfolio Section**: Includes "About Me" and "Contact" information.
-   **Educational Content**: Divided into two main categories:
    -   **Web Development**: Quick examples for Java, Python, JavaScript, and PHP.
    -   **Cybersecurity**: Cheatsheets for a wide range of popular tools like Nmap, Wireshark, Metasploit, Burp Suite, sqlmap, and more.
-   **Code Snippets**: Clear and concise code examples and command snippets for each topic.
-   **User-Friendly Navigation**: "Back" and "Main Menu" buttons for a seamless user experience.

## 🚧 Current Status & Future Plans

The bot is fully functional in its current state but is considered a work in progress. It's a personal project aimed at continuous learning and improvement.

**Planned Enhancements:**
-   **Expanding Content**: Adding more tools, programming languages, and detailed examples.
-   **Search Functionality**: Implementing a way for users to quickly search for a specific command or topic.
-   **User Feedback**: Adding a system for users to suggest new content or report issues.
-   **Code Refactoring**: Continuously improving the codebase for better performance and readability.
-   **Deployment**: Setting up a CI/CD pipeline and deploying to a cloud platform (e.g., Heroku, AWS).

## 🛠️ Tech Stack

-   **Java 17+**: Core programming language.
-   **Spring Boot 3.x**: For easy application setup and dependency management.
-   **Telegram Bots API (`telegrambots-spring-boot-starter` 6.8.0)**: Integration with Telegram.
-   **Maven**: Project build and dependency management.
-   **SLF4J & Logback**: For application logging.

## 📁 Project Structure
src/main/java/com/tardis_han/RP_public/bot/
├── MyTelegramBot.java # Main class, handles updates and message routing.
├── CallbackHandler.java # Processes all button clicks (callbacks).
├── config/
│ └── BotConfig.java # Spring configuration for the bot.
└── service/
├── BotService.java # Service for core bot logic.
├── MenuService.java # Helper service to create menu buttons.
└── MessageService.java # Helper service for sending messages.
src/main/resources/
└── application.properties # Application settings, including bot token.

text

## 🚀 Installation and Launch

### Prerequisites
-   Java 17 or higher
-   Maven 3.6+
-   A Telegram Bot Token from [@BotFather](https://t.me/BotFather)

### Steps

1.  **Clone the repository:**
    ```bash
    git clone https://github.com/your-username/your-repo-name.git
    cd your-repo-name
    ```

2.  **Configure Bot Credentials:**
    Create or edit the `src/main/resources/application.properties` file and add your bot's credentials:
    ```properties
    # Telegram Bot Configuration
    bot.username=YourBotUsername
    bot.token=1234567890:YOUR_SUPER_LONG_TOKEN_FROM_BOTFATHER

    # Optional: Spring Boot specific settings
    spring.main.banner-mode=off
    logging.level.com.tardis_han=info
    ```

3.  **Build the project:**
    ```bash
    mvn clean package
    ```

4.  **Run the application:**
    ```bash
    java -jar target/your-project-name-0.0.1-SNAPSHOT.jar
    ```
    The bot should now be running and responsive in Telegram.

### Running in Development Mode
For faster development, you can use Spring Boot's Maven plugin to run the app directly:
```bash
mvn spring-boot:run
🤖 How to Use

Open Telegram and search for your bot by its username.
Send the /start or /menu command to initiate the bot and open the main menu.
Use the inline keyboard buttons to navigate through the different sections (Portfolio, Web Dev, Cybersecurity).
Click on any topic to view the relevant cheatsheet or code example.
Use the "Back" and "Main Menu" buttons to navigate easily.
📜 License

This project is licensed under the MIT License. See the LICENSE file for details.

🤝 Contributing

As this is primarily a learning and portfolio project, direct contributions may not be the main focus. However, suggestions, ideas, and feedback are always welcome!

If you find a bug or have a feature request, please open an issue.
If you have a suggestion for a new cheatsheet or code example, feel free to describe it in an issue.
<p align="center"> <i>Built with ❤️ using Spring Boot and the Telegram API.</i> </p> ```
