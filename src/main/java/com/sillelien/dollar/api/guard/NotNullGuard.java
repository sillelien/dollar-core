/*
 * Copyright (c) 2014-2015 Neil Ellis
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sillelien.dollar.api.guard;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Method;
import java.util.Collection;

public class NotNullGuard implements Guard {
    @NotNull @Override
    public String description() {
        return "Non Null Collection Guard";
    }

    @Override
    public void postCondition(Object guarded, Method method, Object[] args, Object result) {
        if (result instanceof Collection) {
            assertNotNull("return value", result, method);
        }
    }

    @Override
    public void preCondition(Object guarded, Method method, @Nullable Object[] args) {
        int count = 0;
        if (args != null) {
            for (Object arg : args) {
                assertNotNull("parameter argument " + (count++), arg, method);
            }
        }
    }

}
