package org.bookmc.ink.api.mapping;

public interface IMapping {
    String resolveClass(String obfuscated);

    String resolveMethod(String owner, String name, String descriptor);

    String resolveField(String owner, String name);
}
