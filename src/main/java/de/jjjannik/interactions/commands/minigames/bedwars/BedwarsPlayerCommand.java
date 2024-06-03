package de.jjjannik.interactions.commands.minigames.bedwars;

import de.jjjannik.entities.BedwarsPlayer;
import de.jjjannik.interactions.PlayerCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.awt.*;

public class BedwarsPlayerCommand extends PlayerCommand {
    @Override
    public void execute(SlashCommandInteractionEvent evt) {
        handlePlayerCommand(evt, player -> {
            BedwarsPlayer stats = jga.getBedwarsPlayer(player.getUuid());

            evt.replyEmbeds(new EmbedBuilder()
                            .setColor(Color.GREEN)
                            .setTitle("Bedwars stats of " + player.getName())
                            .addField("Kills", String.valueOf(stats.getKills()), true)
                            .addField("Deaths", String.valueOf(stats.getDeaths()), true)
                            .addField("K/D", TWO_DECIMALS.format(stats.getKills() * 1D / stats.getDeaths()), true)
                            .addField("Wins", String.valueOf(stats.getWins()), true)
                            .addField("Loses", String.valueOf(stats.getLoses()), true)
                            .addField("W/L", TWO_DECIMALS.format(stats.getWins() * 1D / stats.getLoses()), true)
                            .addField("Beds broken", String.valueOf(stats.getBrokenBeds()), false)
                            .build())
                    .queue();
        });
    }
}