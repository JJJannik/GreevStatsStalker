package de.jjjannik.interactions.commands.minesweeper;

import de.jjjannik.Main;
import de.jjjannik.api.JGA;
import de.jjjannik.entities.minesweeper.MinesweeperGameEntry;
import de.jjjannik.interactions.Interaction;
import de.jjjannik.interactions.PlayerCommand;
import de.jjjannik.utils.exceptions.APICallException;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

import java.awt.*;

public class MinesweeperGameCommand implements Interaction {
    private final JGA jga = Main.getJga();

    @Override
    public void execute(SlashCommandInteractionEvent evt) {
        int gameId = evt.getOption("id", OptionMapping::getAsInt);

        try {
            MinesweeperGameEntry game = jga.getMinesweeperGameInfo(gameId);

            evt.replyEmbeds(new EmbedBuilder()
                            .setColor(Color.GREEN)
                            .setTitle("Minesweeper game #" + gameId + " solved by ")
                            .addField("Type", game.getType().toString(), true)
                            .addField("Generator", game.getGenerator().toString(), true)
                            .addField("Time", PlayerCommand.MILLIS_TO_SECONDS.format(game.getTime() / 1000D), true)
                            .addField("Timestamp", game.getTimestamp(), true)
                            .addField("Save field", game.getSaveField(), true)
                            .addField("Mines", String.valueOf(game.getMines()), true)
                            .addField("Correct flags", String.valueOf(game.getCorrectFlags()), true)
                            .addField("Incorrect flags", String.valueOf(game.getIncorrectFlags()), true)
                            .addField("Won", String.valueOf(game.isWon()), true)
                            .addField("X size", String.valueOf(game.getXSize()), true)
                            .addField("Z size", String.valueOf(game.getZSize()), true)
                            .addField("", "", true)     // would look awful without this
                            .addField("Game data", game.getGameData(), false)
                            .addField("Game string", game.getGameString(), false)
                            .build())
                    .queue();
        } catch (APICallException e) {
            evt.replyEmbeds(new EmbedBuilder()
                    .setColor(Color.RED)
                    .addField("❌ **No data**", "This player never completed a Minesweeper game in that mode", false)
                    .build()
            ).setEphemeral(true).queue();
        }
    }
}