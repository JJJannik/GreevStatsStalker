package de.jjjannik.interactions.commands.mlgrush;

import de.jjjannik.classes.TopCommand;
import de.jjjannik.entities.MLGRushPlayer;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.utils.TimeFormat;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MLGRushRollingTopCommand extends TopCommand {
    @Override
    public void execute(SlashCommandInteractionEvent evt) {
        handleTopCommand(evt, top -> {
            long start = evt.getOption("start-timestamp", OptionMapping::getAsInt);
            long end = evt.getOption("end-timestamp", OptionMapping::getAsInt);

            List<MLGRushPlayer> topStats = jga.getRollingTopMLGRush(top.amount(), top.offset(), start, end);

            List<MessageEmbed> embeds = new ArrayList<>();

            EmbedBuilder builder = new EmbedBuilder().setColor(Color.GREEN)
                    .setTitle("Top %s MLGRush player starting at #%s from %s to %s".formatted(
                            top.amount(),
                            top.offset(),
                            TimeFormat.DATE_TIME_SHORT.format(start),
                            TimeFormat.DATE_TIME_SHORT.format(end)
                    ));
            EmbedBuilder builder1 = new EmbedBuilder().setColor(Color.GREEN);

            for (int i = 0; i < 50; i++) {
                if (topStats.size() == i) break;

                MLGRushPlayer stats = topStats.get(i);

                MessageEmbed.Field field = new MessageEmbed.Field(stats.getName(), """
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