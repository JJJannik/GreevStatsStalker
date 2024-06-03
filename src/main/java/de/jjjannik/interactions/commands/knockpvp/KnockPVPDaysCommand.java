package de.jjjannik.interactions.commands.knockpvp;

import de.jjjannik.entities.basic.KillsDeathsPlayer;
import de.jjjannik.interactions.PlayerCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

import java.awt.*;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

public class KnockPVPDaysCommand extends PlayerCommand {
    @Override
    public void execute(SlashCommandInteractionEvent evt) {
        handlePlayerCommand(evt, player -> {
            int days = evt.getOption("days", OptionMapping::getAsInt);

            if (days < 1) {
                evt.replyEmbeds(new EmbedBuilder()
                                .setColor(Color.RED)
                                .addField("❌ **Incorrect option**", "Option 'days' can't be smaller 1", false).build())
                        .setEphemeral(true)
                        .queue();
                return;
            }

            Instant now = Instant.now();
            Instant lastDay = now.minusSeconds(TimeUnit.DAYS.toSeconds(days));

            KillsDeathsPlayer stats = jga.getRollingKnockPvPPlayer(player.getUuid(), lastDay.getEpochSecond(), now.getEpochSecond());

            evt.replyEmbeds(new EmbedBuilder()
                            .setColor(Color.GREEN)
                            .setTitle("KnockPVP stats of " + player.getName() + " of the last " + days + " days")
                            .addField("Kills", String.valueOf(stats.getKills()), true)
                            .addField("Deaths", String.valueOf(stats.getDeaths()), true)
                            .addField("K/D", TWO_DECIMALS.format(stats.getKills() * 1D / stats.getDeaths()), true)
                            .build())
                    .queue();
        });
    }
}