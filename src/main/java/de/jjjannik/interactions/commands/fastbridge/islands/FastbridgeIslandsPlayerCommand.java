package de.jjjannik.interactions.commands.fastbridge.islands;

import de.jjjannik.entities.FastBridgePlayer;
import de.jjjannik.interactions.PlayerCommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class FastbridgeIslandsPlayerCommand extends PlayerCommand {
    @Override
    public void execute(SlashCommandInteractionEvent evt) {
        handlePlayerCommand(evt, player -> {
            FastBridgePlayer fastbridgePlayer = jga.getFastbridgeIslandsPlayer(player.getUuid());


            /*evt.replyEmbeds(new EmbedBuilder()
                    .setColor(Color.GREEN)
                    .setTitle("Fastbridge Island stats of " + player.getName())
                    .addField("Tag", clan.getTag(), false)
                    .addField("Size", String.valueOf(clan.getSize()), false)
                    .addField("Id", String.valueOf(clan.getId()), false)
                    .addField("Performance", String.valueOf(clan.getPlayerPerformance()), false)
                    .build()
            ).queue();*/
        });
    }
}