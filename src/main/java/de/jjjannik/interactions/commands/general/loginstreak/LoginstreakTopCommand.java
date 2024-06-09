package de.jjjannik.interactions.commands.general.loginstreak;

import de.jjjannik.classes.TopCommand;
import de.jjjannik.entities.LoginstreakPlayer;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class LoginstreakTopCommand extends TopCommand {
    @Override
    public void execute(SlashCommandInteractionEvent evt) {
        handleTopCommand(evt, top -> {
            List<LoginstreakPlayer> topStats = jga.getTopLoginstreak(top.amount(), top.offset());

            List<MessageEmbed> embeds = new ArrayList<>();

            EmbedBuilder builder = new EmbedBuilder().setColor(Color.GREEN).setTitle("Top " + top.amount() + " Login streaks starting at offset " + top.offset());
            EmbedBuilder builder1 = new EmbedBuilder().setColor(Color.GREEN);

            for (int i = 0; i < 50; i++) {
                if (topStats.size() == i) break;

                LoginstreakPlayer stats = topStats.get(i);
                MessageEmbed.Field field = new MessageEmbed.Field(stats.getName(), """
                        Current Loginstreak: %s
                        Maximal Loginstreak: %s
                        """.formatted(stats.getCurrentStreak(), stats.getMaxStreak()), true);

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