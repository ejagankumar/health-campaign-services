package org.egov.common.models.referralmanagement;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReferralSearch {
    @JsonProperty("id")
    private List<String> id = null;

    @JsonProperty("clientReferenceId")
    private List<String> clientReferenceId = null;

    @JsonProperty("projectBeneficiaryId")
    private List<String> projectBeneficiaryId = null;

    @JsonProperty("projectBeneficiaryClientReferenceId")
    private List<String> projectBeneficiaryClientReferenceId = null;

    @JsonProperty("sideEffectId")
    private List<String> sideEffectId = null;

    @JsonProperty("sideEffectClientReferenceId")
    private List<String> sideEffectClientReferenceId = null;

    @JsonProperty("referrerId")
    private List<String> referrerId = null;

    @JsonProperty("recipientId")
    private List<String> recipientId = null;
}