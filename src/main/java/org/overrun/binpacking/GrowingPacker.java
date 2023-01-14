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
 * This is a binary tree based bin packing algorithm that is more complex than
 * the simple {@link Packer}. Instead of starting off with a fixed width and
 * height, it starts with the width and height of the first block passed and then
 * grows as necessary to accommodate each subsequent block. As it grows it attempts
 * to maintain a roughly square ratio by making 'smart' choices about whether to
 * grow right or down.
 * <p>
 * When growing, the algorithm can only grow to the right OR down. Therefore, if
 * the new block is BOTH wider and taller than the current target then it will be
 * rejected. This makes it very important to initialize with a sensible starting
 * width and height. If you are providing sorted input (the largest first) then this
 * will not be an issue.
 * <p>
 * A potential way to solve this limitation would be to allow growth in BOTH
 * directions at once, but this requires maintaining a more complex tree
 * with 3 children (down, right and center) and that complexity can be avoided
 * by simply choosing a sensible starting block.
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
 * var packer = new GrowingPacker();
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
public final class GrowingPacker extends Packer {
    private PackerNode root;

    /**
     * Creates a new growable packer.
     */
    public GrowingPacker() {
    }

    @Override
    public void fit(List<? extends PackerRegion<?>> regions) {
        PackerNode node;
        boolean isNotEmpty = regions.size() > 0;
        int w = isNotEmpty ? regions.get(0).width() : 0;
        int h = isNotEmpty ? regions.get(0).height() : 0;
        root = new PackerNode()
            .setWidth(w)
            .setHeight(h);
        for (var region : regions) {
            if ((node = findNode(root, region.width(), region.height())) != null) {
                region.setFit(splitNode(node, region.width(), region.height()));
            } else {
                region.setFit(growNode(region.width(), region.height()));
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

    private PackerNode splitNode(PackerNode node, int w, int h) {
        return node.markUsed()
            .setDown(new PackerNode()
                .setX(node.x())
                .setY(node.y() + h)
                .setWidth(node.width())
                .setHeight(node.height() - h))
            .setRight(new PackerNode()
                .setX(node.x() + w)
                .setY(node.y())
                .setWidth(node.width() - w)
                .setHeight(node.height()));
    }

    private PackerNode growNode(int w, int h) {
        boolean canGrowDown = w <= root.width();
        boolean canGrowRight = h <= root.height();

        // attempt to keep square-ish by growing right when height is much greater than width
        boolean shouldGrowRight = canGrowRight && (root.height() >= (root.width() + w));
        // attempt to keep square-ish by growing down when width is much greater than height
        boolean shouldGrowDown = canGrowDown && (root.width() >= (root.height() + h));

        if (shouldGrowRight)
            return growRight(w, h);
        if (shouldGrowDown)
            return growDown(w, h);
        if (canGrowRight)
            return growRight(w, h);
        if (canGrowDown)
            return growDown(w, h);
        // need to ensure sensible root starting size to avoid this happening
        return null;
    }

    private PackerNode growRight(int w, int h) {
        root = new PackerNode()
            .markUsed()
            .setX(0)
            .setY(0)
            .setWidth(root.width() + w)
            .setHeight(root.height())
            .setDown(root)
            .setRight(new PackerNode()
                .setX(root.width())
                .setY(0)
                .setWidth(w)
                .setHeight(root.height()));
        PackerNode node;
        if ((node = findNode(root, w, h)) != null) {
            return splitNode(node, w, h);
        }
        return null;
    }

    private PackerNode growDown(int w, int h) {
        root = new PackerNode()
            .markUsed()
            .setX(0)
            .setY(0)
            .setWidth(root.width())
            .setHeight(root.height() + h)
            .setDown(new PackerNode()
                .setX(0)
                .setY(root.height())
                .setWidth(root.width())
                .setHeight(h))
            .setRight(root);
        PackerNode node;
        if ((node = findNode(root, w, h)) != null) {
            return splitNode(node, w, h);
        }
        return null;
    }
}
