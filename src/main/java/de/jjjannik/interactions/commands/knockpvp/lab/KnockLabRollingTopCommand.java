package de.jjjannik.interactions.commands.knockpvp.lab;

import de.jjjannik.classes.RollingTopCommand;
import de.jjjannik.entities.basic.KillsDeathsPlayer;
import de.jjjannik.requests.KnockPVPLab;
import de.jjjannik.utils.exceptions.APITimeoutException;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.utils.TimeFormat;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class KnockLabRollingTopCommand extends RollingTopCommand {
    @Override
    public void execute(SlashCommandInteractionEvent evt) {
        handleRollingTopCommand(evt, (top, rolling) -> {
            evt.deferReply().queue();
            String experiment = evt.getOption("experiment", OptionMapping::getAsString);

            if (jga.getTopKnockPvPLab(experiment, 10, 0).isEmpty()) { // to check if experiment is valid
                evt.getHook().sendMessageEmbeds(new EmbedBuilder()
                                .setColor(Color.RED)
                                .addField("❌ **No experiment found**", "This lab experiment does not exist", false).build())
                        .setEphemeral(true)
                        .queue();
                return;
            }

            List<KillsDeathsPlayer> topStats;
            try {
                topStats = jga.getRollingTopKnockPvPLab(experiment, top.amount(), top.offset(), rolling.startTime(), rolling.endTime());
            } catch (APITimeoutException e) {
                evt.getHook().sendMessageEmbeds(new EmbedBuilder()
                                .setColor(Color.RED)
                                .addField("❌ **API Call exception**", "The response of the Greev API took too long, try again or click [here](%s)"
                                        .formatted(KnockPVPLab.GET_ROLLING_TOP.getUrl().formatted(top.amount(), top.offset(), rolling.startTime(), rolling.endTime())), false).build())
                        .setEphemeral(true)
                        .queue();
                return;
            }

            if (topStats.isEmpty()) {
                evt.getHook().sendMessageEmbeds(new EmbedBuilder()
                                .setColor(Color.RED)
                                .addField("❌ **No stats found**", "No stats can be found for your specified arguments", false).build())
                        .setEphemeral(true)
                        .queue();
                return;
            }

            List<MessageEmbed> embeds = new ArrayList<>();

            EmbedBuilder builder = new EmbedBuilder().setColor(Color.GREEN)
                    .setTitle("Top %s KnockPVP lab '%s' player starting with #%s from %s to %s".formatted(
                            top.amount(),
                            experiment,
                            top.offset()+1,
                            TimeFormat.DATE_TIME_SHORT.format(rolling.startTime() * 1000),
                            TimeFormat.DATE_TIME_SHORT.format(rolling.endTime() * 1000)
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

            evt.getHook().sendMessageEmbeds(embeds).queue();
        });
    }
}