package com.sillelien.dollar.api;

import com.sillelien.dollar.api.types.DollarFactory;

/**
 * @author hello@neilellis.me
 */
public interface StringAware {

    /**
     * Remove whitespace before and after the first and last non-whitespace characters.
     *
     * @return
     */
    default var $trim() {
        return DollarFactory.fromStringValue(toString().trim());
    }

}
