package de.jjjannik.interactions.commands.general;

import de.jjjannik.interactions.Interaction;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class AllPlayerStatsCommand implements Interaction {
    @Override
    public void execute(SlashCommandInteractionEvent evt) {
        evt.reply("Do you really, REALLY need this command..?").setEphemeral(true).queue();
    }
}