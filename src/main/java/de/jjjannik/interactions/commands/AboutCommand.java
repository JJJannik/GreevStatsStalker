package de.jjjannik.interactions.commands;

import de.jjjannik.interactions.Interaction;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.awt.*;

public class AboutCommand implements Interaction {
    @Override
    public void execute(SlashCommandInteractionEvent evt) {
        evt.replyEmbeds(new EmbedBuilder()
                .setAuthor("JJJannik", "https://github.com/JJJannik", "https://cdn.discordapp.com/avatars/661151077600722947/e37c43f30288c644a2e7dc78b2888b78.webp?size=80")
                .setColor(Color.GREEN)
                .addField("Greev Stats Stalker", """
                        The bot is built with the help of my Java Greev API Wrapper [JGA](https://github.com/JJJannik/JGA).
                        If you experience any issues with the bot feel free to open an Issue on the [GitHub page](https://github.com/JJJannik/GreevStatsStalker) or reach out to me on Discord: `jjjannik`
                        """, false)
                .setFooter("Created by JJJannik")
                .build())
                .setEphemeral(true)
                .queue();
    }
}