package com.novo.microservices.components.helpers;

import com.novo.microservices.constants.ProcessConstants;
import com.novo.microservices.services.contracts.IOrchestratorEncryptionService;
import com.novo.microservices.tbs.utils.dtos.StandardTransaction;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Log4j2
@Component
public class SwitchKeyHelper {

    private final IOrchestratorEncryptionService orchestratorEncryptionService;

    public SwitchKeyHelper(IOrchestratorEncryptionService orchestratorEncryptionService) {
        this.orchestratorEncryptionService = orchestratorEncryptionService;
    }

    public String generateSwitchKey(StandardTransaction standardTransaction) {
        return standardTransaction.getMessageTypeIndicator()
            + standardTransaction.getDataElement_12()
            + standardTransaction.getDataElement_13()
            + standardTransaction.getDataElement_37()
            + truncateCharacters(orchestratorEncryptionService.doOnEncryptData(findPAN(standardTransaction)), 6);

    }

    public String truncateCharacters(final String currentValue, final int characterTruncateLength) {
        if (currentValue.length() > characterTruncateLength) {
            return currentValue.substring(0, characterTruncateLength);
        }
        return currentValue;
    }

    public String findPAN(StandardTransaction standardTransaction) {
        return standardTransaction.getDataElement_2() != null ?
            validatePANBlankOrEmpty(standardTransaction.getDataElement_2(), standardTransaction.getDataElement_35()) :
            findPANInDataElement35(standardTransaction.getDataElement_35());
    }

    public String validatePANBlankOrEmpty(String pan, String dataElement35) {
        return !pan.isBlank() && !pan.isEmpty() ? pan : findPANInDataElement35(dataElement35);
    }

    public String findPANInDataElement35(String dataElement35) {
        String pan = findPANInDataElement35WithSeparators(dataElement35, ProcessConstants.TRANSACTION_DATA_ELEMENT_35_REGEXP_SEPARATOR);
        return pan.equals("") ? dataElement35 : pan;
    }

    public String findPANInDataElement35WithSeparators(String dataElement35, String regExp) {
        Matcher m = Pattern.compile(regExp).matcher(dataElement35);
        if (m.matches()) {
            return m.group(1);
        }
        return "";
    }
}
