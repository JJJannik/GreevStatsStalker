package de.jjjannik.interactions.commands.knockpvp;

import de.jjjannik.classes.RollingPlayerCommand;
import de.jjjannik.entities.basic.KillsDeathsPlayer;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.utils.TimeFormat;

import java.awt.*;

import static de.jjjannik.classes.PlayerCommand.TWO_DECIMALS;

public class KnockPVPRollingPlayerCommand extends RollingPlayerCommand {
    @Override
    public void execute(SlashCommandInteractionEvent evt) {
        handleRollingPlayerCommand(evt, (player, rolling) -> {
            KillsDeathsPlayer stats = jga.getRollingKnockPvPPlayer(player.getUuid(), rolling.startTime(), rolling.endTime());

            evt.replyEmbeds(new EmbedBuilder()
                            .setColor(Color.GREEN)
                            .setTitle("KnockPvP stats of %s from %s to %s".formatted(
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