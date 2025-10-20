package com.hiennhatt.vod.repositories.projections;

import java.time.LocalDate;

public interface SelfUserInformProjection extends PublicUserInformProjection {
    LocalDate getDateOfBirth();
}
