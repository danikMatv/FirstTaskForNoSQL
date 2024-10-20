package org.example.taskonefornosql.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubscriptionRequest {
    private String subscriberId;
    private String subscribedUserId;
}
