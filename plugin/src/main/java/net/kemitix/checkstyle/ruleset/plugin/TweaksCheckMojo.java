/*
The MIT License (MIT)

Copyright (c) 2016 Paul Campbell

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

package net.kemitix.checkstyle.ruleset.plugin;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

/**
 * Runs the Checkstyle Maven Plugin with the Kemitix Checkstyle Ruleset: TWEAKS, JAVADOC, NAMING and LAYOUT.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
@Mojo(name = "4-tweaks", defaultPhase = LifecyclePhase.VALIDATE)
public final class TweaksCheckMojo extends AbstractCheckMojo {

    /**
     * Create the Mojo.
     */
    TweaksCheckMojo() {
        super(new DefaultPluginExecutor());
    }

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        performCheck("4-tweaks");
    }
}
