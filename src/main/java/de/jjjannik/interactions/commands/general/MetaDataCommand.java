package de.jjjannik.interactions.commands.general;

import de.jjjannik.classes.PlayerCommand;
import de.jjjannik.entities.ClanPlayer;
import de.jjjannik.entities.playerstats.PlayerMetaEntity;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class MetaDataCommand extends PlayerCommand {

    @Override
    public void execute(SlashCommandInteractionEvent evt) {
        handlePlayerCommand(evt, player -> {
            PlayerMetaEntity data = jga.getPlayerMetaData(player.getUuid());
            ClanPlayer clan = data.getClanPlayer();
            List<MessageEmbed> embeds = new ArrayList<>();

            embeds.add(new EmbedBuilder()
                    .setColor(Color.GREEN)
                    .setTitle("Metadata of " + player.getName())
                    .addField("Last login", formatTimestamp(data.getLastOnline(), false), false)
                    .addField("First login", formatTimestamp(data.getFirstOnline(), false), false)
                    .build()
            );

            embeds.add(new EmbedBuilder()
                    .setColor(Color.GREEN)
                    .setTitle("Rank data")
                    .addField("Rank", data.getPlayerRank().getName(), false)
                    .addField("Until", (data.getPlayerRank().getUntil() == 0 ? "Lifetime" : formatTimestamp(data.getPlayerRank().getUntil(), false)), false)
                    .build()
            );

            if (clan != null) {
                embeds.add(new EmbedBuilder()
                        .setColor(Color.GREEN)
                        .setTitle("Clan")
                        .addField("Name", clan.getName(), false)
                        .addField("Tag", clan.getTag(), false)
                        .addField("Size", String.valueOf(clan.getSize()), false)
                        .addField("Role", clan.getRole(), false)
                        .build()
                );
            }

            evt.replyEmbeds(embeds).queue();
        });
    }
}