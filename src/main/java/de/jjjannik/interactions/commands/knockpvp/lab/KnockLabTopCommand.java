package de.jjjannik.interactions.commands.knockpvp.lab;

import de.jjjannik.classes.TopCommand;
import de.jjjannik.entities.basic.KillsDeathsPlayer;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class KnockLabTopCommand extends TopCommand {
    @Override
    public void execute(SlashCommandInteractionEvent evt) {
        handleTopCommand(evt, top -> {
            String experiment = evt.getOption("experiment", OptionMapping::getAsString);
            List<KillsDeathsPlayer> topStats = jga.getTopKnockPvPLab(experiment, top.amount(), top.offset());;

            if (topStats.isEmpty()) { // to check if experiment is valid
                evt.replyEmbeds(new EmbedBuilder()
                                .setColor(Color.RED)
                                .addField("❌ **No experiment found**", "This lab experiment does not exist", false).build())
                        .setEphemeral(true)
                        .queue();
                return;
            }

            List<MessageEmbed> embeds = new ArrayList<>();

            EmbedBuilder builder = new EmbedBuilder().setColor(Color.GREEN).setTitle("Top %s KnockPvP-Lab '%s' starting with #%s".formatted(
                    top.amount(),
                    experiment,
                    top.offset()+1
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