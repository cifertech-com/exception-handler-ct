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
package com.cifertech.exceptionhandler.exceptions;

public class ApiException extends RuntimeException {
    private final transient HttpCode httpCode;
    private final transient Object detail;


    public ApiException(HttpCode httpCode, Object details) {
        this.httpCode = httpCode;
        this.detail = details;
    }

    public HttpCode getHttpCode() {
        return httpCode;
    }


    public Object getDetails() {
        return detail;
    }
}
