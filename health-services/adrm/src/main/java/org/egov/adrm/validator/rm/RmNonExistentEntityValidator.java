package org.egov.adrm.validator.rm;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.adrm.repository.ReferralManagementRepository;
import org.egov.common.models.Error;
import org.egov.common.models.adrm.referralmanagement.Referral;
import org.egov.common.models.adrm.referralmanagement.ReferralBulkRequest;
import org.egov.common.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.egov.adrm.Constants.GET_ID;
import static org.egov.common.utils.CommonUtils.checkNonExistentEntities;
import static org.egov.common.utils.CommonUtils.getIdFieldName;
import static org.egov.common.utils.CommonUtils.getIdToObjMap;
import static org.egov.common.utils.CommonUtils.getMethod;
import static org.egov.common.utils.CommonUtils.getObjClass;
import static org.egov.common.utils.CommonUtils.notHavingErrors;
import static org.egov.common.utils.CommonUtils.populateErrorDetails;
import static org.egov.common.utils.ValidatorUtils.getErrorForNonExistentEntity;

@Component
@Order(value = 4)
@Slf4j
public class RmNonExistentEntityValidator implements Validator<ReferralBulkRequest, Referral> {

    private final ReferralManagementRepository referralManagementRepository;

    private final ObjectMapper objectMapper;

    @Autowired
    public RmNonExistentEntityValidator(ReferralManagementRepository referralManagementRepository, ObjectMapper objectMapper) {
        this.referralManagementRepository = referralManagementRepository;
        this.objectMapper = objectMapper;
    }


    @Override
    public Map<Referral, List<Error>> validate(ReferralBulkRequest request) {
        log.info("validating for existence of entity");
        Map<Referral, List<Error>> errorDetailsMap = new HashMap<>();
        List<Referral> referrals = request.getReferrals();
        Class<?> objClass = getObjClass(referrals);
        Method idMethod = getMethod(GET_ID, objClass);
        Map<String, Referral> iMap = getIdToObjMap(referrals
                .stream().filter(notHavingErrors()).collect(Collectors.toList()), idMethod);
        if (!iMap.isEmpty()) {
            List<String> adverseEventIds = new ArrayList<>(iMap.keySet());
            List<Referral> existingReferrals = referralManagementRepository
                    .findById(adverseEventIds, false, getIdFieldName(idMethod));
            List<Referral> nonExistentReferrals = checkNonExistentEntities(iMap,
                    existingReferrals, idMethod);
            nonExistentReferrals.forEach(adverseEvent -> {
                Error error = getErrorForNonExistentEntity();
                populateErrorDetails(adverseEvent, error, errorDetailsMap);
            });
        }

        return errorDetailsMap;
    }
}
