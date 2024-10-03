# GreevStatsStalker Discord Bot

GreevStatsStalker is an easy-to-use bot with slash commands for every endpoint of the public [Greev API](https://api.greev.eu/v2/swagger-ui/index.html#/).<br>
This bot is based on the Java Greev API Wrapper [JGA](https://github.com/JJJannik/JGA).

## Summary

1. [Introduction](#introduction)
2. [How to use](#how-to-use)
3. [Build it yourself](#build-it-yourself)
4. [Getting help](#getting-help)
5. [Contributing](#contributing)

## Introduction

The bot contains all data endpoints of the API with easy to use slash command arguments.<br>
E.g. all commands to get data from a specific player, have the `player` slash command option, <br>
this option accepts either the UUID of the player or the in-game name.

_Note:_ I always try to keep up with changes of the Greev API, if I miss something, feel free to message me or maybe even implement it on your own :^

All commands are split into different subcommand groups. Those are: <br>

- `/general` ~ The slash commands of that group are used for the general data, like namehistory, metadata etc.
- `/minigame` ~ This group contains every standardised minigame, with the two commands for the player stats and the top player stats of that gamemode.
- `/mlgrush` ~ All basic MLGRush slash commands (player stats, rolling player stats, top stats and rolling top stats)
- `/fastbridge` ~ Consists of the Fastbridge Islands mode command (because that one is a bit different) and one command for all the other Fastbridge modes.
- `/advent` ~ Slash commands for yearly the Advent Jump'n Run.
- `/knockpvp` ~ Contains first of all the basic KnockPvP commands, then I also added commands for the daily, weekly, monthly, yearly and N days stats of a player, because I thought that's the command group which will be used the most. <br> Therefor I ensured a better usability.
- `/knockpvp-lab` ~ Basic KnockPvP Lab commands + a command to list all lab experiment which existed including their API names, since those are required by the Lab stats commands.
- `/minesweeper` ~ All minesweeper API endpoints are represented as their own command, I hope those commands are a bit more usable than the API itself.

Additionally, there is a `/about` command, which just displays some general information about this bot and contains links leading to this repository.

## How to use

There are multiple possibilities to use the GreevStatsStalker bot:

1. You can just add the bot hosted by me to your own Discord server via [this invitation link](https://discord.com/oauth2/authorize?client_id=1239303102520361020&scope=bot).
    The bot only requires the permissions to create slash commands on your server.
2. Host the bot on your own from the latest release: [GitHub Release](https://github.com/JJJannik/GreevStatsStalker/releases/). Alternatively, you can also build the bot yourself from the source code, for that visit [Build it yourself](#build-it-yourself).<br><br>
   To run the bot you need at least Java 17. First, copy the bot .jar in your wished folder, in there you open the terminal and execute the command: `java -jar .\greev-stats-stalker-VERSION.jar`. <br>
   **IMPORTANT:** Pay attention that the name of the bot jar equals the name you use in the command, if needed change `VERSION` to the version you got.
    When you run the bot the first time, you will receive the error that no valid config was provided. After you ran the bot, a new folder should appear called `GreevStatsStalker`, inside that folder is the `config.yml`.<br>
    In there you paste the bot token, instead of the placeholder. You get your bot token after you created your own Discord application bot at [Discord's Developer Portal](https://discord.com/developers/applications). <br>
    **IMPORTANT:** Do not share your bot token with anyone, keep it secret! Everyone who has your bot token can run their own bot over that token.

## Build it yourself

To build this bot from source first of all clone the repository:

`git clone https://github.com/JJJannik/GreevStatsStalker.git`

Finally, build the bot with Gradle in the project terminal:

`./gradle shadowJar`

The built Jar will appear in `/build/libs/` and is called `greev-stats-stalker-VERSION.jar`

Now you can continue with [how to use 2)](#how-to-use) the bot.

## Getting help

If you experience any problems with the bot or if there's something unclear about how to use it, feel free to reach me via Discord: `jjjannik`.<br>
Alternatively, you can also open a GitHub issue and I will help you. Any bugs or typo's can be reported in the same way... but I would be happy if you didn't open an issue every time you find a typo... THANK YOU!

## Contributing

Similar to my JGA project, I am happy about every kind of feedback you can give me! <br>
Feel free to open a pull request to submit possible changes or improvements or message me about it via Discord...<br>
I put a lot of work into this project, and I'm always open for new stuff!

To contribute to the project, please fork this repository, commit your changes there and then create a pull request to be reviewed.