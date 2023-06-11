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

package org.overrun.binpacking.internal;

import org.jetbrains.annotations.Nullable;
import org.overrun.binpacking.PackerFitPos;
import org.overrun.binpacking.PackerRegion;

import java.util.Optional;

/**
 * The default implementation of {@link PackerRegion}.
 *
 * @author squid233
 * @since 0.1.0
 */
public final class SizedRegion implements PackerRegion<Object> {
    private final int width;
    private final int height;
    private @Nullable PackerFitPos fit;

    /**
     * Creates the region with the given size.
     *
     * @param width  the width of the region.
     * @param height the height of the region.
     */
    public SizedRegion(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public int width() {
        return width;
    }

    @Override
    public int height() {
        return height;
    }

    @Override
    public void setFit(@Nullable PackerFitPos fit) {
        this.fit = fit;
    }

    @Override
    public Optional<PackerFitPos> fit() {
        return Optional.ofNullable(fit);
    }

    /**
     * Returns {@code null}.
     *
     * @return {@code null}.
     */
    @Override
    public Object userdata() {
        return null;
    }

    @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
    @Override
    public boolean equals(Object o) {
        return regionEquals(o);
    }

    @Override
    public int hashCode() {
        return regionHashCode();
    }

    @Override
    public String toString() {
        return regionToString(SizedRegion.class.getSimpleName());
    }
}
