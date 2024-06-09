package de.jjjannik.interactions.commands.minigames.qsg;

import de.jjjannik.classes.TopCommand;
import de.jjjannik.entities.basic.PvPPlayer;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class QsgTopCommand extends TopCommand {
    @Override
    public void execute(SlashCommandInteractionEvent evt) {
        handleTopCommand(evt, top -> {
            List<PvPPlayer> topStats = jga.getTopQsg(top.amount(), top.offset());

            List<MessageEmbed> embeds = new ArrayList<>();

            EmbedBuilder builder = new EmbedBuilder().setColor(Color.GREEN).setTitle("Top " + top.amount() + " QSG player starting at offset " + top.offset());
            EmbedBuilder builder1 = new EmbedBuilder().setColor(Color.GREEN);

            for (int i = 0; i < 50; i++) {
                if (topStats.size() == i) break;

                PvPPlayer stats = topStats.get(i);

                MessageEmbed.Field field = new MessageEmbed.Field(stats.getName(), """
                        Kills: %s
                        Deaths: %s
                        Wins: %s
                        Loses: %s
                        """.formatted(stats.getKills(), stats.getDeaths(), stats.getWins(), stats.getLoses()), true);

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