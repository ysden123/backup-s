/*
 * Copyright (c) 2022. StulSoft
 */

package com.stulsoft.backup.s;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.FileFilter;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class DirectoryFilter implements FileFilter {
    private static final int showInfoStep = 100;
    private final Set<String> skipFolders;
    private final AtomicInteger handledDirectories = new AtomicInteger(0);
    private final AtomicInteger skippedDirectories = new AtomicInteger(0);
    private final AtomicInteger handledFiles = new AtomicInteger(0);

    private final AtomicInteger showInfoCounter = new AtomicInteger(0);

    public DirectoryFilter(Set<String> skipFolders) {
        this.skipFolders = new HashSet<>(skipFolders
                .stream()
                .map(FilenameUtils::separatorsToSystem)
                .collect(Collectors.toSet()));
    }

    public int getHandledDirectories() {
        return handledDirectories.get();
    }

    public int getSkippedDirectories() {
        return skippedDirectories.get();
    }

    public int getHandledFiles() {
        return handledFiles.get();
    }

    /**
     * Tests whether the specified abstract pathname should be
     * included in a pathname list.
     *
     * @param pathname The abstract pathname to be tested
     * @return {@code true} if and only if {@code pathname}
     * should be included
     */
    @Override
    public boolean accept(File pathname) {
        if (pathname.isDirectory()) {
            handledDirectories.incrementAndGet();
            for (var skipFolder : skipFolders) {
                if (pathname.toPath().toString().contains(skipFolder)) {
                    skippedDirectories.incrementAndGet();
                    return false;
                }
            }
        } else {
            handledFiles.incrementAndGet();

            if (showInfoCounter.incrementAndGet() > showInfoStep) {
                showInfoCounter.set(0);
                System.out.printf("\rProcessing directory number %d, file number %d",
                        handledDirectories.get(), handledFiles.get());
            }
        }
        return true;
    }
}

