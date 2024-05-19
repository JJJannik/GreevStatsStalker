package de.jjjannik.interactions;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public interface Interaction {
    void execute(SlashCommandInteractionEvent evt);
}