package de.jjjannik.interactions.commands.knockpvp;

import de.jjjannik.classes.RollingTopCommand;
import de.jjjannik.entities.basic.KillsDeathsPlayer;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.utils.TimeFormat;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class KnockPVPRollingTopCommand extends RollingTopCommand {
    @Override
    public void execute(SlashCommandInteractionEvent evt) {
        handleRollingTopCommand(evt, (top, rolling) -> {
            List<KillsDeathsPlayer> topStats = jga.getRollingTopKnockPvP(top.amount(), top.offset(), rolling.startTime(), rolling.endTime());

            List<MessageEmbed> embeds = new ArrayList<>();

            EmbedBuilder builder = new EmbedBuilder().setColor(Color.GREEN)
                    .setTitle("Top %s KnockPVP player starting with #%s from %s to %s".formatted(
                            top.amount(),
                            top.offset()+1,
                            TimeFormat.DATE_TIME_SHORT.format(rolling.startTime()),
                            TimeFormat.DATE_TIME_SHORT.format(rolling.endTime())
                    ));
            EmbedBuilder builder1 = new EmbedBuilder().setColor(Color.GREEN);

            for (int i = 0; i < 50; i++) {
                if (topStats.size() == i) break;

                KillsDeathsPlayer stats = topStats.get(i);

                MessageEmbed.Field field = new MessageEmbed.Field("#%s %s".formatted(top.offset()+i+1, stats.getName()), """
                        Kills: %s
                        Deaths: %s
                        """.formatted(stats.getKills(), stats.getDeaths()), true);

                if (i < 25) {
                    builder.addField(field);
                } else {
                    builder1.addField(field);
                }
            }
            embeds.add(builder.build());
            if (!builder1.getFields().isEmpty()) {
                embeds.add(builder1.build());
            }

            evt.replyEmbeds(embeds).queue();
        });

    }
}