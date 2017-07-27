/*
 *    Copyright (c) 2014-2017 Neil Ellis
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.sillelien.dollar.api.types;

import com.sillelien.dollar.api.var;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ConstrainedVar extends DollarWrapper {
    @NotNull
    private final String constraintFingerPrint;

    ConstrainedVar(@Nullable var value, @NotNull var constraint, String constraintSource) {
        super(value);
        this.constraintFingerPrint = constraintSource;
    }

    ConstrainedVar(@Nullable var value, @NotNull String constraintSource) {
        super(value);
        this.constraintFingerPrint = constraintSource;
    }

    @Override
    public var $constrain(var constraint, String constraintSource) {
        if (constraintSource.equals(constraintFingerPrint)) {
            return super.$constrain(constraint,constraintSource);
        } else {
            throw new ConstraintViolation(this, constraint, constraintFingerPrint);
        }
    }
}
