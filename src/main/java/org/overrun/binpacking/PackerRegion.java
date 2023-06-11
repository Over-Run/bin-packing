/*
 * MIT License
 *
 * Copyright (c) 2023 Overrun Organization
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 */

package org.overrun.binpacking;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.overrun.binpacking.internal.DelegateRegion;
import org.overrun.binpacking.internal.SizedRegion;

import java.util.Objects;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.function.BiConsumer;

/**
 * The region of a packer.
 *
 * @param <T> the type of the userdata.
 * @author squid233
 * @since 0.1.0
 */
public interface PackerRegion<T> extends PackerRegionSize, Comparable<PackerRegion<T>> {
    /**
     * Creates a region with the given size.
     *
     * @param width  the width of the region.
     * @param height the height of the region.
     * @return the region.
     */
    static PackerRegion<Object> sized(int width, int height) {
        return new SizedRegion(width, height);
    }

    /**
     * Creates a delegated region with the given instance of size.
     *
     * @param size the size of the region.
     * @return the region.
     */
    static PackerRegion<Object> delegate(PackerRegionSize size) {
        return delegate(size, null);
    }

    /**
     * Creates a delegated region with the given instance of size and userdata.
     *
     * @param size     the size of the region.
     * @param userdata the userdata to be stored in the region. defaults to {@code null}.
     * @param <T>      the type of the userdata.
     * @return the region.
     */
    static <T> PackerRegion<T> delegate(PackerRegionSize size, T userdata) {
        return new DelegateRegion<>(size, userdata);
    }

    /**
     * Sets the fit position of this region.
     *
     * @param fit the fit position.
     */
    void setFit(@Nullable PackerFitPos fit);

    /**
     * Gets the fit position of this region.
     *
     * @return the fit position.
     */
    Optional<PackerFitPos> fit();

    /**
     * Performs the given action if {@link #fit() fit} position is present.
     *
     * @param consumer the action to be performed.
     */
    default void ifFitPresent(BiConsumer<PackerRegion<T>, PackerFitPos> consumer) {
        fit().ifPresent(fit -> consumer.accept(this, fit));
    }

    /**
     * Gets the userdata stored in this region.
     *
     * @return the userdata.
     */
    T userdata();

    @Override
    default int compareTo(@NotNull PackerRegion o) {
        if (o.height() < height()) return -1;
        if (o.height() > height()) return 1;
        // heights implicitly equal
        return Integer.compare(o.width(), width());
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param o the reference object with which to compare.
     * @return {@code true} if this object is the same as the obj argument; {@code false} otherwise.
     * @see Object#equals(Object)
     */
    default boolean regionEquals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        var that = (PackerRegion<?>) o;
        return width() == that.width() && height() == that.height() && Objects.equals(fit(), that.fit());
    }

    /**
     * Returns a hash code value for the object.
     *
     * @return a hash code value for this object.
     * @see Object#hashCode()
     */
    default int regionHashCode() {
        return Objects.hash(width(), height(), fit());
    }

    /**
     * Returns a string representation of the object.
     *
     * @param className the class name of the object.
     * @return a string representation of the object.
     * @see Object#toString()
     */
    default String regionToString(String className) {
        return new StringJoiner(", ", className + "[", "]")
            .add("width=" + width())
            .add("height=" + height())
            .add("fit=" + fit())
            .toString();
    }
}
