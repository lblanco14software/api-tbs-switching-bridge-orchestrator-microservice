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
public class OrchestratorTransactionConstants {

    public static final String ORCHESTRATOR_UNKNOWN_TRANSACTION = "UNKNOWN_TRANSACTION";

    // Check Customer Info Microservice Transactions
    public static final String ORCHESTRATOR_CHECK_CUSTOMER_INFO_TRANSACTION_CODE = "orchestrator.customer.info.transaction";

    // Check commission query Microservice Transactions
    public static final String ORCHESTRATOR_COMMISSION_QUERY_TRANSACTION_CODE = "orchestrator.commission.query.transaction";
    public static final String ORCHESTRATOR_COMMISSION_WITHDRAWAL_QUERY_TRANSACTION_CODE = "orchestrator.commission.withdrawal.query.transaction";
    public static final String ORCHESTRATOR_COMMISSION_BALANCE_QUERY_TRANSACTION_CODE = "orchestrator.commission.balance.query.transaction";
    public static final String ORCHESTRATOR_COMMISSION_REPLACE_CARD_QUERY_TRANSACTION_CODE = "orchestrator.commission.replace.card.query.transaction";

    // Check Balance Microservice Transactions
    public static final String ORCHESTRATOR_CHECK_BALANCE_TRANSACTION_CODE = "orchestrator.check.balance.transaction";

    // Cash Deposit Microservice Transactions
    public static final String ORCHESTRATOR_CASH_DEPOSIT_TRANSACTION_CODE = "orchestrator.cash.deposit.transaction";
    public static final String ORCHESTRATOR_REPLACE_CARD_TRANSACTION_CODE = "orchestrator.replace.card.transaction";
    public static final String ORCHESTRATOR_CASH_INIT_DEPOSIT_TRANSACTION_CODE = "orchestrator.cash.init.deposit.transaction";


    // Cash Withdraw Microservice Transactions
    public static final String ORCHESTRATOR_CASH_WITHDRAW_TRANSACTION_CODE = "orchestrator.cash.withdraw.transaction";
    public static final String ORCHESTRATOR_MOBILE_CASH_WITHDRAW_TRANSACTION_CODE = "orchestrator.mobile.cash.withdraw.transaction";

    // Cash Change Pin Microservice Transactions
    public static final String ORCHESTRATOR_CHANGE_PING_TRANSACTION_CODE = "orchestrator.change.ping.transaction";

    // Reverse Cash Deposit Microservice Transactions
    public static final String ORCHESTRATOR_REVERSE_CASH_DEPOSIT_TRANSACTION_CODE = "orchestrator.reverse.cash.deposit.transaction";
    public static final String ORCHESTRATOR_REVERSE_CARD_REPLACEMENT_TRANSACTION_CODE = "orchestrator.reverse.card.replacement.transaction";
    public static final String ORCHESTRATOR_REVERSE_INIT_DEPOSIT_TRANSACTION_CODE = "orchestrator.reverse.init.deposit.cash.transaction";

    // Reverse Cash Withdrawal Microservice Transactions
    public static final String ORCHESTRATOR_REVERSE_WITHDRAW_CASH_TRANSACTION_CODE = "orchestrator.reverse.withdraw.cash.transaction";
    public static final String ORCHESTRATOR_REVERSE_WITHDRAW_MOBILE_MONEY_TRANSACTION_CODE = "orchestrator.reverse.withdraw.mobile.money.transaction";

    // Reverse Balance Inquiry Microservice Transactions
    public static final String ORCHESTRATOR_REVERSE_CHECK_BALANCE_TRANSACTION_CODE = "orchestrator.reverse.balance.inquiry.transaction";

    // Reverse Balance Inquiry Microservice Transactions
    public static final String ORCHESTRATOR_HSM_GENERATE_KEY_TRANSACTION_CODE = "orchestrator.hsm.generate.key";
}