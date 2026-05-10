/*
 * Copyright © 2026 CiferTech (yacson.ramirez@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.cifertech.exceptionhandler.exceptions._4xx;

import com.cifertech.exceptionhandler.exceptions.ApiException;
import com.cifertech.exceptionhandler.exceptions.HttpCode;

import java.util.Map;

public class MethodNotAllowed extends ApiException {

    public MethodNotAllowed(Object details) {
        super(HttpCode.METHOD_NOT_ALLOWED, details);
    }

    public MethodNotAllowed(String message) {
        super(HttpCode.METHOD_NOT_ALLOWED, Map.of("message", message));
    }

    public MethodNotAllowed(){
        super(HttpCode.METHOD_NOT_ALLOWED, null);
    }
}
