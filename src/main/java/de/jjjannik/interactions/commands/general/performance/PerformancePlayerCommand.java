package de.jjjannik.interactions.commands.general.performance;

import de.jjjannik.classes.PlayerCommand;
import de.jjjannik.entities.PerformancePlayer;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.awt.*;

public class PerformancePlayerCommand extends PlayerCommand {
    @Override
    public void execute(SlashCommandInteractionEvent evt) {
        handlePlayerCommand(evt, player -> {
            PerformancePlayer performance = jga.getPerformancePlayer(player.getUuid());

            evt.replyEmbeds(new EmbedBuilder()
                            .setColor(Color.GREEN)
                            .setTitle("Performance of " + player.getName())
                            .addField("Player performance", String.valueOf(performance.getPerformance()), false)
                            .build())
                    .queue();
        });
    }
}