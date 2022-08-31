package com.novo.microservices.components.configurations;

import com.novo.microservices.tbs.utils.dtos.commons.CommonBrokerConfiguration;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * MessageConfiguration
 * <p>
 * MessageConfiguration class.
 * <p>
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author duhernandez@novopayment.com
 * @since 3/18/2022
 */
@Configuration
@ConfigurationProperties(prefix = "broker-transactions")
@Data
public class MessageConfiguration {

    private CommonBrokerConfiguration customerInformationTransactionProducer;
    private CommonBrokerConfiguration cashDepositTransactionProducer;
    private CommonBrokerConfiguration reverseCashDepositTransactionProducer;
    private CommonBrokerConfiguration commissionQueryTransactionProducer;
    private CommonBrokerConfiguration reverseCardReplacementTransactionProducer;
    private CommonBrokerConfiguration cashWithdrawTransactionProducer;
    private CommonBrokerConfiguration mobileCashWithdrawTransactionProducer;
    private CommonBrokerConfiguration changePinTransactionProducer;
    private CommonBrokerConfiguration replaceCardTransactionProducer;
    private CommonBrokerConfiguration cashInitDepositTransactionProducer;
    private CommonBrokerConfiguration reverseInitDepositTransactionProducer;
    private CommonBrokerConfiguration reverseCashWithdrawTransactionProducer;
    private CommonBrokerConfiguration reverseWithdrawMobileMoneyTransactionProducer;
    private CommonBrokerConfiguration checkBalanceTransactionProducer;
    private CommonBrokerConfiguration reverseCheckBalanceTransactionProducer;
    private CommonBrokerConfiguration hsmGenerateDerivedTransactionProducer;
}
