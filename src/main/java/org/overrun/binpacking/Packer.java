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

import org.overrun.binpacking.internal.PackerNode;

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
     * @param <U>     the type of the userdata.
     * @param <E>     the type of elements in the list.
     * @return the sorted regions wrapped in List.
     */
    @SafeVarargs
    public static <U, E extends PackerRegion<? extends U>> List<E> sort(E... regions) {
        Arrays.sort(regions);
        return List.of(regions);
    }

    /**
     * Sorts the given region list with height, then width.
     * <p>
     * This method mutates the given regions.
     *
     * @param regions the regions.
     * @param <U>     the type of the userdata.
     * @param <E>     the type of elements in the list.
     * @return the sorted regions.
     */
    public static <U, E extends PackerRegion<? extends U>> List<E> sort(List<E> regions) {
        regions.sort(null);
        return regions;
    }

    PackerNode findNode(PackerNode root, int w, int h) {
        if (root.used()) {
            PackerNode node = findNode(root.right(), w, h);
            if (node != null) {
                return node;
            }
            return findNode(root.down(), w, h);
        }
        if (w <= root.width() && h <= root.height()) {
            return root;
        }
        return null;
    }

    /**
     * Fits this packer with the given regions.
     *
     * @param regions the regions.
     */
    public abstract void fit(List<? extends PackerRegion<?>> regions);

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
