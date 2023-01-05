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

import java.util.List;

/**
 * This is a very simple binary tree based bin packing algorithm that is initialized
 * with a fixed width and height and will fit each block into the first node where
 * it fits and then split that node into 2 parts (down and right) to track the
 * remaining whitespace.
 * <p>
 * Best results occur when the input blocks are sorted by height, or even better
 * when sorted by {@code max(width,height)}.
 * <h2>Example</h2>
 * <pre>
 * {@code
 * var regions = Packer.sort(
 *     delegate(sized(100, 300), pixelData0),
 *     delegate(sized(300, 300), pixelData1),
 *     delegate(sized(200, 150), pixelData2),
 *     delegate(sized(200, 200), pixelData3)
 * );
 * var packer = new FixedPacker(400, 500);
 * packer.fit(regions);
 * regions.forEach(region ->
 *     region.ifFitPresent((r, f) ->
 *         TexSubImage(f.x(), f.y(), r.width(), r.height(), r.userdata())
 *     )
 * );
 * }
 * </pre>
 *
 * @author squid233
 * @since 0.1.0
 */
public final class FixedPacker extends Packer {
    private final PackerNode root;

    /**
     * Creates a new fixed packer with the given size.
     *
     * @param width  width of target rectangle.
     * @param height height of target rectangle.
     */
    public FixedPacker(int width, int height) {
        root = new PackerNode()
            .setWidth(width)
            .setHeight(height);
    }

    @Override
    public <T> void fit(List<PackerRegion<T>> regions) {
        PackerNode node;
        for (var region : regions) {
            if ((node = findNode(root, region.width(), region.height())) != null) {
                region.setFit(splitNode(node, region.width(), region.height()));
            }
        }
    }

    @Override
    public int width() {
        return root.width();
    }

    @Override
    public int height() {
        return root.height();
    }

    private PackerNode findNode(PackerNode root, int w, int h) {
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

    private PackerNode splitNode(PackerNode node, int w, int h) {
        return node.markUsed()
            .setDown(new PackerNode()
                .setX(node.x())
                .setY(node.y() + w)
                .setWidth(node.width())
                .setHeight(node.height() - h))
            .setRight(new PackerNode()
                .setX(node.x() + w)
                .setY(node.y())
                .setWidth(node.width() - w)
                .setHeight(h));
    }
}
