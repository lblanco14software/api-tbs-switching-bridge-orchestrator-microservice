package com.novo.microservices.mocks;

import com.novo.microservices.dtos.custom.CustomTransactionInformation;
import com.novo.microservices.dtos.custom.PaymentHeaderInformation;
import com.novo.microservices.dtos.requests.OrchestrationTransactionRequest;
import com.novo.microservices.tbs.utils.dtos.StandardTransaction;
import com.novo.microservices.transactions.mappers.contracts.IStandardTransactionMapper;
import org.springframework.stereotype.Component;

import javax.annotation.processing.Generated;

/**
 * StandardTransactionMapperMockImpl
 * <p>
 * StandardTransactionMapperMockImpl class.
 * <p>
 * <p>
 * ESTE COMPONENTE FUE CONSTRUIDO SIGUIENDO LOS ESTANDARES DE DESARROLLO Y EL PROCEDIMIENTO
 * DE DESARROLLO DE APLICACIONES DE NOVOPAYMENT Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE
 * PROPIEDAD INTELECTUAL Y DERECHOS DE AUTOR.
 *
 * @author Novopayment Inc.
 * @author lblanco@novopayment.com
 * @since 4/10/2022
 */

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-04-10T23:23:26-0500",
    comments = "version: 1.5.0.RC1, compiler: javac, environment: Java 11 (Oracle Corporation)"
)
@Component
public class StandardTransactionMapperMockImpl implements IStandardTransactionMapper {

    @Override
    public StandardTransaction mapperStandardTransaction(OrchestrationTransactionRequest orchestrationTransactionRequest) {
        if (orchestrationTransactionRequest == null) {
            return null;
        }

        StandardTransaction.StandardTransactionBuilder standardTransaction = StandardTransaction.builder();

        standardTransaction.dataElement_127_2(orchestrationTransactionRequestPaymentHeaderTrackingId(orchestrationTransactionRequest));
        standardTransaction.messageTypeIndicator(orchestrationTransactionRequestTransactionMessageTypeIndicator(orchestrationTransactionRequest));
        standardTransaction.dataElement_100(orchestrationTransactionRequestPaymentHeaderBankCode(orchestrationTransactionRequest));
        standardTransaction.dataElement_2(orchestrationTransactionRequestTransactionDe2(orchestrationTransactionRequest));
        standardTransaction.dataElement_3(orchestrationTransactionRequestTransactionDe3(orchestrationTransactionRequest));
        standardTransaction.dataElement_4(orchestrationTransactionRequestTransactionDe4(orchestrationTransactionRequest));
        standardTransaction.dataElement_7(orchestrationTransactionRequestTransactionDe7(orchestrationTransactionRequest));
        standardTransaction.dataElement_11(orchestrationTransactionRequestTransactionDe11(orchestrationTransactionRequest));
        standardTransaction.dataElement_12(orchestrationTransactionRequestTransactionDe12(orchestrationTransactionRequest));
        standardTransaction.dataElement_13(orchestrationTransactionRequestTransactionDe13(orchestrationTransactionRequest));
        standardTransaction.dataElement_18(orchestrationTransactionRequestTransactionDe18(orchestrationTransactionRequest));
        standardTransaction.dataElement_22(orchestrationTransactionRequestTransactionDe22(orchestrationTransactionRequest));
        standardTransaction.dataElement_32(orchestrationTransactionRequestTransactionDe32(orchestrationTransactionRequest));
        standardTransaction.dataElement_35(orchestrationTransactionRequestTransactionDe35(orchestrationTransactionRequest));
        standardTransaction.dataElement_37(orchestrationTransactionRequestTransactionDe37(orchestrationTransactionRequest));
        standardTransaction.dataElement_41(orchestrationTransactionRequestTransactionDe41(orchestrationTransactionRequest));
        standardTransaction.dataElement_42(orchestrationTransactionRequestTransactionDe42(orchestrationTransactionRequest));
        standardTransaction.dataElement_43(orchestrationTransactionRequestTransactionDe43(orchestrationTransactionRequest));
        standardTransaction.dataElement_49(orchestrationTransactionRequestTransactionDe49(orchestrationTransactionRequest));

        return standardTransaction.build();
    }

    @Override
    public StandardTransaction mapperReverseTransaction(OrchestrationTransactionRequest orchestrationTransactionRequest, StandardTransaction standardTransaction) {
        if (orchestrationTransactionRequest == null) {
            return standardTransaction;
        }

        standardTransaction.setDataElement_90(orchestrationTransactionRequestTransactionDe90(orchestrationTransactionRequest));

        return standardTransaction;
    }

    private String orchestrationTransactionRequestPaymentHeaderTrackingId(OrchestrationTransactionRequest orchestrationTransactionRequest) {
        if (orchestrationTransactionRequest == null) {
            return null;
        }
        PaymentHeaderInformation paymentHeader = orchestrationTransactionRequest.getPaymentHeader();
        if (paymentHeader == null) {
            return null;
        }
        String trackingId = paymentHeader.getTrackingId();
        if (trackingId == null) {
            return null;
        }
        return trackingId;
    }

    private String orchestrationTransactionRequestTransactionMessageTypeIndicator(OrchestrationTransactionRequest orchestrationTransactionRequest) {
        if (orchestrationTransactionRequest == null) {
            return null;
        }
        CustomTransactionInformation transaction = orchestrationTransactionRequest.getTransaction();
        if (transaction == null) {
            return null;
        }
        String messageTypeIndicator = transaction.getMessageTypeIndicator();
        if (messageTypeIndicator == null) {
            return null;
        }
        return messageTypeIndicator;
    }

    private String orchestrationTransactionRequestPaymentHeaderBankCode(OrchestrationTransactionRequest orchestrationTransactionRequest) {
        if (orchestrationTransactionRequest == null) {
            return null;
        }
        PaymentHeaderInformation paymentHeader = orchestrationTransactionRequest.getPaymentHeader();
        if (paymentHeader == null) {
            return null;
        }
        String bankCode = paymentHeader.getBankCode();
        if (bankCode == null) {
            return null;
        }
        return bankCode;
    }

    private String orchestrationTransactionRequestTransactionDe2(OrchestrationTransactionRequest orchestrationTransactionRequest) {
        if (orchestrationTransactionRequest == null) {
            return null;
        }
        CustomTransactionInformation transaction = orchestrationTransactionRequest.getTransaction();
        if (transaction == null) {
            return null;
        }
        String de2 = transaction.getDe2();
        if (de2 == null) {
            return null;
        }
        return de2;
    }

    private String orchestrationTransactionRequestTransactionDe3(OrchestrationTransactionRequest orchestrationTransactionRequest) {
        if (orchestrationTransactionRequest == null) {
            return null;
        }
        CustomTransactionInformation transaction = orchestrationTransactionRequest.getTransaction();
        if (transaction == null) {
            return null;
        }
        String de3 = transaction.getDe3();
        if (de3 == null) {
            return null;
        }
        return de3;
    }

    private String orchestrationTransactionRequestTransactionDe4(OrchestrationTransactionRequest orchestrationTransactionRequest) {
        if (orchestrationTransactionRequest == null) {
            return null;
        }
        CustomTransactionInformation transaction = orchestrationTransactionRequest.getTransaction();
        if (transaction == null) {
            return null;
        }
        String de4 = transaction.getDe4();
        if (de4 == null) {
            return null;
        }
        return de4;
    }

    private String orchestrationTransactionRequestTransactionDe7(OrchestrationTransactionRequest orchestrationTransactionRequest) {
        if (orchestrationTransactionRequest == null) {
            return null;
        }
        CustomTransactionInformation transaction = orchestrationTransactionRequest.getTransaction();
        if (transaction == null) {
            return null;
        }
        String de7 = transaction.getDe7();
        if (de7 == null) {
            return null;
        }
        return de7;
    }

    private String orchestrationTransactionRequestTransactionDe11(OrchestrationTransactionRequest orchestrationTransactionRequest) {
        if (orchestrationTransactionRequest == null) {
            return null;
        }
        CustomTransactionInformation transaction = orchestrationTransactionRequest.getTransaction();
        if (transaction == null) {
            return null;
        }
        String de11 = transaction.getDe11();
        if (de11 == null) {
            return null;
        }
        return de11;
    }

    private String orchestrationTransactionRequestTransactionDe12(OrchestrationTransactionRequest orchestrationTransactionRequest) {
        if (orchestrationTransactionRequest == null) {
            return null;
        }
        CustomTransactionInformation transaction = orchestrationTransactionRequest.getTransaction();
        if (transaction == null) {
            return null;
        }
        String de12 = transaction.getDe12();
        if (de12 == null) {
            return null;
        }
        return de12;
    }

    private String orchestrationTransactionRequestTransactionDe13(OrchestrationTransactionRequest orchestrationTransactionRequest) {
        if (orchestrationTransactionRequest == null) {
            return null;
        }
        CustomTransactionInformation transaction = orchestrationTransactionRequest.getTransaction();
        if (transaction == null) {
            return null;
        }
        String de13 = transaction.getDe13();
        if (de13 == null) {
            return null;
        }
        return de13;
    }

    private String orchestrationTransactionRequestTransactionDe18(OrchestrationTransactionRequest orchestrationTransactionRequest) {
        if (orchestrationTransactionRequest == null) {
            return null;
        }
        CustomTransactionInformation transaction = orchestrationTransactionRequest.getTransaction();
        if (transaction == null) {
            return null;
        }
        String de18 = transaction.getDe18();
        if (de18 == null) {
            return null;
        }
        return de18;
    }

    private String orchestrationTransactionRequestTransactionDe22(OrchestrationTransactionRequest orchestrationTransactionRequest) {
        if (orchestrationTransactionRequest == null) {
            return null;
        }
        CustomTransactionInformation transaction = orchestrationTransactionRequest.getTransaction();
        if (transaction == null) {
            return null;
        }
        String de22 = transaction.getDe22();
        if (de22 == null) {
            return null;
        }
        return de22;
    }

    private String orchestrationTransactionRequestTransactionDe32(OrchestrationTransactionRequest orchestrationTransactionRequest) {
        if (orchestrationTransactionRequest == null) {
            return null;
        }
        CustomTransactionInformation transaction = orchestrationTransactionRequest.getTransaction();
        if (transaction == null) {
            return null;
        }
        String de32 = transaction.getDe32();
        if (de32 == null) {
            return null;
        }
        return de32;
    }

    private String orchestrationTransactionRequestTransactionDe35(OrchestrationTransactionRequest orchestrationTransactionRequest) {
        if (orchestrationTransactionRequest == null) {
            return null;
        }
        CustomTransactionInformation transaction = orchestrationTransactionRequest.getTransaction();
        if (transaction == null) {
            return null;
        }
        String de35 = transaction.getDe35();
        if (de35 == null) {
            return null;
        }
        return de35;
    }

    private String orchestrationTransactionRequestTransactionDe37(OrchestrationTransactionRequest orchestrationTransactionRequest) {
        if (orchestrationTransactionRequest == null) {
            return null;
        }
        CustomTransactionInformation transaction = orchestrationTransactionRequest.getTransaction();
        if (transaction == null) {
            return null;
        }
        String de37 = transaction.getDe37();
        if (de37 == null) {
            return null;
        }
        return de37;
    }

    private String orchestrationTransactionRequestTransactionDe41(OrchestrationTransactionRequest orchestrationTransactionRequest) {
        if (orchestrationTransactionRequest == null) {
            return null;
        }
        CustomTransactionInformation transaction = orchestrationTransactionRequest.getTransaction();
        if (transaction == null) {
            return null;
        }
        String de41 = transaction.getDe41();
        if (de41 == null) {
            return null;
        }
        return de41;
    }

    private String orchestrationTransactionRequestTransactionDe42(OrchestrationTransactionRequest orchestrationTransactionRequest) {
        if (orchestrationTransactionRequest == null) {
            return null;
        }
        CustomTransactionInformation transaction = orchestrationTransactionRequest.getTransaction();
        if (transaction == null) {
            return null;
        }
        String de42 = transaction.getDe42();
        if (de42 == null) {
            return null;
        }
        return de42;
    }

    private String orchestrationTransactionRequestTransactionDe43(OrchestrationTransactionRequest orchestrationTransactionRequest) {
        if (orchestrationTransactionRequest == null) {
            return null;
        }
        CustomTransactionInformation transaction = orchestrationTransactionRequest.getTransaction();
        if (transaction == null) {
            return null;
        }
        String de43 = transaction.getDe43();
        if (de43 == null) {
            return null;
        }
        return de43;
    }

    private String orchestrationTransactionRequestTransactionDe49(OrchestrationTransactionRequest orchestrationTransactionRequest) {
        if (orchestrationTransactionRequest == null) {
            return null;
        }
        CustomTransactionInformation transaction = orchestrationTransactionRequest.getTransaction();
        if (transaction == null) {
            return null;
        }
        String de49 = transaction.getDe49();
        if (de49 == null) {
            return null;
        }
        return de49;
    }

    private String orchestrationTransactionRequestTransactionDe90(OrchestrationTransactionRequest orchestrationTransactionRequest) {
        if (orchestrationTransactionRequest == null) {
            return null;
        }
        CustomTransactionInformation transaction = orchestrationTransactionRequest.getTransaction();
        if (transaction == null) {
            return null;
        }
        String de90 = transaction.getDe90();
        if (de90 == null) {
            return null;
        }
        return de90;
    }
}