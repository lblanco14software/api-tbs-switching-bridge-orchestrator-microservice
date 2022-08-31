package com.novo.microservices.transactions.mappers.implementations;

import com.novo.microservices.components.helpers.SwitchKeyHelper;
import com.novo.microservices.dtos.requests.OrchestrationTransactionRequest;
import com.novo.microservices.tbs.utils.dtos.StandardTransaction;
import com.novo.microservices.transactions.mappers.contracts.IStandardTransactionCommonMapper;
import com.novo.microservices.transactions.mappers.contracts.IStandardTransactionMapper;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import postilion.realtime.sdk.message.bitmap.TransactionReference;

/**
 * StandardTransactionCommonMapper
 * <p>
 * StandardTransactionCommonMapper class.
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
@Component
public class StandardTransactionCommonMapper implements IStandardTransactionCommonMapper {

    private final IStandardTransactionMapper standardTransactionMapper;
    private final SwitchKeyHelper switchKeyHelper;

    public StandardTransactionCommonMapper(final IStandardTransactionMapper standardTransactionMapper,
                                           final SwitchKeyHelper switchKeyHelper) {
        this.standardTransactionMapper = standardTransactionMapper;
        this.switchKeyHelper = switchKeyHelper;
        log.debug("StandardTransactionCommonMapper loaded successfully");
    }

    @Override
    public StandardTransaction buildStandardTransaction(final OrchestrationTransactionRequest currentOrchestrationTransactionRequest) {
        final StandardTransaction standardTransaction = standardTransactionMapper.mapperStandardTransaction(currentOrchestrationTransactionRequest);

        // T00053991-164. de55 -> dataElement53
        if (StringUtils.isNotEmpty(currentOrchestrationTransactionRequest.getTransaction().getDe55())) {
            standardTransaction.setDataElement_53(StringUtils.rightPad(
                StringUtils.trimToEmpty(currentOrchestrationTransactionRequest.getTransaction().getDe55()), 96, "0"));
        }

        // T00053991-153. bankCode -> dataElement100
        standardTransaction.setDataElement_100(currentOrchestrationTransactionRequest.getPaymentHeader().getBankCode());

        // process TrackingId
        standardTransaction.setDataElement_127_2(switchKeyHelper.generateSwitchKey(standardTransaction));

        // process data element 127 40
        final TransactionReference transactionReference = new TransactionReference();
        transactionReference.addMatchingReference(currentOrchestrationTransactionRequest.getPaymentHeader().getTrackingId());
        standardTransaction.setDataElement_127_40(transactionReference.toMsgString());

        log.debug("buildStandardTransaction process, standardTransaction: {}", standardTransaction);

        return standardTransaction;
    }
}