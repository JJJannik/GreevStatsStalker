package de.jjjannik.interactions.commands.knockpvp.lab;

import de.jjjannik.classes.TopCommand;
import de.jjjannik.entities.basic.KillsDeathsPlayer;
import de.jjjannik.utils.exceptions.APICallException;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.utils.TimeFormat;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class KnockLabRollingTopCommand extends TopCommand {
    @Override
    public void execute(SlashCommandInteractionEvent evt) {
        handleTopCommand(evt, top -> {
            long start = evt.getOption("start-timestamp", OptionMapping::getAsInt);
            long end = evt.getOption("end-timestamp", OptionMapping::getAsInt);

            String experiment = evt.getOption("experiment", OptionMapping::getAsString);
            List<KillsDeathsPlayer> topStats;

            try {
                topStats = jga.getRollingTopKnockPvPLab(experiment, top.amount(), top.offset(), start, end);
            } catch (APICallException e) {
                evt.replyEmbeds(new EmbedBuilder()
                                .setColor(Color.RED)
                                .addField("❌ **No experiment found**", "This lab experiment does not exist", false).build())
                        .setEphemeral(true)
                        .queue();
                return;
            }

            List<MessageEmbed> embeds = new ArrayList<>();

            EmbedBuilder builder = new EmbedBuilder().setColor(Color.GREEN)
                    .setTitle("Top %s KnockPVP lab `%s` player starting at #%s from %s to %s".formatted(
                            top.amount(),
                            experiment,
                            top.offset(),
                            TimeFormat.DATE_TIME_SHORT.format(start),
                            TimeFormat.DATE_TIME_SHORT.format(end)
                    ));
            EmbedBuilder builder1 = new EmbedBuilder().setColor(Color.GREEN);

            for (int i = 0; i < 50; i++) {
                if (topStats.size() == i) break;

                KillsDeathsPlayer stats = topStats.get(i);

                MessageEmbed.Field field = new MessageEmbed.Field(stats.getName(), """
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