package de.jjjannik.classes;

import de.jjjannik.JGAInitializer;
import de.jjjannik.api.JGA;
import de.jjjannik.classes.entities.KnockPvPAnalyseEntity;
import de.jjjannik.classes.entities.StatsType;
import de.jjjannik.entities.KnockPVPPlayer;
import de.jjjannik.entities.basic.KillsDeathsPlayer;
import de.jjjannik.entities.basic.Player;
import de.jjjannik.utils.exceptions.APICallException;
import de.jjjannik.utils.exceptions.APITimeoutException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.knowm.xchart.*;
import org.knowm.xchart.style.XYStyler;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
public class KnockPvPAnalyser {
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    private final Player player;
    private final JGA jga;

    public static void main(String[] args) {
        JGAInitializer.init();
        JGA jga = JGAInitializer.getJGA();

        KnockPvPAnalyser analyser = new KnockPvPAnalyser(jga.getPlayerUUID("JJJannik"), jga);

        List<KnockPvPAnalyseEntity> analysis = analyser.retrieveStats(364, 7);

        analyser.createKnockPvPStatsChart(analysis, StatsType.KD, "analysis");

        List<KnockPvPAnalyseEntity> prognosis = analyser.developPrognosis(analysis, 70);

        analyser.createKnockPvPStatsChart(prognosis, StatsType.KD, "prognosis");
    }

    public List<KnockPvPAnalyseEntity> retrieveStats(int sinceDays, int periodDays) throws APITimeoutException {
        List<KnockPvPAnalyseEntity> data = new ArrayList<>();

        Instant now = Instant.now(Clock.systemUTC());

        data.add(new KnockPvPAnalyseEntity(Date.from(now.minusSeconds(TimeUnit.DAYS.toSeconds(sinceDays))), 0, 0));

        int killsInPeriod = 0;

        for (long i = now.minusSeconds(TimeUnit.DAYS.toSeconds(sinceDays)).getEpochSecond(); (i+TimeUnit.DAYS.toSeconds(periodDays)) < now.getEpochSecond(); i += TimeUnit.DAYS.toSeconds(periodDays)) {

            KnockPvPAnalyseEntity statsEntity;
            try {
                KillsDeathsPlayer stats = jga.getRollingKnockPvPPlayer(player.getUuid(), i, i + TimeUnit.DAYS.toSeconds(periodDays));

                killsInPeriod += stats.getKills();

                statsEntity = new KnockPvPAnalyseEntity(Date.from(Instant.ofEpochSecond(i)), killsInPeriod, (float) stats.getKills() / (stats.getDeaths() == 0 ? 1 : stats.getDeaths()));
            } catch (APICallException e) {
                statsEntity = data.get(data.size() - 1).clone();
            }

            data.add(statsEntity);

            long sysMillis = System.currentTimeMillis();
            while (System.currentTimeMillis() - sysMillis < 1500) {
                // -> delay to avoid API rate limit
            }
        }
        int killsDiff = jga.getKnockPvPPlayer(player.getUuid()).getKills() - killsInPeriod;

        data.forEach(e -> e.setKills(e.getKills() + killsDiff));

        return data;
    }

    public List<KnockPvPAnalyseEntity> developPrognosis(List<KnockPvPAnalyseEntity> data, int untilDays) {
        float sumKD = 0;
        long sumKills = 0;

        List<KnockPvPAnalyseEntity> prognosis = new ArrayList<>();

        KnockPVPPlayer stats = jga.getKnockPvPPlayer(player.getUuid());

        for (int i = data.size()-1; i >= 0; i--) {
            sumKD += data.get(i).getKd();
            sumKills += stats.getKills() - data.get(i).getKills() - sumKills;
        }

        float avgKD = sumKD / data.size();  // average per time period (based on input data)
        long avgKills = sumKills / data.size();

        Instant now = Instant.now();
        int timePeriod = (int)  (data.get(data.size()-1).getDate().getTime()/1000 -  data.get(0).getDate().getTime()/1000) / data.size();

        int k = 0;
        for (long i = now.getEpochSecond(); i < now.plusSeconds(TimeUnit.DAYS.toSeconds(untilDays)).getEpochSecond(); i+=timePeriod) {
            k++;
            long kills = stats.getKills() + (k * avgKills);
            long deaths = (long) (stats.getDeaths() + (k * avgKills) / avgKD);

            prognosis.add(new KnockPvPAnalyseEntity(
                    Date.from(Instant.ofEpochSecond(i)),
                    kills,
                    (float) kills / deaths
            ));
        }

        return prognosis;
    }

    public void createKnockPvPStatsChart(List<KnockPvPAnalyseEntity> data, StatsType type, String fileName) {
        final XYChart chart = new XYChartBuilder()
                .width(700)
                .height(600)
                .title("Trend of the KnockPvP %s of %s from %s to %s".formatted(
                        type.getChartLabel(),
                        this.player.getName(),
                        dateFormat.format(data.get(0).getDate()),
                        dateFormat.format(data.get(data.size()-1).getDate())
                ))
                .xAxisTitle("Date")
                .yAxisTitle(type.getChartLabel())
                .build();

        XYStyler styler = chart.getStyler();
        styler.setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);
        styler.setTheme(new DarkTheme());
        styler.setDecimalPattern("#.##");
        styler.setLocale(Locale.US);
        styler.setTimezone(TimeZone.getTimeZone("UTC"));

        chart.addSeries(type.getChartLabel(),
                data.stream().map(KnockPvPAnalyseEntity::getDate).toList(),
                data.stream().map(type.getMapper()).toList()
        );

        try {
            BitmapEncoder.saveBitmapWithDPI(chart, "./" + fileName, BitmapEncoder.BitmapFormat.PNG, 300);
        } catch (IOException e) {
            log.error("Could not create image from chart", e);
        }

        new SwingWrapper<>(chart).displayChart();
    }
}