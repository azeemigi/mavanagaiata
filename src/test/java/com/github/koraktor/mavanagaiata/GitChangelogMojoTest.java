/**
 * This code is free software; you can redistribute it and/or modify it under
 * the terms of the new BSD License.
 *
 * Copyright (c) 2011, Sebastian Staudt
 */

package com.github.koraktor.mavanagaiata;

import java.io.IOException;

import org.junit.Test;

public class GitChangelogMojoTest extends AbstractGitOutputMojoTest<GitChangelogMojo> {

    @Test
    public void testError() {
        super.testError("Unable to generate changelog from Git");
    }

    @Test
    public void testCustomization() throws Exception {
        this.mojo.baseDateFormat    = "MM/dd/yy";
        this.mojo.commitPrefix      = "- ";
        this.mojo.createGitHubLinks = true;
        this.mojo.dateFormat        = "dd.MM.yyyy";
        this.mojo.footer            = "\nCreated by Mavanagaiata on %s";
        this.mojo.gitHubProject     = "mavanagaiata";
        this.mojo.gitHubUser        = "koraktor";
        this.mojo.header            = "History\\n-------\\n";
        this.mojo.tagPrefix         = "\nTag ";
        this.mojo.execute();

        assertEquals("History", this.reader.readLine());
        assertEquals("-------", this.reader.readLine());
        assertEquals("", this.reader.readLine());
        assertEquals("Commits on branch \"master\"", this.reader.readLine());
        assertEquals("", this.reader.readLine());
        assertEquals("- Snapshot for version 3.0.0", this.reader.readLine());
        assertEquals("- Added project name", this.reader.readLine());
        assertEquals("", this.reader.readLine());
        assertEquals("See Git history for changes in the \"master\" branch since version 2.0.0 at: https://github.com/koraktor/mavanagaiata/compare/2.0.0...master", this.reader.readLine());
        assertEquals("", this.reader.readLine());
        assertEquals("Tag 2.0.0 - 03.05.2011", this.reader.readLine());
        assertEquals("", this.reader.readLine());
        assertEquals("- Version bump to 2.0.0", this.reader.readLine());
        assertEquals("- Snapshot for version 2.0.0", this.reader.readLine());
        assertEquals("", this.reader.readLine());
        assertEquals("See Git history for version 2.0.0 at: https://github.com/koraktor/mavanagaiata/compare/1.0.0...2.0.0", this.reader.readLine());
        assertEquals("", this.reader.readLine());
        assertEquals("Tag 1.0.0 - 03.05.2011", this.reader.readLine());
        assertEquals("", this.reader.readLine());
        assertEquals("- Initial commit", reader.readLine());
        assertEquals("", this.reader.readLine());
        assertEquals("See Git history for version 1.0.0 at: https://github.com/koraktor/mavanagaiata/commits/1.0.0", this.reader.readLine());
        assertEquals("", reader.readLine());
        assertMatches("Created by Mavanagaiata on \\d{2}/\\d{2}/\\d{2}", reader.readLine());
        assertFalse(this.reader.ready());
    }

    @Test
    public void testSkipTagged() throws Exception {
        this.mojo.footer     = "";
        this.mojo.skipTagged = true;
        this.mojo.execute();

        assertEquals("Changelog", this.reader.readLine());
        assertEquals("=========", this.reader.readLine());
        assertEquals("", this.reader.readLine());
        assertEquals("Commits on branch \"master\"", this.reader.readLine());
        assertEquals("", this.reader.readLine());
        assertEquals(" * Snapshot for version 3.0.0", this.reader.readLine());
        assertEquals(" * Added project name", this.reader.readLine());
        assertEquals("", this.reader.readLine());
        assertEquals("Version 2.0.0 - 05/03/2011 07:18 AM +0200", this.reader.readLine());
        assertEquals("", this.reader.readLine());
        assertEquals(" * Snapshot for version 2.0.0", this.reader.readLine());
        assertEquals("", this.reader.readLine());
        assertEquals("Version 1.0.0 - 05/03/2011 07:18 AM +0200", this.reader.readLine());
        assertEquals("", this.reader.readLine());
        assertFalse(this.reader.ready());
    }

    protected void assertOutput() throws IOException {
        assertEquals("Changelog", this.reader.readLine());
        assertEquals("=========", this.reader.readLine());
        assertEquals("", this.reader.readLine());
        assertEquals("Commits on branch \"master\"", this.reader.readLine());
        assertEquals("", this.reader.readLine());
        assertEquals(" * Snapshot for version 3.0.0", this.reader.readLine());
        assertEquals(" * Added project name", this.reader.readLine());
        assertEquals("", this.reader.readLine());
        assertEquals("Version 2.0.0 - 05/03/2011 07:18 AM +0200", this.reader.readLine());
        assertEquals("", this.reader.readLine());
        assertEquals(" * Version bump to 2.0.0", this.reader.readLine());
        assertEquals(" * Snapshot for version 2.0.0", this.reader.readLine());
        assertEquals("", this.reader.readLine());
        assertEquals("Version 1.0.0 - 05/03/2011 07:18 AM +0200", this.reader.readLine());
        assertEquals("", this.reader.readLine());
        assertEquals(" * Initial commit", this.reader.readLine());
        assertEquals("", this.reader.readLine());
        assertMatches("Generated by Mavanagaiata at \\d{2}/\\d{2}/\\d{4} \\d{2}:\\d{2} [AP]M [+\\-]\\d{4}", this.reader.readLine());
        assertFalse(this.reader.ready());
    }

}
