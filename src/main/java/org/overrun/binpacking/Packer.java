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

import java.util.Arrays;
import java.util.List;

/**
 * The texture atlas packer.
 *
 * @author squid233
 * @since 0.1.0
 */
public sealed abstract class Packer implements PackerRegionSize permits FixedPacker, GrowingPacker {
    /**
     * Sorts the given region array with height, then width.
     * <p>
     * This method mutates the given regions.
     *
     * @param regions the regions.
     * @param <T>     the type of the userdata.
     * @return the sorted regions wrapped in List.
     */
    @SafeVarargs
    public static <T> List<PackerRegion<T>> sort(PackerRegion<T>... regions) {
        Arrays.sort(regions);
        return List.of(regions);
    }

    /**
     * Sorts the given region list with height, then width.
     * <p>
     * This method mutates the given regions.
     *
     * @param regions the regions.
     * @param <T>     the type of the userdata.
     * @return the sorted regions.
     */
    public static <T> List<PackerRegion<T>> sort(List<PackerRegion<T>> regions) {
        regions.sort(null);
        return regions;
    }

    /**
     * Fits this packer with the given regions.
     *
     * @param regions the regions.
     * @param <T>     the type of the userdata.
     */
    public abstract <T> void fit(List<PackerRegion<T>> regions);

    /**
     * Gets the width of the root node of this packer.
     *
     * @return the width.
     */
    @Override
    public abstract int width();

    /**
     * Gets the height of the root node of this packer.
     *
     * @return the height.
     */
    @Override
    public abstract int height();
}
