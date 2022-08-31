package com.novo.microservices.mocks;

import com.novo.microservices.tbs.utils.dtos.StandardTransaction;
import com.novo.microservices.transactions.mappers.contracts.IStandardTransactionDefaultValuesMapper;
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
 * @author duhernandez@novopayment.com
 * @since 4/10/2022
 */

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-06-05T13:46:34-0300",
    comments = "version: 1.5.0.RC1, compiler: javac, environment: Java 11.0.15 (Private Build)"
)
@Component
public class StandardTransactionDefaultValuesMapperMockImpl implements IStandardTransactionDefaultValuesMapper {

    @Override
    public void applyDefaultValues(StandardTransaction standardTransactionToUpdate, StandardTransaction standardTransactionDefaultMapping) {
        if ( standardTransactionDefaultMapping == null ) {
            return;
        }

        if ( standardTransactionDefaultMapping.getMessageTypeIndicator() != null ) {
            standardTransactionToUpdate.setMessageTypeIndicator( standardTransactionDefaultMapping.getMessageTypeIndicator() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_2() != null ) {
            standardTransactionToUpdate.setDataElement_2( standardTransactionDefaultMapping.getDataElement_2() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_3() != null ) {
            standardTransactionToUpdate.setDataElement_3( standardTransactionDefaultMapping.getDataElement_3() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_4() != null ) {
            standardTransactionToUpdate.setDataElement_4( standardTransactionDefaultMapping.getDataElement_4() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_5() != null ) {
            standardTransactionToUpdate.setDataElement_5( standardTransactionDefaultMapping.getDataElement_5() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_7() != null ) {
            standardTransactionToUpdate.setDataElement_7( standardTransactionDefaultMapping.getDataElement_7() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_9() != null ) {
            standardTransactionToUpdate.setDataElement_9( standardTransactionDefaultMapping.getDataElement_9() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_11() != null ) {
            standardTransactionToUpdate.setDataElement_11( standardTransactionDefaultMapping.getDataElement_11() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_12() != null ) {
            standardTransactionToUpdate.setDataElement_12( standardTransactionDefaultMapping.getDataElement_12() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_13() != null ) {
            standardTransactionToUpdate.setDataElement_13( standardTransactionDefaultMapping.getDataElement_13() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_14() != null ) {
            standardTransactionToUpdate.setDataElement_14( standardTransactionDefaultMapping.getDataElement_14() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_15() != null ) {
            standardTransactionToUpdate.setDataElement_15( standardTransactionDefaultMapping.getDataElement_15() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_16() != null ) {
            standardTransactionToUpdate.setDataElement_16( standardTransactionDefaultMapping.getDataElement_16() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_18() != null ) {
            standardTransactionToUpdate.setDataElement_18( standardTransactionDefaultMapping.getDataElement_18() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_22() != null ) {
            standardTransactionToUpdate.setDataElement_22( standardTransactionDefaultMapping.getDataElement_22() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_23() != null ) {
            standardTransactionToUpdate.setDataElement_23( standardTransactionDefaultMapping.getDataElement_23() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_25() != null ) {
            standardTransactionToUpdate.setDataElement_25( standardTransactionDefaultMapping.getDataElement_25() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_26() != null ) {
            standardTransactionToUpdate.setDataElement_26( standardTransactionDefaultMapping.getDataElement_26() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_27() != null ) {
            standardTransactionToUpdate.setDataElement_27( standardTransactionDefaultMapping.getDataElement_27() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_28() != null ) {
            standardTransactionToUpdate.setDataElement_28( standardTransactionDefaultMapping.getDataElement_28() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_29() != null ) {
            standardTransactionToUpdate.setDataElement_29( standardTransactionDefaultMapping.getDataElement_29() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_30() != null ) {
            standardTransactionToUpdate.setDataElement_30( standardTransactionDefaultMapping.getDataElement_30() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_31() != null ) {
            standardTransactionToUpdate.setDataElement_31( standardTransactionDefaultMapping.getDataElement_31() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_32() != null ) {
            standardTransactionToUpdate.setDataElement_32( standardTransactionDefaultMapping.getDataElement_32() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_33() != null ) {
            standardTransactionToUpdate.setDataElement_33( standardTransactionDefaultMapping.getDataElement_33() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_35() != null ) {
            standardTransactionToUpdate.setDataElement_35( standardTransactionDefaultMapping.getDataElement_35() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_37() != null ) {
            standardTransactionToUpdate.setDataElement_37( standardTransactionDefaultMapping.getDataElement_37() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_38() != null ) {
            standardTransactionToUpdate.setDataElement_38( standardTransactionDefaultMapping.getDataElement_38() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_39() != null ) {
            standardTransactionToUpdate.setDataElement_39( standardTransactionDefaultMapping.getDataElement_39() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_40() != null ) {
            standardTransactionToUpdate.setDataElement_40( standardTransactionDefaultMapping.getDataElement_40() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_41() != null ) {
            standardTransactionToUpdate.setDataElement_41( standardTransactionDefaultMapping.getDataElement_41() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_42() != null ) {
            standardTransactionToUpdate.setDataElement_42( standardTransactionDefaultMapping.getDataElement_42() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_43() != null ) {
            standardTransactionToUpdate.setDataElement_43( standardTransactionDefaultMapping.getDataElement_43() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_44() != null ) {
            standardTransactionToUpdate.setDataElement_44( standardTransactionDefaultMapping.getDataElement_44() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_45() != null ) {
            standardTransactionToUpdate.setDataElement_45( standardTransactionDefaultMapping.getDataElement_45() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_48() != null ) {
            standardTransactionToUpdate.setDataElement_48( standardTransactionDefaultMapping.getDataElement_48() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_49() != null ) {
            standardTransactionToUpdate.setDataElement_49( standardTransactionDefaultMapping.getDataElement_49() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_50() != null ) {
            standardTransactionToUpdate.setDataElement_50( standardTransactionDefaultMapping.getDataElement_50() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_52() != null ) {
            standardTransactionToUpdate.setDataElement_52( standardTransactionDefaultMapping.getDataElement_52() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_53() != null ) {
            standardTransactionToUpdate.setDataElement_53( standardTransactionDefaultMapping.getDataElement_53() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_54() != null ) {
            standardTransactionToUpdate.setDataElement_54( standardTransactionDefaultMapping.getDataElement_54() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_56() != null ) {
            standardTransactionToUpdate.setDataElement_56( standardTransactionDefaultMapping.getDataElement_56() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_57() != null ) {
            standardTransactionToUpdate.setDataElement_57( standardTransactionDefaultMapping.getDataElement_57() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_58() != null ) {
            standardTransactionToUpdate.setDataElement_58( standardTransactionDefaultMapping.getDataElement_58() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_59() != null ) {
            standardTransactionToUpdate.setDataElement_59( standardTransactionDefaultMapping.getDataElement_59() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_66() != null ) {
            standardTransactionToUpdate.setDataElement_66( standardTransactionDefaultMapping.getDataElement_66() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_67() != null ) {
            standardTransactionToUpdate.setDataElement_67( standardTransactionDefaultMapping.getDataElement_67() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_70() != null ) {
            standardTransactionToUpdate.setDataElement_70( standardTransactionDefaultMapping.getDataElement_70() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_73() != null ) {
            standardTransactionToUpdate.setDataElement_73( standardTransactionDefaultMapping.getDataElement_73() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_74() != null ) {
            standardTransactionToUpdate.setDataElement_74( standardTransactionDefaultMapping.getDataElement_74() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_75() != null ) {
            standardTransactionToUpdate.setDataElement_75( standardTransactionDefaultMapping.getDataElement_75() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_76() != null ) {
            standardTransactionToUpdate.setDataElement_76( standardTransactionDefaultMapping.getDataElement_76() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_77() != null ) {
            standardTransactionToUpdate.setDataElement_77( standardTransactionDefaultMapping.getDataElement_77() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_78() != null ) {
            standardTransactionToUpdate.setDataElement_78( standardTransactionDefaultMapping.getDataElement_78() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_79() != null ) {
            standardTransactionToUpdate.setDataElement_79( standardTransactionDefaultMapping.getDataElement_79() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_80() != null ) {
            standardTransactionToUpdate.setDataElement_80( standardTransactionDefaultMapping.getDataElement_80() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_81() != null ) {
            standardTransactionToUpdate.setDataElement_81( standardTransactionDefaultMapping.getDataElement_81() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_82() != null ) {
            standardTransactionToUpdate.setDataElement_82( standardTransactionDefaultMapping.getDataElement_82() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_83() != null ) {
            standardTransactionToUpdate.setDataElement_83( standardTransactionDefaultMapping.getDataElement_83() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_84() != null ) {
            standardTransactionToUpdate.setDataElement_84( standardTransactionDefaultMapping.getDataElement_84() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_85() != null ) {
            standardTransactionToUpdate.setDataElement_85( standardTransactionDefaultMapping.getDataElement_85() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_86() != null ) {
            standardTransactionToUpdate.setDataElement_86( standardTransactionDefaultMapping.getDataElement_86() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_87() != null ) {
            standardTransactionToUpdate.setDataElement_87( standardTransactionDefaultMapping.getDataElement_87() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_88() != null ) {
            standardTransactionToUpdate.setDataElement_88( standardTransactionDefaultMapping.getDataElement_88() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_89() != null ) {
            standardTransactionToUpdate.setDataElement_89( standardTransactionDefaultMapping.getDataElement_89() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_90() != null ) {
            standardTransactionToUpdate.setDataElement_90( standardTransactionDefaultMapping.getDataElement_90() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_91() != null ) {
            standardTransactionToUpdate.setDataElement_91( standardTransactionDefaultMapping.getDataElement_91() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_95() != null ) {
            standardTransactionToUpdate.setDataElement_95( standardTransactionDefaultMapping.getDataElement_95() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_97() != null ) {
            standardTransactionToUpdate.setDataElement_97( standardTransactionDefaultMapping.getDataElement_97() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_98() != null ) {
            standardTransactionToUpdate.setDataElement_98( standardTransactionDefaultMapping.getDataElement_98() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_100() != null ) {
            standardTransactionToUpdate.setDataElement_100( standardTransactionDefaultMapping.getDataElement_100() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_101() != null ) {
            standardTransactionToUpdate.setDataElement_101( standardTransactionDefaultMapping.getDataElement_101() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_102() != null ) {
            standardTransactionToUpdate.setDataElement_102( standardTransactionDefaultMapping.getDataElement_102() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_103() != null ) {
            standardTransactionToUpdate.setDataElement_103( standardTransactionDefaultMapping.getDataElement_103() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_118() != null ) {
            standardTransactionToUpdate.setDataElement_118( standardTransactionDefaultMapping.getDataElement_118() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_119() != null ) {
            standardTransactionToUpdate.setDataElement_119( standardTransactionDefaultMapping.getDataElement_119() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_123() != null ) {
            standardTransactionToUpdate.setDataElement_123( standardTransactionDefaultMapping.getDataElement_123() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_127_2() != null ) {
            standardTransactionToUpdate.setDataElement_127_2( standardTransactionDefaultMapping.getDataElement_127_2() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_127_3() != null ) {
            standardTransactionToUpdate.setDataElement_127_3( standardTransactionDefaultMapping.getDataElement_127_3() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_127_4() != null ) {
            standardTransactionToUpdate.setDataElement_127_4( standardTransactionDefaultMapping.getDataElement_127_4() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_127_5() != null ) {
            standardTransactionToUpdate.setDataElement_127_5( standardTransactionDefaultMapping.getDataElement_127_5() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_127_6() != null ) {
            standardTransactionToUpdate.setDataElement_127_6( standardTransactionDefaultMapping.getDataElement_127_6() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_127_7() != null ) {
            standardTransactionToUpdate.setDataElement_127_7( standardTransactionDefaultMapping.getDataElement_127_7() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_127_8() != null ) {
            standardTransactionToUpdate.setDataElement_127_8( standardTransactionDefaultMapping.getDataElement_127_8() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_127_9() != null ) {
            standardTransactionToUpdate.setDataElement_127_9( standardTransactionDefaultMapping.getDataElement_127_9() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_127_10() != null ) {
            standardTransactionToUpdate.setDataElement_127_10( standardTransactionDefaultMapping.getDataElement_127_10() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_127_11() != null ) {
            standardTransactionToUpdate.setDataElement_127_11( standardTransactionDefaultMapping.getDataElement_127_11() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_127_12() != null ) {
            standardTransactionToUpdate.setDataElement_127_12( standardTransactionDefaultMapping.getDataElement_127_12() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_127_13() != null ) {
            standardTransactionToUpdate.setDataElement_127_13( standardTransactionDefaultMapping.getDataElement_127_13() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_127_14() != null ) {
            standardTransactionToUpdate.setDataElement_127_14( standardTransactionDefaultMapping.getDataElement_127_14() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_127_15() != null ) {
            standardTransactionToUpdate.setDataElement_127_15( standardTransactionDefaultMapping.getDataElement_127_15() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_127_16() != null ) {
            standardTransactionToUpdate.setDataElement_127_16( standardTransactionDefaultMapping.getDataElement_127_16() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_127_17() != null ) {
            standardTransactionToUpdate.setDataElement_127_17( standardTransactionDefaultMapping.getDataElement_127_17() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_127_18() != null ) {
            standardTransactionToUpdate.setDataElement_127_18( standardTransactionDefaultMapping.getDataElement_127_18() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_127_19() != null ) {
            standardTransactionToUpdate.setDataElement_127_19( standardTransactionDefaultMapping.getDataElement_127_19() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_127_20() != null ) {
            standardTransactionToUpdate.setDataElement_127_20( standardTransactionDefaultMapping.getDataElement_127_20() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_127_21() != null ) {
            standardTransactionToUpdate.setDataElement_127_21( standardTransactionDefaultMapping.getDataElement_127_21() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_127_22() != null ) {
            standardTransactionToUpdate.setDataElement_127_22( standardTransactionDefaultMapping.getDataElement_127_22() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_127_23() != null ) {
            standardTransactionToUpdate.setDataElement_127_23( standardTransactionDefaultMapping.getDataElement_127_23() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_127_24() != null ) {
            standardTransactionToUpdate.setDataElement_127_24( standardTransactionDefaultMapping.getDataElement_127_24() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_127_25() != null ) {
            standardTransactionToUpdate.setDataElement_127_25( standardTransactionDefaultMapping.getDataElement_127_25() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_127_26() != null ) {
            standardTransactionToUpdate.setDataElement_127_26( standardTransactionDefaultMapping.getDataElement_127_26() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_127_27() != null ) {
            standardTransactionToUpdate.setDataElement_127_27( standardTransactionDefaultMapping.getDataElement_127_27() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_127_28() != null ) {
            standardTransactionToUpdate.setDataElement_127_28( standardTransactionDefaultMapping.getDataElement_127_28() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_127_29() != null ) {
            standardTransactionToUpdate.setDataElement_127_29( standardTransactionDefaultMapping.getDataElement_127_29() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_127_30() != null ) {
            standardTransactionToUpdate.setDataElement_127_30( standardTransactionDefaultMapping.getDataElement_127_30() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_127_31() != null ) {
            standardTransactionToUpdate.setDataElement_127_31( standardTransactionDefaultMapping.getDataElement_127_31() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_127_32() != null ) {
            standardTransactionToUpdate.setDataElement_127_32( standardTransactionDefaultMapping.getDataElement_127_32() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_127_33() != null ) {
            standardTransactionToUpdate.setDataElement_127_33( standardTransactionDefaultMapping.getDataElement_127_33() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_127_34() != null ) {
            standardTransactionToUpdate.setDataElement_127_34( standardTransactionDefaultMapping.getDataElement_127_34() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_127_35() != null ) {
            standardTransactionToUpdate.setDataElement_127_35( standardTransactionDefaultMapping.getDataElement_127_35() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_127_36() != null ) {
            standardTransactionToUpdate.setDataElement_127_36( standardTransactionDefaultMapping.getDataElement_127_36() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_127_37() != null ) {
            standardTransactionToUpdate.setDataElement_127_37( standardTransactionDefaultMapping.getDataElement_127_37() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_127_38() != null ) {
            standardTransactionToUpdate.setDataElement_127_38( standardTransactionDefaultMapping.getDataElement_127_38() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_127_39() != null ) {
            standardTransactionToUpdate.setDataElement_127_39( standardTransactionDefaultMapping.getDataElement_127_39() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_127_40() != null ) {
            standardTransactionToUpdate.setDataElement_127_40( standardTransactionDefaultMapping.getDataElement_127_40() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_127_41() != null ) {
            standardTransactionToUpdate.setDataElement_127_41( standardTransactionDefaultMapping.getDataElement_127_41() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_127_42() != null ) {
            standardTransactionToUpdate.setDataElement_127_42( standardTransactionDefaultMapping.getDataElement_127_42() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_127_43() != null ) {
            standardTransactionToUpdate.setDataElement_127_43( standardTransactionDefaultMapping.getDataElement_127_43() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_127_44() != null ) {
            standardTransactionToUpdate.setDataElement_127_44( standardTransactionDefaultMapping.getDataElement_127_44() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_127_45() != null ) {
            standardTransactionToUpdate.setDataElement_127_45( standardTransactionDefaultMapping.getDataElement_127_45() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_127_46() != null ) {
            standardTransactionToUpdate.setDataElement_127_46( standardTransactionDefaultMapping.getDataElement_127_46() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_127_47() != null ) {
            standardTransactionToUpdate.setDataElement_127_47( standardTransactionDefaultMapping.getDataElement_127_47() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_127_48() != null ) {
            standardTransactionToUpdate.setDataElement_127_48( standardTransactionDefaultMapping.getDataElement_127_48() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_127_49() != null ) {
            standardTransactionToUpdate.setDataElement_127_49( standardTransactionDefaultMapping.getDataElement_127_49() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_127_55() != null ) {
            standardTransactionToUpdate.setDataElement_127_55( standardTransactionDefaultMapping.getDataElement_127_55() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_127_56() != null ) {
            standardTransactionToUpdate.setDataElement_127_56( standardTransactionDefaultMapping.getDataElement_127_56() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_127_57() != null ) {
            standardTransactionToUpdate.setDataElement_127_57( standardTransactionDefaultMapping.getDataElement_127_57() );
        }
        if ( standardTransactionDefaultMapping.getDataElement_127_58() != null ) {
            standardTransactionToUpdate.setDataElement_127_58( standardTransactionDefaultMapping.getDataElement_127_58() );
        }
    }
}