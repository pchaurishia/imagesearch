package com.organization.imagesearch.exception;

/**
 * Thrown to indicate that the method is not supported.
 *
 * @author  Priyanka
 */

public
class ImageMatcherException extends Exception {
    private static final long serialVersionUID = 5195511250079656443L;

    /**
     * Constructs a <code>MethodNotSupportedException</code> with no
     * detail message.
     */
    public ImageMatcherException() {
        super();
    }

    /**
     * Constructs a <code>MethodNotSupportedException</code> with the
     * specified detail message.
     *
     * @param   s   the detail message.
     */
    public ImageMatcherException(String s) {
        super(s);
    }
}