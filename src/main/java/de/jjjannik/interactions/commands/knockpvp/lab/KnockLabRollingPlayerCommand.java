package de.jjjannik.interactions.commands.knockpvp.lab;

import de.jjjannik.entities.basic.KillsDeathsPlayer;
import de.jjjannik.interactions.PlayerCommand;
import de.jjjannik.utils.exceptions.APICallException;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

import java.awt.*;

public class KnockLabRollingPlayerCommand extends PlayerCommand {
    @Override
    public void execute(SlashCommandInteractionEvent evt) {
        handlePlayerCommand(evt, player -> {
            long start = evt.getOption("start-timestamp", OptionMapping::getAsInt);
            long end = evt.getOption("end-timestamp", OptionMapping::getAsInt);

            String experiment = evt.getOption("experiment", OptionMapping::getAsString);

            try {
                jga.getKnockPvPLabPlayer(player.getUuid(), experiment); // to check if experiment is valid
            } catch (APICallException e) {
                evt.replyEmbeds(new EmbedBuilder()
                                .setColor(Color.RED)
                                .addField("❌ **No experiment found**", "This lab experiment does not exist", false).build())
                        .setEphemeral(true)
                        .queue();
                return;
            }
            KillsDeathsPlayer stats = jga.getRollingKnockPvPLabPlayer(player.getUuid(), experiment, start, end);

            evt.replyEmbeds(new EmbedBuilder()
                            .setColor(Color.GREEN)
                            .setTitle("KnockPVP stats of " + player.getName() + " from " + start + " to " + end)
                            .addField("Kills", String.valueOf(stats.getKills()), true)
                            .addField("Deaths", String.valueOf(stats.getDeaths()), true)
                            .addField("K/D", TWO_DECIMALS.format(stats.getKills() * 1D / stats.getDeaths()), true)
                            .build())
                    .queue();
        });
    }
}