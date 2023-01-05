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
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.overrun.binpacking;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.overrun.binpacking.internal.DelegateRegion;
import org.overrun.binpacking.internal.SizedRegion;

import java.util.Optional;
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
        // height implicitly equal
        return Integer.compare(o.width(), width());
    }
}
