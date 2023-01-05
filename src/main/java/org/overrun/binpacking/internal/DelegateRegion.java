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
import org.overrun.binpacking.PackerRegionSize;

import java.util.Objects;
import java.util.Optional;
import java.util.StringJoiner;

/**
 * The {@link PackerRegion} with a delegated {@link PackerRegionSize}.
 *
 * @param <T> the type of the userdata.
 * @author squid233
 * @since 0.1.0
 */
public final class DelegateRegion<T> implements PackerRegion<T> {
    private final PackerRegionSize size;
    private final T userdata;
    private @Nullable PackerFitPos fit;

    /**
     * Creates the region with the given size.
     *
     * @param size     the size of the region.
     * @param userdata the userdata.
     */
    public DelegateRegion(PackerRegionSize size, @Nullable T userdata) {
        this.size = size;
        this.userdata = userdata;
    }

    @Override
    public int width() {
        return size.width();
    }

    @Override
    public int height() {
        return size.height();
    }

    @Override
    public void setFit(@Nullable PackerFitPos fit) {
        this.fit = fit;
    }

    @Override
    public Optional<PackerFitPos> fit() {
        return Optional.ofNullable(fit);
    }

    @Override
    public T userdata() {
        return userdata;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DelegateRegion<?> that = (DelegateRegion<?>) o;
        return size.width() == that.width() && size.height() == that.height() && Objects.equals(fit, that.fit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(size.width(), size.height(), fit);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", DelegateRegion.class.getSimpleName() + "[", "]")
            .add("width=" + size.width())
            .add("height=" + size.height())
            .add("fit=" + fit)
            .toString();
    }
}
