package de.jjjannik.interactions.commands.knockpvp.lab;

import de.jjjannik.classes.RollingPlayerCommand;
import de.jjjannik.entities.basic.KillsDeathsPlayer;
import de.jjjannik.utils.exceptions.APICallException;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.utils.TimeFormat;

import java.awt.*;

import static de.jjjannik.classes.PlayerCommand.TWO_DECIMALS;

public class KnockLabRollingPlayerCommand extends RollingPlayerCommand {
    @Override
    public void execute(SlashCommandInteractionEvent evt) {
        handleRollingPlayerCommand(evt, (player, rolling) -> {
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
            KillsDeathsPlayer stats = jga.getRollingKnockPvPLabPlayer(player.getUuid(), experiment, rolling.startTime(), rolling.endTime());

            evt.replyEmbeds(new EmbedBuilder()
                            .setColor(Color.GREEN)
                            .setTitle("KnockPvP lab `%s` stats of %s from %s to %s".formatted(
                                    experiment,
                                    player.getName(),
                                    TimeFormat.DATE_TIME_SHORT.format(rolling.startTime()),
                                    TimeFormat.DATE_TIME_SHORT.format(rolling.endTime())
                            ))
                            .addField("Kills", String.valueOf(stats.getKills()), true)
                            .addField("Deaths", String.valueOf(stats.getDeaths()), true)
                            .addField("K/D", TWO_DECIMALS.format(stats.getKills() * 1D / stats.getDeaths()), true)
                            .build())
                    .queue();
        });
    }
}