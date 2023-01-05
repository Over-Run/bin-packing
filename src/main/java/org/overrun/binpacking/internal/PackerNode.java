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

import org.overrun.binpacking.PackerFitPos;

import java.util.Objects;
import java.util.StringJoiner;

/**
 * The node of the binary tree of the packer.
 *
 * @author squid233
 * @since 0.1.0
 */
public final class PackerNode implements PackerFitPos {
    private int x, y, width, height;
    private PackerNode right, down;
    private boolean used;

    /**
     * Sets x.
     *
     * @param x x.
     * @return this.
     */
    public PackerNode setX(int x) {
        this.x = x;
        return this;
    }

    /**
     * Sets y.
     *
     * @param y y.
     * @return this.
     */
    public PackerNode setY(int y) {
        this.y = y;
        return this;
    }

    /**
     * Sets width.
     *
     * @param width width.
     * @return this.
     */
    public PackerNode setWidth(int width) {
        this.width = width;
        return this;
    }

    /**
     * Sets height.
     *
     * @param height height.
     * @return this.
     */
    public PackerNode setHeight(int height) {
        this.height = height;
        return this;
    }

    /**
     * Sets right.
     *
     * @param right right.
     * @return this.
     */
    public PackerNode setRight(PackerNode right) {
        this.right = right;
        return this;
    }

    /**
     * Sets down.
     *
     * @param down down.
     * @return this.
     */
    public PackerNode setDown(PackerNode down) {
        this.down = down;
        return this;
    }

    /**
     * Gets the x position of this node.
     *
     * @return the x position.
     */
    @Override
    public int x() {
        return x;
    }

    /**
     * Gets the y position of this node.
     *
     * @return the y position.
     */
    @Override
    public int y() {
        return y;
    }

    /**
     * Gets the width of this node.
     *
     * @return the width.
     */
    public int width() {
        return width;
    }

    /**
     * Gets the height of this node.
     *
     * @return the height.
     */
    public int height() {
        return height;
    }

    /**
     * Gets the right node of this node.
     *
     * @return the right node.
     */
    public PackerNode right() {
        return right;
    }

    /**
     * Gets the down node of this node.
     *
     * @return the down node.
     */
    public PackerNode down() {
        return down;
    }

    /**
     * Marks this node as used.
     *
     * @return this.
     */
    public PackerNode markUsed() {
        used = true;
        return this;
    }

    /**
     * Returns {@code true} if this node is used, e.g. split or grew.
     *
     * @return {@code true} if this node is used, e.g. split or grew.
     */
    public boolean used() {
        return used;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PackerNode that = (PackerNode) o;
        return x == that.x &&
               y == that.y &&
               width == that.width &&
               height == that.height &&
               used == that.used &&
               Objects.equals(right, that.right) &&
               Objects.equals(down, that.down);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, width, height, right, down, used);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", PackerNode.class.getSimpleName() + "[", "]")
            .add("x=" + x)
            .add("y=" + y)
            .add("width=" + width)
            .add("height=" + height)
            .add("right=" + right)
            .add("down=" + down)
            .add("used=" + used)
            .toString();
    }
}
