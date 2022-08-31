package com.novo.microservices.transactions.bases;

import com.google.gson.Gson;
import com.novo.microservices.components.configurations.MessageConfiguration;
import com.novo.microservices.components.enums.ValidateResult;
import com.novo.microservices.dtos.OrchestratorValidationResult;
import com.novo.microservices.dtos.custom.CustomPaymentInformation;
import com.novo.microservices.dtos.custom.CustomTransactionInformation;
import com.novo.microservices.dtos.requests.OrchestrationTransactionRequest;
import com.novo.microservices.dtos.responses.OrchestrationTransactionResponse;
import com.novo.microservices.tbs.utils.dtos.StandardTransaction;
import com.novo.microservices.tbs.utils.dtos.commons.CommonBrokerConfiguration;
import com.novo.microservices.transactions.contracts.IOrchestratorTransaction;
import com.novo.microservices.transactions.enums.CustomTransactionResponses;
import com.novo.microservices.transactions.mappers.contracts.ICustomTransactionCommonMapper;
import com.novo.microservices.transactions.mappers.contracts.IStandardTransactionCommonDefaultValuesMapper;
import com.novo.microservices.transactions.mappers.contracts.IStandardTransactionCommonMapper;
import com.novo.microservices.transactions.mappers.contracts.IStandardTransactionStructureDataMapper;
import com.novo.microservices.transactions.validations.contracts.IStandardTransactionValidation;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.StringUtils;
import postilion.realtime.sdk.message.bitmap.StructuredData;
import postilion.realtime.sdk.message.bitmap.TransactionReference;

import static com.novo.microservices.transactions.constants.OrchestratorCustomResponsesCodes.CUSTOM_RESPONSE_STATUS_COMPLETED_CODE;
import static com.novo.microservices.transactions.constants.OrchestratorCustomResponsesCodes.CUSTOM_RESPONSE_STATUS_DECLINED_CODE;
import static com.novo.microservices.transactions.constants.OrchestratorTransactionFieldsConstants.ORCHESTRATOR_REQUEST_TRANSACTION_FIELD_NAME_DE_35;

/**
 * OrchestratorBaseTransaction
 * <p>
 * OrchestratorBaseTransaction class.
 * <p>
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author lblanco@novopayment.com
 * @since 4/4/2022
 */
@Log4j2
public abstract class OrchestratorBaseTransaction implements IOrchestratorTransaction {

    private StandardTransaction currentStandardTransaction;
    private final String transactionId;
    protected final IStandardTransactionCommonMapper standardTransactionCommonMapper;
    protected final IStandardTransactionCommonDefaultValuesMapper standardTransactionDefaultValuesMapper;
    protected final IStandardTransactionStructureDataMapper standardTransactionStructureDataMapper;
    protected final ICustomTransactionCommonMapper customTransactionCommonMapper;
    protected final IStandardTransactionValidation standardTransactionValidation;
    protected final MessageConfiguration messageConfiguration;

    protected OrchestratorBaseTransaction(final IStandardTransactionCommonMapper standardTransactionCommonMapper,
                                          final IStandardTransactionCommonDefaultValuesMapper standardTransactionDefaultValuesMapper,
                                          final IStandardTransactionStructureDataMapper standardTransactionStructureDataMapper,
                                          final ICustomTransactionCommonMapper customTransactionCommonMapper,
                                          final IStandardTransactionValidation standardTransactionValidation,
                                          final MessageConfiguration messageConfiguration,
                                          final String transactionId) {

        this.standardTransactionCommonMapper = standardTransactionCommonMapper;
        this.standardTransactionDefaultValuesMapper = standardTransactionDefaultValuesMapper;
        this.standardTransactionStructureDataMapper = standardTransactionStructureDataMapper;
        this.customTransactionCommonMapper = customTransactionCommonMapper;
        this.standardTransactionValidation = standardTransactionValidation;
        this.messageConfiguration = messageConfiguration;
        this.transactionId = transactionId;
    }

    /***
     * metodo que retorna una instancia de la clase {@link StandardTransaction} retorna la respuesta establecida para postilion
     * @param currentOrchestrationTransactionRequest transacción original enviada por el cliente
     * @return {@link StandardTransaction} retorna la transaccion estandar de cara a Postilion
     */
    public StandardTransaction buildStandardTransaction(final OrchestrationTransactionRequest currentOrchestrationTransactionRequest) {

        final StandardTransaction standardTransaction = standardTransactionCommonMapper.buildStandardTransaction(currentOrchestrationTransactionRequest);

        final StructuredData structuredData = buildCommonStructuredData(currentOrchestrationTransactionRequest);

        // set data element 127_22
        standardTransaction.setDataElement_127_22(structuredData.toMsgString());

        if (isReverseTransaction()) {
            if (StringUtils.isNoneEmpty(currentOrchestrationTransactionRequest.getTransaction().getDe35())) {
                structuredData.put(ORCHESTRATOR_REQUEST_TRANSACTION_FIELD_NAME_DE_35, currentOrchestrationTransactionRequest.getTransaction().getDe35());
            }
            // set data element 127_2
            standardTransaction.setDataElement_127_22(structuredData.toMsgString());

            // assign original transaction to reverse and assign to field 127_40
            final TransactionReference transactionReference = new TransactionReference();
            transactionReference.addOriginalReference(currentOrchestrationTransactionRequest.getPaymentHeader().getTrackingId());
            standardTransaction.setDataElement_127_40(transactionReference.toMsgString());
        }

        // apply default values defined in the mongoDB configuration
        standardTransactionDefaultValuesMapper.applyDefaultValuesToRequestTransaction(currentOrchestrationTransactionRequest.getPaymentHeader().getServiceId(),
            currentOrchestrationTransactionRequest.getTransaction().getMessageTypeIndicator(),
            currentOrchestrationTransactionRequest.getTransaction().getDe3(), // Processing Code
            currentOrchestrationTransactionRequest.getPaymentHeader().getBankCode(),
            standardTransaction);

        // apply custom values defined by transaction type
        addCustomRequestMapping(currentOrchestrationTransactionRequest, standardTransaction, structuredData);

        // set current standard transaction
        this.writeLogTransactionInformation(standardTransaction);

        currentStandardTransaction = standardTransaction;

        return standardTransaction;
    }

    protected void addCustomRequestMapping(final OrchestrationTransactionRequest currentOrchestrationTransactionRequest,
                                           final StandardTransaction standardTransaction,
                                           final StructuredData structuredData) {
        // do nothing
    }

    protected boolean isReverseTransaction() {
        return false;
    }

    /***
     * metodo que procesa la respuesta obtenida por parte de postilion y la procesa de cara al cliente
     * method that processes the response obtained from postilion and processes it for the client
     * @param currentOrchestrationTransactionRequest request original de la transaccion se requiere para transferir algunos campos originales
     * @param standardTransactionResponse response obtenido por parte del core postilion se requiere para setear los campos especificos de las respuesta al cliente
     * @return {@link OrchestrationTransactionResponse} retorna la respuesta establecida con el cliente
     */
    public OrchestrationTransactionResponse buildSuccessfullyTransactionResponse(final OrchestrationTransactionRequest currentOrchestrationTransactionRequest, final StandardTransaction standardTransactionResponse) {
        // apply default values defined in the mongoDB configuration
        standardTransactionDefaultValuesMapper.applyDefaultValuesToRequestTransaction(currentOrchestrationTransactionRequest.getPaymentHeader().getServiceId(),
            currentOrchestrationTransactionRequest.getTransaction().getMessageTypeIndicator(),
            currentOrchestrationTransactionRequest.getTransaction().getDe3(), // Processing Code
            currentOrchestrationTransactionRequest.getPaymentHeader().getBankCode(),
            standardTransactionResponse);

        final CustomTransactionInformation customTransactionInformation = customTransactionCommonMapper.buildCustomTransactionInformation(
            currentOrchestrationTransactionRequest,
            standardTransactionResponse
        );

        final CustomTransactionResponses customTransactionResponses = CustomTransactionResponses.findByCustomResponseCode(CUSTOM_RESPONSE_STATUS_COMPLETED_CODE);
        return OrchestrationTransactionResponse.builder()
            .transaction(customTransactionInformation)
            .paymentResponse(CustomPaymentInformation.builder()
                .responseStatus(customTransactionResponses.getCustomResponseCode())
                .responseDescription(customTransactionResponses.getCustomResponseDescription())
                .trackingId(currentOrchestrationTransactionRequest.getPaymentHeader().getTrackingId())
                .serviceId(currentOrchestrationTransactionRequest.getPaymentHeader().getServiceId())
                .build()
            )
            .build();
    }

    /***
     * metodo que retorna la respuesta establecida con el cliente en caso de error
     * @param currentOrchestrationTransactionRequest request original de la transaccion se requiere para transferir algunos campos originales
     * @return {@link OrchestrationTransactionResponse} retorna la respuesta establecida con el cliente
     */
    public OrchestrationTransactionResponse buildErrorTransactionResponse(OrchestrationTransactionRequest currentOrchestrationTransactionRequest) {

        final CustomTransactionResponses customTransactionResponses = CustomTransactionResponses.findByCustomResponseCode(CUSTOM_RESPONSE_STATUS_DECLINED_CODE);
        return OrchestrationTransactionResponse.builder()
            .paymentResponse(CustomPaymentInformation.builder()
                .responseStatus(customTransactionResponses.getCustomResponseCode())
                .responseDescription(customTransactionResponses.getCustomResponseDescription())
                .trackingId(currentOrchestrationTransactionRequest.getPaymentHeader().getTrackingId())
                .serviceId(currentOrchestrationTransactionRequest.getPaymentHeader().getServiceId())
                .build()
            ).build();
    }

    /***
     * metodo construye el StructuredData comun para todas las transacciones, el StructuredData se asigna en el campo
     * en caso de que se requiera una customización por transaccion se debe sobre escribir el metodo en la transacción concreta
     * @param currentOrchestrationTransactionRequest request original de la transaccion se requiere para transferir algunos campos originales
     * @return {@link StructuredData} clase que contiene el StructuredData estandard definido para postilion se usa para enviar
     */
    protected StructuredData buildCommonStructuredData(final OrchestrationTransactionRequest currentOrchestrationTransactionRequest) {
        return standardTransactionStructureDataMapper.buildCommonStructuredData(currentOrchestrationTransactionRequest);
    }

    /***
     * metodo que valida la StandardTransaction y valida que los campos minimos requeridos estan encendidos
     * @param standardTransactionRequest retorna un instancia de la clase {@link OrchestratorValidationResult} con el resultado de la validacion
     * en caso de que se requiera una customización por transaccion se debe sobre escribir el metodo en la transacción concreta
     * @return {@link OrchestratorValidationResult} clase que contiene el estado de la validacion
     */
    public OrchestratorValidationResult validateStandardTransactionRequest(final StandardTransaction standardTransactionRequest) {

        final OrchestratorValidationResult orchestratorValidationResult = standardTransactionValidation.validateFields(standardTransactionRequest);

        if (ValidateResult.NOT_VALID.equals(orchestratorValidationResult.getValidateResult())) {
            log.error("error in validateStandardTransactionRequest the additional field required are dataElement_25 and dataElement_123");
        }

        log.debug("validateStandardTransactionRequest for OrchestratorBaseTransaction, OrchestratorValidationResult: {}", orchestratorValidationResult);
        return orchestratorValidationResult;
    }

    /***
     * retorna la transaccion construida de cara al request para el proceso saga
     * @return {@link StructuredData} clase que contiene el StructuredData estandard definido para postilion se usa para enviar
     */
    public StandardTransaction getCurrentStandardTransactionRequest() {
        return this.currentStandardTransaction;
    }

    /***
     * metodo de control que dispara una excepcion en caso de que la transacción no tenga implementado el broker configuration producer
     * @return  {@link NotImplementedException} dispara una excepcion de cara al saga en caso de que no este implementado en la transacción
     */
    public CommonBrokerConfiguration getBrokerConfigurationProducer() {
        throw new NotImplementedException("broker configuration producer not implemented by current transaction");
    }

    /***
     * metodo que registra en logs la transaccion construida de cara al request para postilion
     */
    private void writeLogTransactionInformation(final StandardTransaction standardTransactionRequest) {
        log.info("StandardTransaction by transaction type: [ {} ]", transactionId);
        log.debug("StandardTransaction by transaction type: [ {} ], value: {}", transactionId, new Gson().toJson(standardTransactionRequest));
    }
}