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

import org.overrun.binpacking.internal.PackerFitPosImpl;

/**
 * The fit position of the {@link PackerRegion}.
 *
 * @author squid233
 * @since 0.1.0
 */
public interface PackerFitPos {
    /**
     * Creates the fit position.
     *
     * @param x the x position.
     * @param y the y position.
     * @return the position.
     */
    static PackerFitPos of(int x, int y) {
        return new PackerFitPosImpl(x, y);
    }

    /**
     * Gets the x position.
     *
     * @return the x position.
     */
    int x();

    /**
     * Gets the y position.
     *
     * @return the y position.
     */
    int y();
}
