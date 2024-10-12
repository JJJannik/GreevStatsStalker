package de.jjjannik.classes.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.function.Function;

@Getter
@RequiredArgsConstructor
public enum SeriesType {
    KD("K/D Ratio", KnockPvPAnalyseEntity::getKd),
    KILLS("Kills", KnockPvPAnalyseEntity::getKills);

    private final String chartLabel;
    private final Function<? super KnockPvPAnalyseEntity, ? extends Number> mapper;
}