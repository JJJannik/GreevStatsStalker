package de.jjjannik;

import de.jjjannik.api.JGA;
import de.jjjannik.interactions.Interaction;
import de.jjjannik.interactions.commands.advent.AdventDayCommand;
import de.jjjannik.interactions.commands.advent.AdventTopCommand;
import de.jjjannik.interactions.commands.advent.AdventYearCommand;
import de.jjjannik.interactions.commands.fastbridge.islands.FastbridgeIslandsPlayerCommand;
import de.jjjannik.interactions.commands.fastbridge.islands.FastrbidgeIslandsTopCommand;
import de.jjjannik.interactions.commands.fastbridge.mode.FastbridgeModePlayerCommand;
import de.jjjannik.interactions.commands.fastbridge.mode.FastrbidgeModeTopCommand;
import de.jjjannik.interactions.commands.general.*;
import de.jjjannik.interactions.commands.general.clans.ClanDetailsCommand;
import de.jjjannik.interactions.commands.general.clans.ClanMembersCommand;
import de.jjjannik.interactions.commands.general.clans.ClanTopCommand;
import de.jjjannik.interactions.commands.general.loginstreak.LoginstreakPlayerCommand;
import de.jjjannik.interactions.commands.general.loginstreak.LoginstreakTopCommand;
import de.jjjannik.interactions.commands.general.performance.PerfomanceTopCommand;
import de.jjjannik.interactions.commands.general.performance.PerformancePlayerCommand;
import de.jjjannik.interactions.commands.general.tokens.TokensPlayerCommand;
import de.jjjannik.interactions.commands.general.tokens.TokensTopCommand;
import de.jjjannik.interactions.commands.knockpvp.*;
import de.jjjannik.interactions.commands.knockpvp.lab.*;
import de.jjjannik.interactions.commands.minesweeper.*;
import de.jjjannik.interactions.commands.minigames.bedwars.BedwarsPlayerCommand;
import de.jjjannik.interactions.commands.minigames.bedwars.BedwarsTopCommand;
import de.jjjannik.interactions.commands.minigames.bow_spleef.BowSpleefPlayerCommand;
import de.jjjannik.interactions.commands.minigames.bow_spleef.BowSpleefTopCommand;
import de.jjjannik.interactions.commands.minigames.cores.CoresPlayerCommand;
import de.jjjannik.interactions.commands.minigames.cores.CoresTopCommand;
import de.jjjannik.interactions.commands.minigames.jump_league.JumpLeaguePlayerCommand;
import de.jjjannik.interactions.commands.minigames.jump_league.JumpLeagueTopCommand;
import de.jjjannik.interactions.commands.minigames.knockffa.KnockFFAPlayerCommand;
import de.jjjannik.interactions.commands.minigames.knockffa.KnockFFATopCommand;
import de.jjjannik.interactions.commands.minigames.one_vs_one.OneVsOnePlayerCommand;
import de.jjjannik.interactions.commands.minigames.one_vs_one.OneVsOneTopCommand;
import de.jjjannik.interactions.commands.minigames.oneline.OnelinePlayerCommand;
import de.jjjannik.interactions.commands.minigames.oneline.OnelineTopCommand;
import de.jjjannik.interactions.commands.minigames.qsg.QsgPlayerCommand;
import de.jjjannik.interactions.commands.minigames.qsg.QsgTopCommand;
import de.jjjannik.interactions.commands.minigames.quake.QuakePlayerCommand;
import de.jjjannik.interactions.commands.minigames.quake.QuakeTopCommand;
import de.jjjannik.interactions.commands.minigames.rush.RushPlayerCommand;
import de.jjjannik.interactions.commands.minigames.rush.RushTopCommand;
import de.jjjannik.interactions.commands.minigames.skywars.SkywarsPlayerCommand;
import de.jjjannik.interactions.commands.minigames.skywars.SkywarsTopCommand;
import de.jjjannik.interactions.commands.minigames.snowball_fight.SnowballFightPlayerCommand;
import de.jjjannik.interactions.commands.minigames.snowball_fight.SnowballFightTopCommand;
import de.jjjannik.interactions.commands.minigames.spleef.SpleefPlayerCommand;
import de.jjjannik.interactions.commands.minigames.spleef.SpleefTopCommand;
import de.jjjannik.interactions.commands.minigames.sumo.SumoPlayerCommand;
import de.jjjannik.interactions.commands.minigames.sumo.SumoTopCommand;
import de.jjjannik.interactions.commands.minigames.tnt_run.TNTRunPlayerCommand;
import de.jjjannik.interactions.commands.minigames.tnt_run.TNTRunTopCommand;
import de.jjjannik.interactions.commands.minigames.uhc.UhcPlayerCommand;
import de.jjjannik.interactions.commands.minigames.uhc.UhcTopCommand;
import de.jjjannik.interactions.commands.mlgrush.MLGRushPlayerCommand;
import de.jjjannik.interactions.commands.mlgrush.MLGRushRollingPlayerCommand;
import de.jjjannik.interactions.commands.mlgrush.MLGRushRollingTopCommand;
import de.jjjannik.interactions.commands.mlgrush.MLGRushTopCommand;
import de.jjjannik.listener.SlashCommandListener;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.exceptions.InvalidTokenException;
import net.dv8tion.jda.api.interactions.commands.Command.Choice;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
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
import java.util.TimeZone;

@Slf4j
public class Main {
    public static final Map<String, Interaction> INTERACTIONS = new HashMap<>();
    @Getter private static JGA jga;

    public static void main(String[] args) throws IOException, InterruptedException {
        PropertyConfigurator.configure(Main.class.getClassLoader().getResourceAsStream("log4j2.properties"));
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
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

        OptionData playerOption = new OptionData(OptionType.STRING, "player", "The uuid or name of the player", true);
        OptionData clanOption = new OptionData(OptionType.STRING, "name", "The name of the clan", true);
        OptionData[] topOption = {
                new OptionData(OptionType.INTEGER, "amount", "Amount of data to return. Limit: 1-50", true),
                new OptionData(OptionType.INTEGER, "offset", "Offset of when data should begin", true)
        };
        OptionData[] rollingOption = {
                new OptionData(OptionType.INTEGER, "start-timestamp", "Timestamp as start of tracked data", true),
                new OptionData(OptionType.INTEGER, "end-timestamp", "Timestamp as end of tracked data", true)
        };
        OptionData minesweeperMode = new OptionData(OptionType.STRING, "mode", "The Minesweeper playing mode", false).addChoices(
                new Choice("Default", "DEFAULT"),
                new Choice("Training", "TRAINING"),
                new Choice("Countdown", "COUNTDOWN"),
                new Choice("Color", "COLOR")
        );
        OptionData minesweeperCriteria = new OptionData(OptionType.STRING, "criteria", "The ranking criteria", false).addChoices(
                new Choice("Time", "TIME"),
                new Choice("Mastery", "MASTERY")
        );
        OptionData minesweeperType = new OptionData(OptionType.STRING, "type", "The difficulty of Minesweeper", false).addChoices(
                new Choice("Easy", "EASY"),
                new Choice("Medium", "MEDIUM"),
                new Choice("Hard", "HARD"),
                new Choice("Extreme", "EXTREME"),
                new Choice("Hell", "HELL")
        );
        OptionData minesweeperGenerator = new OptionData(OptionType.STRING, "generator", "The Minesweeper map generator", false).addChoices(
                new Choice("OG", "OG"),
                new Choice("Greev", "GREEV"),
                new Choice("No Guessing", "NO_GUESSING"),
                new Choice("Speedrun", "SPEEDRUN")
        );
        OptionData labOption = new OptionData(OptionType.STRING, "experiment", "The name of lab experiment", true);

        jda.updateCommands().addCommands(
                Commands.slash("general", "Command for all general stats")
                        .addSubcommands(
                                new SubcommandData("metadata", "Get all MetaData of player")
                                        .addOptions(playerOption),
                                new SubcommandData("badges", "Get all Badges of player")
                                        .addOptions(playerOption),
                                new SubcommandData("player", "Get uuid and name of player")
                                        .addOptions(playerOption),
                                new SubcommandData("name-history", "Get Namehistory of player")
                                        .addOptions(playerOption),
                                new SubcommandData("all-player-stats", "Get all player stats combined")
                                        .addOptions(playerOption)
                        )
                        .addSubcommandGroups(
                                new SubcommandGroupData("performance", "Get player Performance")
                                        .addSubcommands(
                                                new SubcommandData("player", "Get player Performance of player")
                                                        .addOptions(playerOption),
                                                new SubcommandData("top", "Get top Performance players")
                                                        .addOptions(topOption)
                                        ),
                                new SubcommandGroupData("tokens", "Get player Tokens")
                                        .addSubcommands(
                                                new SubcommandData("player", "Get Tokens of player")
                                                        .addOptions(playerOption),
                                                new SubcommandData("top", "Get top Tokens players")
                                                        .addOptions(topOption)
                                        ),
                                new SubcommandGroupData("loginstreak", "Get player Loginstreak")
                                        .addSubcommands(
                                                new SubcommandData("player", "Get the Loginstreak of player")
                                                        .addOptions(playerOption),
                                                new SubcommandData("top", "Get top Loginstreak players")
                                                        .addOptions(topOption)
                                        ),
                                new SubcommandGroupData("clans", "Get Clan information")
                                        .addSubcommands(
                                                new SubcommandData("details", "Get Clan details")
                                                        .addOptions(clanOption),
                                                new SubcommandData("top", "Get top Clans")
                                                        .addOptions(topOption[0], topOption[1]),
                                                new SubcommandData("members", "Get Clan members. Note: This will only show up to 250 members")
                                                        .addOptions(clanOption)
                                        )
                        ),
                Commands.slash("minigame","Standardised minigame stats")
                        .addSubcommandGroups(
                                new SubcommandGroupData("bedwars", "Get Bedwars stats")
                                        .addSubcommands(
                                                new SubcommandData("player", "Get Bedwars stats of player")
                                                        .addOptions(playerOption),
                                                new SubcommandData("top", "Get top Bedwars players")
                                                        .addOptions(topOption)
                                        ),
                                new SubcommandGroupData("bow-spleef", "Get BowSpleef stats")
                                        .addSubcommands(
                                                new SubcommandData("player", "Get BowSpleef stats of player")
                                                        .addOptions(playerOption),
                                                new SubcommandData("top", "Get top BowSpleef players")
                                                        .addOptions(topOption)
                                        ),
                                new SubcommandGroupData("cores", "Get Cores stats")
                                        .addSubcommands(
                                                new SubcommandData("player", "Get Cores stats of player")
                                                        .addOptions(playerOption),
                                                new SubcommandData("top", "Get top Cores players")
                                                        .addOptions(topOption)
                                        ),
                                new SubcommandGroupData("jump-league", "Get JumpLeague stats")
                                        .addSubcommands(
                                                new SubcommandData("player", "Get JumpLeague stats of player")
                                                        .addOptions(playerOption),
                                                new SubcommandData("top", "Get top JumpLeague players")
                                                        .addOptions(topOption)
                                        ),
                                new SubcommandGroupData("knockffa", "Get KnockFFA stats")
                                        .addSubcommands(
                                                new SubcommandData("player", "Get KnockFFA stats of player")
                                                        .addOptions(playerOption),
                                                new SubcommandData("top", "Get top KnockFFA players")
                                                        .addOptions(topOption)
                                        ),
                                new SubcommandGroupData("oneline", "Get Oneline stats")
                                        .addSubcommands(
                                                new SubcommandData("player", "Get Oneline stats of player")
                                                        .addOptions(playerOption),
                                                new SubcommandData("top", "Get top Oneline players")
                                                        .addOptions(topOption)
                                        ),
                                new SubcommandGroupData("1vs1", "Get 1vs1 stats")
                                        .addSubcommands(
                                                new SubcommandData("player", "Get 1vs1 stats of player")
                                                        .addOptions(playerOption),
                                                new SubcommandData("top", "Get top 1vs1 players")
                                                        .addOptions(topOption)
                                        ),
                                new SubcommandGroupData("qsg", "Get QSG stats")
                                        .addSubcommands(
                                                new SubcommandData("player", "Get QSG stats of player")
                                                        .addOptions(playerOption),
                                                new SubcommandData("top", "Get top QSG players")
                                                        .addOptions(topOption)
                                        ),
                                new SubcommandGroupData("quake", "Get Quake stats")
                                        .addSubcommands(
                                                new SubcommandData("player", "Get Quake stats of player")
                                                        .addOptions(playerOption),
                                                new SubcommandData("top", "Get top Quake players")
                                                        .addOptions(topOption)
                                        ),
                                new SubcommandGroupData("rush", "Get Rush stats")
                                        .addSubcommands(
                                                new SubcommandData("player", "Get Rush stats of player")
                                                        .addOptions(playerOption),
                                                new SubcommandData("top", "Get top Rush players")
                                                        .addOptions(topOption)
                                        ),
                                new SubcommandGroupData("skywars", "Get Skywars stats")
                                        .addSubcommands(
                                                new SubcommandData("player", "Get Skywars stats of player")
                                                        .addOptions(playerOption),
                                                new SubcommandData("top", "Get top Skywars players")
                                                        .addOptions(topOption)
                                        ),
                                new SubcommandGroupData("snowball-fight", "Get SnowballFight stats")
                                        .addSubcommands(
                                                new SubcommandData("player", "Get SnowballFight stats of player")
                                                        .addOptions(playerOption),
                                                new SubcommandData("top", "Get top SnowballFight players")
                                                        .addOptions(topOption)
                                        ),
                                new SubcommandGroupData("spleef", "Get Spleef stats")
                                        .addSubcommands(
                                                new SubcommandData("player", "Get Spleef stats of player")
                                                        .addOptions(playerOption),
                                                new SubcommandData("top", "Get top Spleef players")
                                                        .addOptions(topOption)
                                        ),
                                new SubcommandGroupData("sumo", "Get Sumo stats")
                                        .addSubcommands(
                                                new SubcommandData("player", "Get Sumo stats of player")
                                                        .addOptions(playerOption),
                                                new SubcommandData("top", "Get top Sumo players")
                                                        .addOptions(topOption)
                                        ),
                                new SubcommandGroupData("tnt-run", "Get TNTRun stats")
                                        .addSubcommands(
                                                new SubcommandData("player", "Get TNTRun stats of player")
                                                        .addOptions(playerOption),
                                                new SubcommandData("top", "Get top TNTRun players")
                                                        .addOptions(topOption)
                                        ),
                                new SubcommandGroupData("uhc", "Get UHC stats")
                                        .addSubcommands(
                                                new SubcommandData("player", "Get UHC stats of player")
                                                        .addOptions(playerOption),
                                                new SubcommandData("top", "Get top UHC players")
                                                        .addOptions(topOption)
                                        )
                        ),
                        Commands.slash("minesweeper", "Get Minesweeper stats").addSubcommands(
                                new SubcommandData("top", "Get top Minesweeper player")
                                        .addOptions(topOption)
                                        .addOptions(
                                                minesweeperType.setRequired(true),
                                                minesweeperGenerator.setRequired(true),
                                                minesweeperMode,
                                                minesweeperCriteria,
                                                rollingOption[0].setRequired(false),
                                                rollingOption[1].setRequired(false)
                                        ),
                                new SubcommandData("player", "Get Minesweeper stats of player")
                                        .addOptions(playerOption, minesweeperMode),
                                new SubcommandData("best", "Get best Minesweeper stats of player")
                                        .addOptions(playerOption, minesweeperMode),
                                new SubcommandData("best-filtered", "Get best Minesweeper stats of player with filter")
                                        .addOptions(playerOption, minesweeperType.setRequired(true), minesweeperGenerator.setRequired(true), minesweeperMode),
                                new SubcommandData("game", "Get Minesweeper game info")
                                        .addOption(OptionType.INTEGER, "id", "The game Id of your Minesweeper round", true)
                        ),
                Commands.slash("mlgrush", "Get MLGRush stats")
                        .addSubcommands(
                                new SubcommandData("top", "Get top MLGRush players")
                                        .addOptions(topOption),
                                new SubcommandData("rolling-top", "Get top rolling MLGRush players")
                                        .addOptions(topOption)
                                        .addOptions(rollingOption),
                                new SubcommandData("player", "Get MLGRush stats of player")
                                        .addOptions(playerOption),
                                new SubcommandData("rolling-player", "Get rolling MLGRush stats of player")
                                        .addOptions(playerOption)
                                        .addOptions(rollingOption)
                        ),
                Commands.slash("fastbridge", "Get Fastbridge stats")
                        .addSubcommandGroups(
                                new SubcommandGroupData("islands", "Get Fastbridge Island stats")
                                        .addSubcommands(
                                                new SubcommandData("player", "Get Fastbridge Island stats of player")
                                                        .addOptions(playerOption),
                                                new SubcommandData("top", "Get top Fastbridge Island players")
                                                        .addOptions(topOption)
                                                        .addOptions(new OptionData(OptionType.STRING, "map", "The Fastbridge map to get the data for", true).addChoices(
                                                                new Choice("Cubes", "CUBES"),
                                                                new Choice("Rails", "RAILS"),
                                                                new Choice("Street", "STREET"),
                                                                new Choice("Coral", "CORAL"),
                                                                new Choice("Athen", "ATHEN")
                                                        ))
                                        ),
                                new SubcommandGroupData("mode", "Get other Fastbridge mode stats")
                                        .addSubcommands(
                                                new SubcommandData("player", "Get Fastbridge mode stats of player")
                                                        .addOptions(playerOption)
                                                        .addOptions(new OptionData(OptionType.STRING, "mode", "The Fastbridge mode to get the data for", true).addChoices(
                                                                new Choice("Inclined", "INCLINED"),
                                                                new Choice("Inclined Short", "INCLINED_SHORT"),
                                                                new Choice("Normal", "NORMAL"),
                                                                new Choice("Short", "SHORT"),
                                                                new Choice("Extra Short", "EXTRA_SHORT"),
                                                                new Choice("Staircase", "STAIRCASE")
                                                        )),
                                                new SubcommandData("top", "Get top Fastbridge mode players")
                                                        .addOptions(topOption)
                                                        .addOptions(new OptionData(OptionType.STRING, "mode", "The Fastbridge mode to get the data for", true).addChoices(
                                                                new Choice("Inclined", "INCLINED"),
                                                                new Choice("Inclined Short", "INCLINED_SHORT"),
                                                                new Choice("Normal", "NORMAL"),
                                                                new Choice("Short", "SHORT"),
                                                                new Choice("Extra Short", "EXTRA_SHORT"),
                                                                new Choice("Staircase", "STAIRCASE")
                                                        ))
                                        )
                        ),
                Commands.slash("advent", "Get Advent Jump and Run stats")
                        .addSubcommands(
                                new SubcommandData("top", "Get top Advent Jump&Run times of day")
                                        .addOptions(topOption)
                                        .addOption(OptionType.INTEGER, "day", "Day 1-24 of advent", true)
                                        .addOption(OptionType.INTEGER, "year", "Year of the advent", true),
                                new SubcommandData("year", "Get Advent Jump&Run times of player of year")
                                        .addOptions(playerOption)
                                        .addOption(OptionType.INTEGER, "year", "Year of the advent", true),
                                new SubcommandData("day", "Get Advent Jump&Run times of player of day")
                                        .addOptions(playerOption)
                                        .addOption(OptionType.INTEGER, "year", "Year of the advent", true)
                                        .addOption(OptionType.INTEGER, "day", "Day 1-24 of advent", true)
                        ),
                Commands.slash("knockpvp", "Get KnockPVP stats")
                        .addSubcommands(
                                new SubcommandData("top", "Get top KnockPVP players")
                                        .addOptions(topOption),
                                new SubcommandData("rolling-top", "Get top rolling KnockPVP players")
                                        .addOptions(topOption)
                                        .addOptions(rollingOption),
                                new SubcommandData("player", "Get KnockPVP stats of player")
                                        .addOptions(playerOption),
                                new SubcommandData("rolling-player", "Get rolling KnockPVP stats of player")
                                        .addOptions(playerOption)
                                        .addOptions(rollingOption),
                                new SubcommandData("yearly", "Get yearly KnockPVP stats of player")
                                        .addOptions(playerOption),
                                new SubcommandData("monthly", "Get KnockPVP stats of player of the last 30 days")
                                        .addOptions(playerOption),
                                new SubcommandData("weekly", "Get KnockPVP stats of player of the last 7 days")
                                        .addOptions(playerOption),
                                new SubcommandData("daily", "Get KnockPVP stats of player of the last 24 hours")
                                        .addOptions(playerOption),
                                new SubcommandData("days", "Get KnockPVP stats of player of last N days")
                                        .addOptions(playerOption)
                                        .addOption(OptionType.INTEGER, "days", "Number of last days to return data from", true)
                        ),
                Commands.slash("knockpvp-lab", "Get KnockPVP-Lab stats from experiment")
                        .addSubcommands(
                                new SubcommandData("top", "Get top KnockPVP-Lab players")
                                        .addOptions(labOption)
                                        .addOptions(topOption),
                                new SubcommandData("rolling-top", "Get top rolling KnockPVP-Lab players")
                                        .addOptions(labOption)
                                        .addOptions(topOption)
                                        .addOptions(rollingOption),
                                new SubcommandData("player", "Get KnockPVP-Lab stats of player")
                                        .addOptions(playerOption)
                                        .addOptions(labOption),
                                new SubcommandData("rolling-player", "Get rolling KnockPVP-Lab stats of player")
                                        .addOptions(playerOption)
                                        .addOptions(labOption)
                                        .addOptions(rollingOption),
                                new SubcommandData("list", "Lists all KnockPVP-Lab experiments")
                        )
        ).queue();

        registerAllCommands();

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

    private static void registerAllCommands() {
        registerInteraction("general metadata", new MetaDataCommand());
        registerInteraction("general badges", new BadgesCommand());
        registerInteraction("general player", new UUIDNameCommand());
        registerInteraction("general name-history", new NamehistoryCommand());
        registerInteraction("general all-player-stats", new AllPlayerStatsCommand());

        registerInteraction("general performance player", new PerformancePlayerCommand());
        registerInteraction("general performance top", new PerfomanceTopCommand());

        registerInteraction("general tokens player", new TokensPlayerCommand());
        registerInteraction("general tokens top", new TokensTopCommand());

        registerInteraction("general loginstreak player", new LoginstreakPlayerCommand());
        registerInteraction("general loginstreak top", new LoginstreakTopCommand());

        registerInteraction("general clans details", new ClanDetailsCommand());
        registerInteraction("general clans top", new ClanTopCommand());
        registerInteraction("general clans members", new ClanMembersCommand());

        registerInteraction("minigame bedwars player", new BedwarsPlayerCommand());
        registerInteraction("minigame bedwars top", new BedwarsTopCommand());

        registerInteraction("minigame bow-spleef player", new BowSpleefPlayerCommand());
        registerInteraction("minigame bow-spleef top", new BowSpleefTopCommand());

        registerInteraction("minigame cores player", new CoresPlayerCommand());
        registerInteraction("minigame cores top", new CoresTopCommand());

        registerInteraction("minigame jump-league player", new JumpLeaguePlayerCommand());
        registerInteraction("minigame jump-league top", new JumpLeagueTopCommand());

        registerInteraction("minigame knockffa player", new KnockFFAPlayerCommand());
        registerInteraction("minigame knockffa top", new KnockFFATopCommand());

        registerInteraction("minigame oneline player", new OnelinePlayerCommand());
        registerInteraction("minigame oneline top", new OnelineTopCommand());

        registerInteraction("minigame 1vs1 player", new OneVsOnePlayerCommand());
        registerInteraction("minigame 1vs1 top", new OneVsOneTopCommand());

        registerInteraction("minigame qsg player", new QsgPlayerCommand());
        registerInteraction("minigame qsg top", new QsgTopCommand());

        registerInteraction("minigame quake player", new QuakePlayerCommand());
        registerInteraction("minigame quake top", new QuakeTopCommand());

        registerInteraction("minigame rush player", new RushPlayerCommand());
        registerInteraction("minigame rush top", new RushTopCommand());

        registerInteraction("minigame skywars player", new SkywarsPlayerCommand());
        registerInteraction("minigame skywars top", new SkywarsTopCommand());

        registerInteraction("minigame snowball-fight player", new SnowballFightPlayerCommand());
        registerInteraction("minigame snowball-fight top", new SnowballFightTopCommand());

        registerInteraction("minigame spleef player", new SpleefPlayerCommand());
        registerInteraction("minigame spleef top", new SpleefTopCommand());

        registerInteraction("minigame sumo player", new SumoPlayerCommand());
        registerInteraction("minigame sumo top", new SumoTopCommand());

        registerInteraction("minigame tnt-run player", new TNTRunPlayerCommand());
        registerInteraction("minigame tnt-run top", new TNTRunTopCommand());

        registerInteraction("minigame uhc player", new UhcPlayerCommand());
        registerInteraction("minigame uhc top", new UhcTopCommand());

        registerInteraction("minesweeper player", new MinesweeperPlayerCommand());
        registerInteraction("minesweeper top", new MinesweeperTopCommand());
        registerInteraction("minesweeper best", new MinesweeperBestCommand());
        registerInteraction("minesweeper best-filtered", new MinesweeperBestFilteredCommand());
        registerInteraction("minesweeper game", new MinesweeperGameCommand());

        registerInteraction("mlgrush top", new MLGRushTopCommand());
        registerInteraction("mlgrush rolling-top", new MLGRushRollingTopCommand());
        registerInteraction("mlgrush player", new MLGRushPlayerCommand());
        registerInteraction("mlgrush rolling-player", new MLGRushRollingPlayerCommand());

        registerInteraction("fastbridge islands player", new FastbridgeIslandsPlayerCommand());
        registerInteraction("fastbridge islands top", new FastrbidgeIslandsTopCommand());
        registerInteraction("fastbridge mode player", new FastbridgeModePlayerCommand());
        registerInteraction("fastbridge mode top", new FastrbidgeModeTopCommand());

        registerInteraction("advent year", new AdventYearCommand());
        registerInteraction("advent day", new AdventDayCommand());
        registerInteraction("advent top", new AdventTopCommand());

        registerInteraction("knockpvp top", new KnockPVPTopCommand());
        registerInteraction("knockpvp rolling-top", new KnockPVPRollingTopCommand());
        registerInteraction("knockpvp player", new KnockPVPPlayerCommand());
        registerInteraction("knockpvp rolling-player", new KnockPVPRollingPlayerCommand());
        registerInteraction("knockpvp yearly", new KnockPVPYearlyCommand());
        registerInteraction("knockpvp monthly", new KnockPVPMonthlyCommand());
        registerInteraction("knockpvp weekly", new KnockPVPWeeklyCommand());
        registerInteraction("knockpvp daily", new KnockPVPDailyCommand());
        registerInteraction("knockpvp days", new KnockPVPDaysCommand());

        registerInteraction("knockpvp-lab top", new KnockLabTopCommand());
        registerInteraction("knockpvp-lab rolling-top", new KnockLabRollingTopCommand());
        registerInteraction("knockpvp-lab player", new KnockLabPlayerCommand());
        registerInteraction("knockpvp-lab rolling-player", new KnockLabRollingPlayerCommand());
        registerInteraction("knockpvp-lab list", new ListExperimentsCommand());
    }
}