package de.jjjannik.interactions.commands.mlgrush;

import de.jjjannik.classes.RollingTopCommand;
import de.jjjannik.entities.MLGRushPlayer;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.utils.TimeFormat;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MLGRushRollingTopCommand extends RollingTopCommand {
    @Override
    public void execute(SlashCommandInteractionEvent evt) {
        handleRollingTopCommand(evt, (top, rolling) -> {
            List<MLGRushPlayer> topStats = jga.getRollingTopMLGRush(top.amount(), top.offset(), rolling.startTime(), rolling.endTime());
            List<MessageEmbed> embeds = new ArrayList<>();

            EmbedBuilder builder = new EmbedBuilder().setColor(Color.GREEN)
                    .setTitle("Top %s MLGRush player starting with #%s from %s to %s".formatted(
                            top.amount(),
                            top.offset()+1,
                            TimeFormat.DATE_TIME_SHORT.format(rolling.startTime()),
                            TimeFormat.DATE_TIME_SHORT.format(rolling.endTime())
                    ));
            EmbedBuilder builder1 = new EmbedBuilder().setColor(Color.GREEN);

            for (int i = 0; i < 50; i++) {
                if (topStats.size() == i) break;

                MLGRushPlayer stats = topStats.get(i);

                MessageEmbed.Field field = new MessageEmbed.Field("#%s %s".formatted(top.offset()+i+1, stats.getName()), """
                        Wins: %s
                        Loses: %s
                        Broken Beds: %s
                        Lost Beds: %s
                        """.formatted(stats.getWins(), stats.getLostBeds(), stats.getBrokenBeds(), stats.getLostBeds()), true);

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