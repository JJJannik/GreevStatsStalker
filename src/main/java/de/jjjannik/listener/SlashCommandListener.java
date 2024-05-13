package de.jjjannik.listener;

import de.jjjannik.Main;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class SlashCommandListener extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        Main.INTERACTIONS.get((event.getSubcommandGroup() == null ? "" : event.getSubcommandGroup() + " ") + event.getSubcommandName()).execute(event);
    }
}