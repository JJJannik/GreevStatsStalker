package de.jjjannik.interactions.commands.knockpvp;

import de.jjjannik.classes.PlayerCommand;
import de.jjjannik.entities.basic.KillsDeathsPlayer;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

import java.awt.*;
import java.time.DateTimeException;
import java.time.Instant;

public class KnockPVPDaysCommand extends PlayerCommand {
    @Override
    public void execute(SlashCommandInteractionEvent evt) {
        handlePlayerCommand(evt, player -> {
            double days = evt.getOption("days", OptionMapping::getAsDouble);

            if (days <= 0) {
                evt.replyEmbeds(new EmbedBuilder()
                                .setColor(Color.RED)
                                .addField("❌ **Incorrect option**", "Option 'days' can't be smaller or equal to 0", false).build())
                        .setEphemeral(true)
                        .queue();
                return;
            }

            Instant now = Instant.now();
            Instant lastDay;
            try {
                lastDay = now.minusSeconds((int) (days * 24 * 60 * 60)); // days * 24 * 60 * 60
            } catch (DateTimeException e) {
                evt.replyEmbeds(new EmbedBuilder()
                                .setColor(Color.RED)
                                .addField("❌ **Incorrect option**", "Option 'days' can't be longer back than start of the [unix time](https://www.unixtimestamp.com/)", false).build())
                        .setEphemeral(true)
                        .queue();
                return;
            }

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