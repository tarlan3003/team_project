package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution;

import java.io.File;

import org.slf4j.Logger;

import de.uni.mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.SteamGame;
import de.uni.mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.SteamGameXMLReader;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.GamePublisherComparatorJaccard;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.GamePublisherComparatorJaccardLowercase;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.GamePublisherComparatorLevenshtein;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.GameTitleComparatorEqual;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.GameTitleComparatorJaccard;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.GameTitleComparatorLevenshtein;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.ReleaseYearComparatorFive;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.ReleaseYearComparatorOne;
import de.uni_mannheim.informatik.dws.winter.matching.MatchingEngine;
import de.uni_mannheim.informatik.dws.winter.matching.MatchingEvaluator;
import de.uni_mannheim.informatik.dws.winter.matching.algorithms.RuleLearner;
import de.uni_mannheim.informatik.dws.winter.matching.blockers.NoBlocker;
import de.uni_mannheim.informatik.dws.winter.matching.rules.WekaMatchingRule;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.HashedDataSet;
import de.uni_mannheim.informatik.dws.winter.model.MatchingGoldStandard;
import de.uni_mannheim.informatik.dws.winter.model.Performance;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.model.io.CSVCorrespondenceFormatter;
import de.uni_mannheim.informatik.dws.winter.processing.Processable;
import de.uni_mannheim.informatik.dws.winter.utils.WinterLogManager;

public class IR_using_linear_combination {
   private static final Logger logger = WinterLogManager.activateLogger("default");

   public static void main(String[] args) throws Exception {
      logger.info("*\tLoading datasets\t*");
      HashedDataSet<SteamGame, Attribute> dataSteam = new HashedDataSet<>();
      (new SteamGameXMLReader()).loadFromXML(new File("C:/Users/HP/Downloads/WDI Project/steam.xml"), "/games/game", dataSteam);
      HashedDataSet<SteamGame, Attribute> dataVGSales = new HashedDataSet<>();
      (new SteamGameXMLReader()).loadFromXML(new File("C:/Users/HP/Downloads/WDI Project/vgsales.xml"), "/games/game", dataVGSales);
      
      MatchingGoldStandard gsTraining = new MatchingGoldStandard();
      gsTraining.loadFromCSVFile(new File("C:/Users/HP/Downloads/WDI Project/goldstandard/gs_steam_2_vgsales_train.csv"));
      
      String[] options = new String[]{"-S"};
      String modelType = "SimpleLogistic";
      WekaMatchingRule<SteamGame, Attribute> matchingRule = new WekaMatchingRule(0.7D, modelType, options);
      matchingRule.activateDebugReport("data/output/debugResultsMatchingRule.csv", 1000, gsTraining);
      matchingRule.addComparator(new GameTitleComparatorEqual());
      matchingRule.addComparator(new GamePublisherComparatorJaccard());
      matchingRule.addComparator(new GamePublisherComparatorJaccardLowercase());
      matchingRule.addComparator(new GamePublisherComparatorLevenshtein());
      matchingRule.addComparator(new GameTitleComparatorLevenshtein());
      matchingRule.addComparator(new GameTitleComparatorJaccard());
      matchingRule.addComparator(new ReleaseYearComparatorFive());
      matchingRule.addComparator(new ReleaseYearComparatorOne());

      logger.info("*\tLearning matching rule\t*");
      RuleLearner<SteamGame, Attribute> learner = new RuleLearner();
      learner.learnMatchingRule(dataSteam, dataVGSales, (Processable)null, matchingRule, gsTraining);
      logger.info(String.format("Matching rule is:\n%s", matchingRule.getModelDescription()));

      NoBlocker<SteamGame, Attribute> blocker = new NoBlocker<>();
      MatchingEngine<SteamGame, Attribute> engine = new MatchingEngine();
      
      logger.info("*\tRunning identity resolution\t*");
      Processable<Correspondence<SteamGame, Attribute>> correspondences = engine.runIdentityResolution(dataSteam, dataVGSales, (Processable)null, matchingRule, blocker);
      (new CSVCorrespondenceFormatter()).writeCSV(new File("data/output/steam_games_2_vgsales_correspondences.csv"), correspondences);

      logger.info("*\tLoading gold standard\t*");
      MatchingGoldStandard gsTest = new MatchingGoldStandard();
      gsTest.loadFromCSVFile(new File("C:/Users/HP/Downloads/WDI Project/goldstandard/gs_steam_2_vgsales_test.csv"));

      logger.info("*\tEvaluating result\t*");
      MatchingEvaluator<SteamGame, Attribute> evaluator = new MatchingEvaluator();
      Performance perfTest = evaluator.evaluateMatching(correspondences, gsTest);
      logger.info("Steam Games <-> VG Sales");
      logger.info(String.format("Precision: %.4f", perfTest.getPrecision()));
      logger.info(String.format("Recall: %.4f", perfTest.getRecall()));
      logger.info(String.format("F1: %.4f", perfTest.getF1()));
   }
}
