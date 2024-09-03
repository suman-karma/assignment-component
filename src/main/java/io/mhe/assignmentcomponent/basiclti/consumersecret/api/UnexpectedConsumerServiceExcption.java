package io.mhe.assignmentcomponent.basiclti.consumersecret.api;

public class UnexpectedConsumerServiceExcption extends RuntimeException {

	/**
     * serialVersionUID
     */
    private static final long serialVersionUID = -8332692966206669521L;

	public UnexpectedConsumerServiceExcption() {
	    super();
    }

	public UnexpectedConsumerServiceExcption(String message, Throwable cause) {
	    super(message, cause);
    }

	public UnexpectedConsumerServiceExcption(String message) {
	    super(message);
    }

	public UnexpectedConsumerServiceExcption(Throwable cause) {
	    super(cause);
    }

}
