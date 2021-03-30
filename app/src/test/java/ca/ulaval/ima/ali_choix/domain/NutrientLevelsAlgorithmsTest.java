package ca.ulaval.ima.ali_choix.domain;

import org.junit.Test;

import static org.junit.Assert.*;

public class NutrientLevelsAlgorithmsTest {
    @Test
    public void givenLowFatLevel_whenEvaluatingFat_thenReturnGoodNutrientQualities() {
        assertEquals(NutrientQualities.GOOD, NutrientLevelsAlgorithms.evaluateFat(1.1f));
    }

    @Test
    public void givenMediumFatLevel_whenEvaluatingFat_thenReturnModerateNutrientQualities() {
        assertEquals(NutrientQualities.MODERATE, NutrientLevelsAlgorithms.evaluateFat(6f));
    }

    @Test
    public void givenHighFatLevel_whenEvaluatingFat_thenReturnBadNutrientQualities() {
        assertEquals(NutrientQualities.BAD, NutrientLevelsAlgorithms.evaluateFat(25f));
    }

    @Test
    public void givenLowSaturatedFatLevel_whenEvaluatingSaturatedFat_thenReturnGoodNutrientQualities() {
        assertEquals(NutrientQualities.GOOD, NutrientLevelsAlgorithms.evaluateSaturatedFat(1f));
    }

    @Test
    public void givenMediumSaturatedFatLevel_whenEvaluatingSaturatedFat_thenReturnModerateNutrientQualities() {
        assertEquals(NutrientQualities.MODERATE, NutrientLevelsAlgorithms.evaluateSaturatedFat(4.5f));
    }

    @Test
    public void givenHighSaturatedFatLevel_whenEvaluatingSaturatedFat_thenReturnBadNutrientQualities() {
        assertEquals(NutrientQualities.BAD, NutrientLevelsAlgorithms.evaluateSaturatedFat(10f));
    }

    @Test
    public void givenLowSugarLevel_whenEvaluatingSugar_thenReturnGoodNutrientQualities() {
        assertEquals(NutrientQualities.GOOD, NutrientLevelsAlgorithms.evaluateSugar(4f));
    }

    @Test
    public void givenMediumSugarLevel_whenEvaluatingSugar_thenReturnModerateNutrientQualities() {
        assertEquals(NutrientQualities.MODERATE, NutrientLevelsAlgorithms.evaluateSugar(9.5f));
    }

    @Test
    public void givenHighSugarLevel_whenEvaluatingSugar_thenReturnBadNutrientQualities() {
        assertEquals(NutrientQualities.BAD, NutrientLevelsAlgorithms.evaluateSugar(13f));
    }

    @Test
    public void givenLowSaltLevel_whenEvaluatingSalt_thenReturnGoodNutrientQualities() {
        assertEquals(NutrientQualities.GOOD, NutrientLevelsAlgorithms.evaluateSalt(0.3f));
    }

    @Test
    public void givenMediumSaltLevel_whenEvaluatingSalt_thenReturnModerateNutrientQualities() {
        assertEquals(NutrientQualities.MODERATE, NutrientLevelsAlgorithms.evaluateSalt(1.4f));
    }

    @Test
    public void givenHighSaltLevel_whenEvaluatingSalt_thenReturnBadNutrientQualities() {
        assertEquals(NutrientQualities.BAD, NutrientLevelsAlgorithms.evaluateSalt(2f));
    }
}
