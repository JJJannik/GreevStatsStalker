package de.jjjannik.interactions.commands.mlgrush;

import de.jjjannik.classes.PlayerCommand;
import de.jjjannik.entities.MLGRushPlayer;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.awt.*;

public class MLGRushPlayerCommand extends PlayerCommand {
    @Override
    public void execute(SlashCommandInteractionEvent evt) {
        handlePlayerCommand(evt, player -> {
            MLGRushPlayer stats = jga.getMLGRushPlayer(player.getUuid());

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