package de.jjjannik.classes;

import de.jjjannik.Main;
import de.jjjannik.api.JGA;
import de.jjjannik.interactions.Interaction;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

import java.awt.*;
import java.util.function.Consumer;

public abstract class TopCommand implements Interaction {
    protected final JGA jga = Main.getJga();

    protected void handleTopCommand(SlashCommandInteractionEvent evt, Consumer<TopOptions> top) {
        int amount = evt.getOption("amount", OptionMapping::getAsInt);
        int offset = evt.getOption("offset", OptionMapping::getAsInt);

        if (amount < 1 || amount > 50) {
            evt.replyEmbeds(new EmbedBuilder()
                            .setColor(Color.RED)
                            .addField("❌ **Incorrect option**", "Option 'amount' must lies between 1 and 50", false).build())
                    .setEphemeral(true)
                    .queue();
            return;
        }

        if (offset < 0) {
            evt.replyEmbeds(new EmbedBuilder()
                            .setColor(Color.RED)
                            .addField("❌ **Incorrect option**", "Option 'offset' can't be negative", false).build())
                    .setEphemeral(true)
                    .queue();
            return;
        }

        top.accept(new TopOptions(amount, offset));
    }
}