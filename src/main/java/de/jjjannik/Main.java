package de.jjjannik;

import de.jjjannik.api.JGA;
import de.jjjannik.interactions.Interaction;
import de.jjjannik.listener.SlashCommandListener;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.exceptions.InvalidTokenException;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandGroupData;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.apache.log4j.PropertyConfigurator;
import org.apache.logging.log4j.util.Strings;
import org.simpleyaml.configuration.file.YamlFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class Main {
    public static final Map<String, Interaction> INTERACTIONS = new HashMap<>();
    @Getter private static JGA jga;

    public static void main(String[] args) throws IOException, InterruptedException {
        PropertyConfigurator.configure(Main.class.getClassLoader().getResourceAsStream("log4j2.properties"));
        JDA jda = null;

        File file = new File("./GreevStatsStalker/config.yml");
        new File("./GreevStatsStalker").mkdirs();
        if (!file.exists()) {
            getResourceAsFile("config.yml").renameTo(file);
        }
        YamlFile config = YamlFile.loadConfiguration(file);

        if (!config.isSet("botToken") || Strings.isEmpty(config.get("botToken").toString())) {
            log.error("No valid config provided! Please add a correct property with the path `botToken`\nIf you feel unsure, take a look into the default config");
            System.exit(1);
        }

        try {
            jda = JDABuilder.create(config.getString("botToken"),
                            List.of(GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_PRESENCES))
                    .disableCache(CacheFlag.ACTIVITY, CacheFlag.VOICE_STATE, CacheFlag.EMOJI, CacheFlag.STICKER, CacheFlag.CLIENT_STATUS, CacheFlag.ONLINE_STATUS, CacheFlag.SCHEDULED_EVENTS)
                    .setActivity(Activity.watching(" your Greev Stats."))
                    .setChunkingFilter(ChunkingFilter.ALL).setMemberCachePolicy(MemberCachePolicy.NONE)
                    .setStatus(OnlineStatus.ONLINE)
                    .build();
        } catch (InvalidTokenException e) {
            log.error("Bot could not be initialized", e);
            System.exit(1);
        }

        JGAInitializer.init();
        jga = JGAInitializer.getJGA();

        jda.awaitReady();

        jda.addEventListener(new SlashCommandListener());
        jda.updateCommands().addCommands(
                Commands.slash("general", "Command for all general stats")
                        .addSubcommands(
                                new SubcommandData("metadata", "Get all meta data from a player"),
                                new SubcommandData("badges", "Get all badges off a player"),
                                new SubcommandData("uuid", "Get player uuid by player name"),
                                new SubcommandData("name", "Get player name by player uuid"),
                                new SubcommandData("name-history", "Get namehistory of a player"),
                                new SubcommandData("all-player-stats", "Get all player stats combined")
                        )
                        .addSubcommandGroups(
                                new SubcommandGroupData("playerperformance", "Get player Performance")
                                        .addSubcommands(
                                                new SubcommandData("player", "Get player Performance of player"),
                                                new SubcommandData("top", "Get top Performance players")
                                        ),
                                new SubcommandGroupData("tokens", "Get player Tokens")
                                        .addSubcommands(
                                                new SubcommandData("player", "Get Tokens of player"),
                                                new SubcommandData("top", "Get top Tokens players")
                                        ),
                                new SubcommandGroupData("loginstreak", "Get player Loginstreaks")
                                        .addSubcommands(
                                                new SubcommandData("player", "Get the Loginstreak of player"),
                                                new SubcommandData("top", "Get top Loginstreak players")
                                        ),
                                new SubcommandGroupData("clans", "Get clan information")
                                        .addSubcommands(
                                                new SubcommandData("details", "Get clan details"),
                                                new SubcommandData("top", "Get top clans"),
                                                new SubcommandData("members", "Get clan members")
                                        )
                        ),
                Commands.slash("minigame","Standardised minigame stats")
                        .addSubcommandGroups(
                                new SubcommandGroupData("bedwars", "Get Bedwars stats")
                                        .addSubcommands(
                                                new SubcommandData("player", "Get Bedwars stats of player"),
                                                new SubcommandData("top", "Get top Bedwars players")
                                        ),
                                new SubcommandGroupData("bow-spleef", "Get BowSpleef stats")
                                        .addSubcommands(
                                                new SubcommandData("player", "Get BowSpleef stats of player"),
                                                new SubcommandData("top", "Get top BowSpleef players")
                                        ),
                                new SubcommandGroupData("cores", "Get Cores stats")
                                        .addSubcommands(
                                                new SubcommandData("player", "Get Cores stats of player"),
                                                new SubcommandData("top", "Get top Cores players")
                                        ),
                                new SubcommandGroupData("jump-league", "Get JumpLeague stats")
                                        .addSubcommands(
                                                new SubcommandData("player", "Get JumpLeague stats of player"),
                                                new SubcommandData("top", "Get top JumpLeague players")
                                        ),
                                new SubcommandGroupData("knockffa", "Get KnockFFA stats")
                                        .addSubcommands(
                                                new SubcommandData("player", "Get KnockFFA stats of player"),
                                                new SubcommandData("top", "Get top KnockFFA players")
                                        ),
                                new SubcommandGroupData("oneline", "Get Oneline stats")
                                        .addSubcommands(
                                                new SubcommandData("player", "Get Oneline stats of player"),
                                                new SubcommandData("top", "Get top Oneline players")
                                        ),
                                new SubcommandGroupData("1vs1", "Get 1vs1 stats")
                                        .addSubcommands(
                                                new SubcommandData("player", "Get 1vs1 stats of player"),
                                                new SubcommandData("top", "Get top 1vs1 players")
                                        ),
                                new SubcommandGroupData("qsg", "Get QSG stats")
                                        .addSubcommands(
                                                new SubcommandData("player", "Get QSG stats of player"),
                                                new SubcommandData("top", "Get top QSG players")
                                        ),
                                new SubcommandGroupData("quake", "Get Quake stats")
                                        .addSubcommands(
                                                new SubcommandData("player", "Get Quake stats of player"),
                                                new SubcommandData("top", "Get top Quake players")
                                        ),
                                new SubcommandGroupData("rush", "Get Rush stats")
                                        .addSubcommands(
                                                new SubcommandData("player", "Get Rush stats of player"),
                                                new SubcommandData("top", "Get top Rush players")
                                        ),
                                new SubcommandGroupData("skywars", "Get Skywars stats")
                                        .addSubcommands(
                                                new SubcommandData("player", "Get Skywars stats of player"),
                                                new SubcommandData("top", "Get top Skywars players")
                                        ),
                                new SubcommandGroupData("snowball-fight", "Get SnowballFight stats")
                                        .addSubcommands(
                                                new SubcommandData("player", "Get SnowballFight stats of player"),
                                                new SubcommandData("top", "Get top SnowballFight players")
                                        ),
                                new SubcommandGroupData("spleef", "Get Spleef stats")
                                        .addSubcommands(
                                                new SubcommandData("player", "Get Spleef stats of player"),
                                                new SubcommandData("top", "Get top Spleef players")
                                        ),
                                new SubcommandGroupData("sumo", "Get Sumo stats")
                                        .addSubcommands(
                                                new SubcommandData("player", "Get Sumo stats of player"),
                                                new SubcommandData("top", "Get top Sumo players")
                                        ),
                                new SubcommandGroupData("tnt-run", "Get TNTRun stats")
                                        .addSubcommands(
                                                new SubcommandData("player", "Get TNTRun stats of player"),
                                                new SubcommandData("top", "Get top TNTRun players")
                                        ),
                                new SubcommandGroupData("uhc", "Get UHC stats")
                                        .addSubcommands(
                                                new SubcommandData("player", "Get UHC stats of player"),
                                                new SubcommandData("top", "Get top UHC players")
                                        )
                        ),
                Commands.slash("minesweeper", "Get Minesweeper stats")
                        .addSubcommands(
                                new SubcommandData("top", "Get top Minesweeper player"),
                                new SubcommandData("player", "Get Minesweeper stats of player"),
                                new SubcommandData("best", "Get best Minesweeper stats of player"),
                                new SubcommandData("best-filtered", "Get best Minesweeper stats of player with filter"),
                                new SubcommandData("game", "Get Minesweeper game info")
                        ),
                Commands.slash("mlgrush", "Get MLGRush stats")
                        .addSubcommands(
                                new SubcommandData("top", "Get top MLGRush players"),
                                new SubcommandData("rolling-top", "Get top rolling MLGRush players"),
                                new SubcommandData("player", "Get MLGRush stats of player"),
                                new SubcommandData("rolling-player", "Get rolling MLGRush stats of player")
                        ),
                Commands.slash("fastbridge", "Get Fastbridge stats")
                        .addSubcommandGroups(
                                new SubcommandGroupData("islands", "Get Fastbridge Island stats")
                                        .addSubcommands(
                                                new SubcommandData("player", "Get Fastbridge Island stats of player"),
                                                new SubcommandData("top", "Get top Fastbridge Island players")
                                        ),
                                new SubcommandGroupData("fastbridge", "Get other Fastbridge mode stats")
                                        .addSubcommands(
                                                new SubcommandData("player", "Get Fastbridge mode stats of player"),
                                                new SubcommandData("top", "Get top Fastbridge mode players")
                                        )
                        ),
                Commands.slash("advent", "Get Advent Jump and Run stats")
                        .addSubcommands(
                                new SubcommandData("year", "Get Advent Jump&Run times of player of year"),
                                new SubcommandData("day", "Get Advent Jump&Run times of player of day")
                        ),
                Commands.slash("knockpvp", "Get KnockPVP stats")
                        .addSubcommands(
                                new SubcommandData("top", "Get top KnockPVP players"),
                                new SubcommandData("rolling-top", "Get top rolling KnockPVP players"),
                                new SubcommandData("player", "Get KnockPVP stats of player"),
                                new SubcommandData("rolling-player", "Get rolling KnockPVP stats of player"),
                                new SubcommandData("yearly", "Get yearly KnockPVP stats of player"),
                                new SubcommandData("monthly", "Get monthly KnockPVP stats of player"),
                                new SubcommandData("weekly", "Get weekly KnockPVP stats of player"),
                                new SubcommandData("daily", "Get daily KnockPVP stats of player"),
                                new SubcommandData("days", "Get KnockPVP stats of player of last days")
                        ),
                Commands.slash("knockpvp-lab", "Get KnockPVP-Lab stats from experiment")
                        .addSubcommands(
                                new SubcommandData("top", "Get top KnockPVP-Lab players"),
                                new SubcommandData("rolling-top", "Get top rolling KnockPVP-Lab players"),
                                new SubcommandData("player", "Get KnockPVP-Lab stats of player"),
                                new SubcommandData("rolling-player", "Get rolling KnockPVP-Lab stats of player")
                        )
        ).queue();

        log.info("Started: {}", OffsetDateTime.now(ZoneId.systemDefault()));
    }

    private static File getResourceAsFile(String resourcePath) {
        try (InputStream in = ClassLoader.getSystemClassLoader().getResourceAsStream(resourcePath)) {
            if (in == null) return null;
            File tempFile = File.createTempFile(String.valueOf(in.hashCode()), ".tmp");
            tempFile.deleteOnExit();
            try (FileOutputStream out = new FileOutputStream(tempFile)) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = in.read(buffer)) != -1) out.write(buffer, 0, bytesRead);
            }
            return tempFile;
        } catch (IOException e) {
            log.error("Could not get resource as file", e);
            return null;
        }
    }

    private static void registerInteraction(String identifier, Interaction interaction) {
        INTERACTIONS.put(identifier, interaction);
    }
}