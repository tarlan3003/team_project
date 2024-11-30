package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution;

import java.io.File;

import org.slf4j.Logger;

import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Blocking.GameBlockingKeyByTitleGenerator;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.GameTitleComparatorJaccard;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.GamePublisherComparatorJaccardLowercase;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.ReleaseYearComparatorOne;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Game;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.GameXMLReader;
import de.uni_mannheim.informatik.dws.winter.matching.MatchingEngine;
import de.uni_mannheim.informatik.dws.winter.matching.MatchingEvaluator;
import de.uni_mannheim.informatik.dws.winter.matching.blockers.StandardRecordBlocker;
import de.uni_mannheim.informatik.dws.winter.matching.rules.LinearCombinationMatchingRule;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.HashedDataSet;
import de.uni_mannheim.informatik.dws.winter.model.MatchingGoldStandard;
import de.uni_mannheim.informatik.dws.winter.model.Performance;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.model.io.CSVCorrespondenceFormatter;
import de.uni_mannheim.informatik.dws.winter.processing.Processable;
import de.uni_mannheim.informatik.dws.winter.utils.WinterLogManager;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.GameTitleComparatorLevenshtein;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.GamePublisherComparatorLevenshtein;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Blocking.GameBlockingKeyByPublisherGenerator;


public class IR_using_linear_combination {

    private static final Logger logger = WinterLogManager.activateLogger("default");

    public static void main(String[] args) throws Exception {

        // Load datasets
        logger.info("*\tLoading datasets\t*");
        HashedDataSet<Game, Attribute> dataSteam = new HashedDataSet<>();
        new GameXMLReader().loadFromXML(new File("data/input/Games_Updated_ID_Cleaned-2.xml"), "/Games/Game", dataSteam);

        HashedDataSet<Game, Attribute> dataVGSales = new HashedDataSet<>();
        new GameXMLReader().loadFromXML(new File("data/input/vgsales_ID_updated_new.xml"), "/Games/Game", dataVGSales);

        // Load the gold standard
        logger.info("*\tLoading gold standard\t*");
        MatchingGoldStandard gsTest = new MatchingGoldStandard();
        gsTest.loadFromCSVFile(new File("data/goldstandard/GS_gameUpd_test_vgsales.csv"));

        // Create a matching rule
        LinearCombinationMatchingRule<Game, Attribute> matchingRule = new LinearCombinationMatchingRule<>(0.5);
        matchingRule.activateDebugReport("data/output/debugResultsMatchingRule.csv", 1000, gsTest);

        // Add comparators
        matchingRule.addComparator(new GameTitleComparatorLevenshtein(), 1); // Title comparison weight: 0.5
        //matchingRule.addComparator(new GamePublisherComparatorLevenshtein(), 0.4); // Publisher comparison weight: 0.3
        //matchingRule.addComparator(new ReleaseYearComparatorOne(), 0.1); // Release year comparison weight: 0.2

        // Create a blocker
        StandardRecordBlocker<Game, Attribute> blocker = new StandardRecordBlocker<>(new GameBlockingKeyByTitleGenerator());
        //StandardRecordBlocker<Game, Attribute> blocker = new StandardRecordBlocker<>(new GameBlockingKeyByPublisherGenerator());
        blocker.setMeasureBlockSizes(true);
        blocker.collectBlockSizeData("data/output/debugResultsBlocking.csv", 100);

        // Initialize matching engine
        MatchingEngine<Game, Attribute> engine = new MatchingEngine<>();

        // Execute the matching
        logger.info("*\tRunning identity resolution\t*");
        Processable<Correspondence<Game, Attribute>> correspondences = engine.runIdentityResolution(
                dataSteam, dataVGSales, null, matchingRule, blocker);

        // Write correspondences to output file
        new CSVCorrespondenceFormatter().writeCSV(new File("data/output/steam_2_vgsales_correspondences.csv"), correspondences);

        // Evaluate the results
        logger.info("*\tEvaluating result\t*");
        MatchingEvaluator<Game, Attribute> evaluator = new MatchingEvaluator<>();
        Performance perfTest = evaluator.evaluateMatching(correspondences, gsTest);

        // Print evaluation results
        logger.info("Steam <-> VG Sales");
        logger.info(String.format("Precision: %.4f", perfTest.getPrecision()));
        logger.info(String.format("Recall: %.4f", perfTest.getRecall()));
        logger.info(String.format("F1: %.4f", perfTest.getF1()));
    }
}
