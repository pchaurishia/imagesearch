package com.organization.imagesearch.exception;

/**
 * Thrown to indicate that the method is not supported.
 *
 * @author  Priyanka
 */

public
class MethodNotSupportedException extends RuntimeException {
    private static final long serialVersionUID = 5195511250079656443L;

    /**
     * Constructs a <code>MethodNotSupportedException</code> with no
     * detail message.
     */
    public MethodNotSupportedException() {
        super();
    }

    /**
     * Constructs a <code>MethodNotSupportedException</code> with the
     * specified detail message.
     *
     * @param   s   the detail message.
     */
    public MethodNotSupportedException(String s) {
        super(s);
    }
}