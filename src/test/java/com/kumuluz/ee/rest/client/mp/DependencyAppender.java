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
package com.kumuluz.ee.rest.client.mp;

import com.kumuluz.ee.testing.arquillian.spi.MavenDependencyAppender;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Library appender for libraries required by tests.
 *
 * @author Miha Jamsek
 * @since 1.0.1
 */
public class DependencyAppender implements MavenDependencyAppender {

    private static final ResourceBundle versionsBundle = ResourceBundle.getBundle("META-INF/kumuluzee/rest-client/versions");

    @Override
    public List<String> addLibraries() {

        List<String> libs = new ArrayList<>();

        libs.add("com.kumuluz.ee:kumuluzee-jax-rs-jersey:");
        libs.add("com.kumuluz.ee:kumuluzee-json-p-jsonp:");
        libs.add("org.glassfish.jersey.media:jersey-media-json-processing:" +
                versionsBundle.getString("jersey-media-version"));
        libs.add("com.kumuluz.ee.config:kumuluzee-config-mp:" +
                versionsBundle.getString("kumuluzee-config-mp-version"));

        libs.add("org.eclipse.microprofile.rest.client:microprofile-rest-client-api:" +
                versionsBundle.getString("microprofile-rest-client-version"));
        libs.add("org.eclipse.microprofile.fault-tolerance:microprofile-fault-tolerance-api:" +
                versionsBundle.getString("microprofile-fault-tolerance-version"));

        libs.add("org.hamcrest:hamcrest-all:" + versionsBundle.getString("hamcrest-version"));
        libs.add("junit:junit:" + versionsBundle.getString("junit-version"));

        return libs;
    }
}
