package main.TestDataHandlers;

import main.Entity.TestFailPassRate;
import main.Entity.TestSummary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/testData")
@CrossOrigin
public class TestDataRestController {
    private static final Logger logger = LoggerFactory.getLogger(TestDataRestController.class);

    @Autowired
    TestDataServiceBean testDataServiceBean;

    @GetMapping(value= "/summary/enm/{period}", produces={"application/json","application/xml"})
    public ResponseEntity<Collection<TestSummary>> getAllTestSummaries(@PathVariable Long period) {
        logger.info("Executing Get on testData");
        Collection<TestSummary> result = testDataServiceBean.getAllTestSummaries(period);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping(value= "/summary/cna/{cna}/{period}",produces={"application/json","application/xml"})
    public ResponseEntity<Collection<TestSummary>> getTestSummaryByCNA(@PathVariable String cna, @PathVariable Long period) {
        logger.info("Executing Get on testData via cna");
        System.out.println(period);
        Collection<TestSummary> result = testDataServiceBean.getTestSummariesForCNA(cna, period);
        if(result.isEmpty()){
            Collection<TestSummary> empty = testDataServiceBean.returnMessage(cna, period);
            System.out.println(empty);
            return ResponseEntity.ok().body(empty);
        } else{
            return ResponseEntity.ok().body(result);
        }
    }
    @GetMapping(value= "/summary/product/{product}/{period}",produces={"application/json","application/xml"})
    public ResponseEntity<Collection<TestSummary>> getTestSummaryByProduct(@PathVariable String product, @PathVariable Long period) {
        logger.info("Executing Get on testData via product");
        Collection<TestSummary> result = testDataServiceBean.getTestSummariesForProduct(product, period);
        if(result.isEmpty()){
            Collection<TestSummary> empty = testDataServiceBean.returnMessage(product, period);
            System.out.println(empty);
            return ResponseEntity.ok().body(empty);
        } else{
            return ResponseEntity.ok().body(result);
        }
    }

    //testPassFailRate

    @GetMapping(value= "/testFailPassRate/enm", produces={"application/json","application/xml"})
    public ResponseEntity<Collection<TestFailPassRate>> getAllTestSummaries() {
        logger.info("Executing Get on testData");
        Collection<TestFailPassRate> result = testDataServiceBean.getAllTestPassFailRate();
        return ResponseEntity.ok().body(result);
    }

    @GetMapping(value= "/testFailPassRate/cna/{cna}",produces={"application/json","application/xml"})
    public ResponseEntity<Collection<TestFailPassRate>> getTestSummaryByCNA(@PathVariable String cna) {
        logger.info("Executing Get on testData via cna");
        Collection<TestFailPassRate> result = testDataServiceBean.getPassFailRateForCNA(cna);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping(value= "/testFailPassRate/product/{product}",produces={"application/json","application/xml"})
    public ResponseEntity<Collection<TestFailPassRate>> getTestPassFailRateByProduct(@PathVariable String product) {
        logger.info("Executing Get on testData via product");
        Collection<TestFailPassRate> result = testDataServiceBean.getPassFailRateForProduct(product);
        return ResponseEntity.ok().body(result);
    }
}
