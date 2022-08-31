package com.novo.microservices.transactions.constants;

import lombok.experimental.UtilityClass;

/**
 * OrchestratorTransactionConstants
 * <p>
 * OrchestratorTransactionConstants class.
 * <p>
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author lblanco@novopayment.com
 * @since 4/2/2022
 */
@UtilityClass
public class OrchestratorTransactionSagaConstants {

    public static final String ORCHESTRATOR_UNKNOWN_TRANSACTION_FORMAT = "UNKNOWN_TRANSACTION";

    // Check Customer Info Microservice Transactions
    public static final String ORCHESTRATOR_CHECK_CUSTOMER_INFO_TRANSACTION_FORMAT = "CUSTOMER_INFO_TRANSACTION";

    // Check commission query Microservice Transactions
    public static final String ORCHESTRATOR_COMMISSION_QUERY_TRANSACTION_FORMAT = "COMMISSION_QUERY_TRANSACTION";
    public static final String ORCHESTRATOR_COMMISSION_WITHDRAWAL_QUERY_TRANSACTION_FORMAT = "COMMISSION_WITHDRAWAL_QUERY_TRANSACTION";
    public static final String ORCHESTRATOR_COMMISSION_BALANCE_QUERY_TRANSACTION_FORMAT = "COMMISSION_BALANCE_QUERY_TRANSACTION";

    // Check Balance Microservice Transactions
    public static final String ORCHESTRATOR_CHECK_BALANCE_TRANSACTION_FORMAT = "CHECK_BALANCE_TRANSACTION";

    // Cash Deposit Microservice Transactions
    public static final String ORCHESTRATOR_CASH_DEPOSIT_TRANSACTION_FORMAT = "CASH_DEPOSIT_TRANSACTION";
    public static final String ORCHESTRATOR_REPLACE_CARD_TRANSACTION_FORMAT = "REPLACE_CARD_TRANSACTION";
    public static final String ORCHESTRATOR_CASH_INIT_DEPOSIT_TRANSACTION_FORMAT = "CASH_INIT_DEPOSIT_TRANSACTION";

    // Cash Withdraw Microservice Transactions
    public static final String ORCHESTRATOR_WITHDRAW_CASH_TRANSACTION_FORMAT = "WITHDRAW_CASH_TRANSACTION";
    public static final String ORCHESTRATOR_WITHDRAW_MOBILE_MONEY_TRANSACTION_FORMAT = "WITHDRAW_MOBILE_MONEY_TRANSACTION";

    // Cash Change Pin Microservice Transactions
    public static final String ORCHESTRATOR_CHANGE_PING_TRANSACTION_FORMAT = "CHANGE_PING_TRANSACTION";

    // Reverse Cash Deposit Microservice Transactions
    public static final String ORCHESTRATOR_REVERSE_DEPOSIT_CASH_TRANSACTION_FORMAT = "REVERSE_DEPOSIT_CASH_TRANSACTION";
    public static final String ORCHESTRATOR_REVERSE_CARD_REPLACEMENT_TRANSACTION_FORMAT = "REVERSE_CARD_REPLACEMENT_TRANSACTION";

    // Reverse Cash Withdrawal Microservice Transactions
    public static final String ORCHESTRATOR_REVERSE_WITHDRAW_CASH_TRANSACTION_FORMAT = "REVERSE_WITHDRAW_CASH_TRANSACTION";
    public static final String ORCHESTRATOR_REVERSE_WITHDRAW_MOBILE_MONEY_TRANSACTION_FORMAT = "REVERSE_WITHDRAW_MOBILE_MONEY_TRANSACTION";

    // Reverse Balance Inquiry Microservice Transactions
    public static final String ORCHESTRATOR_REVERSE_BALANCE_INQUIRY_TRANSACTION_FORMAT = "REVERSE_BALANCE_INQUIRY_TRANSACTION";
}