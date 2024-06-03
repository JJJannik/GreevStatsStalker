package de.jjjannik.interactions.commands.knockpvp;

import de.jjjannik.entities.basic.KillsDeathsPlayer;
import de.jjjannik.interactions.PlayerCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.awt.*;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

public class KnockPVPWeeklyCommand extends PlayerCommand {
    @Override
    public void execute(SlashCommandInteractionEvent evt) {
        handlePlayerCommand(evt, player -> {
            Instant now = Instant.now();
            Instant lastDay = now.minusSeconds(TimeUnit.DAYS.toSeconds(7));

            KillsDeathsPlayer stats = jga.getRollingKnockPvPPlayer(player.getUuid(), lastDay.getEpochSecond(), now.getEpochSecond());

            evt.replyEmbeds(new EmbedBuilder()
                            .setColor(Color.GREEN)
                            .setTitle("Weekly KnockPVP stats of " + player.getName())
                            .addField("Kills", String.valueOf(stats.getKills()), true)
                            .addField("Deaths", String.valueOf(stats.getDeaths()), true)
                            .addField("K/D", TWO_DECIMALS.format(stats.getKills() * 1D / stats.getDeaths()), true)
                            .build())
                    .queue();
        });
    }
}