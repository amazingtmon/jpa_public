package jpa.jpazone.exception;

import javax.persistence.NoResultException;

public class CouldNotFoundException extends NoResultException {

    public CouldNotFoundException() {
        super();
    }

    public CouldNotFoundException(String message) {
        super(message);
    }
}
