package com.novo.microservices.constants;

import lombok.experimental.UtilityClass;

/**
 * ProcessConstants
 * <p>
 * ProcessConstants class.
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO DE DESARROLLO DE
 * APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author lblanco@novopayment.com
 * @since 11/28/2020
 */
@UtilityClass
public class ProcessConstants {

    public static final String PROCESS_TYPE_STRING = "String";
    public static final String PARAM_COMPONENT_UUID = "String";
    public static final String PARAMETER_EMPTY_VALUE = "";
    public static final String PARAMETER_ACTUATOR_PATH_CONTAIN_VALUE = "actuator";

    public static final String HSM_GENERATE_KEY_HEADER_CONFIG = "0000";
    public static final String HSM_GENERATE_KEY_COMMAND_CONFIG = "A0";
    public static final String HSM_GENERATE_KEY_TRANSACTION_TYPE = "HSM_GENERATE_KEY";

    /***
     * controllers path
     */
    public static final String MICROSERVICE_PATH_CONTEXT = "";
    public static final String PROCESS_TRANSACTIONS_PATH = "/transactions";
    public static final String PROCESS_HSM_GENERATE_KEY_PATH = "/keys/ipek";
    public static final String GENERATE_CONCILIATION_REPORT_PATH = "/generate-conciliation-report/{date}";
    public static final String PROCESS_WARMUP_PATH = "/microservices-warmup";

    public static final String GENERATE_BILLING_REPORT_PATH = "/generate-billing-report/{dateFrom}/{dateTo}";
    public static final String RESYNC_ORCHESTRATOR_PATH = "/orchestrator/resync";


    public static final String ORCHESTRATOR_TRANSACTION_EXCEPTION = "Transaction not found process error, error in method factory";

    public static final int CHARACTER_TRUNCATE_32_LENGTH = 32;

    public static final String MICROSERVICES_ORCHESTRATOR_TRANSACTION_TIME_MILLISECONDS_CONFIG = "orchestrator.transaction.time.milliseconds";
    public static final String MICROSERVICES_ORCHESTRATOR_TRANSACTION_DELAY_MILLISECONDS_CONFIG = "orchestrator.transaction.delay.milliseconds";
    public static final String MICROSERVICES_ORCHESTRATOR_HSM_TIME_MILLISECONDS_CONFIG = "orchestrator.hsm.time.milliseconds";
    public static final String MICROSERVICES_ORCHESTRATOR_HSM_DELAY_MILLISECONDS_CONFIG = "orchestrator.hsm.delay.milliseconds";
    public static final String MICROSERVICES_CONFIG_NOT_FOUND = "configuration.not.found";
    public static final int MICROSERVICES_ORCHESTRATOR_TRANSACTION_TIME_MILLISECONDS_DEFAULT_VALUE = 10000;
    public static final int MICROSERVICES_ORCHESTRATOR_TRANSACTION_DELAY_MILLISECONDS_DEFAULT_VALUE = 500;

    public static final String MICROSERVICES_ORCHESTRATOR_HSM_CONFIGURATIONS_PATH = "orchestrator.hsm.configurations.path";
    public static final String MICROSERVICES_ORCHESTRATOR_TRANSACTIONS_CODES_PATH = "orchestrator.transactions.codes.configurations.path";
    public static final String MICROSERVICES_ORCHESTRATOR_TRANSACTIONS_MAPPINGS_PATH = "orchestrator.transactions.mappings.configurations.path";
    public static final String MICROSERVICES_ORCHESTRATOR_TRANSACTIONS_STRUCTURE_DATA_MAPPINGS_PATH = "orchestrator.transactions.structure.data.mappings.configurations.path";
    public static final String MICROSERVICES_ORCHESTRATOR_ENCRYPTION_KEY = "${orchestrator.encryption-key}";
    public static final String MICROSERVICES_ORCHESTRATOR_TRANSACTION_ENCRYPTION_EMPTY = "transaction.encryption.pending";
    public static final String MICROSERVICES_UUID_CONFIGURATION = "${entityUuid}";

    // orchestrator-transaction-response-consumer
    public static final String BROKER_ORCHESTRATOR_TRANSACTION_RESPONSE_QUEUE = "${broker-transactions.orchestrator-transaction-response-consumer.queue}";
    public static final String BROKER_ORCHESTRATOR_INFORMATION_TRANSACTION_RESPONSE_ROUTING_KEY = "${broker-transactions.orchestrator-transaction-response-consumer.routing-key}";
    public static final String ABSTRACT_FACTORY_TRANSACTION_COMPONENT_CONFIGURATION = "ORCHESTRATOR_TRANSACTION_FACTORY";
    public static final String MONGO_SAGA_ORCHESTRATOR_TRANSACTIONS_DOCUMENT = "saga-orchestrator-transactions";
    public static final String MONGO_HSM_GENERATE_KEY_CONFIGURATION = "saga-hsm-configuration";
    public static final String MONGO_SAGA_ORCHESTRATOR_TRANSACTIONS_REPORT_DOCUMENT = "saga-orchestrator-transactions-report";
    public static final String MONGO_SAGA_ORCHESTRATOR_HSM_REPORT_DOCUMENT = "saga-orchestrator-hsm-report";

    public static final String TRANSACTION_DATA_ELEMENT_35_REGEXP_SEPARATOR = "[^0-9]*([0-9]+).*";

    public static final String BROKER_HSM_GENERATE_DERIVED_RESPONSE_CONSUMER_QUEUE = "${broker-transactions.hsm-generate-derived-transaction-response-consumer.queue}";
    public static final String BROKER_HSM_GENERATE_DERIVED_RESPONSE_CONSUMER_ROUTING_KEY = "${broker-transactions.hsm-generate-derived-transaction-response-consumer.routing-key}";
    public static final String BROKER_HSM_GENERATE_DERIVED_TRANSACTION_PRODUCER_ROUTING_KEY = "${broker-transactions.hsm-generate-derived-transaction-producer.routing-key}";

    public static final String ORCHESTRATOR_UNIDENTIFIED_TRANSACTION_METHOD_CONTEXT = "UNIDENTIFIED_TRANSACTION";
}
