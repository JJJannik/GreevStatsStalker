package de.jjjannik.interactions.commands.general;

import de.jjjannik.entities.BadgeEntity;
import de.jjjannik.interactions.PlayerCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BadgesCommand extends PlayerCommand {
    @Override
    public void execute(SlashCommandInteractionEvent evt) {
        handlePlayerCommand(evt, player -> {
            List<BadgeEntity> badges = jga.getPlayerBadges(player.getUuid());
            List<MessageEmbed> embeds = new ArrayList<>();

            EmbedBuilder builder = new EmbedBuilder().setColor(Color.GREEN).setTitle("Badges of " + player.getName());

            if (badges.isEmpty()) {
                evt.replyEmbeds(new EmbedBuilder()
                        .setColor(Color.RED)
                        .addField("❌ **No badges**", "This player never joined the server since Badges exist", false)
                        .build()
                ).setEphemeral(true).queue();
            }

            for (int i = 1; i <= badges.size(); i++) {
                if (builder.getFields().size() == 25) {
                    embeds.add(builder.build());
                    builder.clearFields().setTitle(null);
                }
                BadgeEntity badge = badges.get(i - 1);
                builder.addField("#" + badge.getId() + " " + badge.getName(),
                                """
                                %s
                                Item: %s
                                Received: %s
                                """.formatted(badge.getDescription(), badge.getItem(), badge.getTimestamp().replace("Z", "").replace("T", " ")),
                        false
                );
            }
            embeds.add(builder.build());
            evt.replyEmbeds(embeds).queue();
        });
    }
}