package com.novo.microservices.components.helpers;

import com.novo.microservices.MicroservicesBrokerTestConfiguration;
import com.novo.microservices.MicroservicesCommonTestConfiguration;
import com.novo.microservices.MicroservicesSagaTestConfiguration;
import com.novo.microservices.MicroservicesTestConfiguration;
import com.novo.microservices.dtos.base.BusinessResponse;
import com.novo.microservices.mocks.StandardTransactionResponseFixture;
import com.novo.microservices.tbs.utils.dtos.StandardTransaction;
import com.novo.microservices.utils.common.context.AppSessionContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
@Import({
    MicroservicesBrokerTestConfiguration.class,
    MicroservicesCommonTestConfiguration.class,
    MicroservicesSagaTestConfiguration.class,
    MicroservicesTestConfiguration.class,
    DocumentationMicroservice.class,
    BusinessResponse.class,
    ContextInformationService.class,
    AppSessionContext.class
})
class SwitchKeyHelperTest {

    private static final Logger logger = LogManager.getLogger(SwitchKeyHelperTest.class);

    @Autowired
    SwitchKeyHelper switchKeyHelper;

    @Test
    void truncateCharactersLenghtMinorToParameter() {
        String stringTruncate = "6x789a";

        String stringTest = switchKeyHelper.truncateCharacters(stringTruncate, 6);
        assertEquals(stringTruncate, stringTest);
    }

    @Test
    void truncateCharactersLenghtMajorToParameter() {
        String stringTruncate = "6x789axY631123";
        String stringExpected = "6x789a";

        String stringTest = switchKeyHelper.truncateCharacters(stringTruncate, 6);
        assertEquals(stringExpected, stringTest);
    }

    @Test
    void generateSwitchKey() {
        final StandardTransaction standardTransaction = new StandardTransactionResponseFixture().getInstance();

        standardTransaction.setMessageTypeIndicator("0400");
        standardTransaction.setDataElement_2("4513010000000043");
        standardTransaction.setDataElement_12("0100");
        standardTransaction.setDataElement_13("0200");
        standardTransaction.setDataElement_37("0300");

        String switchKeyExpected = "04000100020003006vPWqP";
        String switchKey = switchKeyHelper.generateSwitchKey(standardTransaction);

        assertEquals(switchKeyExpected, switchKey);
    }


    @Test
    void findPANwithDataElement2NotNull() {
        final StandardTransaction standardTransaction = new StandardTransactionResponseFixture().getInstance();
        String expectedPan = "4513010000000043";
        standardTransaction.setDataElement_2(expectedPan);
        standardTransaction.setDataElement_35(expectedPan);

        String pan = switchKeyHelper.findPAN(standardTransaction);
        assertEquals(expectedPan, pan);
    }

    @Test
    void findPANwithDataElement2Null() {
        final StandardTransaction standardTransaction = new StandardTransactionResponseFixture().getInstance();
        String expectedPan = "4513010000000043";
        standardTransaction.setDataElement_2("");
        standardTransaction.setDataElement_35(expectedPan);

        String pan = switchKeyHelper.findPAN(standardTransaction);
        assertEquals(expectedPan, pan);
    }

    @Test
    void findPANInDataElement35() {
        final StandardTransaction standardTransaction = new StandardTransactionResponseFixture().getInstance();
        standardTransaction.setDataElement_2("");
        String expectedPan = "4513010000000043";
        standardTransaction.setDataElement_35("4513010000000043");

        String pan = switchKeyHelper.findPANInDataElement35(standardTransaction.getDataElement_35());
        assertEquals(expectedPan, pan);
    }

    @Test
    void findPANInDataElement35WithSeparators() {
        final StandardTransaction standardTransaction = new StandardTransactionResponseFixture().getInstance();
        standardTransaction.setDataElement_2("");
        String expectedPan = "4572498119911390";
        standardTransaction.setDataElement_35("4572498119911390D26012010000057100000");

        String pan = switchKeyHelper.findPANInDataElement35(standardTransaction.getDataElement_35());

        logger.info(pan);

        assertEquals(expectedPan, pan);
    }
}