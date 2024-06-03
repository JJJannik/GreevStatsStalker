package de.jjjannik.interactions.commands.minigames.tnt_run;

import de.jjjannik.entities.basic.WinsLosesPlayer;
import de.jjjannik.interactions.PlayerCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.awt.*;

public class TNTRunPlayerCommand extends PlayerCommand {
    @Override
    public void execute(SlashCommandInteractionEvent evt) {
        handlePlayerCommand(evt, player -> {
            WinsLosesPlayer stats = jga.getTnTRunPlayer(player.getUuid());

            evt.replyEmbeds(new EmbedBuilder()
                            .setColor(Color.GREEN)
                            .setTitle("TNTRun stats of " + player.getName())
                            .addField("Wins", String.valueOf(stats.getWins()), true)
                            .addField("Loses", String.valueOf(stats.getLoses()), true)
                            .addField("W/L", TWO_DECIMALS.format(stats.getWins() * 1D / stats.getLoses()), true)
                            .build())
                    .queue();
        });
    }
}