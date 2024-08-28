package de.jjjannik.interactions.commands.minigames.cores;

import de.jjjannik.classes.TopCommand;
import de.jjjannik.entities.CoresPlayer;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CoresTopCommand extends TopCommand {
    @Override
    public void execute(SlashCommandInteractionEvent evt) {
        handleTopCommand(evt, top -> {
            List<CoresPlayer> topStats = jga.getTopCores(top.amount(), top.offset());

            List<MessageEmbed> embeds = new ArrayList<>();

            EmbedBuilder builder = new EmbedBuilder().setColor(Color.GREEN).setTitle("Top " + top.amount() + " Cores player starting with" + top.offset()+1);
            EmbedBuilder builder1 = new EmbedBuilder().setColor(Color.GREEN);

            for (int i = 0; i < 50; i++) {
                if (topStats.size() == i) break;

                CoresPlayer stats = topStats.get(i);

                MessageEmbed.Field field = new MessageEmbed.Field("#%s %s".formatted(top.offset()+i+1, stats.getName()), """
                        Kills: %s
                        Deaths: %s
                        Wins: %s
                        Loses: %s
                        Broken Cores: %s
                        """.formatted(stats.getKills(), stats.getDeaths(), stats.getWins(), stats.getLoses(), stats.getBrokenCores()), true);

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