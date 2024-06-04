package de.jjjannik.interactions.commands.mlgrush;

import de.jjjannik.classes.PlayerCommand;
import de.jjjannik.entities.MLGRushPlayer;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

import java.awt.*;

public class MLGRushRollingPlayerCommand extends PlayerCommand {
    @Override
    public void execute(SlashCommandInteractionEvent evt) {
        handlePlayerCommand(evt, player -> {
            long start = evt.getOption("start-timestamp", OptionMapping::getAsInt);
            long end = evt.getOption("end-timestamp", OptionMapping::getAsInt);

            MLGRushPlayer stats = jga.getRollingMLGRushPlayer(player.getUuid(), start, end);

            evt.replyEmbeds(new EmbedBuilder()
                            .setColor(Color.GREEN)
                            .setTitle("MLGRush stats of " + player.getName())
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