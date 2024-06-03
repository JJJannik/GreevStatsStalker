package de.jjjannik.interactions.commands.knockpvp.lab;

import de.jjjannik.interactions.Interaction;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.awt.*;

public class ListExperimentsCommand implements Interaction {
    @Override
    public void execute(SlashCommandInteractionEvent evt) { //TODO: this list has to be updated manually, since there is no API endpoint to receive this list
        evt.replyEmbeds(new EmbedBuilder().setColor(Color.GREEN).setTitle("All KnockPvP-Lab experiments")
                .addField("Double Jump", "API: DOUBLEJUMP", true)
                .addField("TNT", "API: TNTJUMP", true)
                .addField("Fireballs", "API: FIREBALL", true)
                .addField("Grappling Hook", "API: GRAPPLING_HOOK", true)
                .addField("Switcher", "API: SWITCHBALLS", true)
                .addField("Sumo", "API: SUMO", true)
                .addField("Knock Stick", "API: KNOCKSTICK", true)
                .addField("KnockFFA", "API: KNOCKFFA", true)
                .addField("Bows", "API: BOW", true)
                .build())
                .queue();
    }
}