package de.jjjannik.interactions.commands.mlgrush;

import de.jjjannik.classes.RollingTopCommand;
import de.jjjannik.entities.MLGRushPlayer;
import de.jjjannik.requests.MLGRush;
import de.jjjannik.utils.exceptions.APITimeoutException;
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
            evt.deferReply().queue();
            List<MLGRushPlayer> topStats;
            try {
                topStats = jga.getRollingTopMLGRush(top.amount(), top.offset(), rolling.startTime(), rolling.endTime());
            } catch (APITimeoutException e) {
                evt.getHook().sendMessageEmbeds(new EmbedBuilder()
                                .setColor(Color.RED)
                                .addField("❌ **API Call exception**", "The response of the Greev API took too long, try again or click [here](%s)"
                                        .formatted(MLGRush.GET_ROLLING_TOP.getUrl().formatted(top.amount(), top.offset(), rolling.startTime(), rolling.endTime())), false).build())
                        .setEphemeral(true)
                        .queue();
                return;
            }

            List<MessageEmbed> embeds = new ArrayList<>();

            EmbedBuilder builder = new EmbedBuilder().setColor(Color.GREEN)
                    .setTitle("Top %s MLGRush player starting with #%s from %s to %s".formatted(
                            top.amount(),
                            top.offset()+1,
                            TimeFormat.DATE_TIME_SHORT.format(rolling.startTime() * 1000),
                            TimeFormat.DATE_TIME_SHORT.format(rolling.endTime() * 1000)
                    ));
            EmbedBuilder builder1 = new EmbedBuilder().setColor(Color.GREEN);

            if (topStats.isEmpty()) {
                evt.getHook().sendMessageEmbeds(new EmbedBuilder()
                                .setColor(Color.RED)
                                .addField("❌ **No stats found**", "No stats can be found for your specified arguments", false).build())
                        .setEphemeral(true)
                        .queue();
                return;
            }

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

            evt.getHook().sendMessageEmbeds(embeds).queue();
        });
    }
}