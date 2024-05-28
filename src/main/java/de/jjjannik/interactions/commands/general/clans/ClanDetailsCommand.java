package de.jjjannik.interactions.commands.general.clans;

import de.jjjannik.Main;
import de.jjjannik.entities.ClanEntity;
import de.jjjannik.interactions.Interaction;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

import java.awt.*;

public class ClanDetailsCommand implements Interaction {
    @Override
    public void execute(SlashCommandInteractionEvent evt) {
        String name = evt.getOption("name", OptionMapping::getAsString);

        try {
            ClanEntity clan = Main.getJga().getClan(name);

            evt.replyEmbeds(new EmbedBuilder()
                    .setColor(Color.GREEN)
                    .setTitle("Clan " + clan.getName())
                    .addField("Tag", clan.getTag(), false)
                    .addField("Size", String.valueOf(clan.getSize()), false)
                    .addField("Id", String.valueOf(clan.getId()), false)
                    .addField("Performance", String.valueOf(clan.getPlayerPerformance()), false)
                    .build()
            ).queue();
        } catch (UnsupportedOperationException e) {
            evt.replyEmbeds(new EmbedBuilder()
                            .setColor(Color.RED)
                            .addField("❌ **Clan not found**", "A clan with that name does not exist", false).build())
                    .setEphemeral(true)
                    .queue();
        }
    }
}