package de.jjjannik.interactions.commands.general.clans;

import de.jjjannik.Main;
import de.jjjannik.entities.ClanMember;
import de.jjjannik.interactions.Interaction;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.utils.MarkdownSanitizer;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ClanMembersCommand implements Interaction {
    @Override
    public void execute(SlashCommandInteractionEvent evt) {
        String clan = evt.getOption("name", OptionMapping::getAsString);
        List<ClanMember> members = Main.getJga().getClanMembers(clan);

        List<MessageEmbed> embeds = new ArrayList<>();
        EmbedBuilder builder = new EmbedBuilder().setColor(Color.GREEN).setTitle("Members of " + clan);

        if (members.isEmpty()) {
            evt.replyEmbeds(new EmbedBuilder()
                    .setColor(Color.RED)
                    .addField("❌ **Clan not found**", "A clan with that name does not exist", false)
                    .build()
            ).setEphemeral(true).queue();
        }

        for (int i = 1; i <= members.size(); i++) {
            if (builder.getFields().size() == 25) {
                if (embeds.size() == 9) break;

                embeds.add(builder.build());
                builder.clearFields().setTitle(null);
            }
            ClanMember member = members.get(i - 1);
            builder.addField(MarkdownSanitizer.escape(member.getName()), member.isLeader() ? "Leader" : "Member",true);
        }

        embeds.add(builder.build());
        evt.replyEmbeds(embeds).queue();
    }
}