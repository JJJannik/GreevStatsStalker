package de.jjjannik.interactions.commands.minesweeper;

import de.jjjannik.classes.RollingPlayerCommand;
import de.jjjannik.entities.minesweeper.MinesweeperPlayer;
import de.jjjannik.requests.Minesweeper.Generator;
import de.jjjannik.requests.Minesweeper.Mode;
import de.jjjannik.requests.Minesweeper.Type;
import de.jjjannik.utils.exceptions.APICallException;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

import java.awt.*;

import static de.jjjannik.classes.PlayerCommand.MILLIS_TO_SECONDS;

public class MinesweeperPlayerCommand extends RollingPlayerCommand {
    @Override
    public void execute(SlashCommandInteractionEvent evt) {
        handleRollingPlayerCommand(evt, (player, rolling) -> {
            String modeOption = evt.getOption("mode", OptionMapping::getAsString);
            String typeOption = evt.getOption("type", OptionMapping::getAsString);
            String genOption = evt.getOption("generator", OptionMapping::getAsString);

            Mode mode = modeOption == null ? null : Mode.valueOf(modeOption);
            Type type = typeOption == null ? null : Type.valueOf(typeOption);
            Generator gen = genOption == null ? null : Generator.valueOf(genOption);

            MinesweeperPlayer msPlayer;
            try {
                msPlayer = jga.getMinesweeperPlayer(
                        player.getUuid(),
                        type,
                        gen,
                        mode,
                        rolling == null ? null : rolling.startTime(),
                        rolling == null ? null : rolling.endTime()
                );
            } catch (APICallException e) {
                evt.getHook().sendMessageEmbeds(new EmbedBuilder()
                                .setColor(Color.RED)
                                .addField("❌ **No stats found**", "No stats can be found for your specified arguments", false).build())
                        .setEphemeral(true)
                        .queue();
                return;
            }

            evt.replyEmbeds(new EmbedBuilder()
                            .setColor(Color.GREEN)
                            .setTitle("Minesweeper stats of " + player.getName())
                            .addField("Rank", String.valueOf(msPlayer.getRank()), true)
                            .addField("Wins", String.valueOf(msPlayer.getWins()), true)
                            .addField("Rounds", String.valueOf(msPlayer.getRounds()), true)
                            .addField("Best Time", MILLIS_TO_SECONDS.format(msPlayer.getBestTime()/1000d), true)
                            .addField("Average Time", MILLIS_TO_SECONDS.format(msPlayer.getAvgTime()/1000d), true)
                            .addField("Correct flags", String.valueOf(msPlayer.getCorrectPlacedFlags()), true)
                            .addField("Incorrect flags", String.valueOf(msPlayer.getIncorrectPlacedFlags()), true)
                            .addField("Mastery", String.valueOf(msPlayer.getMastery()), true)
                            .build())
                    .queue();
        });
    }
}