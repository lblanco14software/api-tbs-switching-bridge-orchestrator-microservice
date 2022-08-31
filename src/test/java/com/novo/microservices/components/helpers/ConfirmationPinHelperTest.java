package com.novo.microservices.components.helpers;

import com.novo.microservices.mocks.OrchestrationTransactionRequestFixture;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ConfirmationPinHelperTest {

    @Test
    void testPinConfirmation() {
        var request = new OrchestrationTransactionRequestFixture().getInstance();
        request.getTransaction().setDe63("& 0000800426! B200158 7FF900008080800080009600599693F889B40000000000000000000000001C0000024844842205130095B110D2000706010A03A0900000000000000000000000000000000000000000000000000000! B300080 4B004021576100E0B0C800000000000022008D1E03000007A0000000031010000000000000000000! Q200002 03! C400012 000000021080! B400020 051510011E0300     0! 0600032 2130FFBC1A5DF22D38A2F30CC15120CB! KS00040 016E3658290000000002016E3658290000000004");
        String expectedConfirmationPin = getPinConfirmation(request.getTransaction().getDe55(), "2130FFBC1A5DF22D");

        Assertions.assertEquals(expectedConfirmationPin, ConfirmationPinHelper.getConfirmationPin(request));
    }

    @Test
    void testPinConfirmationWithoutToken06() {
        var request = new OrchestrationTransactionRequestFixture().getInstance();
        request.getTransaction().setDe63("& 0000800426! B200158 7FF900008080800080009600599693F889B40000000000000000000000001C0000024844842205130095B110D2000706010A03A0900000000000000000000000000000000000000000000000000000! B300080 4B004021576100E0B0C800000000000022008D1E03000007A0000000031010000000000000000000! Q200002 03! C400012 000000021080! B400020 051510011E0300     0! KS00040 016E3658290000000002016E3658290000000004");
        String expectedConfirmationPin = getPinConfirmation(request.getTransaction().getDe55(), "");

        Assertions.assertEquals(expectedConfirmationPin, ConfirmationPinHelper.getConfirmationPin(request));
    }

    @Test
    void testPinConfirmationWithMultipleToken06() {
        var request = new OrchestrationTransactionRequestFixture().getInstance();
        request.getTransaction().setDe63("& 0000800426! 0600032 8899FFBC1A5DF22D38A2F30CC15120CB otracosa ! 0600032 1933FFBC1A5DF22D38A2F30CC15120CB");
        String expectedConfirmationPin = getPinConfirmation(request.getTransaction().getDe55(), "");

        Assertions.assertEquals(expectedConfirmationPin, ConfirmationPinHelper.getConfirmationPin(request));
    }

    @Test
    void testPinConfirmationWithInvalidToken06() {
        var request = new OrchestrationTransactionRequestFixture().getInstance();
        request.getTransaction().setDe63("& 0000800426! 0600002 1234 otracosa");
        String expectedConfirmationPin = getPinConfirmation(request.getTransaction().getDe55(), "");

        Assertions.assertEquals(expectedConfirmationPin, ConfirmationPinHelper.getConfirmationPin(request));
    }

    @Test
    void testPinConfirmationWithNullToken06() {
        var request = new OrchestrationTransactionRequestFixture().getInstance();
        request.getTransaction().setDe63(null);
        String expectedConfirmationPin = getPinConfirmation(request.getTransaction().getDe55(), "");

        Assertions.assertEquals(expectedConfirmationPin, ConfirmationPinHelper.getConfirmationPin(request));
    }

    private String getPinConfirmation(String de55, String token) {
        String de53 = String.format("%s%s%s",
            "01",
            token,
            StringUtils.trimToEmpty(de55));

        return StringUtils.rightPad(de53, 96, "0");
    }
}