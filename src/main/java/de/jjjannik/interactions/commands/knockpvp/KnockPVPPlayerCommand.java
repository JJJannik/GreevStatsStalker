package de.jjjannik.interactions.commands.knockpvp;

import de.jjjannik.classes.PlayerCommand;
import de.jjjannik.entities.KnockPVPPlayer;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.awt.*;

public class KnockPVPPlayerCommand extends PlayerCommand {
    @Override
    public void execute(SlashCommandInteractionEvent evt) {
        handlePlayerCommand(evt, player -> {
            KnockPVPPlayer stats = jga.getKnockPvPPlayer(player.getUuid());

            evt.replyEmbeds(new EmbedBuilder()
                            .setColor(Color.GREEN)
                            .setTitle("KnockPVP stats of " + player.getName())
                            .addField("Rank", String.valueOf(stats.getRank()), true)
                            .addField("Kills", String.valueOf(stats.getKills()), true)
                            .addField("Deaths", String.valueOf(stats.getDeaths()), true)
                            .addField("K/D", TWO_DECIMALS.format(stats.getKills() * 1D / stats.getDeaths()), true)
                            .build())
                    .queue();
        });
    }
}