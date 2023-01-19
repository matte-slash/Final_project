package com.ITCube.Booking.util;

import javax.validation.constraints.Min;

/**
 * @author Matteo Rosso
 */

public class UtilObject {
    @Min(0)
    private final Long idUser;
    @Min(0)
    private final Long idDesk;

    public UtilObject(Long idUser, Long idDesk) {
        this.idUser = idUser;
        this.idDesk = idDesk;
    }

    public Long getIdUser() {
        return idUser;
    }

    public Long getIdDesk() {
        return idDesk;
    }
}
