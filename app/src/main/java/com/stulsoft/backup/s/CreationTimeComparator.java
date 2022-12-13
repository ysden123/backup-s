/*
 * Copyright (c) 2022. StulSoft
 */

package com.stulsoft.backup.s;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Comparator;

public class CreationTimeComparator  implements Comparator<Path> {
    @Override
    public int compare(Path p1, Path p2) {
        try {
            var attr1 = Files.readAttributes(p1, BasicFileAttributes.class);
            var attr2 = Files.readAttributes(p2, BasicFileAttributes.class);
            return attr1.creationTime().compareTo(attr2.creationTime());
        } catch (Exception exception) {
            exception.printStackTrace();
            return 0;
        }
    }
}
