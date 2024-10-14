package de.jjjannik.interactions.commands.knockpvp.analyser;

import de.jjjannik.api.JGA;
import de.jjjannik.classes.DarkTheme;
import de.jjjannik.classes.entities.KnockPvPAnalyseEntity;
import de.jjjannik.classes.entities.SeriesType;
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

    public List<KnockPvPAnalyseEntity> developPrognosis(Player player, List<KnockPvPAnalyseEntity> data, int untilDays) {
        float sumKD = 0;
        long sumKills = 0;

        for (KnockPvPAnalyseEntity e : data) {
            sumKD += e.getKd();
            sumKills += e.getKills();
        }

        KnockPVPPlayer stats = jga.getKnockPvPPlayer(player.getUuid());

        float avgKD = sumKD / data.size();  // average per time period (based on input data)
        long avgKills = sumKills / data.size();

        Instant now = Instant.now();
        int timePeriod = (int)  (data.get(1).getDate().getTime() -  data.get(0).getDate().getTime())/1000;

        int k = 0;
        for (long i = now.getEpochSecond(); i < TimeUnit.DAYS.toSeconds(untilDays); i+=timePeriod) {
            k++;

            data.add(new KnockPvPAnalyseEntity(
                    Date.from(Instant.ofEpochSecond(i)),
                    stats.getKills() + (k * avgKills),
                    ((float) stats.getKills() / stats.getDeaths()) + (k * avgKD)
            ));
        }

        return data;
    }

    public void createKDStatsChart(List<KnockPvPAnalyseEntity> data, SeriesType type) {
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
            BitmapEncoder.saveBitmapWithDPI(chart, "./" + player.getName(), BitmapEncoder.BitmapFormat.PNG, 300);
        } catch (IOException e) {
            log.error("Could not create image from chart", e);
        }

        new SwingWrapper<>(chart).displayChart();
    }
}