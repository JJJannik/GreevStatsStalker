package de.jjjannik.interactions.commands.general;

import de.jjjannik.entities.NameHistoryEntry;
import de.jjjannik.interactions.PlayerCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class NamehistoryCommand extends PlayerCommand {
    @Override
    public void execute(SlashCommandInteractionEvent evt) {
        handlePlayerCommand(evt, player -> { // This method will fail if the player has more than 10 Embeds * 25 Fields = 250 Name changes (max possible for an event reply)  -> 250 Name changes -> 1 name change / month = 20.8 years of playing Minecraft and changing every month the name is unlikely ig // most name changes has player: 55e64ca3-95c1-4d80-8110-9ea32cea9809
            List<NameHistoryEntry> nameHistory = jga.getNameHistory(player.getUuid());
            List<MessageEmbed> embeds = new ArrayList<>();

            EmbedBuilder builder = new EmbedBuilder().setColor(Color.GREEN).setTitle("Namehistory of " + player.getName());

            if (nameHistory.isEmpty()) {
                evt.replyEmbeds(new EmbedBuilder()
                                .setColor(Color.RED)
                                .addField("❌ **Unknown player**", "Name changes of this player are unknown to the Greev API, try your luck here: https://de.namemc.com/profile/" + player.getUuid().toString(), false)
                                .build()
                ).setEphemeral(true).queue();
            }

            for (int i = 1; i <= nameHistory.size(); i++) {
                if (builder.getFields().size() == 25) {
                    embeds.add(builder.build());
                    builder.clearFields().setTitle(null);
                }
                NameHistoryEntry entry = nameHistory.get(i - 1);
                builder.addField(entry.getName(), formatTimestamp(entry.getChangedToAt(), true), false);
            }
            embeds.add(builder.build());
            evt.replyEmbeds(embeds).queue();
        });
    }
}