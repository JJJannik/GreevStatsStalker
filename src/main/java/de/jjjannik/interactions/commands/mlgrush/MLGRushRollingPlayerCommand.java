package de.jjjannik.interactions.commands.mlgrush;

import de.jjjannik.classes.RollingPlayerCommand;
import de.jjjannik.entities.MLGRushPlayer;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.utils.TimeFormat;

import java.awt.*;

import static de.jjjannik.classes.PlayerCommand.TWO_DECIMALS;

public class MLGRushRollingPlayerCommand extends RollingPlayerCommand {
    @Override
    public void execute(SlashCommandInteractionEvent evt) {
        handleRollingPlayerCommand(evt, (player, rolling) -> {
            MLGRushPlayer stats = jga.getRollingMLGRushPlayer(player.getUuid(), rolling.startTime(), rolling.endTime());

            evt.replyEmbeds(new EmbedBuilder()
                            .setColor(Color.GREEN)
                            .setTitle("MLGRush stats of %s from %s to %s".formatted(
                                    player.getName(),
                                    TimeFormat.DATE_TIME_SHORT.format(rolling.startTime() * 1000),
                                    TimeFormat.DATE_TIME_SHORT.format(rolling.endTime() * 1000)
                            ))
                            .addField("Wins", String.valueOf(stats.getWins()), true)
                            .addField("Loses", String.valueOf(stats.getLoses()), true)
                            .addField("W/L", TWO_DECIMALS.format(stats.getWins() * 1D / stats.getLoses()), true)
                            .addField("Bed breaks", String.valueOf(stats.getBrokenBeds()), true)
                            .addField("Bed loses", String.valueOf(stats.getLostBeds()), true)
                            .addField("BB/BL", TWO_DECIMALS.format(stats.getBrokenBeds() * 1D / stats.getLostBeds()), true)
                            .build())
                    .queue();
        });
    }
}