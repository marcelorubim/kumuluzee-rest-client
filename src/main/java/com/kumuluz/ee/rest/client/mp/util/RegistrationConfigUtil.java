/*
 *  Copyright (c) 2014-2017 Kumuluz and/or its affiliates
 *  and other contributors as indicated by the @author tags and
 *  the contributor list.
 *
 *  Licensed under the MIT License (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  https://opensource.org/licenses/MIT
 *
 *  The software is provided "AS IS", WITHOUT WARRANTY OF ANY KIND, express or
 *  implied, including but not limited to the warranties of merchantability,
 *  fitness for a particular purpose and noninfringement. in no event shall the
 *  authors or copyright holders be liable for any claim, damages or other
 *  liability, whether in an action of contract, tort or otherwise, arising from,
 *  out of or in connection with the software or the use or other dealings in the
 *  software. See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.kumuluz.ee.rest.client.mp.util;

import com.kumuluz.ee.configuration.utils.ConfigurationUtil;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;

import java.util.*;

/**
 * Common configuration for rest client registrations.
 *
 * @author Urban Malc
 * @since 1.0.1
 */
public class RegistrationConfigUtil {

    private static Config config = ConfigProvider.getConfig();

    private static Map<String, Integer> registrationToIndex;

    private synchronized static void scanRegistrations() {
        if (registrationToIndex == null) {
            ConfigurationUtil keeConf = ConfigurationUtil.getInstance();

            Map<String, Integer> registrations = new HashMap<>();

            int noRegistrations = keeConf.getListSize("kumuluzee.rest-client.registrations").orElse(0);
            for (int i = 0; i < noRegistrations; i++) {
                Optional<String> registrationClass = keeConf
                        .get("kumuluzee.rest-client.registrations[" + i + "].class");

                if (registrationClass.isPresent()) {
                    registrations.put(registrationClass.get(), i);
                }
            }

            registrationToIndex = registrations;
        }
    }

    public static <T> Optional<T> getConfigurationParameter(Class<?> registration, String property, Class<T> tClass) {
        if (registrationToIndex == null) {
            scanRegistrations();
        }

        String classname = registration.getName();
        List<String> keys = new ArrayList<>();
        keys.add(classname + "/mp-rest/" + property);

        if (registrationToIndex.containsKey(classname)) {
            keys.add("kumuluzee.rest-client.registrations[" + registrationToIndex.get(classname) + "]." + property);
        }

        Optional<T> param = Optional.empty();
        for (String key : keys) {
            param = config.getOptionalValue(key, tClass);

            if (param.isPresent()) {
                break;
            }
        }

        return param;
    }
}